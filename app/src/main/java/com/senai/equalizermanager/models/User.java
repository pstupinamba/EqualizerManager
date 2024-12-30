package com.senai.equalizermanager.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Classe que representa a entidade "User" no banco de dados Room.
 * Armazena as informações básicas do usuário.
 */
@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id; // ID único do usuário, gerado automaticamente.

    @NonNull
    @ColumnInfo(name = "username") // Nome da coluna no banco de dados.
    private String username; // Nome de usuário (obrigatório).

    /**
     * Construtor padrão inicializando valores com padrões.
     */
    public User() {
        this.id = 0; // Inicializa o ID com 0 (será sobrescrito pelo Room).
        this.username = ""; // Inicializa o nome de usuário com uma string vazia.
    }

    /**
     * Construtor para inicializar o usuário com um nome específico.
     *
     * @param username nome do usuário.
     */
    public User(String username) {
        this.username = username;
    }

    // Métodos getter e setter para acessar e modificar os atributos da entidade.

    /**
     * Obtém o ID do usuário.
     *
     * @return ID do usuário.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Define o ID do usuário.
     *
     * @param id ID a ser atribuído ao usuário.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome do usuário.
     *
     * @return nome do usuário.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Define o nome do usuário.
     *
     * @param username nome a ser atribuído ao usuário.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
