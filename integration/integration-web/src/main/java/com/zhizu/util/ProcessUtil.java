package com.zhizu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessUtil {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public boolean ProcessUtil(String processName, String processInfo) {
        String[] cmd = new String[] { "cmd.exe", "/C", "wmic process " };
        BufferedReader bReader = null;
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            process.getOutputStream().close();
            bReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = bReader.readLine()) != null) {
                // 如果该进程存在于进程列表中
                String[] words = line.split("\\s+");
                if ((words[0].indexOf(processName)) != -1) {
                    // 如果该进程与配置的进程路径相符
                    if ((words[1].indexOf(processInfo)) != 1) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
