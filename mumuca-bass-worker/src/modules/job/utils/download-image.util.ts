import { HttpService } from '@nestjs/axios';
import { Injectable } from '@nestjs/common';
import fs from 'node:fs';

@Injectable()
export class DownloadImageUtil {
  constructor(private readonly httpService: HttpService) {}

  async execute(url: string, outputPath: string): Promise<void> {
    const response = await this.httpService.axiosRef.get(url, { responseType: 'stream' });

    const fileStream = fs.createWriteStream(outputPath);

    return new Promise((resolve, reject) => {
      response.data.pipe(fileStream);

      fileStream.on('finish', () => {
        fileStream.close((err) => {
          err ? reject(err) : resolve();
        });
      });

      fileStream.on('error', reject);
    });
  }
}