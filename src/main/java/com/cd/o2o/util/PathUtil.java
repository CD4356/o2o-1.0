package com.cd.o2o.util;


public class PathUtil {

    //获取当前操作系统的文件分隔符
    private static String separator = System.getProperty("file.separator");
    //也可通过下面语句获取当前操作系统的文件分隔符
    //private static String separator2 = File.separator;

    /**
     * 获取文件保存的根目录路径
     *
     * @return
     */
    public static String getImgBasePath(){
        //获取当前操作系统名称
        String os = System.getProperty("os.name");

        String basePath = "";

        //判断当前操作系统是Windows还是Linux
        if(os.toLowerCase().startsWith("win")){
            basePath = "D:/idea/java_workspace/image";
        }else {
            basePath = "home/shop/image";
        }
        //由于Windows和Linux操作系统的文件分隔符不一样,所以将'/'分隔符替换成当前操作系统的文件分隔符
        basePath = basePath.replace("/",separator);
        return basePath;
    }


    /**
     * 获取店铺图片保存的相对目录路径
     *
     * @param shopId
     * @return
     */
    public static String getShopImagePath(Long shopId){
        //生成对应的图片保存路径
        String imagePath = "/upload/shop/"+ shopId +"/";
        //将路径分隔符‘/’替换成当前操作系统的分隔符
        //return imagePath.replace("/",separator);
        return imagePath;
    }


    /**
     * 获取头像保存的相对目录路径
     *
     * @param username
     * @return
     */
    public static String getLocalImagePath(String username){
        //生成对应的图片保存路径
        String imagePath = "/upload/local/"+ username +"/";
        return imagePath;
    }

}
