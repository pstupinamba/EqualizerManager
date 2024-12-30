package com.senai.equalizermanager.controllers;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.senai.equalizermanager.dao.AppDatabase;
import com.senai.equalizermanager.models.User;
import com.senai.equalizermanager.utils.Callback;
import java.util.List;

/**
 * Classe controladora responsável por gerenciar as operações relacionadas ao usuário no banco de dados.
 * Utiliza Room para interação com o banco de dados local.
 */
public class UserController {
    private AppDatabase db;

    /**
     * Construtor que inicializa o banco de dados Room.
     * @param context Contexto da aplicação, necessário para a criação do banco de dados.
     */
    public UserController(Context context){
        // Inicializa o banco de dados Room, utilizando a classe AppDatabase
        this.db = Room.databaseBuilder(context, AppDatabase.class, "database-name")
                .fallbackToDestructiveMigration()  // Caso ocorra falha na migração, o banco será destruído e recriado
                .build();
    }

    /**
     * Cria um novo usuário no banco de dados.
     * @param username Nome do usuário a ser criado.
     * @throws Exception Se o nome de usuário for nulo ou vazio, lança uma exceção.
     */
    public void createUser(String username) throws Exception{
        // Verifica se o nome de usuário não é nulo ou vazio
        if(username == null || username.trim().isEmpty()){
            throw new Exception("O nome de usuário não pode ser vazio.");
        }

        // Cria o objeto usuário com o nome fornecido
        User user = new User();
        user.setUsername(username);

        // Executa a inserção em uma thread separada para não bloquear a interface do usuário
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    // Insere o usuário no banco de dados
                    db.userDao().insertUser(user);
                }catch(Exception ex){
                    // Caso ocorra uma exceção, é registrada no log
                    Log.i("Thread UserController: ", ex.getMessage());
                }
            }
        });
        t.start();  // Inicia a thread para a operação de inserção
    }

    /**
     * Obtém todos os usuários cadastrados no banco de dados.
     * @param callback O callback para retornar a lista de usuários ou uma falha.
     */
    public void getAllUsers(Callback<List<User>> callback) {
        new Thread(() -> {
            try {
                // Recupera todos os usuários do banco de dados
                List<User> users = db.userDao().getAll();
                // Se a operação for bem-sucedida, retorna a lista de usuários pelo callback
                callback.onSuccess(users);
            } catch (Exception ex) {
                // Caso ocorra uma falha, é registrada no log e o callback é chamado com erro
                Log.e("Thread UserController", ex.getMessage());
                callback.onFailure(ex);
            }
        }).start();  // Executa a operação em uma thread separada
    }

    /**
     * Obtém um usuário pelo seu ID.
     * @param callback O callback para retornar o usuário ou uma falha.
     * @param userId O ID do usuário a ser recuperado.
     */
    public void getUserById(Callback<User> callback, int userId){
        new Thread(() ->{
            try{
                // Recupera o usuário pelo ID
                User user = db.userDao().getUserById(userId);
                // Se o usuário for encontrado, retorna pelo callback
                callback.onSuccess(user);
            }catch (Exception ex){
                // Caso ocorra uma falha, é registrada no log e o callback é chamado com erro
                Log.e("Thread UserController", ex.getMessage());
                callback.onFailure(ex);
            }
        }).start();  // Executa a operação em uma thread separada
    }

    /**
     * Exclui um usuário do banco de dados pelo seu ID.
     * @param id O ID do usuário a ser excluído.
     */
    public void deleteUser(int id){
        // Executa a operação em uma thread separada para não bloquear a interface do usuário
        new Thread(() -> {
            try {
                // Recupera o usuário pelo ID
                User user = db.userDao().getUserById(id);
                // Se o usuário for encontrado, exclui do banco de dados
                if (user != null) {
                    db.userDao().delete(user);
                } else {
                    // Se o usuário não for encontrado, registra no log
                    Log.e("UserController", "Usuário não encontrado.");
                }
            } catch (Exception ex) {
                // Caso ocorra uma falha, é registrada no log
                Log.e("Thread UserController", ex.getMessage());
            }
        }).start();  // Executa a operação de exclusão em uma thread separada
    }
}
