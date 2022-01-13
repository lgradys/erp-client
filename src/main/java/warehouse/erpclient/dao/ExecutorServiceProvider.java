package warehouse.erpclient.dao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum ExecutorServiceProvider {

    INSTANCE;

    private final ExecutorService executorService;


    ExecutorServiceProvider() {
        executorService = Executors.newCachedThreadPool();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
