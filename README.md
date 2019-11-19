# o2o
基于前端UI库SUI Mobile快速迭代出的校园商铺平台，该平台有三个子系统，分别供普通消费用户，商家和超级管理员使用
<br>
<hr>

<font face=consolas>**项目核心技术栈**：

 - <font face=黑体  color=gray>**前端**</font><font face=consolas>：前端UI库SUI Mobile，[ Ajax](https://blog.csdn.net/master_ning/article/details/79822971)，jQuery
 - <font face=黑体  color=gray>**后端**</font><font face=consolas>：SSM框架，[Kaptcha](https://blog.csdn.net/sinat_33010325/article/details/80768304)验证码组件，[Thumbnailator](https://www.cnblogs.com/MuZi0627/p/11095970.html)图片开源工具
 - <font face=黑体  color=gray>**数据库**</font><font face=consolas>：MySQL
- <font face=黑体  color=gray>**缓存**</font><font face=consolas>：Redis

<br><font face=consolas>**项目核心功能模块**：

 - [ ] <font face=consolas color=gray>**前台展示系统**</font>
	 -  <font face=黑体>注册，登录登出
	 - <font face=黑体>首页模板数据填充开发
	 - <font face=黑体>店铺列表页开发
 	- <font face=黑体>店铺详情页开发
	 - <font face=黑体>商品详情页开发
 	- <font face=黑体>消费记录页开发
 	- <font face=黑体>我的积分，积分兑换
	- <font face=黑体>密码修改，账号绑定
	- <font face=黑体>顾客信息展示
	- <font face=黑体>组合搜索功能开发
 
 - [ ] <font face=consolas color=gray>**商家管理系统**</font>

	 - <font face=黑体>商户入驻申请
	 - <font face=黑体>店铺列表页开发（一个商家可以注册多家店铺）
	 - <font face=黑体>店铺编辑页开发（店铺注册、店铺信息修改）
	 - <font face=黑体>商品类别管理
	 - <font face=黑体>商品列表页开发（编辑、上下架、前台预览）
	 - <font face=黑体>商品编辑页开发（添加商品、修改商品信息）
	 - <font face=黑体>权限验证

 - [ ] <font face=consolas color=gray>**超级管理员系统**</font>

	 - <font face=黑体>头条管理
	 - <font face=黑体>店铺类别管理
	 - <font face=黑体>区域信息管理
	 - <font face=黑体>新店铺审核列表
	 - <font face=黑体>入驻商家信息管理
	 - <font face=黑体>用户管理（普通用户、商家）
	 - <font face=黑体>权限验证

<br><font face=consolas>**开发环境**：

 - <font face=consolas>idea 2018.12
 - <font face=consolas>jdk 1.8
 - <font face=consolas>mysql 5.7.17
 - <font face=consolas>maven 3.3.9
- <font face=consolas>tomcat 9.0.16

<font face=黑体>不一定要一致，你的机器上原本测试能用即可

<br><br><font color=red face=consolas>**友情提示**</font><font face=consolas>：[SUI Mobile](https://www.jianshu.com/p/bf63b9a6355d) 是一套基于 Framework7 开发的UI库。它非常轻量、精美，只需要引入我们的CDN文件就可以使用。但由于项目开发团队的核心成员已经离开阿里，项目没有得到更新、无人维护，里面有很多坑。所以使用时要注意哦，别挖坑把队友都给埋了哈！！！

<hr>

<font face=consolas>前台展示系统Git图：
<img width=30% src="https://img-blog.csdnimg.cn/20191115221438373.gif"/>
![cd4356](https://img-blog.csdnimg.cn/20191115220500529.png)

<br><font face=consolas>店家管理系统Git图：
<table>
		<tr>
		<img width=30% src="https://img-blog.csdnimg.cn/20191115230546889.gif"/>
		</tr>
		<tr>
			&emsp;&emsp;
		</tr>
		<tr>
		<img width=30% src="https://img-blog.csdnimg.cn/20191116180636297.gif"/>
		</tr>
</table>

![cd4356](https://img-blog.csdnimg.cn/20191116185628273.png)
<hr>

<font face=黑体>由于内容过多，所以不会全部展示，下面主要介绍项目中使用到的组件和工具，以及一些可能会忽略的细节。
<hr>

<font face=consolas>**Kaptcha验证码组件**

<font face=黑体>kaptcha是一款非常实用的验证码生成工具，在没有手机短信验证的前提下，使用kaptcha将是一个很好的选择。kaptcha可以通过配置生成多样化的验证码，验证码以图片形式存在，无法进行黏贴复制的哦！
<font face=黑体>在账号注册，店铺注册和修改、商品注册和修改等都需要用到验证码哈！！

<font face=consolas>1、pom.xml文件中引入Kaptcha依赖

```xml
<!--验证码-->
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
    <version>2.3.2</version>
</dependency>
```

<font face=consolas>2、web.xml文件中对验证码相关属性进行配置

```xml
<!-- 验证码相关属性的配置 -->
<servlet>
    <servlet-name>Kaptcha</servlet-name>
    <servlet-class>com.google.code.kaptcha.servlet.KaptchaServlet</servlet-class>
    <!-- 定义 Kaptcha 的样式 -->
    <!-- 是否有边框 -->
    <init-param>
        <param-name>kaptcha.border</param-name>
        <param-value>no</param-value>
    </init-param>
    <!-- 字体颜色 -->
    <init-param>
        <param-name>kaptcha.textproducer.font.color</param-name>
        <param-value>red</param-value>
    </init-param>
    <!-- 图片宽度 -->
    <init-param>
        <param-name>kaptcha.image.width</param-name>
        <param-value>135</param-value>
    </init-param>
    <!-- 图片高度 -->
    <init-param>
        <param-name>kaptcha.image.height</param-name>
        <param-value>50</param-value>
    </init-param>
    <!-- 使用哪些字符生成验证码 -->
    <init-param>
        <param-name>kaptcha.textproducer.char.string</param-name>
        <param-value>ACDEFHKPRSTWX3456975</param-value>
    </init-param>
    <!-- 字体大小 -->
    <init-param>
        <param-name>kaptcha.textproducer.font.size</param-name>
        <param-value>43</param-value>
    </init-param>
    <!-- 干扰线的颜色 -->
    <init-param>
        <param-name>kaptcha.noise.color</param-name>
        <param-value>black</param-value>
    </init-param>
    <!-- 字符个数 -->
    <init-param>
        <param-name>kaptcha.textproducer.char.length</param-name>
        <param-value>4</param-value>
    </init-param>
    <!-- 字体 -->
    <init-param>
        <param-name>kaptcha.textproducer.font.names</param-name>
        <param-value>Arial</param-value> <!--Arial是宋体字-->
    </init-param>
</servlet>
<servlet-mapping>
    <servlet-name>Kaptcha</servlet-name>
    <!-- 外部访问路径，即当访问/Kaptcha这个url时使用这个servlet处理 -->
    <url-pattern>/Kaptcha</url-pattern>
</servlet-mapping>
```

<font face=consolas>3、在html中添加验证码控件，并设置点击更新验证码的设置

```html
<!-- 验证码 kaptcha -->
<div class="item-inner">
    <div class="item-title label">验证码</div>
    <input type="text" id="j_captcha" placeholder="验证码">
    <div class="item-input">
        <img id="captcha_img" alt="点击更换" title="点击更换"
             onclick="changeVerifyCode(this)" src="../Kaptcha"/>
    </div>
</div>
```

<font face=consolas>4、在共有js文件common.js中，定义点击更新验证码的方法
```js
function changeVerifyCode(img) {
    img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}
```

<font face=consolas>5、获取前端数据，并传输至后端
```js
$(function () {

    $('#submit').click(function() {
        // 获取输入的帐号
        var username = $('#username').val();
        // 获取输入的密码
        var password = $('#password').val();
        // 获取表单输入的验证码
        var verifyCode = $('#j_captcha').val();
        // 判断输入的验证码是否为空
        if(!verifyCode){
            $.toast('请输入验证码!');
            return;
        }

        // 访问后台，绑定帐号
        $.ajax({
            url: '/o2o/local/account_bind', //请求地址
            type: "post", //指定Ajax请求方式
            data: {
                username : username,
                password : password,
                verifyCode : verifyCode
            }, //传输至后台的数据（json对象）
            dataType: 'json', //指定服务器返回的数据类型
            success: function(data) {
            	//判断请求是否成功处理
                if (data.success) {
                	//toast是一种轻量的提示，在页面中间显示，并且会在2秒(默认值，可修改)之后自动消失
                    $.toast('绑定成功！');
                    window.location.href = '/o2o/shop_admin/shop_list';
                } else {
                    $.toast('绑定失败！' + data.errMsg);
                    $('#captcha_img').click(); //绑定失败则刷新验证码
                }
            }
        });
    });

});
```

<font face=consolas>6、后端对应的路由方法接收数据，并进行处理

```java
@RequestMapping("/account_bind")
@ResponseBody //@ResponseBody注解会将这个方法的返回值转换为JSON类型数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
private Map<String,Object> addLocalAccount(HttpServletRequest request){
    Map<String,Object> map = new HashMap<String, Object>();
    //验证码校验
    if(!VerifyCodeUtil.checkVerifyCode(request)){
        map.put("success",false);
        map.put("errorMsg","验证码错误!");
        return map;
    }
    map.put("success",true);
    return map;
}
```

<font face=consolas>7、后端处理验证码的方法（该方法封装在VerifyCodeUtil 工具类中）

```java
import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class VerifyCodeUtil {

    public static boolean checkVerifyCode(HttpServletRequest request){
        //获取生产的验证码
        String verifyCodeGenerated = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //获取实际提交的验证码
        String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCode");
        //判断实际提交的验证码是否与生产的验证码是否一致
        if(verifyCodeActual == null && !verifyCodeActual.equals(verifyCodeGenerated)){
            return false;
        }
        return true;
    }
}
```
<font face=黑体>Ok，至此验证码的内容已完成。


<br><hr>

<font face=consolas>**commons-fileupload文件上传组件 和 [Thumbnailator](https://www.cnblogs.com/daily-note/p/5505709.html)图片开源工具**

<font face=consolas>1) 考虑到该项目会产生大量图片（用户头像，店铺图片，商品图片，商品详情图等），所以使用图片开源工具Thumbnailator，对图片进行压缩。此外，[Thumbnailator](https://www.oschina.net/question/76860_25758?sort=default&p=2)还支持图片按比例缩放，生成水印，旋转等处理操作

<font face=consolas>2) 考虑到在Windows和Linux系统上运行，所以图片使用相对路径来保存

<font face=consolas>3) 用户头像，店铺类别图，店铺图、商品图和商品详情图分别保存在不同的文件夹中

<font face=consolas>4)配置虚拟路径，让图片能正常在前端展示

<br><font face=consolas>1、pom.xml文件中引入Commons文件上传组件 和 Thumbnailator图片开源工具

```xml
<!--commons文件上传组件-->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.3.3</version>
</dependency>

<!--缩略图 ~ 图片处理-->
<dependency>
    <groupId>net.coobird</groupId>
    <artifactId>thumbnailator</artifactId>
    <version>0.4.8</version>
</dependency>
```

<font face=consolas>2、PathUtil工具类中，定义图片保存的子路径的方法

```java
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
```

<font face=consolas>3、ImageUtil工具类中，通过Thumbnailator图片开源工具压缩图片，并输出到指定路径中。

```java
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
```


<font face=consolas>2、在html中添加文件上传控件，并设置只能上传图片

```html
<div class="content">
   <form>
       <div class="list-block">
           <ul>
               <!-- 用户名 Text -->
               <li>
                   <div class="item-content">
                       <div class="item-media">
                           <i class="icon icon-form-email"></i>
                       </div>
                       <div class="item-inner">
                           <div class="item-input">
                               <input type="text" id="username" placeholder="用户名">
                           </div>
                       </div>
                   </div>
               </li>

               <!-- 密码 password -->
               <li>
                   <div class="item-content">
                       <div class="item-media">
                           <i class="icon icon-form-email"></i>
                       </div>
                       <div class="item-inner">
                           <div class="item-input">
                               <input type="password" id="pwd" placeholder="密码">
                           </div>
                       </div>
                   </div>
               </li>

               <!-- 用户头像缩略图 -->
               <li>
                   <div class="item-content">
                       <div class="item-media">
                           <i class="icon icon-form-email"></i>
                       </div>
                       <div class="item-content">
                           <div class="item-inner">
                               <div class="item-title label">头像</div>
                               <div class="item-input">
                                   <!-- accept="image/*"规定只能上传图片 -->
                                   <input type="file" accept="image/*" id="upload_file">
                               </div>
                           </div>
                       </div>
                   </div>
               </li>

               <!-- captcha验证码 -->
               <li>
                   <div class="item-content">
                       <div class="item-media">
                           <i class="icon icon-form-email"></i>
                       </div>
                       <div class="item-inner">
                           <label for="j_captcha" class="item-title label">验证码</label>
                           <input id="j_captcha" name="j_captcha" type="text"
                                  class="form-control in" placeholder="验证码" />
                           <div class="item-input">
                               <img id="captcha_img" alt="点击更换" title="点击更换"
                                    onclick="changeVerifyCode(this)" src="../Kaptcha" />
                           </div>
                       </div>
                   </div>
               </li>
           </ul>
       </div>

       <div class="content-block">
           <div class="row">
               <div class="col-50">
                   <a href="#" class="button button-big button-fill button-danger" id="clear">重置</a>
               </div>
               <div class="col-50">
                   <a href="#" class="button button-big button-fill button-success" id="submit">注册</a>
               </div>
           </div>
       </div>
       <input type="reset" style="display: none">
   </form>
</div>
```

```js
$(function () {
    // 账号注册Url
    var registerUrl = '/o2o/local/register';

    $('#submit').click(function () {

        // 定义表单对象，用于接收前端数据以提交给后端
        var formData = new FormData();

        // 创建person对象
        var person = {};
        // 获取表单里的数据,并填充进person对象对应的属性里
        person.name = $('#username').val();
        person.pwd = $('#pwd').val();
        // 将person对象转化成json字符串，并保存到formDate表单对象中名为personStr的Key里
        formData.append('personStr',JSON.stringify(person));

        // 获取上传的图片文件流
        var uploadFile = $('#upload_file')[0].files[0];
        // 将图片流添加进表单对象里
        formData.append('uploadFile',uploadFile);

        // 获取表单输入的验证码
        var verifyCode = $('#j_captcha').val();
        // 判断输入的验证码是否为空
        if(!verifyCode){
            $.toast('请输入验证码!');
            return;
        }
        // 将验证码保存到ormDate表单对象中名为verifyCode的Key里
        formData.append('verifyCode',verifyCode);

        /**
         * 发送ajax，将数据提交至后台处理相关操作
         */
        $.ajax({
            url : registerUrl,
            type: 'post',
            data: formData,
            dataType: 'json', //指定服务器返回的数据类型
            processData: false, //必须参数(默认为true)，因为data值是FormData对象，告诉jQuery不要将data处理转化为查询字符串(URL参数)
            /*必须参数(默认为true)，data默认以查询字符串形式传输(如id=1&pwd=1)，这种传数据的格式，无法传输复杂的数据(如文件、数组)
              把contentType 改成 false 就会改掉之前默认的数据格式，在上传文件时就不会报错了
             */
            contentType: false,
            cache: false, //不从浏览器中加载请求信息
            //判断是否成功返回数据(即是否成功将数据提交给后台)
            success: function(data){
                if(data.success){
                    //toast是一种轻量的提示，在页面中间显示，并且会在2秒(默认值，可修改)之后自动消失。
                    $.toast('注册成功！');
                    window.location.href = '/o2o/local/to_login';
                }else{
                    $.toast(data.errorMsg);
                }
                //进行提交后,不管成功还是失败,都对验证码进行更新
                $('#captcha_img').click();
            }
        });
    });

    //点击重置按钮，清空表单内容  参考：https://blog.csdn.net/qq_34578253/article/details/71108155
    $('#clear').click(function () {
        $("input[type=reset]").trigger("click");//通过trigger()方法触发reset按钮
    });

});
```

<font face=consolas>Controller实现

```java
@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    /**
     * 账号注册
     *
     * @param person
     * @param multipartFile
     * @return
     */
    @Transactional
    public int addPerson(Person person, MultipartFile multipartFile) {
        //给账号信息赋初始值
        person.setCreateTime(new Date());
        person.setLastEditTime(new Date());
        person.setEnableStatus(1);
        person.setPersonType(1);
        try{
            //添加图片
            addLocalImg(person,multipartFile);
        }catch (Exception e){
            throw new RuntimeException("addLocalImg error:" + e.getMessage());
        }
        //添加账号
        int effectedNum = personDao.insertPerson(person);
        if(effectedNum <= 0){
            throw new RuntimeException("账号注册失败！");
        }
        return effectedNum;
    }


    /**
     * 添加不带水印的缩略图，并将图片保存的相对路径设置到Person对象的ProfileImg属性里
     *
     * @param person
     * @param multipartFile
     */
    private void addLocalImg(Person person, MultipartFile multipartFile){
        // 定义用户头像的相对路径
        String relativeAddress = PathUtil.getLocalImagePath(person.getName());
        // 生成不带水印的缩略图，并返回其相对路径
        String localImgAddress = ImageUtil.thumbnail(multipartFile,relativeAddress);
        // 将相对地址设置进Person实体类的属性中
        person.setProfileImg(localImgAddress);
    }

    /**
     * 修改用户信息
     *
     * @param person
     * @param multipartFile
     * @return
     */
    @Transactional
    public int modifyPerson(Person person, MultipartFile multipartFile) {
        int effectNum = 0;
        //空值判断
        if(person != null){
            if(multipartFile != null){
                try{
                    //删除原有的图片
                    ImageUtil.deleteFileOrPath(person.getProfileImg());
                }catch (Exception e){
                    throw  new RuntimeException(e.getMessage());
                }
                //添加新图片
                addLocalImg(person,multipartFile);
            }
            //修改用户信息
           effectNum = personDao.updatePerson(person);
        }
        return effectNum;
    }
}
```

<font face=consolas>Controller实现

```java
@Controller
@RequestMapping("/local")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * 账号注册
     *
     * @param uploadFile
     * @param request
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    private Map<String,Object> register(MultipartFile uploadFile, HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        //验证码校验
        if(!VerifyCodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errorMsg","验证码错误!");
            return map;
        }
        //获取前端提交过来的用户名和密码信息
        String personStr = HttpServletRequestUtil.getString(request,"personStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = null;
        try {
            //将personStr字符串转化成Person实体类
            person = objectMapper.readValue(personStr,Person.class);
        } catch (IOException e) {
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }
        //添加账号信息
        int effectedNum = personService.addPerson(person,uploadFile);
        if(effectedNum <= 0){
            map.put("success",false);
            map.put("errorMsg","账号注册失败！");
            return map;
        }
        map.put("success",true);
        return map;
    }
}
```

<font face=consolas>




<br><hr>

<font face=consolas>**Redis缓存**：

<font face=consolas>项目中引入了Redis缓存技术，对一些经常使用且不常更改的数据，通过Redis进行缓存，可以减轻数据库的压力（因为访问高峰期，很有可能把数据库搞崩）！


<br><hr>

<font face=consolas>**DTO及相关枚举类**：


<br><br>


前期规划不够好，后期花费了很多时间重构代码










<br><br><hr>
前端时使用SUI Mobile迭代出来的

由于项目会产生大量的图片，所以引入图片开源工具Thumbnailator 对图片进行压缩。

[图片处理](https://blog.csdn.net/Sunmeok/article/details/82182571)
https://blog.csdn.net/qq_43699339/article/details/89420647





Ajax的核心对象是XmlHttpRequest，即可扩展超文本传输请求，主要用来向服务器发送http请求，获取数据，实现页面的局部刷新功能，提高用户的体验效果
[https://blog.csdn.net/Ag_wenbi/article/details/52840801](https://blog.csdn.net/Ag_wenbi/article/details/52840801)
[https://blog.csdn.net/master_ning/article/details/79822971](https://blog.csdn.net/master_ning/article/details/79822971)
[http://tieba.baidu.com/p/5810286152](http://tieba.baidu.com/p/5810286152)

[Quartz定时任务](https://blog.csdn.net/noaman_wgs/article/details/80984873)
