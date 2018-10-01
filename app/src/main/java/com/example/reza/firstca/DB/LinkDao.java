package com.example.reza.firstca.DB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LinkDao {
    @Insert
    void insert(Link link);

    @Query("DELETE FROM link_tbl")
    void deleteAll();

    @Query("SELECT * from link_tbl ORDER BY id ASC")
    LiveData<List<Link>> getAll();

}
