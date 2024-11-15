import "dotenv/config";
import fastify from "fastify";
import {connectLucida, disconnectLucida} from "./libs/lucida";
import {handleTrackDownload} from "./handlers/handle-track-download";

connectLucida()

export const app = fastify({
    caseSensitive: true,
});

app.post('/job/track/download', handleTrackDownload)

process.on("SIGINT", async () => {
    console.log("Closing HTTP Server...")
    await app.close()
    console.log("Disconnecting from Lucida...")
    await disconnectLucida()
})