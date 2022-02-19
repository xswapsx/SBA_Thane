package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class ImagePojo {

    private String image2;

    private String image1;

    private String AfterImage;

    private String beforeImage;

    private String comment;

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getAfterImage() {
        return AfterImage;
    }

    public void setAfterImage(String afterImage) {
        AfterImage = afterImage;
    }

    public String getBeforeImage() {
        return beforeImage;
    }

    public void setBeforeImage(String beforeImage) {
        this.beforeImage = beforeImage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ImagePojo{" +
                "image2='" + image2 + '\'' +
                ", image1='" + image1 + '\'' +
                ", AfterImage='" + AfterImage + '\'' +
                ", beforeImage='" + beforeImage + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
