import { Logger, Module } from '@nestjs/common';
import { LucidaModule } from './modules/lucida/lucida.module';
import { JobModule } from './modules/job/job.module';
import { ConfigModule } from '@nestjs/config';
import configuration from './config/configuration';

@Module({
  imports: [
    ConfigModule.forRoot({
      load: [configuration],
      isGlobal: true,
    }),
    LucidaModule,
    JobModule
  ],
  controllers: [],
  providers: [Logger],
})
export class AppModule {}
