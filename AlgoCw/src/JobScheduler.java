class JobScheduler{
    JobGraph jobGraph;
    int[] inDegree;
    Job[] zeroInDegreeQueue;
    int front,rear;


    JobScheduler(JobGraph jobGraph){
        this.jobGraph=jobGraph;
        initialize();
    }

    private void initialize(){
        Job[] jobs = jobGraph.getJobs();
        Job[][] dependencies = jobGraph.getDependencies();
        int n = jobs.length;

        inDegree = new int[n];
        zeroInDegreeQueue = new Job[n];
        front=0;
        rear=0;

        for (int i=0; i<n; i++){
            for (Job dependency : dependencies[i]){
                for (int j=0; j<n; j++){
                    if (jobs[j].id.equals(dependency.id)){
                        inDegree[j]++;
                    }
                }
            }
        }

        for (int i=0; i<n; i++){
            if (inDegree[i]==0){
                zeroInDegreeQueue[rear++]=jobs[i];
            }
        }
    }

    String[] scheduleJobs(){
        initialize();
        Job[] jobs= jobGraph.getJobs();
        Job[][] dependencies= jobGraph.getDependencies();
        int n = jobs.length;

        String[] result = new String[n];
        int index=0;
        while (front<rear){
            Job current = zeroInDegreeQueue[front++];
            result[index++]= current.id;
            current.isCompleted=true;

            int currentIndex=jobGraph.getJobIndex(current.id);

            for (Job dependency : dependencies[currentIndex]) {
                for (int j = 0; j < n; j++) {
                    if (jobs[j].id.equals(dependency.id)) {
                        inDegree[j]--;
                        if (inDegree[j] == 0) {
                            zeroInDegreeQueue[rear++] = jobs[j];
                        }
                    }
                }
            }
        }
        if (index != n) {
            throw new RuntimeException("There exists a cycle in the dependencies, scheduling not possible.");
        }
        return result;
    }

    Job getNextJob(){
        if (front<rear){
            return zeroInDegreeQueue[front];
        }else {
            return null;
        }
    }

    void executeNextJob(){
        if (front<rear){
            Job job = zeroInDegreeQueue[front++];
            job.isCompleted=true;
            System.out.println("Executed Job: "+job.id);

            Job[] jobs=jobGraph.getJobs();
            Job[][] dependencies = jobGraph.getDependencies();
            int n = jobs.length;

            for (int i=0; i<n; i++){
                for (Job dependency : dependencies[i]){
                    if (dependency.id.equals(job.id)){
                        inDegree[i]--;
                        if (inDegree[i]==0){
                            zeroInDegreeQueue[rear++]=jobs[i];
                        }
                    }
                }
            }
        }else{
            System.out.println("No job available to execute");
        }
    }
    void printJobStatus() {
        Job[] jobs = jobGraph.getJobs();
        for (Job job : jobs) {
            System.out.println("Job ID: " + job.id + ", Completed: " + job.isCompleted);
        }
    }
}
