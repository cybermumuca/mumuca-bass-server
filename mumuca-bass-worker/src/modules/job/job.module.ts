import { JobController } from './job.controller';
import { LucidaModule } from '../lucida/lucida.module';
import { Module } from '@nestjs/common';
import { JobService } from './job.service';
import { AddMetadataToFileUtil } from './utils/add-metadata-to-file.util';
import { DetectAudioFormatUtil } from './utils/detect-audio-format.util';
import { DownloadImageUtil } from './utils/download-image.util';
import { GenerateFilePathUtil } from './utils/generate-file-path.util';
import { ServiceModule } from '../../service/service.module';
import { HttpModule } from '@nestjs/axios';

@Module({
  imports: [LucidaModule, ServiceModule, HttpModule],
  controllers: [JobController],
  providers: [
    JobService,
    AddMetadataToFileUtil,
    DetectAudioFormatUtil,
    DownloadImageUtil,
    GenerateFilePathUtil
  ],
})
export class JobModule {}