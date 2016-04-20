package com.zhizu.crawler.creditReport;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zhizu.crawler.constant.Response;
import com.zhizu.util.DateUtils;
import com.zhizu.util.FileUtil;
import com.zhizu.util.HttpParser;
import com.zhizu.util.HttpUtils;
import com.zhizu.util.JsonUtil;
import com.zhizu.util.RandomUtil;
import com.zhizu.util.SSLUtils;

/**
 * 征信报告　
 * 
 * @author liuheli
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreditReportFetcher implements Closeable {
    private static final Logger logger = Logger.getLogger(CreditReportFetcher.class);
    private CloseableHttpClient client;
    private CookieStore cookieStore = new BasicCookieStore();
    private Map<String, String> map = new HashMap<>();
    public static final String RESOURCE_TYPE = "creditReport";
    private Long yzmStart;

    public CreditReportFetcher() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(CreditReportFetcher.class.getResourceAsStream("/certs/ipcrs.pbccrc.org.cn.keystore"),
                    "123456".toCharArray());
            SSLContext sslcontext = SSLContexts.custom().useSSL()
                    .loadTrustMaterial(trustStore, new SSLUtils.TrustAllStrategy()).build();
            sslsf = new SSLConnectionSocketFactory(sslcontext);
        } catch (Exception e) {
            e.printStackTrace();
        }

        client = HttpUtils.getHttpClient(sslsf, cookieStore);
    }

    /**
     * 加载图片验证码
     * 
     * @author liuheli
     * @return
     */
    public File loadCaptchaCode(String applyNo, String productType) {
        yzmStart = System.currentTimeMillis();
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        String url = "https://ipcrs.pbccrc.org.cn/imgrc.do?" + System.currentTimeMillis();
        HttpGet get = new HttpGet(url);
        get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/userReg.do?method=initReg");
        File file = HttpUtils.getCaptchaCodeImage(client, get);
        // monitor.set(sdf.format(new Date()));
        // monitorClient.SUCCESSStatusToLog(yzmStart, "login", "getPicCode");
        return file;
    }

    /**
     * 首次进入页面，获取html.TOKEN
     * 
     * @author liuheli
     * @return
     */
    public String loadToken(String methodtype) {
        try {
            String url = "";
            if ("initReg".equals(methodtype)) {
                url = "https://ipcrs.pbccrc.org.cn/userReg.do?method=initReg";
            } else if ("findLoginName".equals(methodtype)) {
                url = "https://ipcrs.pbccrc.org.cn/findLoginName.do?method=init";
            } else if ("checkLoginName".equals(methodtype)) {
                url = "https://ipcrs.pbccrc.org.cn/resetPassword.do?method=init";
            }
            HttpGet get = new HttpGet(url);
            if ("initReg".equals(methodtype)) {
                get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/top1.do");
            } else if ("findLoginName".equals(methodtype)) {
                // https://ipcrs.pbccrc.org.cn/page/login/loginreg.jsp
                get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/page/login/loginreg.jsp");
            } else if ("checkLoginName".equals(methodtype)) {
                get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/page/login/loginreg.jsp");

            }

            CloseableHttpResponse response = client.execute(get);
            String html = EntityUtils.toString(response.getEntity());
            logger.info(html);
            return getHtmlTokenForHtml(html);
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 获取html源码中org.apache.struts.taglib.html.TOKEN的value值
     * 
     * @author liuheli
     * @param html
     * @return
     */
    public String getHtmlTokenForHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("input");
        if (elements.size() > 0) {
            Element form = elements.get(0);
            Elements inputs = form.select("input[type=hidden]");
            Map<String, Object> params = new HashMap<String, Object>();
            for (int i = 0; i < inputs.size(); i++) {
                params.put(inputs.get(i).attr("name"), inputs.get(i).attr("value"));
                System.out.println(inputs.get(i).attr("name") + "----" + inputs.get(i).attr("value"));
                if (inputs.get(i).attr("name").equals("org.apache.struts.taglib.html.TOKEN")) {
                    return inputs.get(i).attr("value");
                }
            }
        }
        return "";
    }

    /**
     * @author liuheli
     * @param loginname
     *            用户名
     * @param password
     *            密码
     * @param tradeCode
     *            查询码
     * @param captchaCode
     *            图片验证码
     * @throws Exception
     */
    public Response loadCreditReport(String loginname, String password, String tradeCode, String captchaCode,
            String applyNo, String productType) {
        logger.info("获取征信报告[" + loginname + "," + password + "," + tradeCode + "," + captchaCode + "]");
        yzmStart = System.currentTimeMillis();
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        map.put("loginname", loginname);
        map.put("tradeCode", tradeCode);
        // toJsonString(map).toString()
        System.out.println(JsonUtil.toJSON(map).toString());
        yzmStart = System.currentTimeMillis();
        File crFile = null;
        try {
            logger.info("登录开始");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("method", "login");
            params.put("date", System.currentTimeMillis());
            params.put("loginname", loginname);
            params.put("password", password);
            params.put("_@IMGRC@_", captchaCode);
            String url = "https://ipcrs.pbccrc.org.cn/login.do";
            String responseString = HttpUtils.executePostWithResult(client, url, params);
            if (responseString.contains("登录名或密码错误")) {

                return Response.CREDIT_REPORT_WRONG_USERNAME_OR_PASSWORD;
            } else if (responseString.contains("验证码输入错误")) {
                return Response.CREDIT_REPORT_WRONG_CAPTCHA_CODE;
            }
            logger.info("登录结束");
            url = "https://ipcrs.pbccrc.org.cn/reportAction.do?method=queryReport";
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = client.execute(get);
            String html = EntityUtils.toString(response.getEntity());
            logger.info(html);
            response.close();

            url = "https://ipcrs.pbccrc.org.cn/reportAction.do";
            params = new HashMap<String, Object>();
            params.put("method", "checkTradeCode");
            params.put("code", tradeCode);
            params.put("reportformat", "21");
            HttpUtils.executePost(client, url, params);
            url = "https://ipcrs.pbccrc.org.cn/simpleReport.do?method=viewReport";
            params = new HashMap<String, Object>();
            params.put("tradeCode", tradeCode);
            params.put("reportformat", 21);
            logger.info("抓取信用报告开始");
            responseString = HttpUtils.executePostWithResult(client, url, params);
            logger.info("抓取信用报告返回源码：" + responseString);
            if (responseString.contains("查询码输入错误，请重新输入")) {
                // monitorClient.FAILStatusToLog("loginAndDowload",
                // Response.CREDIT_REPORT_ERR_WRONG_TRACECODE);

                return Response.CREDIT_REPORT_WRONG_TRACECODE;
            }
            crFile = new File(getDownPath(applyNo), System.currentTimeMillis() + ".html");
            FileUtils.write(crFile, responseString, HttpUtils.UTF_8);
            // 拷贝一份到其他位置供下载
            FileUtils.write(crFile, responseString, HttpUtils.UTF_8);
            if (crFile.length() >= 8 * 1024) {

                logger.info("抓取信用报告结束");
                this.close();
                return Response.SUCCESS;
            } else {
                logger.error("抓取信用报告失败");
                FileUtils.deleteDirectory(crFile);
                return Response.CREDIT_REPORT_WRONG;
            }
        } catch (Exception e) {
            // monitorClient.EXCEPTIONStatusToLog(getExceptionStack(e,
            // CreditReportFetcher.class.getCanonicalName()),
            // "loginAndDowload", "loadCreditReportException");

            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 登录征信中心
     * 
     * @author liuheli
     * @param loginname
     * @param password
     * @param captchaCode
     * @return
     */
    public Response loginCreditReport(String loginname, String password, String captchaCode, String applyNo,
            String productType) {
        logger.info("获取征信报告[" + loginname + "," + password + "," + captchaCode + "]");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        map.put("loginname", loginname);
        yzmStart = System.currentTimeMillis();
        try {
            logger.info("登录开始");
            String url = "https://ipcrs.pbccrc.org.cn/top1.do";
            HttpGet get = new HttpGet(url);
            get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/");
            CloseableHttpResponse response = client.execute(get);
            String responseString = EntityUtils.toString(response.getEntity());
            logger.info("登录首页返回源码：" + responseString);

            url = "https://ipcrs.pbccrc.org.cn/index1.do";
            get = new HttpGet(url);
            get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/");
            response = client.execute(get);
            responseString = EntityUtils.toString(response.getEntity());

            url = "https://ipcrs.pbccrc.org.cn/login.do?method=initLogin";
            get = new HttpGet(url);
            get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/top1.do");
            response = client.execute(get);
            responseString = EntityUtils.toString(response.getEntity());

            url = "https://ipcrs.pbccrc.org.cn/page/login/loginreg.jsp";
            get = new HttpGet(url);
            get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/top1.do");
            response = client.execute(get);
            responseString = EntityUtils.toString(response.getEntity());
            logger.info("登录首页返回源码：" + responseString);

            String token = getHtmlTokenForHtml(responseString);
            logger.info("获取登录首页返回源码token结束：token=" + token);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("org.apache.struts.taglib.html.TOKEN", token);
            params.put("method", "login");
            params.put("date", System.currentTimeMillis());
            params.put("loginname", loginname);
            params.put("password", password);
            params.put("_@IMGRC@_", captchaCode);
            url = "https://ipcrs.pbccrc.org.cn/login.do";
            HttpPost post = HttpUtils.post(url, params);
            post.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/page/login/loginreg.jsp");
            responseString = HttpUtils.executePostWithResult(client, post);
            logger.info("登录返回源码：" + responseString);
            if (responseString.contains("登录名或密码错误")) {
                // monitorClient.FAILStatusToLog("login",
                // Response.CREDIT_REPORT_ERR_WRONG_USERNAME_OR_PASSWORD);
                return Response.CREDIT_REPORT_WRONG_USERNAME_OR_PASSWORD;
            } else if (responseString.contains("验证码输入错误,请重新输入")) {
                // monitorClient.FAILStatusToLog("login",
                // Response.CREDIT_REPORT_ERR_WRONG_CAPTCHA_CODE.toString());

                return Response.CREDIT_REPORT_WRONG_CAPTCHA_CODE;
            } else if (responseString.contains("请使用做过身份认证的用户重新登录")) {

                return Response.CREDIT_REPORT_WRONG_USER_ORDERUSER;
            } else if (responseString.contains("您的账户已经销户")) {

                return Response.CREDIT_REPORT_WRONG_FINDLOGINNAME;
            } else if (responseString.contains("密码连续输入错误。系统已对您的登录名进行锁定")
                    || responseString.contains("您的登录名已因密码连续输入错误次数超过")) {

                return Response.CREDIT_REPORT_WRONG_PASSWORD_TOOMUCH;
            } else if (responseString.contains("<span id=\"_error_field_\">")) {

                return Response.SYSTEM_WRONG_UNKNOWN;
            }
            logger.info("登录成功");

            url = "https://ipcrs.pbccrc.org.cn/top2.do";
            get = new HttpGet(url);
            get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/login.do");
            response = client.execute(get);
            responseString = EntityUtils.toString(response.getEntity());
            logger.info("top2.do返回源码：" + responseString);

            url = "https://ipcrs.pbccrc.org.cn/menu.do";
            get = new HttpGet(url);
            get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/login.do");
            response = client.execute(get);
            responseString = EntityUtils.toString(response.getEntity());
            logger.info("menu.do返回源码：" + responseString);

            url = "https://ipcrs.pbccrc.org.cn/welcome.do";
            get = new HttpGet(url);
            get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/login.do");
            response = client.execute(get);
            responseString = EntityUtils.toString(response.getEntity());
            logger.info("welcome.do返回源码：" + responseString);

            logger.info("开始判断用户是否已经生成个人信用报告");
            url = "https://ipcrs.pbccrc.org.cn/reportAction.do?method=applicationReport";
            get = new HttpGet(url);
            get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/menu.do");
            response = client.execute(get);
            String html = EntityUtils.toString(response.getEntity());
            logger.info(html);
            response.close();
            String replacehtml = getReplaceHtml(html);
            token = getHtmlTokenForHtml(html);
            logger.info("获取页面token结束：token=" + token);

            Response response1;
            if (replacehtml.contains("个人信用报告</label>&nbsp;&nbsp;<fontclass='span-redspan-12'>(已生成)")) {
                logger.info("用户已经生成个人信用报告");
                Map<String, Object> map = new HashMap<>();
                map.put("htmlToken", token);
                // monitorClient.SUCCESSStatusToLog("login", "loginSuccess");
                response1 = new Response(true, Response.CREDIT_REPORT_SUC_CREDIT_YES, map);
            } else if (replacehtml.contains("个人信用报告</label>&nbsp;&nbsp;<fontclass='span-redspan-12'>(处理中)")) {
                logger.info("个人信用报告申请处理中");
                response1 = new Response(true, Response.CREDIT_REPORT_SUC_CREDIT_DOING);
            } else {
                logger.info("用户没有生成个人信用报告或验证未通过");
                // 获取回答问题的页面
                params = new HashMap<String, Object>();
                params.put("method", "checkishasreport");
                params.put("org.apache.struts.taglib.html.TOKEN", token);
                params.put("ApplicationOption", "21");
                params.put("authtype", "2");
                url = "https://ipcrs.pbccrc.org.cn/reportAction.do?method=checkishasreport";
                logger.info("用户没有生成个人信用报告--获取申请问题集开始");
                // post = new HttpPost(url);
                // responseString = HttpUtils.executePostWithResult(client, url,
                // params);
                post = HttpUtils.post(url, params);
                post.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/reportAction.do?method=applicationReport");
                responseString = HttpUtils.executePostWithResult(client, post);
                logger.info("用户没有生成个人信用报告--获取申请问题集返回源码：" + responseString);
                if (responseString.contains("距离身份验证结束时间")) {
                    logger.info("用户没有生成个人信用报告--获取申请问题集成功");
                    File file = new File(getDownPath(applyNo), "question");
                    File crFile = new File(file, token + ".html");
                    FileUtils.write(crFile, responseString, HttpUtils.UTF_8);
                    Map<String, Object> map = HttpParser.getAllHiddenFromInput(responseString);
                    logger.info("返回的Map：" + map);
                    response1 = new Response(true, Response.CREDIT_REPORT_SUC_CREDIT_NO, map);
                } else if (responseString.contains("目前系统尚未收录足够的信息对您的身份进行")) {
                    logger.info("用户没有生成个人信用报告--目前系统尚未收录足够的信息对您的身份进行问题验证");
                    response1 = new Response(false, Response.CREDIT_REPORT_ERR_WRONG_NOMESSAGE_VALIDATE);
                } else if (responseString.contains("timeout.jsp")) {
                    logger.info("由于您长时间未进行任何操作，系统已退出，如需继续使用请您重新登录。");
                    response1 = new Response(false, Response.ERR_CODE_CONN_TIMEOUT);
                } else {
                    logger.info("用户没有生成个人信用报告--获取申请问题集失败");
                    response1 = new Response(false, Response.CREDIT_REPORT_ERR_CREDIT_NO);
                }
            }
            return response1;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 申请查询码---提交申请问题结果
     * 
     * @author liuheli
     * @param loginname
     * @param password
     * @param captchaCode
     * @return
     */
    @SuppressWarnings("unchecked")
    public Response submitKBACreditReport(String questions, String applyNo, String productType) {
        logger.info("提交申请问题结果[" + questions + "]");
        try {
            yzmStart = System.currentTimeMillis();

            // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // monitor.setCreateDate(sdf.format(new Date()));
            // 规则mark :kbaList[0].answerresult kbaList[0].options 非空且相等 ，参数标识。
            String url = "https://ipcrs.pbccrc.org.cn/reportAction.do?method=submitKBA";
            Map<String, Object> params = (Map<String, Object>) JSON.parse(questions);
            logger.info("提交申请问题结果转Map：" + params);
            // Referer:
            // https://ipcrs.pbccrc.org.cn/reportAction.do?method=checkishasreport
            HttpPost post = HttpUtils.post(url, params);
            post.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/reportAction.do?method=checkishasreport");
            String responseString = HttpUtils.executePostWithResult(client, post);
            // String responseString = HttpUtils.executePostWithResult(client,
            // url, params);
            logger.info("提交申请问题结果返回源码：" + responseString);
            if (responseString.contains("您的信用信息查询请求已提交，请在24小时后访问平台获取结果，身份验证不通过请重新申请。")) {
                this.close();
                return Response.SUCCESS;
            } else if (responseString.contains("请勿重复提交申请")) {
                this.close();
                return Response.SUCCESS;
            } else if (responseString.contains("当前页面已过期")) {
                return Response.CREDIT_REPORT_WRONG_PAGEINVAILD_REAWRPASSWORD;

            } else {
                return Response.SYSTEM_WRONG_UNKNOWN;
            }
        } catch (Exception e) {

            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 获取征信报告
     * 
     * @author liuheli
     * @param tradeCode
     * @param token
     * @return
     */
    public Response downloadCreditR(String applyNo, String loginName, String idCardNo, String tradeCode, String token,
            String productType) {
        logger.info("获取征信报告[" + applyNo + "," + loginName + "," + idCardNo + "," + tradeCode + "," + token + "]");
        File crFile = null;
        map.put("loginName", loginName);
        map.put("tradeCode", tradeCode);
        try {

            yzmStart = System.currentTimeMillis();
            // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // monitor.setCreateDate(sdf.format(new Date()));
            String url = "https://ipcrs.pbccrc.org.cn/reportAction.do?method=queryReport";
            HttpGet get = new HttpGet(url);
            // Referer https://ipcrs.pbccrc.org.cn/menu.do
            get.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/menu.do");
            CloseableHttpResponse response = client.execute(get);
            String html = EntityUtils.toString(response.getEntity());
            logger.info(html);
            response.close();

            url = "https://ipcrs.pbccrc.org.cn/reportAction.do";

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("method", "checkTradeCode");
            params.put("code", tradeCode);
            params.put("reportformat", "21");
            // Referer
            // 未看到真实数据根据规则判断为https://ipcrs.pbccrc.org.cn/reportAction.do?method=queryReport
            HttpUtils.executePost(client, url, params);

            url = "https://ipcrs.pbccrc.org.cn/simpleReport.do?method=viewReport";
            params = new HashMap<String, Object>();
            params.put("tradeCode", tradeCode);
            params.put("reportformat", 21);
            // Referer
            // 未看到真实数据根据规则判断为https://ipcrs.pbccrc.org.cn/reportAction.do?method=queryReport
            logger.info("抓取信用报告开始");
            String responseString = HttpUtils.executePostWithResult(client, url, params);
            logger.info("抓取信用报告返回源码：" + responseString);
            if (responseString.contains("查询码输入错误，请重新输入")) {
                return Response.CREDIT_REPORT_WRONG_TRACECODE;
            } else if (responseString.contains("身份验证码不正确")) {
                return Response.CREDIT_REPORT_WRONG_TRACECODE;
            }
            crFile = new File(getDownPath(applyNo), applyNo + "_" + productType + "_" + loginName + ".html");
            String fileName = crFile.getAbsolutePath();
            logger.info("抓取信用报告存放路径：" + fileName);
            if (responseString.contains("报告编号") & responseString.contains("姓名")) {
                // 拷贝一份到其他位置供下载
                // Date now = new Date();
                // File destFile = new File(creditreportDir4download,
                // DateUtils.formatDate(now, "yyyy") + "/"
                // + DateUtils.formatDate(now, "MM") + "/" +
                // DateUtils.formatDate(now, "dd"));
                // FileUtils.forceMkdir(destFile);
                // destFile = new File(destFile, crFile.getName());
                // logger.info("拷贝征信报告到:" + destFile.getAbsolutePath());
                // FileUtils.write(destFile, responseString, HttpUtils.UTF_8);
                // 发送到kafka里去解析
                File destFile = crFile;
                FileUtils.write(crFile, responseString, HttpUtils.UTF_8);

                logger.info("抓取信用报告结束");
                this.close();
                return Response.SUCCESS;
            } else {
                logger.error("抓取信用报告失败");
                FileUtils.deleteDirectory(crFile);
                return Response.CREDIT_REPORT_WRONG;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 找回登录名,填写用户信息
     * 
     * @author liuheli
     * @param name
     * @param idtype
     * @param idNo
     * @param captchaCode
     * @param token
     * @return
     */
    public Response findLoginName(String name, String idtype, String idNo, String captchaCode, String token,
            String applyNo, String productType) {
        logger.info("找回登录名,填写用户信息[" + name + "," + idNo + "," + captchaCode + "]");
        map.put("name", name);
        map.put("idtype", idtype);

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        yzmStart = System.currentTimeMillis();
        try {
            logger.info("找回登录名开始");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("org.apache.struts.taglib.html.TOKEN", token);
            params.put("method", "findLoginName");
            params.put("name", name);
            params.put("certType", idtype);
            params.put("certNo", idNo);
            params.put("_@IMGRC@_", captchaCode);
            String url = "https://ipcrs.pbccrc.org.cn/findLoginName.do";
            // https://ipcrs.pbccrc.org.cn/findLoginName.do?method=init

            HttpPost post = HttpUtils.post(url, params);
            post.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/findLoginName.do?method=init");
            String responseString = HttpUtils.executePostWithResult(client, post);

            // String responseString = HttpUtils.executePostWithResult(client,
            // url, params);
            logger.info("找回登录名返回源码：" + responseString);
            if (responseString.contains("验证码输入错误,请重新输入")) {
                logger.info("验证码输入错误,请重新输入");
                return Response.CREDIT_REPORT_WRONG_CAPTCHA_CODE;
            } else if (responseString.contains("未注册或已销户")) {
                logger.info("您无法使用该功能找回登录名，可能是因为您的安全等级为低、未注册或已销户，请重新注册。");
                return Response.CREDIT_REPORT_WRONG_FINDLOGINNAME;
            } else if (responseString.contains("您的登录名已短信发送至平台预留的手机号码，请查收。")) {
                logger.info("您的登录名已短信发送至平台预留的手机号码，请查收。");
                return Response.SUCCESS;
            } else {
                logger.info("找回登录名,填写用户信息。---其他错误");
                return Response.SYSTEM_WRONG_UNKNOWN;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 找回密码,1.填写登录名
     * 
     * @author liuheli
     * @param loginname
     * @param captchaCode
     * @param token
     * @return
     */
    // public Response checkLoginName(String loginname, String captchaCode,
    // String token, String applyNo,
    // String productType) {
    public Response checkLoginName(HttpServletRequest request) {
        String captchaCode = request.getParameter("captchaCode").toString();
        String token = request.getParameter("token").toString();
        String applyNo = request.getParameter("applyNo").toString();
        String productType = request.getParameter("productType").toString();
        String name = request.getParameter("name").toString();
        String certNo = request.getParameter("idNo").toString();
        String loginname = request.getParameter("loginName").toString();
        String certType = request.getParameter("idType").toString();
        if (certNo.equals("") || certNo == null) {
            logger.info("未填写证件号或证件类型");
            return new Response(false, Response.ERR_CODE_INTER_PRAM);
        }
        map.put("loginname", request.getParameter("loginName").toString());
        logger.info("找回密码,填写登录名[" + loginname + "," + captchaCode + "]");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        yzmStart = System.currentTimeMillis();
        try {
            logger.info("找回密码填写登录名开始");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("org.apache.struts.taglib.html.TOKEN", token);
            params.put("method", "checkLoginName");
            params.put("loginname", loginname);

            params.put("name", name);
            params.put("certType", certType);
            params.put("certNo", certNo);

            params.put("_@IMGRC@_", captchaCode);
            String url = "https://ipcrs.pbccrc.org.cn/resetPassword.do";
            // Referer: https://ipcrs.pbccrc.org.cn/resetPassword.do?method=init
            HttpPost post = HttpUtils.post(url, params);
            post.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/resetPassword.do");
            String responseString = HttpUtils.executePostWithResult(client, post);
            logger.info("找回密码填写登录名返回源码：" + responseString);
            if (responseString.contains("确认新密码")) {
                logger.info("找回密码填写登录名结束");
                String mobileTel = getRealName(responseString);
                Map<String, Object> map = new HashMap<>();
                map.put("mobileTel", mobileTel);
                Response response = new Response(map);
                return response;
            } else if (responseString.contains("提交的重置密码申请正在审核中，请耐心等待。")) {
                logger.info("提交的重置密码申请正在审核中，请耐心等待。");
                return Response.CREDIT_REPORT_WRONG_CHECKLOGINNAME_NO;
            } else if (responseString.contains("验证码输入错误,请重新输入")) {
                logger.info("验证码输入错误,请重新输入");
                return Response.CREDIT_REPORT_WRONG_CAPTCHA_CODE;
            } else if (responseString.contains("三项标识不匹配")) {
                logger.info("三项标识不匹配");
                return Response.CREDIT_REPORT_WRONG_THREE_IDENTIFICATION;
            } else if (responseString.contains("登录名不存在")) {
                logger.info("登录名不存在");
                return Response.CREDIT_REPORT_WRONG_NOLOGINNAME;
            } else {
                return Response.SYSTEM_WRONG_UNKNOWN;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 找回密码 手机验证
     * 
     * @author heqiushuang
     * @param
     * @return
     */
    public Response resetPasswordMobileVerifyCode(String applyNo, String productType) {

        logger.info("获取已绑定手机验证码");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        yzmStart = System.currentTimeMillis();
        try {
            logger.info("获取找回密码手机验证码开始");
            Map<String, Object> params = new HashMap<String, Object>();
            String num = RandomUtil.getNextInt18();
            params.put("method", "getAcvitaveCode");
            params.put("counttime", "119");
            String url = "https://ipcrs.pbccrc.org.cn/resetPassword.do?num=" + num;
            HttpPost post = HttpUtils.post(url, params);
            post.setHeader("X-requested-with", "XMLHttpRequest");
            post.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/resetPassword.do");
            post.setHeader("Accept", "text/plain, */*; q=0.01");
            post.setHeader("Accept-Encoding", "gzip, deflate");
            post.setHeader(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)");
            post.setHeader("Accept-Language", "zh-CN");
            post.setHeader("Host", "ipcrs.pbccrc.org.cn");
            post.setHeader("Cache-Control", "no-cache");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            String responseString = HttpUtils.executePostWithResult(client, post);
            logger.info("获取手机验证码返回源码：" + responseString);
            if (responseString == null || responseString.equals("")) {
                logger.info("获取手机验证码失败");
                return Response.CREDIT_REPORT_WRONG_ACVITAVECODE;
            }
            logger.info("获取手机验证码结束");
            // map.put("tcId", responseString);
            // Response response = new Response(map);
            return Response.SUCCESS;
        } catch (Exception e) {
            // monitorClient.EXCEPTIONStatusToLog(getExceptionStack(e,
            // CreditReportFetcher.class.getCanonicalName()),
            // "findPassword", "resetPasswordMobileVerifyCodeException");
            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }

    }

    /**
     * 找回密码,2.填写新密码
     * 
     * @author liuheli
     * @param loginname
     * @param captchaCode
     * @param token
     * @return
     */
    public Response resetPassword(String password, String confirmpassword, String verifyCode, String applyNo,
            String productType) {
        logger.info("找回密码,填写新密码[" + password + "," + confirmpassword + "]");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        yzmStart = System.currentTimeMillis();
        try {
            logger.info("找回密码填写新密码开始");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("method", "resetPassword");
            params.put("counttime", "1");
            params.put("password", password);
            params.put("confirmpassword", confirmpassword);
            params.put("verifyCode", verifyCode);

            String url = "https://ipcrs.pbccrc.org.cn/resetPassword.do";
            // https://ipcrs.pbccrc.org.cn/resetPassword.do
            HttpPost post = HttpUtils.post(url, params);
            post.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/resetPassword.do");
            String responseString = HttpUtils.executePostWithResult(client, post);
            // String responseString = HttpUtils.executePostWithResult(client,
            // url, params)
            System.out.println(responseString);
            Response response1;
            if (responseString.contains("动态码输入错误或已过期")) {
                response1 = new Response(false, Response.CREDIT_REPORT_ERR_WRONG_WRONG_SMS_CODE);
                logger.info("动态码输入错误");
            } else {
                logger.info("找回密码填写新密码--获取页面token");
                String token = getHtmlTokenForHtml(responseString);
                // 返回回答问题页面
                params = new HashMap<String, Object>();
                params.put("method", "chooseCertify");
                params.put("org.apache.struts.taglib.html.TOKEN", token);
                params.put("authtype", "2");
                url = "https://ipcrs.pbccrc.org.cn/resetPassword.do";
                logger.info("找回密码填写新密码--获取申请问题集开始");
                responseString = HttpUtils.executePostWithResult(client, url, params);
                logger.info("找回密码填写新密码返回源码：" + responseString);
                if (responseString.contains("距离身份验证结束时间")) {
                    logger.info("找回密码填写新密码--获取申请问题集成功");
                    File crFile = new File(getDownPath(applyNo), token + ".html");
                    FileUtils.write(crFile, responseString, HttpUtils.UTF_8);
                    Map<String, Object> map = HttpParser.getAllHiddenFromInput(responseString);
                    response1 = new Response(true, Response.CREDIT_REPORT_SUC_CREDIT_NO, map);
                } else {
                    response1 = new Response(false, Response.CREDIT_REPORT_ERR_CREDIT_NO);
                    logger.info("找回密码填写新密码--获取申请问题集失败");
                }
            }

            return response1;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 找回密码,3.提交申请问题结果
     * 
     * @author liuheli
     * @param questions
     * @return
     */
    @SuppressWarnings("unchecked")
    public Response saveKbaApply(String questions, String applyNo, String productType) {
        logger.info("提交申请问题结果[" + questions + "]");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        yzmStart = System.currentTimeMillis();
        try {
            String url = "https://ipcrs.pbccrc.org.cn/resetPassword.do";
            Map<String, Object> params = (Map<String, Object>) JSON.parse(questions);
            params.put("method", "saveKbaApply");
            logger.info("提交申请问题结果转Map：" + params);
            String responseString = HttpUtils.executePostWithResult(client, url, params);
            logger.info("提交申请问题结果返回源码：" + responseString);
            if (responseString.contains("您的重置密码申请提交成功，请耐心等待。您可在24小时后登录平台查看验证结果")) {
                this.close();
                return Response.SUCCESS;
            } else if (responseString.contains("当前页面已过期")) {
                return Response.CREDIT_REPORT_WRONG_PAGEINVAILD_REAWRPASSWORD;

            } else {
                return Response.SYSTEM_WRONG_UNKNOWN;
            }
        } catch (Exception e) {

            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 注册
     * 
     * @author liuheli
     * @param name
     * @param idtype
     * @param idNo
     * @param captchaCode
     * @param token
     * @return
     */
    public Response registerUser(String name, String idtype, String idNo, String captchaCode, String token,
            String applyNo, String productType) {
        logger.info("注册征信报告新用户[" + name + "," + idNo + "," + captchaCode + "]");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        map.put("name", name);
        map.put("idtype", idtype);
        map.put("idNo", idNo);
        yzmStart = System.currentTimeMillis();
        try {
            logger.info("注册开始");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("org.apache.struts.taglib.html.TOKEN", token);
            params.put("method", "checkIdentity");
            params.put("date", System.currentTimeMillis());
            params.put("userInfoVO.name", name);
            params.put("userInfoVO.certType", idtype);
            params.put("userInfoVO.certNo", idNo);
            params.put("_@IMGRC@_", captchaCode);
            params.put("1", "on");
            String url = "https://ipcrs.pbccrc.org.cn/userReg.do";
            // String
            // Referer="https://ipcrs.pbccrc.org.cn/userReg.do?method=initReg";
            HttpPost post = HttpUtils.post(url, params);
            post.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/userReg.do?method=initReg");
            String responseString = HttpUtils.executePostWithResult(client, post);
            // String responseString = HttpUtils.executePostWithResult(client,
            // url, params);
            logger.info("注册返回源码：" + responseString);
            if (responseString.contains("目前系统尚未收录您的个人信息，无法进行注册。")) {
                return Response.CREDIT_REPORT_WRONG_REG;
            } else if (responseString.contains("验证码输入错误，请重新输入。")) {
                return Response.CREDIT_REPORT_WRONG_CAPTCHA_CODE;
            } else if (responseString.contains("您已注册过用户，请返回首页直接登录。")) {
                return Response.CREDIT_REPORT_WRONG_HAVEUSER;
            }
            logger.info("注册结束");

            token = getHtmlTokenForHtml(responseString);
            Map<String, Object> map = new HashMap<>();
            map.put("htmlToken", token);
            Response response = new Response(map);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 获取手机验证码
     * 
     * @author liuheli
     * @param mobileTel
     * @return
     */
    public Response getMobileVerifyCode(String mobileTel, String applyNo, String productType) {
        logger.info("获取手机验证码[" + mobileTel + "]");
        map.put("mobileTel", mobileTel);
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        yzmStart = System.currentTimeMillis();
        try {
            logger.info("获取手机验证码开始");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("method", "getAcvitaveCode");
            params.put("mobileTel", mobileTel);
            // old url String url =
            // "https://ipcrs.pbccrc.org.cn/userReg.do?num=" + num;
            String url = "https://ipcrs.pbccrc.org.cn/userReg.do";
            // https://ipcrs.pbccrc.org.cn/userReg.do
            HttpPost post = HttpUtils.post(url, params);
            post.setHeader("X-requested-with", "XMLHttpRequest");
            post.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/userReg.do");
            post.setHeader("Accept", "text/plain, */*; q=0.01");
            post.setHeader("Accept-Encoding", "gzip, deflate");
            post.setHeader(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)");
            post.setHeader("Accept-Language", "zh-cn");
            post.setHeader("Host", "ipcrs.pbccrc.org.cn");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            String responseString = HttpUtils.executePostWithResult(client, post);
            // post.setHeader("Referer",
            // "https://ipcrs.pbccrc.org.cn/userReg.do");
            // String responseString = HttpUtils.executePostWithResult(client,
            // post);
            logger.info("获取手机验证码返回源码：" + responseString);
            if (responseString == null || responseString.equals("")) {
                logger.info("获取手机验证码失败");
                return Response.CREDIT_REPORT_WRONG_ACVITAVECODE;
            }
            logger.info("获取手机验证码结束");
            Map<String, Object> map = new HashMap<>();
            map.put("tcId", responseString);
            Response response = new Response(map);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 验证登陆用户名是否存在
     * 
     * @author liuheli
     * @param loginname
     * @return
     */
    public Response checkRegLoginnameHasUsed(String loginname, String applyNo, String productType) {
        logger.info("验证登陆用户名是否存在[" + loginname + "]");
        map.put("loginname", loginname);
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        yzmStart = System.currentTimeMillis();
        try {
            logger.info("验证登陆用户名是否存在开始");
            Map<String, Object> params = new HashMap<String, Object>();
            String num = RandomUtil.getNextInt18();
            params.put("method", "checkRegLoginnameHasUsed");
            params.put("loginname", loginname);
            String url = "https://ipcrs.pbccrc.org.cn/userReg.do?num=" + num;
            HttpPost httpPost = HttpUtils.post(url, params);
            httpPost.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/userReg.do");
            httpPost.setHeader("Accept", "text/plain, */*; q=0.01");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate");
            httpPost.setHeader("Accept-Language", "zh-CN");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
            String responseString = HttpUtils.executePostWithResult(client, httpPost);
            logger.info("验证登陆用户名是否存在返回源码：" + responseString);
            if (responseString.equals("1")) {
                // 登录名已经存在
                return Response.CREDIT_REPORT_WRONG_LOGINNAME;
            }
            logger.info("验证登陆用户名是否存在结束");
            // Response response = new Response(responseString);
            // monitorClient.SUCCESSStatusToLog("register",
            // "checkRegLoginnameHasUsedSuccess");
            return Response.SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    /**
     * 提交补充信息，完成注册
     * 
     * @author liuheli
     * @param loginName
     * @param password
     * @param confirmpassword
     * @param mobileTel
     * @param email
     * @param verifyCode
     * @param tcId
     * @param htmltoken
     * @return
     */
    public Response saveUser(String loginName, String password, String confirmpassword, String mobileTel, String email,
            String verifyCode, String tcId, String htmltoken, String applyNo, String productType) {
        logger.info("提交补充信息，完成注册[" + loginName + "," + verifyCode + "," + tcId + "," + htmltoken + "]");
        map.put("loginName", loginName);
        map.put("mobileTel", mobileTel);
        map.put("email", email);
        map.put("verifyCode", verifyCode);
        map.put("tcId", tcId);
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // monitor.setCreateDate(sdf.format(new Date()));
        yzmStart = System.currentTimeMillis();
        try {
            logger.info("提交补充信息，完成注册开始");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("org.apache.struts.taglib.html.TOKEN", htmltoken);
            params.put("method", "saveUser");
            params.put("counttime", "1");
            params.put("tcId", tcId);
            params.put("userInfoVO.loginName", loginName);
            params.put("userInfoVO.password", password);
            params.put("userInfoVO.confirmpassword", confirmpassword);
            params.put("userInfoVO.email", email);
            params.put("userInfoVO.mobileTel", mobileTel);
            params.put("userInfoVO.verifyCode", verifyCode);

            String url = "https://ipcrs.pbccrc.org.cn/userReg.do";
            HttpPost httpPost = HttpUtils.post(url, params);
            httpPost.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/userReg.do");
            httpPost.setHeader("Accept", "text/html, application/xhtml+xml, */*");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate");
            httpPost.setHeader("Accept-Language", "zh-CN");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
            httpPost.setHeader("Referer", "https://ipcrs.pbccrc.org.cn/userReg.do");
            String responseString = HttpUtils.executePostWithResult(client, httpPost);
            logger.info("提交补充信息，完成注册返回源码：" + responseString);
            // 重新获取页面token
            String token = getHtmlTokenForHtml(responseString);
            Map<String, Object> map = new HashMap<>();
            map.put("htmlToken", token);
            Response response;
            if (responseString.contains("此手机号码已注册")) {
                // 注册失败
                logger.info("提交补充信息，完成注册失败--此手机号码已注册");
                response = new Response(false, Response.CREDIT_REPORT_ERR_WRONG_REPHONE, map);
            } else if (responseString.contains("动态码输入错误或已过期")) {

                logger.info("动态码输入错误或过期，完成注册失败");
                response = new Response(false, Response.CREDIT_REPORT_ERR_WRONG_WRONG_SMS_CODE, map);

            } else if (!responseString.contains("您在个人信用信息平台已注册成功。")) {

                logger.info("提交补充信息，完成注册失败");
                response = new Response(false, Response.CREDIT_REPORT_ERR_WRONG_SAVEUSER, map);

            } else {
                logger.info("提交补充信息，完成注册结束");
                this.close();
                response = new Response(true, Response.ERROR_CODE_SUCCESS);
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.SYSTEM_ERROR;
        }
    }

    public static String getRealName(String responseString) {
        Pattern pattern = Pattern.compile("class=\"user_text span-14 span-grey\">.*</span>");
        Matcher m = pattern.matcher(responseString);
        String realname = null;
        while (m.find()) {
            String group = m.group();
            realname = group.replace("class=\"user_text span-14 span-grey\">", "").replace("</span>", "");
        }
        return realname;

    }

    public static String getReplaceHtml(String html) {
        String newhtml = html.replaceAll("\t", "").replaceAll("\r\n", "").replaceAll(" ", "");
        System.out.println(newhtml);
        return newhtml;
    }

    public String getExceptionStack(Exception e, String className) {
        if (e != null) {
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            String result = "";
            result = stackTraceElements[0].getLineNumber() + ":" + stackTraceElements[0].toString() + ":" + className;
            return result;
        } else {
            return "";
        }
    }

    public void close() {
        if (this.client != null) {
            try {
                this.client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取征信报告的RawData，类型为String
     * 
     * @author liuheli
     * @param applyNo
     * @param loginName
     * @param src
     * @param e
     * @param response
     * @return
     * @throws IOException
     */
    // public static RawData getRawData4CreditReport(String applyNo, String
    // idCardNo, String loginName, String src,
    // String e, String response) throws IOException {
    // // 生产方与消费方约定好每个字段存放的具体内容
    // Builder builder = RawData.newBuilder();
    // String serverIp = InetAddress.getLocalHost().getHostAddress();
    // builder.setProgress("1/1");
    // builder.setGenericInfo(GenericInfo.newBuilder().setApplyNo(applyNo)
    // .setIdCardNo(idCardNo == null ? "" :
    // idCardNo).setServerIp(serverIp).build());
    // builder.setCategoryInfo(CategoryInfo.newBuilder().setSrc(src).setA("creditReport").setB(loginName).setC("")
    // .setD("").setE(e).build());
    // // baiscinfo 可能会分布在多个文件里，需要生产方与消费方要约定好如何取数据
    // // content.add(ByteBuffer.wrap(FileUtils.readFileToByteArray(new
    // // File("D:/tmp/gd.txt"))));// 从文件里读取
    // List<ByteBuffer> content = new ArrayList<>();
    // content.add(ByteBuffer.wrap("".getBytes()));
    // content.set(0, ByteBuffer.wrap(response.getBytes("utf-8")));//
    // 直接由string转化
    // builder.setContent(content);
    // RawData rawData = builder.build();
    // return rawData;
    // }

    private String getDownPath(String applyNo) {
        return FileUtil.getCrawlerFilePath(null, applyNo, "creditReport");
    }

    public static void main(String[] args) throws IOException {
        Date now = new Date();
        File destFile = new File("c:/tmp", DateUtils.formatDate(now, "yyyy") + "/" + DateUtils.formatDate(now, "MM")
                + "/" + DateUtils.formatDate(now, "dd"));
        FileUtils.forceMkdir(destFile);
    }
}
