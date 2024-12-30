package com.senai.equalizermanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.senai.equalizermanager.models.User;

import java.util.List;

/**
 * Interface DAO para gerenciar operações relacionadas à entidade User no banco de dados.
 * Contém métodos para realizar consultas, inserções, atualizações e exclusões.
 */
@Dao
public interface UserDao {

    /**
     * Obtém todos os registros da tabela User.
     *
     * @return lista de todos os usuários no banco de dados.
     */
    @Query("SELECT * FROM user")
    List<User> getAll();

    /**
     * Carrega os usuários com base em uma lista de IDs.
     *
     * @param userIds array de IDs dos usuários.
     * @return lista de usuários correspondentes aos IDs fornecidos.
     */
    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    /**
     * Obtém um único usuário com base no ID.
     *
     * @param userId ID do usuário desejado.
     * @return o usuário correspondente ao ID, ou null se não encontrado.
     */
    @Query("SELECT * FROM user WHERE id = (:userId)")
    User getUserById(int userId);

    /**
     * Insere múltiplos usuários no banco de dados.
     *
     * @param users array de usuários a serem inseridos.
     */
    @Insert
    void insertAll(User... users);

    /**
     * Insere um único usuário no banco de dados.
     *
     * @param user usuário a ser inserido.
     */
    @Insert
    void insertUser(User user);

    /**
     * Atualiza as informações de um usuário existente no banco de dados.
     *
     * @param user usuário a ser atualizado.
     */
    @Update
    void updateUser(User user);

    /**
     * Remove um usuário do banco de dados.
     *
     * @param user usuário a ser removido.
     */
    @Delete
    void delete(User user);
}
