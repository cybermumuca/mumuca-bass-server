import {app} from "./app";

const PORT = Number(process.env.WORKER_PORT)

function start() {
    app.listen({
        port: PORT,
    }, () => {
        console.log(`Worker is running on port ${PORT}`)
    })
}

try {
    start()
} catch (error) {
    console.error("Failed to start worker", error)
    process.exit(1)
}