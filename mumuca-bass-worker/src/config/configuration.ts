export default () => ({
  worker: {
    port: Number(process.env.WORKER_PORT),
    host: process.env.WORKER_HOST || 'localhost'
  },
  lucida: {
    deezer: {
      login: process.env.DEEZER_LOGIN,
      password: process.env.DEEZER_PASSWORD
    }
  }
});