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

        System.out.println("\n\nScheduler\tTotal Time\tService Sequence");
        System.out.println("----------------------------");
        System.out.println(fcfsServiceSequence.algorithmName + "\t\t" + fcfsServiceSequence.totalTime + "\t\t" + fcfsServiceSequence.servicingSequence);
        System.out.println(lookServiceSequence.algorithmName + "\t\t" + lookServiceSequence.totalTime + "\t\t\t" + lookServiceSequence.servicingSequence);
        System.out.println(sstfServiceSequence.algorithmName + "\t\t" + sstfServiceSequence.totalTime + "\t\t\t" + sstfServiceSequence.servicingSequence);

    }
}
