import { Readable } from 'node:stream';
import { Injectable } from '@nestjs/common';
import { dynamicImport } from '../../../dynamic-import';

@Injectable()
export class DetectAudioFormatUtil {
  async execute(stream: Readable): Promise<string> {
    const { fileTypeFromStream } = await dynamicImport('file-type');

    const type = await fileTypeFromStream(stream);

    if (!type) {
      throw new Error("Unknown format");
    }

    return type.ext
  }
}