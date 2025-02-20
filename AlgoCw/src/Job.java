class Job {
    String id;
    boolean isCompleted;

    Job(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Job id cannot be null or empty");
        }
        this.id = id;
        this.isCompleted = false;
    }
}

