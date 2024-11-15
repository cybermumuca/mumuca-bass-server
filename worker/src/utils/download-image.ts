import fs from "node:fs";
import axios from "axios";

export async function downloadImage(url: string, outputPath: string): Promise<void> {
    const response = await axios.get(url, { responseType: 'stream' });

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