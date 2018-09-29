package com.example.reza.firstca.DB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserInfoDao {

    @Insert
    public void insert(UserInfo userInfo);

    @Query("Update user_tbl SET fname=:fname,lname=:lname where id=:id")
    public void update(int id, String fname, String lname);

    @Query("DELETE FROM user_tbl")
    void deleteAll();

    @Query("SELECT * from user_tbl ORDER BY id ASC")
    LiveData<List<UserInfo>> getuser();
}
