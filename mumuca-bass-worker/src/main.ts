import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { ConfigService } from '@nestjs/config';
import { Logger } from '@nestjs/common';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.enableShutdownHooks();

  const configService = app.get(ConfigService);

  const port = configService.get<number>("worker.port");

  await app.listen(port);

  new Logger("Applicaton").log(`Worker started in ${await app.getUrl()}`);
}

bootstrap();
