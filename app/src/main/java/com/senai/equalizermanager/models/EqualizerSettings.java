package com.senai.equalizermanager.models;

import static androidx.room.ForeignKey.CASCADE;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Classe que representa a entidade "EqualizerSettings" no banco de dados Room.
 * Armazena as configurações do equalizador associadas a um usuário.
 */
@Entity(
        tableName = "equalizer_settings",
        foreignKeys = @ForeignKey(
                entity = User.class, // Classe de referência (tabela "User")
                parentColumns = "id", // Coluna na tabela "User" que serve de chave primária
                childColumns = "id_user", // Coluna nesta tabela que é chave estrangeira
                onDelete = CASCADE // Define que ao excluir o usuário, as configurações relacionadas também serão excluídas
        )
)
public class EqualizerSettings {

    @PrimaryKey(autoGenerate = true)
    private int id; // ID único da configuração (gerado automaticamente)

    private int id_user; // ID do usuário associado (chave estrangeira)
    private int low_freq; // Valor da frequência baixa
    private int mid_freq; // Valor da frequência média
    private int high_freq; // Valor da frequência alta
    private String name; // Nome da configuração
    private boolean isActive; // Indica se esta configuração está ativa

    /**
     * Construtor padrão inicializando valores com padrões.
     */
    public EqualizerSettings() {
        this.low_freq = 0;
        this.mid_freq = 0;
        this.high_freq = 0;
        this.name = "";
        this.isActive = false;
    }

    /**
     * Construtor para inicializar configurações com valores específicos.
     *
     * @param low_freq  valor da frequência baixa.
     * @param mid_freq  valor da frequência média.
     * @param high_freq valor da frequência alta.
     * @param name      nome da configuração.
     */
    public EqualizerSettings(int low_freq, int mid_freq, int high_freq, String name) {
        this.low_freq = low_freq;
        this.mid_freq = mid_freq;
        this.high_freq = high_freq;
        this.name = name;
        this.isActive = false;
    }

    // Métodos getter e setter para acessar e modificar os atributos da entidade.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getLow_freq() {
        return low_freq;
    }

    public void setLow_freq(int low_freq) {
        this.low_freq = low_freq;
    }

    public int getMid_freq() {
        return mid_freq;
    }

    public void setMid_freq(int mid_freq) {
        this.mid_freq = mid_freq;
    }

    public int getHigh_freq() {
        return high_freq;
    }

    public void setHigh_freq(int high_freq) {
        this.high_freq = high_freq;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
