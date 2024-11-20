export default () => ({
  lucida: {
    deezer: {
      login: process.env.DEEZER_LOGIN,
      password: process.env.DEEZER_PASSWORD
    }
  },
  rabbitmq: {
    connection: {
      user: process.env.RABBITMQ_USER,
      password: process.env.RABBITMQ_PASSWORD,
      host: process.env.RABBITMQ_HOST,
      port: process.env.RABBITMQ_PORT
    },
    queues: {
      trackDownloadQueue: process.env.TRACK_DOWNLOAD_QUEUE
    },
    routingKeys: {
      trackDownload: process.env.TRACK_DOWNLOAD_ROUTING_KEY
    }
  }
});