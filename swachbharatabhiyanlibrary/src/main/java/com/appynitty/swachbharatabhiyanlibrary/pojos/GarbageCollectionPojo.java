package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class GarbageCollectionPojo {

    private String image2;

    private String image1;

    private String AfterImage;

    private String beforeImage;

    private String id;

    private String comment;

    private int garbageType;

    private double weightTotal;

    private double weightTotalDry;

    private double weightTotalWet;

    private double distance;

    private String gcType;
    private String CType;
    private String LevelOS;
    private String TNS;
    private String TOT;

    public String getTNS() {
        return TNS;
    }

    public void setTNS(String TNS) {
        this.TNS = TNS;
    }

    public String getTOT() {
        return TOT;
    }

    public void setTOT(String TOT) {
        this.TOT = TOT;
    }

    public String getLevelOS() {
        return LevelOS;
    }

    public void setLevelOS(String levelOS) {
        LevelOS = levelOS;
    }

    public String getGcType() {
        return gcType;
    }

    public void setGcType(String gcType) {
        this.gcType = gcType;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public int getGarbageType() {
        return garbageType;
    }

    public void setGarbageType(int garbageType) {
        this.garbageType = garbageType;
    }

    public double getWeightTotal() {
        return weightTotal;
    }

    public void setWeightTotal(double weightTotal) {
        this.weightTotal = weightTotal;
    }

    public double getWeightTotalDry() {
        return weightTotalDry;
    }

    public void setWeightTotalDry(double weightTotalDry) {
        this.weightTotalDry = weightTotalDry;
    }

    public double getWeightTotalWet() {
        return weightTotalWet;
    }

    public void setWeightTotalWet(double weightTotalWet) {
        this.weightTotalWet = weightTotalWet;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCType() {
        return CType;
    }

    public void setCType(String CType) {
        this.CType = CType;
    }

    @Override
    public String toString() {
        return "GarbageCollectionPojo{"
                + "image2='" + image2 + '\''
                + ", image1='" + image1 + '\''
                + ", AfterImage='" + AfterImage + '\''
                + ", beforeImage='" + beforeImage + '\''
                + ", id='" + id + '\''
                + ", comment='" + comment + '\''
                + ", weightTotal='" + weightTotal + '\''
                + ", weightTotalDry='" + weightTotalDry + '\''
                + ", weightTotalWet='" + weightTotalWet + '\''
                + ", distance='" + distance + '\''
                + ", garbageType=" + garbageType + '}';
    }
}
