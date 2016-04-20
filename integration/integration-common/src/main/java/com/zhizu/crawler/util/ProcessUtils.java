package com.zhizu.crawler.util;

public class ProcessUtils {
    public static void killDllhost() {
        killProcess("dllhost.exe");
    }

    public static void killProcess(String name) {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                Runtime.getRuntime().exec("killall " + name);
            } else {
                Runtime.getRuntime().exec("taskkill /T /IM " + name + " /F");
            }
        } catch (Exception e) {
        }
    }

    public static void killProcess(String[] list) {
        if (list != null) {
            for (String s : list) {
                killProcess(s);
            }
        }
    }

    public static void executeCmd(String... cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        ProcessUtils.killProcess(new String[] { "firefox.exe", "iexplore.exe", "IEDriverServer_32.exe" });
    }
}
