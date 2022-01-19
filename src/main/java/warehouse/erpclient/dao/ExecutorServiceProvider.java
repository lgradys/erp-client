package warehouse.erpclient.dao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum ExecutorServiceProvider {

    INSTANCE;

    private final ExecutorService executorService;

    ExecutorServiceProvider() {
        executorService = Executors.newCachedThreadPool(
                r -> {
                    Thread thread = Executors.defaultThreadFactory().newThread(r);
                    thread.setDaemon(true);
                    return thread;
                }
        );
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
