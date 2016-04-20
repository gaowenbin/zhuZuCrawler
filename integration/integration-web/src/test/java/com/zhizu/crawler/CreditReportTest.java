package com.zhizu.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhizu.crawler.util.TokenUtils;
import com.zhizu.util.HttpUtils;
import com.zhizu.util.ParamsBuilder;

public class CreditReportTest implements Runnable {
    public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:32.0) Gecko/20100101 Firefox/33.0";

    private static String imageCode;

    @Override
    public void run() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // resetPassword();
            // testReg();
            // getTel();
            // System.out.println(System.currentTimeMillis());
            // System.out.println(RandomUtil.getNextInt18());
            // System.out.println(getRealName());
            // jsonmark();
            // findLoginName();
            // resetPassword();
            // loginDownload();
            // testJson();
            // findLoginName();
            testReg();
            // // cctest();
            // testReg();
            // resetPassword();
            // resetPassword();
            // testReg();
            // String sourceValue = "jge123";
            // String txt = "jge123asfdfdfdf";
            // Pattern p = Pattern.compile("\\D|" + sourceValue + "\\D");
            // Matcher m1 = p.matcher(txt);
            // boolean result1 = m1.find();
            // System.out.print(result1);
            // findLoginName();
            // loginApplayVerifyKey();
            // findLoginName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cctest() throws InterruptedException, ClientProtocolException, IOException {

        String productType = "gedaiapp";
        String token = TokenUtils.getInstance().generateToken(productType, true);
        String applyNo = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("applyNo", "creditReportTest");
        params.put("productType", productType);
        params.put("loginName", "heqiushuang666");
        params.put("passWord", "ck19880525");
        params.put("durex", "11");
        // 获取验证码
        CloseableHttpClient client = HttpClients.createDefault();
        System.out.println("paramsTOString:" + ParamsBuilder.convert(params));
        String url = "http://192.168.120.85:8080/crawler-web/creditReport/captcha?1=1" + ParamsBuilder.convert(params);
        String result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);

        url = "http://192.168.120.85:8080/crawler-web/queryResult?1=1" + ParamsBuilder.convert(params);
        while (true) {
            Thread.sleep(1000);
            result = HttpUtils.executeGetWithResult(client, url);
            if (StringUtils.isNotBlank(result)) {
                break;
            }
        }
        System.out.println("第一步结果：" + url + "\t" + result);
        imageCode = (String) JOptionPane.showInputDialog("请输入图片验证码:");
        // 提交身份信息
        params.put("captchaCode", imageCode);

        url = "http://192.168.120.85:8080/crawler-web/creditReport/login?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        params.put("durex", true);
        url = "http://192.168.120.85:8080/crawler-web/creditReport/queryResult?1=1" + ParamsBuilder.convert(params);
        while (true) {
            Thread.sleep(1000);
            result = HttpUtils.executeGetWithResult(client, url);
            if (StringUtils.isNotBlank(result)) {
                break;
            }
        }
        System.out.println("第二步结果：" + url + "\t" + result);

    }

    public static void testReg() throws Exception {
        String productType = "lendApp";
        String token = TokenUtils.getInstance().generateToken(productType, true);
        String applyNo = "201201510080023599";// UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("applyNo", applyNo);
        params.put("productType", productType);

        // params.put("durex", "11");
        // 获取验证码
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://localhost:8080/integration-web/testController/captcha?1=1" + ParamsBuilder.convert(params);
        String result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);

        // 查询结果

        imageCode = (String) JOptionPane.showInputDialog("请输入图片验证码:");
        // 提交身份信息
        params = new HashMap<>();
        params.put("captchaCode", imageCode);
        token = TokenUtils.getInstance().generateToken(productType, true);
        params.put("token", token);
        params.put("applyNo", applyNo);
        params.put("name", "郭琳钊");
        params.put("idType", "0");
        params.put("productType", productType);
        params.put("idNo", "130124198808094532");
        url = "http://172.16.36.11/openapi/creditReport/register?1=1" + ParamsBuilder.convert(params);
        result = executePostWithResult(client, url, params);
        System.out.println(url + "-------------" + result);
        // 查询结果
        url = "http://172.16.36.11/openapi/creditReport/queryResult?durex=true&1=1" + ParamsBuilder.convert(params);
        while (true) {
            Thread.sleep(1000);
            result = HttpUtils.executeGetWithResult(client, url);
            if (StringUtils.isNotBlank(result)) {
                break;
            }
        }
        System.out.println("第一步结果：" + url + "\t" + result);

        // 检测注册的登陆名是否重复
        params = new HashMap<>();
        params.put("loginName", "dashuju123");
        token = TokenUtils.getInstance().generateToken(productType, true);
        System.out.println("token:" + token);
        params.put("token", token);
        params.put("applyNo", applyNo);
        params.put("productType", productType);
        url = "http://172.16.36.11/openapi/creditReport/checkRegLoginnameHasUsed?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        // 查询结果
        url = "http://172.16.36.11/openapi/creditReport/queryResult?durex=true&1=1" + ParamsBuilder.convert(params);
        while (true) {
            Thread.sleep(1000);
            result = HttpUtils.executeGetWithResult(client, url);
            if (StringUtils.isNotBlank(result)) {
                break;
            }
        }
        System.out.println("第一步结果：" + url + "\t" + result);

        // 发短信
        params = new HashMap<>();
        params.put("mobileTel", "18612132344");
        token = TokenUtils.getInstance().generateToken(productType, true);
        System.out.println("token:" + token);
        params.put("token", token);
        params.put("applyNo", applyNo);
        params.put("productType", productType);
        url = "http://172.16.36.11/openapi/creditReport/getMobileVerifyCode?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        // 查询结果
        url = "http://172.16.36.11/openapi/creditReport/queryResult?durex=true&1=1" + ParamsBuilder.convert(params);
        while (true) {
            Thread.sleep(1000);
            result = HttpUtils.executeGetWithResult(client, url);
            if (StringUtils.isNotBlank(result)) {
                break;
            }
        }
        System.out.println("第一步结果：" + url + "\t" + result);

        // -----------------------------以下没测试
        imageCode = JOptionPane.showInputDialog("请输入手机验证码:");
        params.put("verifyCode", imageCode);
        params.put("passWord", "5880341");
        params.put("confirmPassWord", "5880341");
        params.put("email", "");
        // obj = (JSONObject) JSON.parse(result);
        // subObj = (JSONObject) obj.get("result");
        // params.put("tcId", subObj.get("tcId"));

        url = "http://192.168.111.40/openapi/creditReport/checkRegLoginnameHasUsed?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);

        url = "http://localhost:8080/crawler-web/creditReport/pbccrcSaveUser?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        // 二次提交

        // 补充用户信息
        // obj = (JSONObject) JSON.parse(result);
        // subObj = (JSONObject) obj.get("result");
        // params.put("htmlToken", subObj.get("htmlToken"));
        params.put("mobileTel", "15210189743");

        url = "http://localhost:8080/crawler-web/creditReport/getMobileVerifyCode?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);

        // 检测注册的登陆名是否重复
        String decode = JOptionPane.showInputDialog("请输入手机验证码:");
        params.put("verifyCode", decode);
        params.put("passWord", "5880341");
        params.put("confirmPassWord", "5880341");
        params.put("email", "");
        // obj = (JSONObject) JSON.parse(result);
        // subObj = (JSONObject) obj.get("result");
        // params.put("tcId", subObj.get("tcId"));
        url = "http://localhost:8080/crawler-web/creditReport/checkRegLoginnameHasUsed?1=1"
                + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);
        url = "http://localhost:8080/crawler-web/creditReport/pbccrcSaveUser?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);

    }

    public static void loginDownload() throws Exception {

        String productType = "gedaiapp";
        String token = TokenUtils.getInstance().generateToken(productType, true);
        String applyNo = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("applyNo", "creditReport001");
        params.put("productType", productType);
        params.put("durex", "11");
        // 获取验证码
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://localhost:8080/crawler-web/creditReport/captcha?1=1" + ParamsBuilder.convert(params);
        String result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        url = "http://172.16.28.43:8080/crawler-web/queryResult?1=1" + ParamsBuilder.convert(params);
        while (true) {
            Thread.sleep(1000);
            result = HttpUtils.executeGetWithResult(client, url);
            if (StringUtils.isNotBlank(result)) {
                break;
            }
        }
        System.out.println("第一步结果：" + url + "\t" + result);

        imageCode = (String) JOptionPane.showInputDialog("请输入图片验证码:");
        // 提交身份信息
        params.put("captchaCode", imageCode);
        token = TokenUtils.getInstance().generateToken(productType, true);
        params.put("token", token);
        params.put("loginName", "heqiushuang666");
        params.put("passWord", "ck19880525");
        url = "http://localhost:8080/crawler-web/creditReport/login?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);

        System.out.println(url + "\t" + result);

        JSONObject obj = (JSONObject) JSON.parse(result);
        JSONObject subObj = (JSONObject) obj.get("result");

        params.put("loginName", "xuchao85");
        params.put("tradeCode", "7n");
        params.put("htmlToken", subObj.get("htmlToken"));
        System.out.println("aaaaaaaa:" + subObj.get("htmlToken"));
        // 下载征信报告 -单元测试成功
        client = HttpClients.createDefault();
        System.out.println("paramsTOString:" + ParamsBuilder.convert(params));
        url = "http://localhost:8080/crawler-api/creditReport/downloadCreditR?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        // JSONObject obj = (JSONObject) JSON.parse(result);;
        // JSONObject subObj = (JSONObject) obj.get("result");;
        //
        // params.put("loginName", "yangyunhan");
        // params.put("tradeCode", "7n8ksf");
        // params.put("htmlToken", subObj.get("htmlToken"));
        // System.out.println("aaaaaaaa:"+subObj.get("htmlToken"));
    }

    // 登录到申请验证key
    public static void loginApplayVerifyKey() {
        String productType = "gedaiapp";
        String token = TokenUtils.getInstance().generateToken(productType, true);
        String applyNo = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("applyNo", "creditReport001");
        params.put("productType", productType);
        params.put("durex", "11");
        // 获取验证码
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://localhost:8080/crawler-web/creditReport/captcha?1=1" + ParamsBuilder.convert(params);
        String result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        imageCode = (String) JOptionPane.showInputDialog("请输入图片验证码:");
        // 提交身份信息
        params.put("captchaCode", imageCode);
        token = TokenUtils.getInstance().generateToken(productType, true);
        params.put("token", token);
        params.put("loginName", "316951069");
        params.put("passWord", "Meng139");
        url = "http://localhost:8080/crawler-web/creditReport/login?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);

        System.out.println(url + "\t" + result);
        JSONObject obj = (JSONObject) JSON.parse(result);
        JSONObject subObj;
        obj = (JSONObject) JSON.parse(result);
        subObj = (JSONObject) obj.get("result");
        subObj.put("kbaList[0].options", "1");
        subObj.put("kbaList[1].options", "2");
        subObj.put("kbaList[2].options", "3");
        subObj.put("kbaList[3].options", "1");
        subObj.put("kbaList[4].options", "2");
        subObj.remove("kbaList[0].answerresult");
        subObj.remove("kbaList[1].answerresult");
        subObj.remove("kbaList[2].answerresult");
        subObj.remove("kbaList[3].answerresult");
        subObj.remove("kbaList[4].answerresult");
        subObj.put("kbaList[0].answerresult", "1");
        subObj.put("kbaList[1].answerresult", "2");
        subObj.put("kbaList[2].answerresult", "3");
        subObj.put("kbaList[3].answerresult", "1");
        subObj.put("kbaList[4].answerresult", "2");
        String questions = subObj.toJSONString();
        params.put("questions", questions);
        url = "http://localhost:8080/crawler-web/creditReport/submitKBA?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);

        System.out.println(url + "\t" + result);
    }

    public static String executeGetWithResult(CloseableHttpClient client, String url) {
        try {
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse resp = client.execute(get);
            String result = EntityUtils.toString(resp.getEntity());
            resp.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String executePostWithResult(CloseableHttpClient client, String url, Map<String, Object> params) {
        try {
            String result = HttpUtils.executePostWithResult(client, url, params);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 测试找回用户名
    public static void findLoginName() throws ClientProtocolException, IOException, InterruptedException {
        String productType = "gedaiapp";
        String token = TokenUtils.getInstance().generateToken(productType, true);
        String applyNo = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("applyNo", "creditReport001");
        params.put("productType", productType);
        // params.put("durex", "11");
        // 获取验证码
        CloseableHttpClient client = HttpClients.createDefault();
        System.out.println("paramsTOString:" + ParamsBuilder.convert(params));
        String url = "http://192.168.120.90/crawler-web/creditReport/captcha?1=1" + ParamsBuilder.convert(params);
        String result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);

        url = "http://192.168.120.90:8080/crawler-web/queryResult?1=1" + ParamsBuilder.convert(params);
        while (true) {
            Thread.sleep(1000);
            result = HttpUtils.executeGetWithResult(client, url);
            if (StringUtils.isNotBlank(result)) {
                break;
            }
        }
        System.out.println("第一步结果：" + url + "\t" + result);

        System.out.println(url + "\t" + result);
        JSONObject obj = (JSONObject) JSON.parse(result);
        JSONObject subObj;
        System.err.println(obj.get("result"));
        imageCode = (String) JOptionPane.showInputDialog("请输入图片验证码:");
        // 提交身份信息
        params.put("captchaCode", imageCode);
        params.put("name", "杨韵涵");
        params.put("idType", "0");
        params.put("idNo", "513701198808296419");
        System.out.println(" ParamsBuilder.convert(params):" + ParamsBuilder.convert(params));
        url = "http://192.168.120.90:8080/crawler-web/creditReport/findLoginName";
        HttpPost post = HttpUtils.post(url, params);
        // HttpUtils.executePostWithResult(client, post);
        result = HttpUtils.executePostWithResult(client, url, params, HttpUtils.UTF_8);
        // result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        url = "http://192.168.120.90:8080/crawler-api/queryResult?1=1" + ParamsBuilder.convert(params);
        while (true) {
            Thread.sleep(1000);
            result = HttpUtils.executeGetWithResult(client, url);
            if (StringUtils.isNotBlank(result)) {
                break;
            }
        }
        System.out.println("第二步结果：" + url + "\t" + result);

    }

    // 测试手机获取验证码
    public static void tel() {
        String productType = "gedaiapp";
        String token = TokenUtils.getInstance().generateToken(productType, true);
        String applyNo = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("applyNo", "creditReport001");
        params.put("productType", productType);
        params.put("mobilTel", "15210189743");
        CloseableHttpClient client = HttpClients.createDefault();
        System.out.println("paramsTOString:" + ParamsBuilder.convert(params));
        String url = "http://localhost:8080/crawler-web/creditReport/getMobileVerifyCode?1=1"
                + ParamsBuilder.convert(params);
        String result = executeGetWithResult(client, url);
        System.out.println("result:" + result);

    }

    // 回答问题
    public static void submitKBA() {

    }

    // 下载征信报告
    public static void downloadCreditR() {
        String productType = "gedaiapp";
        String token = TokenUtils.getInstance().generateToken(productType, true);
        String applyNo = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("applyNo", "creditReport001");
        params.put("productType", productType);
        params.put("durex", "11");
        params.put("loginname", "heqiushuang");
        params.put("loginName", "yangyunhan");
        params.put("tradeCode", "7n8ksf");
        params.put("htmlToken", "htmlToken");

        // 获取验证码
        CloseableHttpClient client = HttpClients.createDefault();
        System.out.println("paramsTOString:" + ParamsBuilder.convert(params));
        String url = "http://localhost:8080/crawler-web/creditReport/downloadCreditR?1=1"
                + ParamsBuilder.convert(params);
    }

    // 回答文体 自动拼接答案 字符串
    public static void testJson() {
        String result = "{\"result\":{\"org.apache.struts.taglib.html.TOKEN\":\"bb4e00a31bd1b089e0ae0d1fb4c88fa9\"}}";
        JSONObject obj = (JSONObject) JSON.parse(result);
        JSONObject subObj;
        obj = (JSONObject) JSON.parse(result);
        subObj = (JSONObject) obj.get("result");
        subObj.put("kbaList[0].options", "1");
        subObj.put("kbaList[1].options", "2");
        subObj.put("kbaList[2].options", "3");
        subObj.put("kbaList[3].options", "1");
        subObj.put("kbaList[4].options", "2");
        subObj.remove("kbaList[0].answerresult");
        subObj.remove("kbaList[1].answerresult");
        subObj.remove("kbaList[2].answerresult");
        subObj.remove("kbaList[3].answerresult");
        subObj.remove("kbaList[4].answerresult");
        subObj.put("kbaList[0].answerresult", "1");
        subObj.put("kbaList[1].answerresult", "2");
        subObj.put("kbaList[2].answerresult", "3");
        subObj.put("kbaList[3].answerresult", "1");
        subObj.put("kbaList[4].answerresult", "2");
        System.out.println("ggg:" + subObj.toJSONString());
    }

    // @Test
    // public void testJSON(){
    // String
    // json="{\"success\":true,\"errorCode\":\"00000\",\"result\":{\"htmlToken\":\"4487d22cd78a1990de74c5743f422e88\"}}";
    // JSONObject obj=(JSONObject)JSON.parse(json);
    // JSONObject subObj=(JSONObject)obj.get("result");
    // System.err.println(subObj.get("htmlToken"));
    //
    // }
    // 测试找回密码
    public static void resetPassword() {

        String productType = "gedaiapp";
        String token = TokenUtils.getInstance().generateToken(productType, true);
        String applyNo = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("applyNo", "creditReport001");
        params.put("productType", productType);
        params.put("durex", "11");
        // 获取验证码
        CloseableHttpClient client = HttpClients.createDefault();
        System.out.println("paramsTOString:" + ParamsBuilder.convert(params));
        String url = "http://localhost:8080/crawler-web/creditReport/captcha?1=1" + ParamsBuilder.convert(params);
        String result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        JSONObject obj = (JSONObject) JSON.parse(result);
        JSONObject subObj;
        System.err.println(obj.get("result"));

        imageCode = (String) JOptionPane.showInputDialog("请输入图片验证码:");
        params.put("loginName", "heqiushuang666");
        params.put("name", "何秋爽");
        params.put("idNo", "210503198709141516");
        params.put("captchaCode", imageCode);
        params.put("idType", "0");

        url = "http://localhost:8080/crawler-web/creditReport/checkLoginName";
        // url =
        // "http://localhost:8080/crawler-web/creditReport/checkLoginName?1=1" +
        // ParamsBuilder.convert(params);

        result = executePostWithResult(client, url, params);

        // result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);

        // //手机获取验证码
        url = "http://localhost:8080/crawler-web/creditReport/resetPasswordMobileVerifyCode?1=1"
                + ParamsBuilder.convert(params);
        params.put("idType", "0");
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        String verifyCode = (String) JOptionPane.showInputDialog("请输入短信验证码:");
        params.put("verifyCode", verifyCode);
        params.put("passWord", "ck19880525");
        params.put("confirmPassWord", "ck19880525");
        url = "http://localhost:8080/crawler-web/creditReport/resetPassword?1=1" + ParamsBuilder.convert(params);

        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);

        // //手机获取验证码
        url = "http://localhost:8080/crawler-web/creditReport/resetPasswordMobileVerifyCode?1=1"
                + ParamsBuilder.convert(params);
        params.put("idType", "0");
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        verifyCode = (String) JOptionPane.showInputDialog("请输入短信验证码:");
        params.put("verifyCode", verifyCode);
        params.put("passWord", "ck19880525");
        params.put("confirmPassWord", "ck19880525");
        url = "http://localhost:8080/crawler-web/creditReport/resetPassword?1=1" + ParamsBuilder.convert(params);

        result = executeGetWithResult(client, url);

        System.out.println(url + "\t" + result);
        obj = (JSONObject) JSON.parse(result);
        subObj = (JSONObject) obj.get("result");
        obj = (JSONObject) JSON.parse(result);
        subObj = (JSONObject) obj.get("result");
        subObj.put("kbaList[0].options", "1");
        subObj.put("kbaList[1].options", "2");
        subObj.put("kbaList[2].options", "3");
        subObj.put("kbaList[3].options", "1");
        subObj.put("kbaList[4].options", "2");
        subObj.remove("kbaList[0].answerresult");
        subObj.remove("kbaList[1].answerresult");
        subObj.remove("kbaList[2].answerresult");
        subObj.remove("kbaList[3].answerresult");
        subObj.remove("kbaList[4].answerresult");
        subObj.put("kbaList[0].answerresult", "1");
        subObj.put("kbaList[1].answerresult", "2");
        subObj.put("kbaList[2].answerresult", "3");
        subObj.put("kbaList[3].answerresult", "1");
        subObj.put("kbaList[4].answerresult", "2");
        String questions = subObj.toJSONString();
        params.put("questions", questions);
        url = "http://localhost:8080/crawler-web/creditReport/pbccrcSaveKbaApply?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);

        System.out.println(url + "\t" + result);

    }

    // 解析短话号码

    public static void getTel() {
        String productType = "gedaiapp";
        String token = TokenUtils.getInstance().generateToken(productType, true);
        String applyNo = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("applyNo", "creditReport001");
        params.put("productType", productType);
        params.put("durex", "11");
        // 获取验证码
        CloseableHttpClient client = HttpClients.createDefault();
        System.out.println("paramsTOString:" + ParamsBuilder.convert(params));
        String url = "http://localhost:8080/crawler-web/creditReport/captcha?1=1" + ParamsBuilder.convert(params);
        String result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);
        JSONObject obj = (JSONObject) JSON.parse(result);
        JSONObject subObj;
        System.err.println(obj.get("result"));

        imageCode = (String) JOptionPane.showInputDialog("请输入图片验证码:");
        params.put("loginName", "yang");
        params.put("captchaCode", imageCode);
        url = "http://localhost:8080/crawler-web/creditReport/checkLoginName?1=1" + ParamsBuilder.convert(params);
        result = executeGetWithResult(client, url);
        System.out.println(url + "\t" + result);

    }

    public static void jsonmark() {

        String json = "{\"RSP_HEAD\":{\"TRAN_SUCCESS\":\"1\"},\"RSP_BODY\":{\"PSessionId\":\"8c387482ffff003ade4a2381bfd4adaa\",\"safeValue\":\"sHo6bONZwpxdcKSPAMp7FY1WZ4sMBGTWAjZLrGuDdRujliFQA0W7jc0Gu6Z8UV8udngaCItXNiUABOuzumca8A==\",\"accounts\":[{\"alias\":null,\"cardNo\":\"4581 2309 1426 7762\",\"uuid\":\"39486fe1e655d39ca487c329046a55be\"}],\"user\":{\"locale\":{\"language\":\"zh\",\"country\":\"CN\",\"variant\":\"\",\"iso3Language\":\"zho\",\"iso3Country\":\"CHN\",\"displayLanguage\":\"中文\",\"displayCountry\":\"中国\",\"displayVariant\":\"\",\"displayName\":\"中文 (中国)\"},\"cifId\":\"0115643032009688\",\"userId\":\"2001326336\",\"uniqueId\":\"\",\"uuid\":\"d5ddd791-3272-4d18-860f-6d4bfa363009\",\"name\":\"杨帆\",\"roles\":[\"201\"],\"state\":\"\"},\"menuCode\":\"P047003\",\"safeField\":\"ReqSafeFields\",\"x-channel\":\"0\",\"cardNo\":\"39486fe1e655d39ca487c329046a55be\",\"SafeInputVersion\":null,\"description\":\"\n\t\t\n\t\t\t您可在此处了解您的信用卡人民币账户的账务概况。\n\t\t\n\t\"}}";
        JSONObject obj = (JSONObject) JSON.parse(json);
        String newjson = obj.get("RSP_BODY").toString();
        JSONObject obj1 = (JSONObject) JSON.parse(newjson);
        System.out.println("AAAAAAAAAAAA:" + obj1.get("safeValue"));

        String ob = obj1.get("accounts").toString();
        System.out.println("ob:" + ob);
        JSONObject obj2 = (JSONObject) JSON.parse(ob);

    }

    public static String getRealName() {
        String responseString = "class=\"user_text span-14 span-grey\">avcxz</span>";
        Pattern pattern = Pattern.compile("class=\"user_text span-14 span-grey\">.*</span>");
        Matcher m = pattern.matcher(responseString);
        String realname = null;
        while (m.find()) {
            String group = m.group();
            realname = group.replace("class=\"user_text span-14 span-grey\">", "").replace("</span>", "");
        }
        return realname;

    }
}
