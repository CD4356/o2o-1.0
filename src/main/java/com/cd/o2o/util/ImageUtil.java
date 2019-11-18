package com.cd.o2o.util;

import com.cd.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    //类路径资源目录
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //时间格式
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    //随机类
    private static Random random = new Random();

    /**
     * 将CommonsMultiFile转化成File
     *
     * @param file
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile file){
        //创建File文件对象 （file的getOriginalFilename()方法用于获取文件的原文件名）
        File newFile = new File(file.getOriginalFilename());
        try {
            //将CommonsMultipartFile转换成File（即将传入的文件流写入到指定的文件对象中）
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new RuntimeException("文件流写入失败:" + e.getMessage());
        }
        return newFile;
    }


    /**
     * 生成不带水印缩略图,并返回新生产图片的相对路径
     *
     * @param uploadFile 缩略图（包含图片流和图片名）
     * @param targetAddr 图片存储相对路径/目标路径
     * @return
     */
    public static String thumbnail(MultipartFile uploadFile, String targetAddr){
        //获取文件随机名称
        String realFileName = getRandomFileName();
        // 获取上传文件的原文件名
        String fileName = uploadFile.getOriginalFilename();
        // 获取上传文件的后缀名
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //如果目标路径不存在，则自动创建
        makeDirsPath(targetAddr);
        //拼接文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + realFileName + suffix;
        //生成新的文件，并保存到指定路径中
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        //调用thumbnails生成带有水印的缩略图
        try {
            //生成带水印的缩略图
            Thumbnails.of(uploadFile.getInputStream())
                    .size(200,200)
                    .outputQuality(0.8f)
                    .toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("缩略图创建失败:" + e.getMessage());
        }

        //返回文件保存的相对路径地址
        return relativeAddr;
    }


    /**
     * 生成带水印缩略图,并返回新生产图片的相对路径
     *
     * @param thumbnail 缩略图（包含图片流和图片名）
     * @param targetAddr 图片存储相对路径/目标路径
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        //获取文件随机名称
        String realFileName = getRandomFileName();
        //获取文件扩展名(后缀名)
        String extension = getFileExtension(thumbnail.getImageName());
        //如果目标路径不存在，则自动创建
        makeDirsPath(targetAddr);
        //拼接文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + realFileName + extension;
        //生成新的文件，并保存到指定路径中
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        //调用thumbnails生成带有水印的缩略图
        try {
            //生成带水印的缩略图
            Thumbnails.of(thumbnail.getImage())
                      .size(200,200)
                      .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/watermark.jpg")),0.8f)
                      .outputQuality(0.8f)
                      .toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("缩略图创建失败:" + e.getMessage());
        }

        //返回文件保存的相对路径地址
        return relativeAddr;
    }


    /**
     * 生成带水印商品详情图缩略图,并返回新生产图片的相对值路径
     *
     * @param thumbnail 缩略图（包含图片流和图片名）
     * @param targetAddr 图片存储相对路径/目标路径
     * @return
     */
    public static String generateNormalThumbnail(ImageHolder thumbnail, String targetAddr){
        //获取文件随机名称
        String realFileName = getRandomFileName();
        //获取文件扩展名(后缀名)
        String extension = getFileExtension(thumbnail.getImageName());
        //如果目标路径不存在，则自动创建
        makeDirsPath(targetAddr);
        //拼接文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + realFileName + extension;
        //生成新的文件对象，并保存到指定路径中
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        try {
            //生成带水印的缩略图
            Thumbnails.of(thumbnail.getImage())
                    .size(337,640)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/watermark.jpg")),0.8f)
                    .outputQuality(0.9f)
                    .toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("缩略图创建失败:" + e.getMessage());
        }
        //返回文件保存的相对路径地址
        return relativeAddr;
    }



    /**
     * 创建目标路径所涉及到的目录, 如 D:/tmp/one/two/xxx.jpg , 那么/tmp/one/two这三个文件夹都得自动创建
     *
     * @param targetAddress 图片存储的相对路径
     */
    private static void makeDirsPath(String targetAddress) {
        //获取文件全路径 ~ ~ 全路径 = 根路径 + 相对路径 （不包括文件名）
        String fileParentPath = PathUtil.getImgBasePath() + targetAddress;
        //获取文件全路径目录
        File dirPath = new File(fileParentPath);
        //判断目录是否存在,不存在就创建该目录 (mkdir用于创建单级目录, mkdirs用于创建多级目录)
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * 生产随机文件名 ~ ~ 当前的(年月日时分秒 +5位随机数)
     *
     * @return
     */
    public static String getRandomFileName() {
        //获取10000~99999之间的5位随机数
        int randomNumber = random.nextInt(99999) + 10000;
        //获取当前时间,并按指定时间格式返回
        String nowTime = format.format(new Date());
        String randomFileName = nowTime + randomNumber;
        return randomFileName;
    }

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    private static String getFileExtension(String filename) {
        //获取原始文件扩展名
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        return fileExtension;
    }



    /**
     * 删除存储在磁盘中的指定图片
     *
     * imgAddr是文件的路径或目录的路径
     *      如果imgAddr是文件路径则删除该文件
     *      如果imgAddr是目录路径则删除该目录下的所有文件
     * @param imgAddr
     */
    public static void deleteFileOrPath(String imgAddr){
        //获取指定文件对象 （文件的绝对路径=PathUtil.getImgBasePath() + imgAddr，即根路径+相对路径）
        File fileOrPath = new File(PathUtil.getImgBasePath() + imgAddr);
        //判断该文件对象是否存在
        if(fileOrPath.exists()){
            //判断fileOrPath文件对象是文件还是目录
            if(fileOrPath.isDirectory()){
                //获取文件列表
                File files[] = fileOrPath.listFiles();
                for(int i=0;i<files.length;i++){
                    //删除文件
                    files[i].delete();
                }
            }
            //不是文件就是目录,是文件则直接删除,是目录则删除里面文件后顺便将目录删除,（删除目录必须保证目录为空的）
            fileOrPath.delete();
        }
    }

}
