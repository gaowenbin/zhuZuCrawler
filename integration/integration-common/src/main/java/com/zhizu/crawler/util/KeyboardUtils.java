package com.zhizu.crawler.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KeyboardUtils {

    public static final String path = "C:\\keyboard\\KeyForm.exe";
    public static final String codepath = "C:\\keyboard\\keyword.txt";
    public static final String VK_TAB = "_Tab_";
    public static final String VK_BACKSPACE = "_Backspace_";
    public static Map<String, String> keyMaps = new HashMap<String, String>();

    public KeyboardUtils() {
        if (keyMaps == null || keyMaps.size() == 0) {
            initKeyMaps();
        }
    }

    static Process p;

    static public String getKeyboardCodes(String sec, String x, String y, String words) {
        StringBuffer sbu = new StringBuffer();
        sbu.append(sec + "," + x + "," + y + ",");
        char[] chars = words.toCharArray();
        if (VK_TAB.equals(words)) {
            sbu.append(keyMaps.get(words));
            return sbu.toString();
        }
        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            String tempS = keyMaps.get("" + temp);
            if (i != chars.length - 1) {
                sbu.append(tempS).append(",");
            } else {
                sbu.append(tempS);
            }
        }
        System.out.println(sbu.toString());
        return sbu.toString();
    }

    static public String String2Ascii(String words) {
        StringBuffer asc = new StringBuffer();
        // 字符串转换为ASCII码
        char[] chars = words.toCharArray(); // 把字符中转换为字符数组

        for (int i = 0; i < chars.length; i++) {// 输出结果
            if (i != chars.length - 1) {
                asc.append((int) chars[i]).append(",");
            } else {
                asc.append((int) chars[i]);
            }
        }
        return asc.toString();
    }

    public static void writeCode(String content) {
        try {
            // 打开一个写文件器
            FileWriter writer = new FileWriter(codepath, false);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public String openApplication(String filePath) throws InterruptedException {
        try {
            p = java.lang.Runtime.getRuntime().exec(filePath);

            System.out.print(p.toString());
            BufferedInputStream br = new BufferedInputStream(p.getInputStream());
            BufferedOutputStream br1 = new BufferedOutputStream(p.getOutputStream());
            int ch;
            StringBuffer text = new StringBuffer("获得的信息是: \n");

            while ((ch = br.read()) != -1) {
                text.append((char) ch);
            }
            int retval = p.waitFor();

            System.out.println(text + br1.toString());
            System.out.println(retval);
            return br1.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String doKeyBoard(String codes) {
        try {
            new KeyboardUtils();
            codes = getKeyboardCodes("2", "0", "0", codes);
            writeCode(codes);
            return openApplication(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static String doKeyBoard(String x, String y, String codes) {
        try {
            new KeyboardUtils();
            codes = getKeyboardCodes("2", x, y, codes);
            writeCode(codes);
            return openApplication(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static String doKeyBoard(String sec, String x, String y, String codes) {
        try {
            new KeyboardUtils();
            codes = getKeyboardCodes(sec, x, y, codes);
            writeCode(codes);
            return openApplication(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static String doKeyBoardBackspace(int backspace) {
        try {
            new KeyboardUtils();
            String codes = "2,0,0,";
            for (int i = 0; i < backspace; i++) {
                if (i != backspace - 1) {
                    codes += keyMaps.get(VK_BACKSPACE) + ",";
                } else {
                    codes += keyMaps.get(VK_BACKSPACE);
                }

            }
            writeCode(codes);
            return openApplication(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static String doKeyBoardBackspace(String x, String y, int backspace) {
        try {
            new KeyboardUtils();
            String codes = "2," + x + "," + y + ",";
            for (int i = 0; i < backspace; i++) {
                if (i != backspace - 1) {
                    codes += keyMaps.get(VK_BACKSPACE) + ",";
                } else {
                    codes += keyMaps.get(VK_BACKSPACE);
                }

            }
            writeCode(codes);
            return openApplication(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static String doKeyBoardBackspace(String sec, String x, String y, int backspace) {
        try {
            new KeyboardUtils();
            String codes = sec + "," + x + "," + y + ",";
            for (int i = 0; i < backspace; i++) {
                if (i != backspace - 1) {
                    codes += keyMaps.get(VK_BACKSPACE) + ",";
                } else {
                    codes += keyMaps.get(VK_BACKSPACE);
                }

            }
            writeCode(codes);
            return openApplication(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            String p = "q5233586";
            doKeyBoardBackspace("5", "0", "0", 4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void initKeyMaps() {

        keyMaps.put("a", "K65");
        keyMaps.put("A", "S65");
        keyMaps.put("b", "K66");
        keyMaps.put("B", "S66");
        keyMaps.put("c", "K67");
        keyMaps.put("C", "S67");
        keyMaps.put("d", "K68");
        keyMaps.put("D", "S68");
        keyMaps.put("e", "K69");
        keyMaps.put("E", "S69");
        keyMaps.put("f", "K70");
        keyMaps.put("F", "S70");
        keyMaps.put("g", "K71");
        keyMaps.put("G", "S71");
        keyMaps.put("h", "K72");
        keyMaps.put("H", "S72");
        keyMaps.put("i", "K73");
        keyMaps.put("I", "S73");
        keyMaps.put("j", "K74");
        keyMaps.put("J", "S74");
        keyMaps.put("k", "K75");
        keyMaps.put("K", "S75");
        keyMaps.put("l", "K76");
        keyMaps.put("L", "S76");
        keyMaps.put("m", "K77");
        keyMaps.put("M", "S77");
        keyMaps.put("n", "K78");
        keyMaps.put("N", "S78");
        keyMaps.put("o", "K79");
        keyMaps.put("O", "S79");
        keyMaps.put("p", "K80");
        keyMaps.put("P", "S80");
        keyMaps.put("q", "K81");
        keyMaps.put("Q", "S81");
        keyMaps.put("r", "K82");
        keyMaps.put("R", "S82");
        keyMaps.put("s", "K83");
        keyMaps.put("S", "S83");
        keyMaps.put("t", "K84");
        keyMaps.put("T", "S84");
        keyMaps.put("u", "K85");
        keyMaps.put("U", "S85");
        keyMaps.put("v", "K86");
        keyMaps.put("V", "S86");
        keyMaps.put("w", "K87");
        keyMaps.put("W", "S87");
        keyMaps.put("x", "K88");
        keyMaps.put("X", "S88");
        keyMaps.put("y", "K89");
        keyMaps.put("Y", "S89");
        keyMaps.put("z", "K90");
        keyMaps.put("Z", "S90");

        keyMaps.put("`", "K192");
        keyMaps.put("~", "S192");
        keyMaps.put("1", "K49");
        keyMaps.put("!", "S49");
        keyMaps.put("2", "K50");
        keyMaps.put("@", "S50");
        keyMaps.put("3", "K51");
        keyMaps.put("#", "S51");
        keyMaps.put("4", "K52");
        keyMaps.put("$", "S52");
        keyMaps.put("5", "K53");
        keyMaps.put("%", "S53");
        keyMaps.put("6", "K54");
        keyMaps.put("^", "S54");
        keyMaps.put("7", "K55");
        keyMaps.put("&", "S55");
        keyMaps.put("8", "K56");
        keyMaps.put("*", "S56");
        keyMaps.put("9", "K57");
        keyMaps.put("(", "S57");
        keyMaps.put("0", "K48");
        keyMaps.put(")", "S48");
        keyMaps.put("-", "K189");
        keyMaps.put("_", "S189");
        keyMaps.put("=", "K187");
        keyMaps.put("+", "S187");

        keyMaps.put(" ", "K32");
        keyMaps.put("[", "K219");
        keyMaps.put("{", "S219");
        keyMaps.put("]", "K221");
        keyMaps.put("}", "S221");
        keyMaps.put("\\", "K220");
        keyMaps.put("|", "S220");
        keyMaps.put(",", "K188");
        keyMaps.put("<", "S188");
        keyMaps.put(".", "K190");
        keyMaps.put(">", "S190");
        keyMaps.put("/", "K191");
        keyMaps.put("?", "S191");
        keyMaps.put(";", "K186");
        keyMaps.put(":", "S186");
        keyMaps.put("'", "K222");
        keyMaps.put("\"", "S222");

        keyMaps.put(VK_TAB, "K9");
        keyMaps.put(VK_BACKSPACE, "K8");
    }
}
