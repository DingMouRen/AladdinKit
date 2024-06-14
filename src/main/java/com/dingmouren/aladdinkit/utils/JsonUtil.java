package com.dingmouren.aladdinkit.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 */
public class JsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private JsonUtil() {

    }

    /**
     * 将String 转为json
     *
     * @param src
     * @param split
     * @return
     */
    public static JSONObject string2JSON(String src, String split) {
        JSONObject json = new JSONObject();

        try {
            String[] arr = src.split(split);
            for (int i = 0; i < arr.length; i++) {
                String[] arrKey = arr[i].split("=");
                json.put(arrKey[0], arr[i].substring(arrKey[0].length() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * 将对象转换成json格式
     *
     * @param ts
     * @return
     */
    public static String objectToJson(Object ts) {
        String jsonStr = null;
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将json格式转换成list对象
     *
     * @param jsonStr
     * @return
     */
    public static <T> List<T> parseModelList(String jsonStr, TypeToken<List<T>> type) {
        List<T> objList = null;
        if (gson != null) {
            objList = gson.fromJson(jsonStr, type.getType());
        }
        return objList;
    }

    /**
     * 将json格式转换成对象，并准确指定类型
     *
     * @param jsonStr
     * @param type
     * @return
     */
    public static <T> T parseModel(String jsonStr, java.lang.reflect.Type type) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(jsonStr, type);
        }
        return t;
    }

    /**
     * 将json格式转换成map对象
     *
     * @param jsonStr
     * @return
     */
    public static Map<?, ?> jsonToMap(String jsonStr) {
        Map<?, ?> objMap = null;
        if (gson != null) {
            java.lang.reflect.Type type = new TypeToken<Map<?, ?>>() {
            }.getType();
            objMap = gson.fromJson(jsonStr, type);
        }

        return objMap;
    }


    public static JSONObject mapToJson(Map<?, ?> map) {
        JSONObject jsonObject = null;
        if (gson != null) {
            java.lang.reflect.Type type = new TypeToken<Map<?, ?>>() {
            }.getType();
            String json = gson.toJson(map, type);
            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     * 将json转换成bean对象
     *
     * @param jsonStr
     * @return
     */
    public static <T> T parseModel(String jsonStr, Class<T> cl) {
        T obj = null;
        if (gson != null) {
            obj = gson.fromJson(jsonStr, cl);
        }
        return obj;
    }


    public static Gson getGson() {
        if (gson == null) {
            synchronized (JsonUtil.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    public static String jsonFormat(String s) {
        int level = 0;
        //存放格式化的json字符串
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int index = 0; index < s.length(); index++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = s.charAt(index);
//          System.out.println(s.charAt(index));

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
//                System.out.println("123"+jsonForMatStr);
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

}