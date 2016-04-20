package com.zhizu.web;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhizu.crawler.constant.Response;
import com.zhizu.crawler.creditReport.CreditReportFetcher;
import com.zhizu.util.CacheUtils;

@Controller
@RequestMapping("/testController")
public class testController extends BaseController {
    @RequestMapping("/test")
    // @PathVariable String id,
    public String showUser(HttpServletRequest request) {
        System.out.println("1111");
        return "/test";

    }

    /**
     * 获取图片验证码
     * 
     * @author liuheli
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/captcha")
    @ResponseBody
    public Response getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        Response result = null;
        CreditReportFetcher creditReport = getCreditReport(request, true);

        try {
            // CreditReportFetcher creditReport = new CreditReportFetcher();
            Map<String, String> params = null;
            // String applyNo = params.get("applyNo");// 进件ID
            // String productType = params.get("productType");
            String applyNo = "123456";// 进件ID
            String productType = "creditReport";
            // , params.get("loginName")
            File captchaCodeFile = creditReport.loadCaptchaCode(applyNo, productType);

            if (captchaCodeFile == null) {
                result = new Response(false, Response.MOBILE_ERR_CODE_UNNVAILABLE_CAPTCHA_CODE);
                // updateUserRequestInfo(request, result.toString());
                return result;
            }
            String url = getCaptchaPath(request) + captchaCodeFile.getName();
            result = new Response(url);
            return result;
        } catch (Exception e) {
            // logger.error(e.getMessage(), e);
            result = Response.MOBILE_UNNVAILABLE_CAPTCHA_CODE;
            // updateUserRequestInfo(request, result.toString());
            return result;
        }
    }

    // /**
    // * 登录并下载征信报告
    // *
    // * @author liuheli
    // * @param request
    // * @param loginname
    // * @param password
    // * @param tradeCode
    // * @param captchaCode
    // * @return
    // */
    // @RequestMapping(value = "/login_down", produces =
    // "application/json;charset=UTF-8")
    // @ResponseBody
    // public Response login(HttpServletRequest request, @RequestParam(required
    // = true) String loginName,
    // @RequestParam(required = true) String passWord, @RequestParam(required =
    // true) String tradeCode,
    // @RequestParam(required = true) String captchaCode) {
    // Response result = null;
    // CreditReportFetcher creditReport = getCreditReport(request);
    // if (creditReport == null) {
    // result = Response.DEFAULT;
    // // updateUserRequestInfo(request, result.toString());
    // return result;
    // }
    // Map<String, String> params = getParams(request);
    // String applyNo = params.get("applyNo");// 进件ID
    // String productType = params.get("productType");
    // try {
    // result = creditReport.loadCreditReport(loginName, passWord, tradeCode,
    // captchaCode, applyNo, productType);
    // // updateUserRequestInfo(request, result.toString());
    // return result;
    // } finally {
    // // removeCreditReport(request);
    // }
    // }
    //
    // @RequestMapping(value = "/download", produces =
    // "text/html;charset=UTF-8")
    // @ResponseBody
    // public String download(String applyNo) {
    // try {
    // CrawlerResources crawlerResources =
    // crawlerResourcesService.query(applyNo,
    // CreditReportFetcher.RESOURCE_TYPE);
    // if (StringUtils.isNotBlank(crawlerResources.getContent())) {
    // return crawlerResources.getContent();
    // }
    // return FileUtils.readFileToString(new
    // File(crawlerResources.getResourcePath()));
    // } catch (Exception e) {
    // // logger.error(e.getMessage(), e);
    // return null;
    // }
    // }
    //
    // /*
    // *
    // ========================================以下Http简版征信报告======================
    // */
    //
    /**
     * 登录
     *
     * @author liuheli
     * @param request
     * @param loginname
     * @param password
     * @param captchaCode
     * @return
     */
    @RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Response login(HttpServletRequest request, @RequestParam(required = true) String loginName,
            @RequestParam(required = true) String passWord, @RequestParam(required = true) String captchaCode) {
        Response result = null;
        try {
            // CreditReportFetcher creditReport = getCreditReport(request);
            // CreditReportFetcher creditReport = new CreditReportFetcher();
            CreditReportFetcher creditReport = getCreditReport(request);

            Map<String, String> params = getParams(request);
            String applyNo = params.get("applyNo");// 进件ID
            String productType = params.get("productType");
            result = creditReport.loginCreditReport(loginName, passWord, captchaCode, applyNo, productType);
            return result;
        } catch (Exception e) {
            result = Response.SYSTEM_ERROR;
            return result;
        }
    }

    /*
     * ========================================以下Http简版征信报告======================
     */

    private CreditReportFetcher getCreditReport(HttpServletRequest request) {
        return getCreditReport(request, false);
    }

    /**
     * httpclient 登陆简版征信报告
     * 
     * @param request
     * @param alwaysNew
     *            session里面有 则执行其 close方法后再创建
     * @return
     */
    private CreditReportFetcher getCreditReport(HttpServletRequest request, boolean alwaysNew) {
        CreditReportFetcher result = null;
        if (alwaysNew) {
            if (CacheUtils.getHttpCreditReportFetcher(request.getParameter(LEND_REQUEST_ID_NAME)) != null) {
                try {
                    Closeable closeable = (Closeable) CacheUtils.getHttpCreditReportFetcher(request
                            .getParameter(LEND_REQUEST_ID_NAME));
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            result = new CreditReportFetcher();
            CacheUtils.putHttpCreditReportFetcher(request.getParameter(LEND_REQUEST_ID_NAME), result);
            return result;
        }
        return (CreditReportFetcher) CacheUtils.getHttpCreditReportFetcher(request.getParameter(LEND_REQUEST_ID_NAME));
    }

}
