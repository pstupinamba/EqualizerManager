package com.senai.equalizermanager.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.models.User;

/**
 * Classe que define o banco de dados principal do aplicativo utilizando Room.
 * Contém as definições das entidades e os DAOs associados.
 */
@Database(entities = {User.class, EqualizerSettings.class}, version = 3) // Define as tabelas e a versão do banco de dados.
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Método abstrato para obter o DAO da entidade User.
     *
     * @return instância de UserDao para interagir com a tabela User.
     */
    public abstract UserDao userDao();

    /**
     * Método abstrato para obter o DAO da entidade EqualizerSettings.
     *
     * @return instância de EqualizerSettingsDao para interagir com a tabela EqualizerSettings.
     */
    public abstract EqualizerSettingsDao equalizerSettingsDao();

    /**
     * Método abstrato para obter o DAO responsável pelas consultas envolvendo
     * relações entre User e EqualizerSettings.
     *
     * @return instância de UserWithSettingsDao para interagir com consultas relacionadas.
     */
    public abstract UserWithSettingsDao userWithSettingsDao();
}
