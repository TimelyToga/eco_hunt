package tasks;

/**
 * Created by Tim on 14/12/14.
 */
public abstract class Task {

    private int taskType;

    public abstract void executeTask();

    public abstract void cancelTask();
}
