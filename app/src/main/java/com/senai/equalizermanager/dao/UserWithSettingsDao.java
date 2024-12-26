package com.senai.equalizermanager.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.models.User;
import com.senai.equalizermanager.models.UserWithSettings;

import java.util.List;

public interface UserWithSettingsDao {
    @Transaction
    @Query("SELECT * FROM User")
    List<UserWithSettings> getAllUsersWithSettings();

    @Query("SELECT * FROM user WHERE id = (:userId)")
    User getUserById(int userId);

    @Insert
    void insertAll(User... users);

    @Insert
    void insertUser(User user);

    @Insert
    void insertEqualizerSetting(EqualizerSettings setting);

    @Transaction
    @Query("SELECT * FROM User WHERE id = :userId")
    UserWithSettings getUserWithSettings(long userId);

    @Delete
    void delete(User user);
}
