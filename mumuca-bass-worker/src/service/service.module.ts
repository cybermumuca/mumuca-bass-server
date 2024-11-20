import { Module } from '@nestjs/common';
import { HttpModule } from '@nestjs/axios';
import { NotifyJobStatusService } from './notify-job-status.service';

@Module({
  imports: [
    HttpModule.register({
      timeout: 5000,
      maxRedirects: 5,
    }),
  ],
  providers: [NotifyJobStatusService],
  exports: [NotifyJobStatusService],
})
export class ServiceModule {}