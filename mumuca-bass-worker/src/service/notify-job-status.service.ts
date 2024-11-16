import { HttpService } from '@nestjs/axios';
import { Injectable, Logger } from '@nestjs/common';

@Injectable()
export class NotifyJobStatusService {
  private readonly logger = new Logger(NotifyJobStatusService.name);

  constructor(private readonly httpService: HttpService) {}

  async notifyJobStatus(
    jobId: string,
    status: 'IN_PROGRESS' | 'COMPLETED' | 'FAILED',
    maxRetries = 5,
    delayMs = 5000,
    attempt = 1
  ): Promise<void> {
    try {
      await this.httpService.axiosRef.post('http://localhost:8080/api/v1/jobs/status', {
        jobId,
        status,
      });

      this.logger.verbose(`Job status sent successfully: jobId=${jobId}, status=${status}`);
    } catch (error) {
      this.logger.error(`Error sending status (attempt ${attempt} of ${maxRetries}): ${error.message}`);

      if (attempt < maxRetries) {
        await new Promise(resolve => setTimeout(resolve, delayMs));
        return this.notifyJobStatus(jobId, status, maxRetries, delayMs, attempt + 1);
      }

      this.logger.error(`Failed to notify status after ${maxRetries} attempts.`);
    }
  }
}
