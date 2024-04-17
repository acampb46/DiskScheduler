/*
Ashlee Gerard
COSC 525
Disk Scheduler
17 April 2024
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DiskScheduler {
    public static void main(String[] args) throws FileNotFoundException {
        List<Request> requests = Scheduler.readInput();
        Result fcfsServiceSequence = Scheduler.fcfs(new ArrayList<>(requests));
        Result lookServiceSequence = Scheduler.look(new ArrayList<>(requests));
        Result sstfServiceSequence = Scheduler.sstf(new ArrayList<>(requests));

        System.out.println("Scheduler\tTotal Time\tService Sequence");
        System.out.println("----------------------------");
        System.out.println(fcfsServiceSequence.algorithmName + "\t" + fcfsServiceSequence.totalTime + "\t" + fcfsServiceSequence.servicingSequence);
        System.out.println(lookServiceSequence.algorithmName + "\t" + lookServiceSequence.totalTime + "\t" + lookServiceSequence.servicingSequence);
        System.out.println(sstfServiceSequence.algorithmName + "\t" + sstfServiceSequence.totalTime + "\t" + sstfServiceSequence.servicingSequence);

    }
}
