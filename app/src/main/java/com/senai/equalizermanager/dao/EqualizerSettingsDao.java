package com.senai.equalizermanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.senai.equalizermanager.models.EqualizerSettings;

import java.util.List;

/**
 * Interface DAO para realizar operações com a entidade EqualizerSettings no banco de dados.
 * Fornece métodos para consultas, inserções, atualizações e exclusões.
 */
@Dao
public interface EqualizerSettingsDao {

    /**
     * Obtém todos os registros de configurações de equalizador.
     *
     * @return lista de todas as configurações de equalizador.
     */
    @Query("SELECT * FROM equalizer_settings")
    List<EqualizerSettings> getAllSettings();

    /**
     * Obtém as configurações de equalizador associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return lista de configurações de equalizador do usuário.
     */
    @Query("SELECT * FROM equalizer_settings WHERE id_user = :userId")
    List<EqualizerSettings> getSettingsByUserId(int userId);

    /**
     * Obtém uma configuração de equalizador com base no ID.
     *
     * @param settingId ID da configuração.
     * @return a configuração correspondente ao ID, ou null se não encontrada.
     */
    @Query("SELECT * FROM equalizer_settings WHERE id = (:settingId)")
    EqualizerSettings getEqualizeSettingById(int settingId);

    /**
     * Insere uma nova configuração de equalizador no banco de dados.
     *
     * @param setting configuração a ser inserida.
     */
    @Insert
    void insertEqualizerSetting(EqualizerSettings setting);

    /**
     * Atualiza uma configuração de equalizador existente no banco de dados.
     *
     * @param setting configuração a ser atualizada.
     */
    @Update
    void updateEqualizerSetting(EqualizerSettings setting);

    /**
     * Remove uma configuração de equalizador do banco de dados.
     *
     * @param setting configuração a ser removida.
     */
    @Delete
    void deleteEqualizerSetting(EqualizerSettings setting);

    /**
     * Remove todas as configurações de equalizador associadas a um usuário específico.
     *
     * @param userId ID do usuário cujas configurações serão removidas.
     */
    @Query("DELETE FROM equalizer_settings WHERE id_user = :userId")
    void deleteSettingsByUserId(int userId);

    /**
     * Desativa todas as configurações de equalizador de um usuário.
     *
     * @param userId ID do usuário cujas configurações serão desativadas.
     */
    @Query("UPDATE equalizer_settings SET isActive = 0 WHERE id_user = :userId")
    void deactivateAllSettings(int userId);

    /**
     * Ativa uma configuração de equalizador específica.
     *
     * @param settingId ID da configuração a ser ativada.
     */
    @Query("UPDATE equalizer_settings SET isActive = 1 WHERE id = :settingId")
    void activateSetting(int settingId);

    /**
     * Obtém a configuração de equalizador ativa de um usuário específico.
     *
     * @param userId ID do usuário.
     * @return a configuração ativa do usuário, ou null se nenhuma estiver ativa.
     */
    @Query("SELECT * FROM equalizer_settings WHERE id_user = :userId AND isActive = 1 LIMIT 1")
    EqualizerSettings getActiveSettingByUserId(int userId);
}
