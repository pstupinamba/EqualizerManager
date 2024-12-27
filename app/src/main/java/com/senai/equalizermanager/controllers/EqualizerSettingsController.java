package com.senai.equalizermanager.controllers;

import android.content.Context;

import androidx.room.Room;

import com.senai.equalizermanager.dao.AppDatabase;
import com.senai.equalizermanager.dao.EqualizerSettingsDao;
import com.senai.equalizermanager.models.EqualizerSettings;

import java.util.List;

public class EqualizerSettingsController {
    private EqualizerSettingsDao dao;
    private AppDatabase db;

    public EqualizerSettingsController(Context context) {
        this.db = Room.databaseBuilder(context, AppDatabase.class, "database-name").build();
    }

    public void addEqualizerSettings(int userId, int lowFreq, int midFreq, int highFreq) throws Exception{
        if (lowFreq < 0 || midFreq < 0 || highFreq < 0) {
            throw new Exception("Os valores das frequências não podem ser negativos.");
        }
        if (lowFreq < 60 || lowFreq > 250){
            throw new Exception("O valor do Grave deve estar entre 60 e 250");
        }
        if (midFreq < 250 || midFreq > 4000){
            throw new Exception("O valor do Grave deve estar entre 60 e 250");
        }
        if (highFreq < 4000 || highFreq > 16000){
            throw new Exception("O valor do Grave deve estar entre 60 e 250");
        }
        EqualizerSettings settings = new EqualizerSettings();
        settings.setLow_freq(lowFreq);
        settings.setMid_freq(midFreq);
        settings.setHigh_freq(highFreq);
        settings.setId_user(userId);

        db.equalizerSettingsDao().insertEqualizerSetting(settings);
    }

    public List<EqualizerSettings> getSettingsByUserId(int userId){
        return db.equalizerSettingsDao().getSettingsByUserId(userId);
    }

    public void deleteEqualizerSettings(int id) throws Exception{
        EqualizerSettings settings = db.equalizerSettingsDao().getEqualizeSettingById(id);
        if(settings == null){
            throw new Exception("Configuração de equalização não encontrada.");
        }
        db.equalizerSettingsDao().deleteEqualizerSetting(settings);
    }

}
