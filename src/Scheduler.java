/*
Ashlee Gerard
COSC 525
Disk Scheduler
17 April 2024
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Scheduler {
    List<Request> requests;
    List<Request> serviceSequence;
    int numTracks;

    public Scheduler(List<Request> requests, int numTracks) {
        this.requests = requests;
        this.numTracks = numTracks;
        this.serviceSequence = new ArrayList<>();
    }

    static List<Request> readInput() throws FileNotFoundException {
        Scanner myScan = new Scanner(new File("src/input.txt"));

        int tracks = myScan.nextInt();
        List<Request> requests = new ArrayList<>();
        while (myScan.hasNextLine()) {
            int timeElapsed = myScan.nextInt();
            int trackRequested = myScan.nextInt();
            requests.add(new Request(tracks, timeElapsed, trackRequested));
        }
        return requests;
    }

    static Result fcfs(List<Request> requests) {
        List<Integer> servicingSequence = new ArrayList<>();
        int totalRunTime = 0;
        int currentHead = 0;
        int seekTime = 1;

        System.out.println("========================================================");
        System.out.println("FCFS");
        System.out.println("========================================================");

        for (Request req : requests) {
            int requestPosition = req.trackNum;
            int seekDistance = Math.abs(requestPosition - currentHead);
            int serviceTime = seekTime * seekDistance;
            totalRunTime += serviceTime;
            currentHead = requestPosition;
            servicingSequence.add(req.trackNum);
            System.out.printf("[Time = %d] Servicing request %d. \n", totalRunTime, req.trackNum);
        }
        System.out.printf("Total Runtime = %d ms. \n", totalRunTime);
        System.out.println("Servicing Sequence was: " + servicingSequence);
        return new Result("FCFS", totalRunTime, servicingSequence);
    }

    static Result look(List<Request> requests) {
        List<Integer> servicingSequence = new ArrayList<>();
        List<Request> remainingRequests = new ArrayList<>();
        boolean movingUp = true;
        int currentHead = 0;
        int totalTime = 0;
        int totalTracks = requests.get(0).numTracks;
        int timeThreshold = requests.get(1).submissionTime;
        int currentTime = 0;

        System.out.println("========================================================");
        System.out.println("LOOK");
        System.out.println("========================================================");

        remainingRequests.add(requests.get(0));
        requests.remove(0);
        while (!requests.isEmpty() || !remainingRequests.isEmpty()) {
            if (currentTime >= timeThreshold && !requests.isEmpty()) {
                while (currentTime > timeThreshold) {
                    remainingRequests.add(requests.get(0));
                    requests.remove(0);
                    currentTime -= timeThreshold;
                }
            }

            Request closest = findClosestRequestInDirection(currentHead, remainingRequests, movingUp);
            if (closest == null) {
                movingUp = !movingUp;
                closest = findClosestRequestInDirection(currentHead, remainingRequests, movingUp);
            }
            int serviceTime = Math.abs(closest.trackNum - currentHead);
            totalTime += serviceTime;
            currentHead = closest.trackNum;
            System.out.printf("[Time = %d] Servicing request %d. \n", totalTime, closest.trackNum);
            servicingSequence.add(closest.trackNum);
            remainingRequests.remove(closest);

            currentTime += serviceTime;

            if (currentHead == totalTracks - 1 || currentHead == 0) {
                movingUp = !movingUp;
            }
        }
        System.out.println("Total time taken: " + totalTime);
        System.out.printf("Servicing Sequence was: " + servicingSequence);
        return new Result("LOOK", totalTime, servicingSequence);
    }

    static Result sstf(List<Request> requests) {
        List<Integer> servicingSequence = new ArrayList<>();
        List<Request> remainingRequests = new ArrayList<>();
        int totalTime = 0;
        int currentHead = 0;

        System.out.println("\n========================================================");
        System.out.println("SSTF");
        System.out.println("========================================================");

        int timeThreshold = requests.get(1).submissionTime;
        int currentTime = 0;

        remainingRequests.add(requests.get(0));
        requests.remove(0);
        while (!requests.isEmpty() || !remainingRequests.isEmpty()) {
            if (currentTime >= timeThreshold && !requests.isEmpty()) {
                while (currentTime > timeThreshold) {
                    remainingRequests.add(requests.get(0));
                    requests.remove(0);
                    currentTime -= timeThreshold;
                }
            }

            Request closest = findClosestRequest(currentHead, remainingRequests);
            int serviceTime = Math.abs(closest.trackNum - currentHead);
            totalTime += serviceTime;
            currentHead = closest.trackNum;
            System.out.printf("[Time = %d] Servicing request %d. \n", totalTime, closest.trackNum);
            servicingSequence.add(closest.trackNum);
            remainingRequests.remove(closest);

            currentTime += serviceTime;
        }

        System.out.println("Total time taken: " + totalTime);
        System.out.printf("Servicing Sequence was: " + servicingSequence);
        return new Result("SSTF", totalTime, servicingSequence);
    }

    private static Request findClosestRequest(int currentHead, List<Request> remainingRequests) {
        Request closestRequest = remainingRequests.get(0);
        int minDistance = Math.abs(closestRequest.trackNum - currentHead);

        for (Request request : remainingRequests) {
            int distance = Math.abs(request.trackNum - currentHead);
            if (distance < minDistance) {
                minDistance = distance;
                closestRequest = request;
            }
        }
        return closestRequest;
    }

    public static Request findClosestRequestInDirection(int currentHead, List<Request> remainingRequests, boolean movingUp) {
        Request closest = null;
        int minDistance = Integer.MAX_VALUE;
        for (Request request : remainingRequests) {
            if ((movingUp && request.trackNum >= currentHead) || (!movingUp && request.trackNum <= currentHead)) {
                int distance = Math.abs(request.trackNum - currentHead);
                if (distance < minDistance) {
                    minDistance = distance;
                    closest = request;
                }
            }
        }
        return closest;
    }
}
