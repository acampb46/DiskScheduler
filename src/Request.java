/*
Ashlee Gerard
COSC 525
Disk Scheduler
17 April 2024
 */

public class Request {
    int numTracks;
    int trackNum;
    int submissionTime;

    public Request(int numTracks, int submissionTime, int trackNum) {
        this.numTracks = numTracks;
        this.submissionTime = submissionTime;
        this.trackNum = trackNum;
    }
}