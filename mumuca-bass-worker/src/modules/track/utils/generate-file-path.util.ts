import { join } from "node:path"
import { randomUUID } from 'node:crypto';
import { Metadata } from '../interfaces/metadata';
import { Injectable, Logger } from '@nestjs/common';

@Injectable()
export class GenerateFilePathUtil {
  private readonly logger = new Logger(GenerateFilePathUtil.name);

  /**
   * Generates temporary and output file paths based on metadata and format.
   *
   * @param {Metadata} metadata - The metadata object containing file information.
   * @param {string} format - The file format (e.g., 'txt', 'jpg').
   * @returns {string[]} An array containing the temporary file path and the output file path.
   */
  execute(metadata: Metadata, format: string): string[] {
    const tempFilePath = join(__dirname, `temp_${metadata.title}_${randomUUID()}.${format}`);
    const outputFilePath = join(__dirname, `${metadata.title}.${format}`);

    this.logger.debug(`Temp file path generated: ${tempFilePath}`);
    this.logger.debug(`Output file path generated: ${outputFilePath}`);

    return [tempFilePath, outputFilePath];
  }
}