import { AmqpConnection, RabbitSubscribe } from '@golevelup/nestjs-rabbitmq';
import { TrackDownloadStatus } from '../interfaces/track-download-status';
import { Injectable, Logger } from '@nestjs/common';
import { Message } from 'amqplib';
import { setTimeout as sleep } from "node:timers/promises"

@Injectable()
export class TrackDownloadStatusDeadLetterSubscriber {
  private readonly logger = new Logger(TrackDownloadStatusDeadLetterSubscriber.name);

  constructor(private readonly amqpConnection: AmqpConnection) {}

  // TODO: Improve
  @RabbitSubscribe({
    exchange: 'track-dead-letter-exchange',
    routingKey: 'track.download.status.dlq',
    queue: 'track-download-status-dead-letter-queue',
    createQueueIfNotExists: false,
    allowNonJsonMessages: true,
  })
  async retrySendDownloadStatus(message: TrackDownloadStatus, headers: Message) {
    const xDeathHeader = headers.properties.headers["x-death"];

    const retries = !xDeathHeader || xDeathHeader.length === 0 ? 0 : xDeathHeader[0].count;

    const MAX_RETRIES = 3;
    const DELAY_MS = 5000;

    if (retries < MAX_RETRIES) {
      this.logger.log(`Retrying to send download status for job ${message.jobId}...`);

      try {
        if (xDeathHeader[0].reason === 'expired') {
          await sleep(DELAY_MS);
        }

        await this.amqpConnection.publish(
          'track-exchange',
          'track.download.status',
          message,
          { deliveryMode: 2, persistent: true }
        );

        this.logger.log(`Download status sent successfully: jobId=${message.jobId}, status=${message.status}`);
      } catch (error) {
        this.logger.error(`Error sending status for job ${message.jobId}: ${error.message}`);
      }
    } else {
      this.logger.error(`Max retries reached for sent track download status ${message.jobId}. Discarding message.`);
    }
  }
}