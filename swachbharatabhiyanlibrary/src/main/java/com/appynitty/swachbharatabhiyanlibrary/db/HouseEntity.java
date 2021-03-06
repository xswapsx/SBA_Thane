package com.appynitty.swachbharatabhiyanlibrary.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HouseEntity {

    /*@PrimaryKey(autoGenerate = true)
    public int uid;*/

    @ColumnInfo(name = "house_id")
    @PrimaryKey
    @NonNull
    public String houseId;

    @ColumnInfo(name = "ctype")
    public String cType;
}
