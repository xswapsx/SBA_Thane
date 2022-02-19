package com.appynitty.swachbharatabhiyanlibrary.entity;

import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;

/**
 * Created by Ayan Dey on 7/10/19.
 */
public class LastLocationEntity {

    private String columnId;
    private String columnLattitude;
    private String columnLongitude;
    private String columnDate;

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnLattitude() {
        return columnLattitude;
    }

    public void setColumnLattitude(String columnLattitude) {
        this.columnLattitude = columnLattitude;
    }

    public String getColumnLongitude() {
        return columnLongitude;
    }

    public void setColumnLongitude(String columnLongitude) {
        this.columnLongitude = columnLongitude;
    }

    public String getColumnDate() {
        return columnDate;
    }

    public void setColumnDate(String columnDate) {
        this.columnDate = columnDate;
    }

    @Override
    public String toString() {
        return "LastLocationEntity{" +
                "columnId='" + columnId + '\'' +
                ", columnLattitude='" + columnLattitude + '\'' +
                ", columnLongitude='" + columnLongitude + '\'' +
                ", columnDate='" + columnDate + '\'' +
                '}';
    }
}
