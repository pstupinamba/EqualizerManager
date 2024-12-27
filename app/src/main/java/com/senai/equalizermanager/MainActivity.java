package com.senai.equalizermanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.senai.equalizermanager.controllers.UserController;
import com.senai.equalizermanager.models.User;

import java.util.ArrayList;
import java.util.List;

//LISTA DE USUÁRIOS
public class MainActivity extends AppCompatActivity {
    private UserController userController;
    private List<User> users;
//    private TextView tvSpeed;
//    private Text View tvRpm;
//    private Button btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userController = new UserController(getApplicationContext());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        findViewById(R.id.tvSpeed);

        try {
            userController = new UserController(getApplicationContext());
            users = new ArrayList<User>();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}