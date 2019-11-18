package com.cd.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 编写HttpServletRequestUtil工具类, 使代码可复用, 防止反复编写相同的逻辑判断代码而造成代码冗余(rǒng yú)
 */
public class HttpServletRequestUtil {

    /**
     * 前端提交的数据,不管是数字还是字符船,最终都是以字符串的形式提交的
     * 所以,后端接收到的提交过来的参数都是以字符串的形式存在的,
     * 所以,在接收到这些提交过来的参数时,需要对其转化回其对应的数据类型
     */

    /**
     * 将字符串类型的参数,转化成Integer类型
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request, String key){
        try{
            //decode 和 parseInt的区别: decode可以识别十六进制等进制数,而parseInt不可以
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * 将字符串类型的参数,转化成Long类型
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request, String key){
        try{
            //valueOf 和 parseXxx的区别: valueOf()返回值是Integer类型,而parseInt()返回值是int类型
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * 将字符串类型的参数,转化成Double类型
     * @param request
     * @param key
     * @return
     */
    public static double getDouble(HttpServletRequest request, String key){
        try{
            return Double.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * 将字符串类型的参数,转化成Boolean类型
     * @param request
     * @param key
     * @return
     */
    public static boolean getBoolean(HttpServletRequest request, String key){
        try{
            return Boolean.valueOf(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 去除字符串两侧空白符, 避免获取到空字符串" "
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key){
        try{
            String result = request.getParameter(key);
            if(result != null){
                result.trim();
            }
            if("".equals(result)){
                return null;
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }

}
