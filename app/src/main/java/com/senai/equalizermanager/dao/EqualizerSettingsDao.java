package com.senai.equalizermanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.senai.equalizermanager.models.EqualizerSettings;


import java.util.List;

@Dao
public interface EqualizerSettingsDao {
    @Query("SELECT * FROM equalizer_settings")
    List<EqualizerSettings> getAllSettings();

    @Query("SELECT * FROM equalizer_settings WHERE id_user = :userId")
    List<EqualizerSettings> getSettingsByUserId(int userId);

    @Query("SELECT * FROM equalizer_settings WHERE id = (:settingId)")
    EqualizerSettings getEqualizeSettingById(int settingId);

    @Insert
    void insertEqualizerSetting(EqualizerSettings setting);

    @Update
    void updateEqualizerSetting(EqualizerSettings setting);

    @Delete
    void deleteEqualizerSetting(EqualizerSettings setting);

    @Query("DELETE FROM equalizer_settings WHERE id_user = :userId")
    void deleteSettingsByUserId(int userId);
}
