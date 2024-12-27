package com.senai.equalizermanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.senai.equalizermanager.utils.Callback;
import com.senai.equalizermanager.views.adapters.UserAdapter;
import com.senai.equalizermanager.controllers.UserController;
import com.senai.equalizermanager.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserController userController;
    private List<User> users = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        TextView tvEmptyListMessage = findViewById(R.id.tvEmptyListMessage);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            userController = new UserController(getApplicationContext());
            userController.createUser("User 100");
            userController.createUser("User 200");
            getAllUsersAndUpdateUI(tvEmptyListMessage);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllUsersAndUpdateUI(TextView tvEmptyListMessage) {
        userController.getAllUsers(new Callback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                for(User user : users){
                    Log.d("User: ", "" + user.getId());
                }
                MainActivity.this.users.clear();
                MainActivity.this.users.addAll(users);
                runOnUiThread(() -> {
                    if (users.isEmpty()) {
                        tvEmptyListMessage.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        tvEmptyListMessage.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        userAdapter = new UserAdapter(MainActivity.this, users);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(userAdapter);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    Log.e("Error", "Erro ao carregar usuários: " + e.getMessage());
                    tvEmptyListMessage.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                });
            }
        });
    }

}