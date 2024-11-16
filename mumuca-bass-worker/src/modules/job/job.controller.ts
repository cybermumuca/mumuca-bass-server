import {JobService} from "./job.service";
import type {Response} from "express";
import {Body, Controller, Post, Res} from "@nestjs/common";
import { JobDTO } from './job.dto';

@Controller('job')
export class JobController {
    constructor(private readonly jobService: JobService) {}

    @Post('track/download')
    async handleTrackDownload(
        @Body() body: JobDTO,
        @Res() response: Response
    ) {
        const { jobId, trackId } = body;

        if (!jobId || !trackId) {
            return response.status(400).send({ error: "jobId and trackId are required" });
        }

        response.status(201).send({ jobId, trackId });

        await this.jobService.downloadTrack(jobId, trackId);
    }
}