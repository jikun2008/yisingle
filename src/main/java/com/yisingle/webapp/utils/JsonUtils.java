package com.yisingle.webapp.utils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.springframework.util.StringUtils;


public class JsonUtils {

    //protected final static Log logger = LogFactory.getLog(JsonUtils.class);

    public static boolean isBadJson(String json) {
        return !isGoodJson(json);
    }

    public static boolean isGoodJson(String json) {

        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            // logger.error("bad json: " + json);
            return false;
        }
    }
}