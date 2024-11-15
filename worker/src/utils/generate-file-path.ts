import path from "node:path";
import {randomUUID} from "node:crypto";
import {Metadata} from "../metadata";

/**
 * Generates temporary and output file paths based on metadata and format.
 *
 * @param {Metadata} metadata - The metadata object containing file information.
 * @param {string} format - The file format (e.g., 'txt', 'jpg').
 * @returns {string[]} An array containing the temporary file path and the output file path.
 */
export function generateFilePath(metadata: Metadata, format: string): string[] {
    const tempFilePath = path.join(__dirname, `temp_${metadata.title}_${randomUUID()}.${format}`);
    const outputFilePath = path.join(__dirname, `${metadata.title}.${format}`);

    return [tempFilePath, outputFilePath];
}