import { Module } from '@nestjs/common';
import { TrackDownloadSubscriber } from './subscribers/track-download.subscriber';
import { RabbitMQModule } from '@golevelup/nestjs-rabbitmq';
import { ConfigService } from '@nestjs/config';
import { AddMetadataToFileUtil } from './utils/add-metadata-to-file.util';
import { DetectAudioFormatUtil } from './utils/detect-audio-format.util';
import { DownloadImageUtil } from './utils/download-image.util';
import { GenerateFilePathUtil } from './utils/generate-file-path.util';
import { LucidaModule } from '../lucida/lucida.module';
import { HttpModule } from '@nestjs/axios';
import { TrackDownloadService } from './services/track-download.service';
import { NotifyDownloadStatusService } from './services/notify-download-status.service';
import { TrackDownloadStatusDeadLetterSubscriber } from './subscribers/track-download-status-dead-letter.subscriber';

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
          prefetchCount: 20, // TODO: use env var
        };
      },
      inject: [ConfigService],
    }),
    LucidaModule,
    HttpModule
  ],
  providers: [
    TrackDownloadSubscriber,
    TrackDownloadService,
    NotifyDownloadStatusService,
    TrackDownloadStatusDeadLetterSubscriber,
    AddMetadataToFileUtil,
    DetectAudioFormatUtil,
    DownloadImageUtil,
    GenerateFilePathUtil,
  ],
})
export class TrackModule {}