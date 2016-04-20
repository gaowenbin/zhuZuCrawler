package com.zhizu.crawler.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtils {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final SerializerFeature sfs[] = new SerializerFeature[] { SerializerFeature.WriteMapNullValue };

    public static String toJSONStringWithDateFormat(Object object) {
        return toJsonString(object);
    }

    @SuppressWarnings("rawtypes")
    public static String toJsonString(Object object) {
        Map<String, Object> map = new HashMap<>();
        // map.put("errorCode", Response.ERROR_CODE_SUCCESS);
        map.put("result", object);
        if (null == object) {
            // map.put("errorCode", Response.SELECT_NO_RECORD);
        }

        if (object instanceof List) {
            if (((List) object).isEmpty()) {
                // map.put("errorCode", Response.SELECT_NO_RECORD);
            }
        }
        if (object instanceof Map) {
            if (((Map) object).isEmpty()) {
                // map.put("errorCode", Response.SELECT_NO_RECORD);
            }
        }
        return JSON.toJSONStringWithDateFormat(map, FORMAT, sfs);
    }

    public static String toJsonStringForTime(Object object, boolean result) {
        Map<String, Object> map = new HashMap<>();
        // map.put("errorCode", Response.ERROR_CODE_SUCCESS);
        map.put("result", object);
        map.put("timeOut", result);
        if (null == object) {
            // map.put("errorCode", Response.SELECT_NO_RECORD);
        }

        if (object instanceof List) {
            if (((List) object).isEmpty()) {
                // map.put("errorCode", Response.SELECT_NO_RECORD);
            }
        }
        if (object instanceof Map) {
            if (((Map) object).isEmpty()) {
                // map.put("errorCode", Response.SELECT_NO_RECORD);
            }
        }
        return JSON.toJSONStringWithDateFormat(map, FORMAT, sfs);
    }

    public static String toErrorJSONString() {
        Map<String, Object> map = new HashMap<>();
        // map.put("errorCode", Response.SELECT_NO_RECORD);
        map.put("result", null);
        return JSON.toJSONStringWithDateFormat(map, FORMAT, sfs);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("a", new Date());
        map.put("b", 100);
        map.put("c", "def");
        map.put("d", true);

        String ss = "{\"List\":[{\"AcAlias\":\"谢东\",\"AcOrder\":192,\"DeptId\":\"0118\",\"AcNo\":\"6226220123518317\",\"AcSeq\":16854460,\"AcName\":\"谢东\",\"DeptSeq\":195,\"BankAcType\":\"03\",\"AcKind\":null,\"DeptName\":\"中国民生银行北京朝阳门支行\",\"SysFlag\":\"1\",\"BankId\":null,\"OpenDate\":\"2015-02-28\",\"AuthAcFlag\":\"0\",\"AcPermit\":\"15\"}]}";
        Map<String, Object> list = (Map) JSON.parse(ss);
        System.out.println(list.get("List"));
        List map1 = (List) list.get("List");
        System.out.println(map1.get(0));
        Map<String, Object> map2 = (Map) map1.get(0);
        System.out.println(map2.get("AcNo"));
        // System.out.println(toJsonString(map));
        // map = null;
        // System.out.println(toJsonString(map));
        //
        // List<String> list = new ArrayList<>();
        // System.out.println(toJsonString(list));
        //
        // System.out.println(toErrorJSONString());
    }
}
