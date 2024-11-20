import { Module } from '@nestjs/common';
import { TrackDownloadSubscriber } from './track-download.subscriber';
import { RabbitMQModule } from '@golevelup/nestjs-rabbitmq';
import { ConfigService } from '@nestjs/config';
import { AddMetadataToFileUtil } from './utils/add-metadata-to-file.util';
import { DetectAudioFormatUtil } from './utils/detect-audio-format.util';
import { DownloadImageUtil } from './utils/download-image.util';
import { GenerateFilePathUtil } from './utils/generate-file-path.util';
import { LucidaModule } from '../lucida/lucida.module';
import { ServiceModule } from '../../service/service.module';
import { HttpModule } from '@nestjs/axios';
import { TrackDownloadService } from './track-download.service';

@Module({
  imports: [
    RabbitMQModule.forRootAsync(RabbitMQModule, {
      useFactory: (configService: ConfigService) => {
        const user = configService.get<string>('rabbitmq.connection.user');
        const password = configService.get<string>('rabbitmq.connection.password');
        const host = configService.get<string>('rabbitmq.connection.host');
        const port = configService.get<string>('rabbitmq.connection.port');

        const uri = `amqp://${user}:${password}@${host}:${port}`;

        return {
          uri: [uri],
          connectionInitOptions: { wait: true },
          prefetchCount: 5,
        };
      },
      inject: [ConfigService],
    }),
    LucidaModule,
    ServiceModule,
    HttpModule
  ],
  providers: [
    TrackDownloadSubscriber,
    TrackDownloadService,
    AddMetadataToFileUtil,
    DetectAudioFormatUtil,
    DownloadImageUtil,
    GenerateFilePathUtil
  ],
})
export class TrackModule {}