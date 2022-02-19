package com.appynitty.swachbharatabhiyanlibrary.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HouseDao {
    @Query("SELECT * FROM HouseEntity")
    List<HouseEntity> getAllHouses();

    @Insert
    void insertHouse(HouseEntity... houseEntities);

    @Delete
    void deleteUser(HouseEntity houseEntity);

    @Query("SELECT ctype FROM houseentity  WHERE house_id = :houseId")
    String getCtypeFromHouseID(String houseId);

    @Query("DELETE FROM HouseEntity")
    void deleteAllHouse();

    @Query("UPDATE houseentity SET ctype=:cType, house_id=:houseID WHERE house_id = :houseID")
    void update(String houseID, String cType);

    /*@Query("SELECT * FROM HOUSEENTITY WHERE house_id = :houseId")
    String findHouseId(String houseId);*/
    @Query("SELECT * FROM HOUSEENTITY WHERE house_id = :houseId")
    int isDataExist(String houseId);
}
