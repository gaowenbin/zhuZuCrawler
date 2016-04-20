package com.zhizu.util;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class FileUtil {
    public static String FATHER_FILE_PATH = PropertiesUtil.getProps("FATHER_FILE");
    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";
    public static String defaultImageFormat = "jpg";
    private static Logger logger = Logger.getLogger(FileUtil.class);

    // public static String TAOBAO_IMAGE_PATH
    // =PropertiesUtil.getProps("TAOBAO_IMAGE_PATH");

    /**
     * 生成文件
     * 
     * @param fileContent
     *            文件的内容
     * @param filePath
     *            文件路径；
     * @param fileName
     *            文件的文件名；
     * @param isappendContent
     *            是否追加
     */
    public static void writeStrings(String fileContent, String filePath, String fileName, boolean isappendContent) {
        try {

            // 创建文件夹
            File file = new File(filePath);
            if (!file.exists() || !file.isDirectory()) {
                makeDir(file);
            }
            System.out.println(filePath + "创建成功！");

            FileUtils.write(new File(file, fileName), fileContent, UTF_8, isappendContent);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 生成文件
     * 
     * @param fileContent
     *            文件的内容
     * @param fileName
     *            文件的文件名；
     * @param isWriteNewContent
     *            覆盖原文件 或追加新内容
     */
    public static void writeFile(String fileContent, String dir, String fileName, boolean isWriteNewContent,
            InputStream in) {
        File file = new File(dir);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        try {
            if (isWriteNewContent) {

                FileUtils.write(new File(file, fileName), fileContent, UTF_8, false);

            } else {

                FileUtils.copyInputStreamToFile(in, new File(file, fileName));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     * 
     * @param src
     * @return
     */
    public static String read(File src) {
        StringBuffer res = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(src));
            while ((line = reader.readLine()) != null) {
                if (!line.trim().equals("")) {
                    String frist = line.substring(0, 1);
                    if (!frist.equals("#")) {
                        res.append(line + "\n").toString();
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    /**
     * 读取文件
     * 
     * @param src
     * @return
     */
    public static List<String> reads(File src) {
        List<String> res = new ArrayList<String>();
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(src));
            while ((line = reader.readLine()) != null) {
                if (!line.trim().equals("")) {
                    String frist = line.substring(0, 1);
                    if (!frist.equals("#")) {
                        // res.append(line + "\n").toString();
                        res.add(line.trim());
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 重命名文件夹
     * 
     * @param src
     * @param dest
     * @return
     */
    public static boolean renameToNewFile(String src, String dest) {
        File srcDir = new File(src);
        boolean isOk = false;
        File newFile = new File(dest);
        isOk = srcDir.renameTo(newFile);
        logger.info("rename " + src + " to + " + dest + " is OK ? :" + isOk);
        return isOk;
    }

    /**
     * 重命名文件夹
     * 
     * @param src
     * @param dest
     * @return
     */
    public static boolean renameToNewFile(String path, String oldname, String newname) {
        File srcDir = new File(path + File.separator + oldname);
        boolean isOk = false;
        // srcDir = srcDir.getParentFile().getParentFile();
        // tmp.renameTo(new File(tmp.getParent(), tmp.getName() + "_fin"));
        // if (srcDir.exists() || srcDir.isDirectory()) {
        isOk = srcDir.renameTo(new File(path + File.separator + newname));
        // }

        System.out.println("renameToNewFile is OK ? :" + isOk);
        return isOk;
    }

    /**
     * 页面截图
     * 
     * @author liuheli
     * @param fileName
     *            保存图片名称
     * @param savePath
     *            保存路径
     * @param x
     *            起始x坐标
     * @param y
     *            起始y坐标
     * @param width
     *            截图宽度
     * @param height
     *            截图高度
     * @throws Exception
     */
    public static void snapShot(String fileName, String savePath, int x, int y, int width, int height) throws Exception {
        // 拷贝屏幕到一个BufferedImage对象screenshot
        BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(x, y, width, height));
        // 根据文件前缀变量和文件格式变量，自动生成文件名
        String name = savePath + "\\" + fileName + "." + defaultImageFormat;
        // 输出的文件及路径
        File sf = new File(name);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        System.out.print("Save File " + name);
        // 将screenshot对象写入图像文件
        ImageIO.write(screenshot, defaultImageFormat, sf);
        System.out.print("..Finished!\n");
    }

    /**
     * @author liuheli
     * @param webDriver
     * @param filename
     * @throws IOException
     */
    public static void pageDownload(WebDriver webDriver, String path) throws IOException {
        pageDownload(webDriver, path, UTF_8);
    }

    /**
     * @author liuheli
     * @param webDriver
     * @param filename
     * @param encoding
     * @throws IOException
     */
    public static void pageDownload(WebDriver webDriver, String path, String encoding) throws IOException {
        System.out.println("Page DownLoad Html Start the path is: " + path);
        // 保存页面源码
        File f = new File(path);
        if (!f.exists()) {
            f.createNewFile();
        }

        FileOutputStream out = new FileOutputStream(f);
        OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
        writer.write(webDriver.getPageSource());
        writer.close();
        out.close();
        System.out.println("Page DownLoad Html Success the path is: " + path);
    }

    public static File makeDir(File dir) throws IOException {
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File makeDir(String dir) throws IOException {
        return makeDir(new File(dir));
    }

    public static File createFile(File file) throws IOException {
        if (!file.exists() || file.isDirectory()) {
            makeDir(file.getParentFile());
        }
        return file;
    }

    public static File createFile(String file) throws IOException {
        return createFile(new File(file));
    }

    /**
     * 返回 basepath/20151024/type, 日期是从进号里截取的
     * 
     * @author zhuyuhang
     * @param base
     * @param date
     * @return
     */
    public static String getCrawlerFilePath(String basePath, String applyNo, String type) {
        String date = applyNo.length() < 11 ? applyNo : applyNo.substring(3, 11);
        date = date + File.separatorChar + applyNo + File.separatorChar + type;
        return new File(StringUtils.isBlank(basePath) ? FATHER_FILE_PATH : basePath, date).getAbsolutePath();
    }

    public static String getCrawlerFilePath(String applyNo, String type) {
        return getCrawlerFilePath(null, applyNo, type);
    }

    public static void main(String ags[]) throws IOException {

        // Random rand = new Random();
        //
        File codeFile = new File("aaa");

        System.out.println(codeFile);
        System.out.println(codeFile.getPath());

        // String responseString = getUTF8StringFromGBKString(filestr);
        // FileUtils
        // .write(new File("H:/data/", "aaa.html"), filestr, UTF_8, false);
        // writeStrings(filestr, "H:/aaa/bbb/", "aaa.html", false);
    }
}
