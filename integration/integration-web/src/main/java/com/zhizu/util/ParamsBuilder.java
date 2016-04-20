package com.zhizu.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class ParamsBuilder {
    public static String convert(Map<String, ? extends Object> params) {
        return convert(params, StandardCharsets.UTF_8);
    }

    public static String convert(Map<String, ? extends Object> params, Charset charset) {
        StringBuilder builder = new StringBuilder();
        for (Entry<String, ? extends Object> entry : params.entrySet()) {
            builder.append("&").append(encode(entry.getKey(), charset)).append("=")
                    .append(encode(entry.getValue() == null ? "" : entry.getValue().toString(), charset));
        }
        return builder.toString();
    }

    private static String encode(String value, Charset charset) {
        try {
            return URLEncoder.encode(value, charset.name());
        } catch (UnsupportedEncodingException x) {
            throw new UnsupportedCharsetException(charset.name());
        }
    }

    public static String params2string(Map<String, String[]> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (params != null && !params.isEmpty()) {
            for (Entry<String, String[]> entry : params.entrySet()) {
                sb.append(entry.getKey() + "=" + StringUtils.join(entry.getValue(), "|")).append(";");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
