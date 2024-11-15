import axios from "axios";

async function notifyJobStatus(jobId: string, status: "IN_PROGRESS" | "COMPLETED" | "FAILED", maxRetries = 5, delayMs = 5000, attempt = 1) {
    try {
        await axios.post("http://localhost:8080/api/v1/jobs/status", {
            jobId,
            status,
        });
    } catch (error) {
        console.error(`Error sending status (attempt ${attempt} of ${maxRetries}):`, error.message);

        if (attempt < maxRetries) {
            await new Promise(resolve => setTimeout(resolve, delayMs));
            return notifyJobStatus(jobId, status, maxRetries, delayMs, attempt + 1);
        }

        console.error(`Failed to notify status after ${maxRetries} attempts.`);
    }
}

export {
    notifyJobStatus
}