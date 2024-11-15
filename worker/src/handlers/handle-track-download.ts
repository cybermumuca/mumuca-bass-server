import {FastifyReply, FastifyRequest} from "fastify";
import {lucida} from "../libs/lucida";
import {notifyJobStatus} from "../services/main-server";
import {detectAudioFormat} from "../utils/detect-audio-format";
import fs from "node:fs";
import {addMetadataToFile} from "../utils/add-metadata-to-file";
import {Metadata} from "../metadata";
import {generateFilePath} from "../utils/generate-file-path";
import {PassThrough} from "node:stream";

export async function handleTrackDownload(
    request: FastifyRequest,
    reply: FastifyReply,
) {
    const { jobId, trackId } = request.body as { jobId: string, trackId: string };

    if (!jobId || !trackId) {
        return reply.status(400).send({ error: "jobId and trackId are required" });
    }

    reply.status(201).send({ jobId, trackId });

    notifyJobStatus(jobId, "IN_PROGRESS");

    console.log("Processing...")

    try {
        const track = await lucida.getByUrl(`https://www.deezer.com/track/${trackId}`);

        if (track.type !== "track" || !("getStream" in track)) {
            notifyJobStatus(jobId, "FAILED");
            return;
        }

        const {stream} = await track.getStream();

        const passThroughStream = new PassThrough();

        stream.pipe(passThroughStream);

        const createStreamClone = () => {
            const clone = new PassThrough();
            passThroughStream.pipe(clone);
            return clone;
        };

        const format = await detectAudioFormat(createStreamClone());
        console.log("Format detected:", format);

        const buffer = await new Promise<Buffer>((resolve, reject) => {
            const chunks: Buffer[] = [];
            const clone = createStreamClone();
            clone.on("data", (chunk) => chunks.push(chunk));
            clone.on("end", () => {
                const finalBuffer = Buffer.concat(chunks);
                if (finalBuffer.length === 0) {
                    reject(new Error("Buffer is empty, possibly incomplete stream"));
                } else {
                    resolve(finalBuffer);
                }
            });
            clone.on("error", reject);
        });
        console.log("Buffer read:", buffer);

        const metadata: Metadata = {
            title: track.metadata.title,
            artist: track.metadata.artists.map(({name}) => name).join(", "),
            album: track.metadata.album?.title,
            year: track.metadata.releaseDate?.getFullYear(),
            trackNumber: track.metadata.trackNumber,
            genre: track.metadata.genres?.join(", "),
            composer: track.metadata.composers?.join(", "),
            lyricist: track.metadata.lyricists?.join(", "),
            producer: track.metadata.producers?.join(", "),
            arranger: track.metadata.engineers?.join(", "),
            performer: track.metadata.performers?.join(", "),
            comment: track.metadata.description?.concat(" - ", "Downloaded from Mumuca Bass") || "Downloaded from Mumuca Bass",
            copyright: track.metadata.copyright,
            isrc: track.metadata.isrc,
            coverArtwork: track.metadata.coverArtwork?.[0].url,
            discNumber: track.metadata.discNumber,
        };
        console.log("Metadata:", metadata);

        const [tempFilePath, outputFilePath] = generateFilePath(metadata, format);
        console.log("Paths:", tempFilePath, outputFilePath);

        await fs.promises.writeFile(tempFilePath, buffer)
        console.log("Generated:", tempFilePath)

        addMetadataToFile(tempFilePath, outputFilePath, metadata, format)
            .then(() => {
                console.log(`Arquivo salvo com metadados em ${outputFilePath}`);
                fs.unlinkSync(tempFilePath);
                notifyJobStatus(jobId, "COMPLETED");
            })
            .catch((error) => {
                console.error("Erro ao adicionar metadados:", error.message);
                fs.unlinkSync(tempFilePath);
                notifyJobStatus(jobId, "FAILED");
            })
    } catch (error) {
        console.error("Error downloading track:", error);
        notifyJobStatus(jobId, "FAILED");
    }
}