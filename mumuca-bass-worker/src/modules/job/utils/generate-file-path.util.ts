import { join } from "node:path"
import { randomUUID } from 'node:crypto';
import { Metadata } from '../interfaces/metadata';
import { Injectable } from '@nestjs/common';

@Injectable()
export class GenerateFilePathUtil {
  /**
   * Generates temporary and output file paths based on metadata and format.
   *
   * @param {Metadata} metadata - The metadata object containing file information.
   * @param {string} format - The file format (e.g., 'txt', 'jpg').
   * @returns {string[]} An array containing the temporary file path and the output file path.
   */
  execute(metadata: Metadata, format: string): string[] {
    console.log(__dirname, `temp_${metadata.title}_${randomUUID()}.${format}`)
    const tempFilePath = join(__dirname, `temp_${metadata.title}_${randomUUID()}.${format}`);
    const outputFilePath = join(__dirname, `${metadata.title}.${format}`);

    return [tempFilePath, outputFilePath];
  }
}