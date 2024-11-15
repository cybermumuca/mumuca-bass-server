import { Readable } from 'node:stream';

export async function detectAudioFormat(stream: Readable): Promise<string> {
    const { fileTypeFromStream } = await import('file-type');

    const type = await fileTypeFromStream(stream);

    if (!type) {
        throw new Error("Unknown format");
    }

    return type.ext
}
