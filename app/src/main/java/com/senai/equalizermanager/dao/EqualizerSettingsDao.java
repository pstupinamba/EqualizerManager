package com.senai.equalizermanager.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.senai.equalizermanager.models.EqualizerSettings;

import java.util.List;

public interface EqualizerSettingsDao {
    @Query("SELECT * FROM equalizer_settings")
    List<EqualizerSettings> getAll();

    @Query("SELECT * FROM equalizer_settings WHERE id IN (:settingsId)")
    List<EqualizerSettings> loadAllByIds(int[] settingsId);

    @Query("SELECT * FROM equalizer_settings WHERE id = (:settingId)")
    EqualizerSettings getUserById(int settingId);

    @Insert
    void insertAll(EqualizerSettings... users);

    @Insert
    void insertUser(EqualizerSettings user);

    @Delete
    void delete(EqualizerSettings user);
}
