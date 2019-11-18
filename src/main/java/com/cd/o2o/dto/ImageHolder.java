package com.cd.o2o.dto;

import java.io.InputStream;

public class ImageHolder {

    //文件流（图片流）
    private InputStream image;
    //文件名（图片名）
    private String imageName;

    public ImageHolder(InputStream image, String imageName) {
        this.image = image;
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
