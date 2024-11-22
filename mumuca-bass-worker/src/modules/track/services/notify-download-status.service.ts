import { Injectable, Logger } from '@nestjs/common';
import { AmqpConnection } from '@golevelup/nestjs-rabbitmq';
import { TrackDownloadStatus } from '../interfaces/track-download-status';

@Injectable()
export class NotifyDownloadStatusService {
  private readonly logger = new Logger(NotifyDownloadStatusService.name);

  constructor(private readonly amqpConnection: AmqpConnection) {}

  async notifyDownloadStatus({ jobId, status }: TrackDownloadStatus) {
    try {
      const statusMessage = {
        jobId,
        status,
      }

      await this.amqpConnection.publish('track-exchange', 'track.download.status', statusMessage, { deliveryMode: 2, persistent: true });

      this.logger.log(`Download status sent successfully: jobId=${jobId}, status=${status}`);
    } catch (error) {
      this.logger.error(`Error sending status ${status} for job ${jobId}: ${error.message}`);
    }
  }
}