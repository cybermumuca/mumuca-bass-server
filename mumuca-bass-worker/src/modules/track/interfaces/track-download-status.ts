export interface TrackDownloadStatus {
  jobId: string;
  status: "IN_PROGRESS" | "COMPLETED" | "FAILED";
}