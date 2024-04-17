/*
Ashlee Gerard
COSC 525
Disk Scheduler
17 April 2024
 */

import java.util.List;

public class Result {
    String algorithmName;
    int totalTime;
    List<Integer> servicingSequence;

    public Result(String algorithmName, int totalTime, List<Integer> servicingSequence) {
        this.algorithmName = algorithmName;
        this.totalTime = totalTime;
        this.servicingSequence = servicingSequence;
    }
}
