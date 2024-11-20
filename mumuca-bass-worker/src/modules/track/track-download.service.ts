import { writeFile } from 'node:fs/promises';
import fs from 'node:fs';
import { Injectable, Logger } from '@nestjs/common';
import { LucidaModule } from '../lucida/lucida.module';
import { PassThrough } from 'node:stream';
import { Metadata } from './interfaces/metadata';
import { NotifyJobStatusService } from '../../service/notify-job-status.service';
import { DetectAudioFormatUtil } from './utils/detect-audio-format.util';
import { GenerateFilePathUtil } from './utils/generate-file-path.util';
import { AddMetadataToFileUtil } from './utils/add-metadata-to-file.util';

@Injectable()
export class TrackDownloadService {
  private readonly logger = new Logger(TrackDownloadService.name);

  constructor(
    private readonly notifyJobStatusService: NotifyJobStatusService,
    private readonly lucidaModule: LucidaModule,
    private readonly detectAudioFormatUtil: DetectAudioFormatUtil,
    private readonly generateFilePathUtil: GenerateFilePathUtil,
    private readonly addMetadataToFileUtil: AddMetadataToFileUtil
  ) {}

  async downloadTrack(jobId: string, trackId: string) {
    this.logger.log(`Downloading track ${trackId} for job ${jobId}...`);

    await this.notifyJobStatusService.notifyJobStatus(jobId, "IN_PROGRESS");

    try {
      const track = await this.lucidaModule.getLucidaInstance()
        .getByUrl(`https://www.deezer.com/track/${trackId}`);

      if (track.type !== "track" || !("getStream" in track)) {
        await this.notifyJobStatusService.notifyJobStatus(jobId, "FAILED");
        return;
      }

      const { stream } = await track.getStream();

      const passThroughStream = new PassThrough();

      stream.pipe(passThroughStream);

      const createStreamClone = () => {
        const clone = new PassThrough();
        passThroughStream.pipe(clone);
        return clone;
      };

      const format = await this.detectAudioFormatUtil.execute(createStreamClone());
      this.logger.debug(`Format detected: ${format}`);

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
      this.logger.debug(`Buffer read`);

      const metadata: Metadata = {
        title: track.metadata.title,
        artist: track.metadata.artists.map(({ name }) => name).join(", "),
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
      }
      this.logger.debug(`Metadata: ${JSON.stringify(metadata)}`);

      const [tempFilePath, outputFilePath] = this.generateFilePathUtil.execute(metadata, format);
      this.logger.debug(`Temp file path generated: ${tempFilePath}`);
      this.logger.debug(`Output file path generated: ${outputFilePath}`);

      await writeFile(tempFilePath, buffer);
      this.logger.debug(`Generated file: ${tempFilePath}`);

      this.addMetadataToFileUtil.execute(tempFilePath, outputFilePath, metadata)
        .then(async () => {
          this.logger.debug(`File saved with metadata in ${outputFilePath}`);
          fs.unlinkSync(tempFilePath);
          await this.notifyJobStatusService.notifyJobStatus(jobId, "COMPLETED");
        })
        .catch(async (error) => {
          this.logger.error(`Error adding metadata: ${error.message}`);
          fs.unlinkSync(tempFilePath);
          await this.notifyJobStatusService.notifyJobStatus(jobId, "FAILED");
        })
    } catch (error) {
      this.logger.error(`Error downloading track ${trackId} for job ${jobId}: ${error}`);
      await this.notifyJobStatusService.notifyJobStatus(jobId, "FAILED");
    }
  }
}