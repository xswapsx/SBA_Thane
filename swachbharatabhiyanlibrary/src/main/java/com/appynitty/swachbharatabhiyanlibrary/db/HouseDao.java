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

    @Query("SELECT ctype FROM HouseEntity  WHERE house_id = :houseId")
    String getCtypeFromHouseID(String houseId);

    @Query("DELETE FROM HouseEntity")
    void deleteAllHouse();

    @Query("UPDATE HouseEntity SET ctype=:cType, house_id=:houseId WHERE house_id = :houseId")
    void update(String houseId, String cType);

    /*@Query("SELECT * FROM HOUSEENTITY WHERE house_id = :houseId")
    String findHouseId(String houseId);*/
    @Query("SELECT * FROM HouseEntity WHERE house_id = :houseId")
    int isDataExist(String houseId);
}
