package com.senai.equalizermanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.models.User;
import com.senai.equalizermanager.models.UserWithSettings;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE id = (:userId)")
    User getUserById(int userId);

    @Insert
   void insertAll(User... users);

    @Insert
    void insertUser(User user);

    @Delete
    void delete(User user);

}
