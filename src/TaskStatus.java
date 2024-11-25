public enum TaskStatus {
    TODO, IN_PROGRESS, DONE;

    @Override
    public String toString() {
        return name().replace("_", "-").toLowerCase();
    }
}