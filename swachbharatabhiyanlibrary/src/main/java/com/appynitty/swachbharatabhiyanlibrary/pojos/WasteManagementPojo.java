package com.appynitty.swachbharatabhiyanlibrary.pojos;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;

import java.util.List;

/**
 * Created by Ayan Dey on 25/1/20.
 */
public class WasteManagementPojo extends ResultPojo {

    private int UnitID, ID;
    private String SubCategoryID, Weight, UserID, Source, CategoryID, gdDate;
    private String wasteTypeName, wasteCategoryName, wasteUnitName;
    private Boolean isUpdate;

    private List<GarbageCategoryPojo> Category;
    private List<GarbageSubCategoryPojo> SubCategory;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSubCategoryID() {
        return SubCategoryID;
    }

    public void setSubCategoryID(String subCategoryID) {
        SubCategoryID = subCategoryID;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getUnitID() {
        return UnitID;
    }

    public void setUnitID(int unitID) {
        UnitID = unitID;
    }

    public void setSource(String source) {
        Source = source;
    }

    public void setGdDate(String gdDate) {
        this.gdDate = gdDate;
    }

    public String getWasteTypeName() {
        return wasteTypeName;
    }

    public void setWasteTypeName(String wasteTypeName) {
        this.wasteTypeName = wasteTypeName;
    }

    public String getWasteCategoryName() {
        return wasteCategoryName;
    }

    public void setWasteCategoryName(String wasteCategoryName) {
        this.wasteCategoryName = wasteCategoryName;
    }

    public String getWasteUnitName() {
        return wasteUnitName;
    }

    public void setWasteUnitName(String wasteUnitName) {
        this.wasteUnitName = wasteUnitName;
    }

    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public List<GarbageCategoryPojo> getCategory() {
        return Category;
    }

    public List<GarbageSubCategoryPojo> getSubCategory() {
        return SubCategory;
    }

    /*
    * SUB CLASSES
    * */
    public class GarbageCategoryPojo {
        private final String CategoryID;
        private final String GarbageCategory;

        public GarbageCategoryPojo(String ID, String garbageCategory) {
            this.CategoryID = ID;
            GarbageCategory = garbageCategory;
        }

        public String getGarbageCategory() {
            return GarbageCategory;
        }

        public String getCategoryID() {
            return CategoryID;
        }
    }

    public class GarbageSubCategoryPojo {
        private final String SubCategoryID;
        private final String GarbageSubCategory;
        private String CategoryID;

        public GarbageSubCategoryPojo(String ID, String garbageSubCategory) {
            this.SubCategoryID = ID;
            this.GarbageSubCategory = garbageSubCategory;
        }

        public GarbageSubCategoryPojo(String subCategoryID, String garbageSubCategory, String categoryID) {
            SubCategoryID = subCategoryID;
            GarbageSubCategory = garbageSubCategory;
            CategoryID = categoryID;
        }

        public String getSubCategoryID() {
            return SubCategoryID;
        }

        public String getGarbageSubCategory() {
            return GarbageSubCategory;
        }

        public String getCategoryID() {
            return CategoryID;
        }

    }
}
