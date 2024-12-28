package com.senai.equalizermanager.controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.senai.equalizermanager.dao.AppDatabase;
import com.senai.equalizermanager.dao.EqualizerSettingsDao;
import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.utils.Callback;

import java.util.List;

public class EqualizerSettingsController {
    private EqualizerSettingsDao dao;
    private AppDatabase db;

    public EqualizerSettingsController(Context context) {
        this.db = Room.databaseBuilder(context, AppDatabase.class, "database-name")
                .fallbackToDestructiveMigration()
                .build();
    }

    public void createEqualizerSettings(@NonNull EqualizerSettings settings) throws Exception{
        int lowFreq = settings.getLow_freq();
        int midFreq = settings.getMid_freq();
        int highFreq = settings.getHigh_freq();
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

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    db.equalizerSettingsDao().insertEqualizerSetting(settings);
                }catch(Exception ex){
                    Log.i("Thread EqualizerSettingsController: ", ex.getMessage());
                }
            }
        });
        t.start();
    }

    public void getSettingsByUserId(Callback<List<EqualizerSettings>> callback, int userId) {
        new Thread(() -> {
            try {
                List<EqualizerSettings> settings = db.equalizerSettingsDao().getSettingsByUserId(userId);
                callback.onSuccess(settings);
            } catch (Exception ex) {
                Log.e("Thread EqualizerController", ex.getMessage());
                callback.onFailure(ex);
            }
        }).start();
    }

    public void setActiveSetting(int userId, int settingId, Callback<Void> callback) {
        new Thread(() -> {
            try {
                db.equalizerSettingsDao().deactivateAllSettings(userId);
                EqualizerSettings activeSetting = db.equalizerSettingsDao().getEqualizeSettingById(settingId);
                if (activeSetting != null) {
                    activeSetting.setActive(true);
                    db.equalizerSettingsDao().updateEqualizerSetting(activeSetting);
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Configuração não encontrada."));
                }
            } catch (Exception e) {
                callback.onFailure(e);
            }
        }).start();
    }

    public void getActiveSetting(int userId, Callback<EqualizerSettings> callback) {
        new Thread(() -> {
            try {
                EqualizerSettings activeSetting = db.equalizerSettingsDao().getActiveSettingByUserId(userId);
                if (activeSetting != null) {
                    callback.onSuccess(activeSetting);
                } else {
                    callback.onFailure(new Exception("Nenhuma configuração ativa encontrada."));
                }
            } catch (Exception e) {
                callback.onFailure(e);
            }
        }).start();
    }

    public void deleteEqualizerSettings(int id) throws Exception{
        EqualizerSettings settings = db.equalizerSettingsDao().getEqualizeSettingById(id);
        if(settings == null){
            throw new Exception("Configuração de equalização não encontrada.");
        }
        db.equalizerSettingsDao().deleteEqualizerSetting(settings);
    }

}
