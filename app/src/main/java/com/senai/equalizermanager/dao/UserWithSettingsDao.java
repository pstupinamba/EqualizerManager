package com.senai.equalizermanager.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import com.senai.equalizermanager.models.User;
import com.senai.equalizermanager.models.UserWithSettings;

import java.util.List;

@Dao
public interface UserWithSettingsDao {
    @Transaction
    @Query("SELECT * FROM User")
    List<UserWithSettings> getAllUsersWithSettings();

    @Query("SELECT * FROM user WHERE id = (:userId)")
    User getUserById(int userId);

    @Transaction
    @Query("SELECT * FROM User WHERE id = :userId")
    UserWithSettings getUserWithSettings(int userId);

}
