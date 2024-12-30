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

/**
 * Controlador responsável pela lógica de negócios associada às configurações do equalizador.
 * Ele interage com o banco de dados para inserir, consultar e modificar configurações de equalizador.
 */
public class EqualizerSettingsController {
    private EqualizerSettingsDao dao;
    private AppDatabase db;

    /**
     * Construtor que inicializa a conexão com o banco de dados Room.
     *
     * @param context Contexto do aplicativo, utilizado para inicializar o banco de dados.
     */
    public EqualizerSettingsController(Context context) {
        this.db = Room.databaseBuilder(context, AppDatabase.class, "database-name")
                .fallbackToDestructiveMigration()  // Ação a ser tomada caso o banco de dados precise ser migrado
                .build();
    }

    /**
     * Cria uma nova configuração de equalizador, validando os valores antes da inserção.
     *
     * @param settings A configuração de equalizador a ser criada.
     * @throws Exception Caso algum dos valores seja inválido.
     */
    public void createEqualizerSettings(@NonNull EqualizerSettings settings) throws Exception{
        int lowFreq = settings.getLow_freq();
        int midFreq = settings.getMid_freq();
        int highFreq = settings.getHigh_freq();

        // Validação dos valores das frequências
        if (lowFreq < 0 || midFreq < 0 || highFreq < 0) {
            throw new Exception("Os valores das frequências não podem ser negativos.");
        }
        if (lowFreq < 60 || lowFreq > 250){
            throw new Exception("O valor do Grave deve estar entre 60 e 250");
        }
        if (midFreq < 250 || midFreq > 4000){
            throw new Exception("O valor do Médio deve estar entre 250 e 4000");
        }
        if (highFreq < 4000 || highFreq > 16000){
            throw new Exception("O valor do Agudo deve estar entre 4000 e 16000");
        }

        // Inserção no banco de dados em uma thread separada
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

    /**
     * Obtém todas as configurações de equalizador de um usuário específico.
     *
     * @param callback A callback a ser chamada após a execução da consulta.
     * @param userId O ID do usuário cujas configurações serão retornadas.
     */
    public void getSettingsByUserId(Callback<List<EqualizerSettings>> callback, int userId) {
        new Thread(() -> {
            try {
                List<EqualizerSettings> settings = db.equalizerSettingsDao().getSettingsByUserId(userId);
                callback.onSuccess(settings);  // Chama a função de sucesso da callback
            } catch (Exception ex) {
                Log.e("Thread EqualizerController", ex.getMessage());
                callback.onFailure(ex);  // Chama a função de falha da callback
            }
        }).start();
    }

    /**
     * Define uma configuração de equalizador como ativa para um usuário, desativando as demais.
     *
     * @param userId O ID do usuário.
     * @param settingId O ID da configuração que será ativada.
     * @param callback A callback a ser chamada após a execução.
     */
    public void setActiveSetting(int userId, int settingId, Callback<Void> callback) {
        new Thread(() -> {
            try {
                // Desativa todas as configurações do usuário
                db.equalizerSettingsDao().deactivateAllSettings(userId);

                // Recupera a configuração desejada e a define como ativa
                EqualizerSettings activeSetting = db.equalizerSettingsDao().getEqualizeSettingById(settingId);
                if (activeSetting != null) {
                    activeSetting.setActive(true);
                    db.equalizerSettingsDao().updateEqualizerSetting(activeSetting);
                    callback.onSuccess(null);  // Callback de sucesso
                } else {
                    callback.onFailure(new Exception("Configuração não encontrada."));  // Callback de falha
                }
            } catch (Exception e) {
                callback.onFailure(e);
            }
        }).start();
    }

    /**
     * Obtém a configuração ativa de um usuário.
     *
     * @param userId O ID do usuário.
     * @param callback A callback a ser chamada com a configuração ativa ou erro.
     */
    public void getActiveSetting(int userId, Callback<EqualizerSettings> callback) {
        new Thread(() -> {
            try {
                EqualizerSettings activeSetting = db.equalizerSettingsDao().getActiveSettingByUserId(userId);
                if (activeSetting != null) {
                    callback.onSuccess(activeSetting);  // Callback de sucesso
                } else {
                    callback.onFailure(new Exception("Nenhuma configuração ativa encontrada."));  // Callback de falha
                }
            } catch (Exception e) {
                callback.onFailure(e);
            }
        }).start();
    }

    /**
     * Exclui uma configuração de equalizador pelo ID.
     *
     * @param id O ID da configuração a ser excluída.
     * @throws Exception Caso a configuração não seja encontrada.
     */
    public void deleteEqualizerSettings(int id) throws Exception{
        EqualizerSettings settings = db.equalizerSettingsDao().getEqualizeSettingById(id);
        if(settings == null){
            throw new Exception("Configuração de equalização não encontrada.");
        }
        db.equalizerSettingsDao().deleteEqualizerSetting(settings);  // Exclui a configuração
    }
}
