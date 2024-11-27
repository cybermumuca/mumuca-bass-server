import { Readable } from 'node:stream';
import { Injectable, Logger } from '@nestjs/common';
import { dynamicImport } from '../../../dynamic-import';

@Injectable()
export class DetectAudioFormatUtil {
  private readonly logger = new Logger(DetectAudioFormatUtil.name);

  async execute(stream: Readable): Promise<string> {
    const { fileTypeFromStream } = await dynamicImport('file-type');

    const type = await fileTypeFromStream(stream);

    if (!type) {
      throw new Error("Unknown format");
    }

    this.logger.debug(`Format detected: ${type.ext}`);

    return type.ext
  }
}