import { Metadata } from '../interfaces/metadata';
import ffmpeg from 'fluent-ffmpeg';
import path from 'node:path';
import { DownloadImageUtil } from './download-image.util';
import { Injectable } from '@nestjs/common';

@Injectable()
export class AddMetadataToFileUtil {
  constructor(private readonly downloadImageUtil: DownloadImageUtil) {}

  async execute(
    inputFilePath: string,
    outputFilePath: string,
    metadata: Metadata
  ) {
    return new Promise(async (resolve, reject) => {
      const ffmpegCommand = ffmpeg(inputFilePath)

      const outputOptions = this.getBasicMetadata(metadata);

      if (metadata.coverArtwork) {
        const imagePath = await this.getCover(metadata.coverArtwork);
        ffmpegCommand.input(imagePath);
        outputOptions.push('-map', '0:a', '-map', '1:v', '-c:v', 'mjpeg', '-id3v2_version', '3');
      }

      ffmpegCommand
        .outputOptions(...outputOptions)
        .save(outputFilePath)
        .on('end', resolve)
        .on('error', reject)
        .run();

      console.log(ffmpegCommand._getArguments());
    });
  }

  private getBasicMetadata(metadata: Metadata): string[] {
    const outputOptions: string[] = [];

    if (metadata.title) outputOptions.push(`-metadata`, `title=${metadata.title}`);
    if (metadata.artist) outputOptions.push(`-metadata`, `artist=${metadata.artist}`);
    if (metadata.album) outputOptions.push(`-metadata`, `album=${metadata.album}`);
    if (metadata.year) outputOptions.push(`-metadata`, `year=${metadata.year}`);
    if (metadata.trackNumber) outputOptions.push(`-metadata`, `track=${metadata.trackNumber}`);
    if (metadata.genre) outputOptions.push(`-metadata`, `genre=${metadata.genre}`);
    if (metadata.composer) outputOptions.push(`-metadata`, `composer=${metadata.composer}`);
    if (metadata.lyricist) outputOptions.push(`-metadata`, `lyricist=${metadata.lyricist}`);
    if (metadata.producer) outputOptions.push(`-metadata`, `producer=${metadata.producer}`);
    if (metadata.arranger) outputOptions.push(`-metadata`, `arranger=${metadata.arranger}`);
    if (metadata.performer) outputOptions.push(`-metadata`, `performer=${metadata.performer}`);
    if (metadata.comment) {
      outputOptions.push(`-metadata`, `comment=${Buffer.from(metadata.comment, 'utf-8').toString()}`);
      outputOptions.push(`-metadata:s`, `comment=${metadata.comment.replace(/(["\\])/g, '\\$1')}`);
    }
    if (metadata.copyright) outputOptions.push(`-metadata`, `copyright=${metadata.copyright}`);
    if (metadata.isrc) outputOptions.push(`-metadata`, `isrc=${metadata.isrc}`);

    return outputOptions
  }

  private async getCover(coverArtwork: string): Promise<string> {
    return new Promise(async (resolve, reject) => {
      try {
        let imagePath = path.join(__dirname, `temp-cover.jpg`);

        if (coverArtwork.startsWith('http')) {
          await this.downloadImageUtil.execute(coverArtwork, imagePath);
        } else {
          imagePath = coverArtwork;
        }

        resolve(imagePath);
      } catch (error) {
        reject(error);
      }
    });
  }
}