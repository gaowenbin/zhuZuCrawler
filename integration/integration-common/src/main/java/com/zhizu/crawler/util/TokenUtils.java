package com.zhizu.crawler.util;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * 令牌处理器
 * 
 * @author lx
 */
public class TokenUtils {

    private static TokenUtils instance = new TokenUtils();
    private Random rnd = new Random();

    private long previous;

    protected TokenUtils() {
    }

    public static TokenUtils getInstance() {
        return instance;
    }

    public String generateToken(String msg, boolean timeChange) {
        try {
            // long current = System.currentTimeMillis();
            // if (current == previous)
            // current++;
            // previous = current;
            // MessageDigest md = MessageDigest.getInstance("MD5");
            // msg = msg + rnd.nextInt(3);
            // md.update(msg.getBytes());
            // if (timeChange) {
            // // byte now[] = (current+"").toString().getBytes();
            // byte now[] = (new Long(current)).toString().getBytes();
            // md.update(now);
            // }
            // return toHex(md.digest());
        } catch (Exception e) {
            return null;
        }
        return System.currentTimeMillis() + "|" + rnd.nextInt(10) + rnd.nextInt(10) + rnd.nextInt(10) + rnd.nextInt(10);
    }

    private String toHex(byte buffer[]) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 15, 16));
        }

        return sb.toString();
    }

    private static final int tokenTimeToLiveSeconds = 60;

    public long parse(String token) {
        if (StringUtils.isBlank(token)) {
            return 0l;
        }
        try {
            return Long.parseLong(token.split("\\|")[0]);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return 0;
    }

    public boolean isExpired(String token) {
        return ((System.currentTimeMillis() - parse(token)) / 1000) >= tokenTimeToLiveSeconds;
    }

    /**
     * 检测token是否是seconds秒之前生成的 如果是 则返回true
     * 
     * @author zhuyuhang
     * @param token
     * @param seconds
     * @return
     */
    public boolean isExpired(String token, int seconds) {
        return ((System.currentTimeMillis() - parse(token)) / 1000) >= seconds;
    }

    public static void main(String ags[]) {
        System.out.println(System.currentTimeMillis());
        Date date = new Date(1428489671693l);
        System.out.println(DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss S"));
        System.out.println(TokenUtils.getInstance().isExpired("1428489671693|0635", 280));
    }
}