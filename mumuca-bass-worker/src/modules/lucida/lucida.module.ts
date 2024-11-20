import { Logger, Module, OnApplicationShutdown, OnModuleInit } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import {dynamicImport} from '../../dynamic-import';

@Module({
  providers: [LucidaModule],
  exports: [LucidaModule],
})
export class LucidaModule implements OnModuleInit, OnApplicationShutdown {
  private readonly logger = new Logger(LucidaModule.name);
  private lucida: any;

  constructor(private readonly configService: ConfigService) {}

  async onModuleInit() {
    this.logger.log('Trying to connect to Lucida...');

    const deezerLogin = this.configService.get<string>('DEEZER_LOGIN');
    const deezerPassword = this.configService.get<string>('DEEZER_PASSWORD');

    const Lucida = (await dynamicImport("lucida")).default;
    // @ts-ignore
    const Deezer = (await dynamicImport('lucida/streamers/deezer/main.js')).default;

    this.lucida = new Lucida({
      modules: {
        deezer: new Deezer({}),
      },
      logins: {
        deezer: {
          username: deezerLogin,
          password: deezerPassword,
        },
      },
    });

    await this.lucida.login();
    this.logger.log('Successfully connected to Lucida.');
  }

  async onApplicationShutdown() {
    this.logger.log('Disconnecting from Lucida...');
    await this.lucida.disconnect();
    this.logger.log('Lucida disconnected.');
  }

  getLucidaInstance() {
    return this.lucida;
  }
}