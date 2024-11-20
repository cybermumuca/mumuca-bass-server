import { Injectable, Logger } from '@nestjs/common';
import { Nack, RabbitSubscribe } from '@golevelup/nestjs-rabbitmq';
import { TrackDownloadMessage } from './interfaces/track-download';
import { TrackDownloadService } from './track-download.service';

@Injectable()
export class TrackDownloadSubscriber {
  private readonly logger = new Logger(TrackDownloadSubscriber.name);

  constructor(private readonly trackDownloadService: TrackDownloadService) {}

  @RabbitSubscribe({
    exchange: 'track-exchange',
    routingKey: 'track.download',
    queue: 'track-download-queue',
    createQueueIfNotExists: false,
    allowNonJsonMessages: true,
  })
  async handleTrackDownload(message: TrackDownloadMessage,) {
    this.logger.debug(`Received message: ${JSON.stringify(message)}`);

    try {
      await this.trackDownloadService.downloadTrack(message.jobId, message.trackId);
    } catch {
      return new Nack(true);
    }
  }
}