import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        JobGraph jobGraph = new JobGraph(new Job[]{});
        JobScheduler scheduler = new JobScheduler(jobGraph);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add Job");
            System.out.println("2. Add Dependency");
            System.out.println("3. Schedule Jobs");
            System.out.println("4. Execute Next Job");
            System.out.println("5. Print Job Status");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    String jobId;
                    while (true) {
                        System.out.print("Enter job id (numeric): ");
                        jobId = scanner.nextLine();
                        if (jobId.matches("\\d+")) {
                            try {
                                Job job = new Job(jobId);
                                jobGraph.addJob(job);
                                System.out.println("Job " + jobId + " added.");
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a numeric job ID.");
                        }
                    }
                    break;
                case 2:
                    String jobId1, dependencyId;
                    while (true) {
                        System.out.print("Enter job id (numeric): ");
                        jobId1 = scanner.nextLine();
                        if (jobId1.matches("\\d+")) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a numeric job ID.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter dependency job id (numeric): ");
                        dependencyId = scanner.nextLine();
                        if (dependencyId.matches("\\d+")) {
                            try {
                                int jobIndex = jobGraph.getJobIndex(jobId1);
                                int depIndex = jobGraph.getJobIndex(dependencyId);
                                jobGraph.addDependency(jobId1, dependencyId);
                                System.out.println("Dependency added: " + jobId1 + " depends on " + dependencyId);
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                                System.out.println("Dependency not added: Make sure both job IDs exist.");
                            }
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a numeric dependency job ID.");
                        }
                    }
                    break;
                case 3:
                    try {
                        String[] scheduledJobs = scheduler.scheduleJobs();
                        System.out.println("Scheduled Jobs: ");
                        for (String id : scheduledJobs) {
                            System.out.print(id + " ");
                        }
                        System.out.println();
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    Job nextJob = scheduler.getNextJob();
                    if (nextJob != null) {
                        System.out.println("Next job to execute: " + nextJob.id);
                        scheduler.executeNextJob();
                    } else {
                        System.out.println("No job available to execute");
                    }
                    break;
                case 5:
                    scheduler.printJobStatus();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
