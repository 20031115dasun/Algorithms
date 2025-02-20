public class JobGraph {
    Job[] jobs;
    Job[][] dependencies;

    JobGraph(Job[] jobs){
        this.jobs = jobs;
        this.dependencies=new Job[jobs.length][];
        for(int i=0;i<jobs.length;i++){
            this.dependencies[i] = new Job[0];
        }
    }

    void addJob(Job job){
        if (job==null ||job.id==null||job.id.trim().isEmpty()){
            System.out.println("Invalid Job, Job Id cannot be null or empty");
        }

        int n=jobs.length;
        Job[] newJobs = new Job[n+1];
        for (int i=0; i<n; i++){
            newJobs[i] = jobs[i];
        }
        newJobs[n] =job;
        jobs=newJobs;

        Job[][] newDependencies = new Job[n+1][];
        for (int i=0; i<n; i++){
            newDependencies[i]=dependencies[i];
        }
        newDependencies[n]=new Job[0];
        dependencies=newDependencies;
    }

    void addDependency(String jobId,String dependencyId){
        if (jobId==null || jobId.trim().isEmpty()||dependencyId==null || dependencyId.trim().isEmpty()){
            System.out.println("Invalid Job, Job Id cannot be null or empty");
        }
        int jobIndex = -1,depIndex=-1;
        for (int i=0; i<jobs.length; i++){
            if (jobs[i].id.equals(jobId)) jobIndex=i;
            if (jobs[i].id.equals(dependencyId)) depIndex=i;
        }
        if (jobIndex==-1 || depIndex ==-1) {
            System.out.println("Invalid Input,  Id not found");
            return;
        }

        Job[] newDependencies = new Job[dependencies[jobIndex].length+1];
        for (int i=0; i< dependencies[jobIndex].length;i++){
            newDependencies[i]=dependencies[jobIndex][i];
        }
        newDependencies[newDependencies.length-1]=jobs[depIndex];
        dependencies[jobIndex]=newDependencies;
    }

    Job[] getJobs(){
        return jobs;
    }

    Job[][] getDependencies(){
        return dependencies;
    }

    int getJobIndex(String jobId) {
        for (int i = 0; i < jobs.length; i++) {
            if (jobs[i].id.equals(jobId)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Job ID not found: " + jobId);
    }
}

