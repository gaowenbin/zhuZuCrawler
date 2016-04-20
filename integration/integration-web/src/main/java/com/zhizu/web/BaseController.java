package com.zhizu.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

/**
 * basecontroller
 * 
 * @author zhuyuhang
 */
public class BaseController {
    private static final Logger logger = Logger.getLogger(BaseController.class);
    // captcha.localhost=false
    // captcha.path=http://172.16.4.2/authcode/
    // captcha.dir=/data/captcha

    public static String localhostTest = "false";
    protected static String CAPTCHA_PATH = "F://1213/";

    protected static final String LEND_REQUEST_ID_NAME = "applyNo";

    // 谁也不要在base controller里写各种fetcher的get与remove了，写在各自的controller里去

    public String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        return basePath;
    }

    public String getCaptchaPath(HttpServletRequest request) {
        return CAPTCHA_PATH;
    }

    public Map<String, String> getParams(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            result.put(entry.getKey(),
                    (entry.getValue() == null || entry.getValue().length == 0) ? null : entry.getValue()[0]);
        }
        return result;
    }

    public Boolean toBoolean(String str) {
        if ("1".equals(str)) {
            return Boolean.TRUE;
        }
        return BooleanUtils.toBoolean(str);
    }
}
