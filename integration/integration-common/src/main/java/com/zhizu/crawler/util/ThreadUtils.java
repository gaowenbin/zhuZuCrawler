package com.zhizu.crawler.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
}
