package com.senai.equalizermanager.controllers;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.senai.equalizermanager.dao.AppDatabase;
import com.senai.equalizermanager.models.User;
import com.senai.equalizermanager.utils.Callback;

import java.util.ArrayList;
import java.util.List;



public class UserController {
    private AppDatabase db;

    public UserController(Context context){
        this.db = Room.databaseBuilder(context, AppDatabase.class, "database-name").build();
    }

    public void createUser(String username) throws Exception{
        if(username == null || username.trim().isEmpty()){
            throw new Exception("O nome de usuário não pode ser vazio.");
        }
        User user = new User();
        user.setUsername(username);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    db.userDao().insertUser(user);
                }catch(Exception ex){
                    Log.i("Thread UserController: ", ex.getMessage());
                }
            }
        });
        t.start();
    }


    public void getAllUsers(Callback<List<User>> callback) {
        new Thread(() -> {
            try {
                List<User> users = db.userDao().getAll();
                callback.onSuccess(users);
            } catch (Exception ex) {
                Log.e("Thread UserController", ex.getMessage());
                callback.onFailure(ex);
            }
        }).start();
    }

    public User getUserById(int id) throws Exception{
        User user = db.userDao().getUserById(id);
        if(user == null){
            throw new Exception("Usuário não encontrado.");
        }
        return user;
    }

    public void deleteUser(int id){
        User user = db.userDao().getUserById(id);
        db.userDao().delete(user);
    }
}