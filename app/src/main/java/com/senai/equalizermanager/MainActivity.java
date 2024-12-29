package com.senai.equalizermanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.senai.equalizermanager.utils.Callback;
import com.senai.equalizermanager.views.CreateUserActivity;
import com.senai.equalizermanager.views.HomeActivity;
import com.senai.equalizermanager.views.adapters.UserAdapter;
import com.senai.equalizermanager.controllers.UserController;
import com.senai.equalizermanager.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity - Tela principal do aplicativo que exibe a lista de usuários.
 */
public class MainActivity extends AppCompatActivity {
    private UserController userController; // Controlador de operações relacionadas ao usuário
    private List<User> users = new ArrayList<>(); // Lista de usuários
    private RecyclerView recyclerView; // Componente de lista para exibir usuários
    private UserAdapter userAdapter; // Adaptador para o RecyclerView
    private Button btnAddUser; // Botão para adicionar um novo usuário
    private static int REQUEST_CREATE_USER = 1; // Código de solicitação para criar um usuário
    TextView tvEmptyListMessage; // Text View que informa se não existir usuários salvos no banco de dados.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        userController = new UserController(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        tvEmptyListMessage = findViewById(R.id.tvEmptyListMessage);
        btnAddUser = findViewById(R.id.btnAddUser);

        // Configura o clique no botão para abrir a tela de criação de usuário
        btnAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
            startActivityForResult(intent, REQUEST_CREATE_USER);
        });

        // Ajusta os paddings conforme os insets do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Carrega e atualiza a lista de usuários
        try {
            getAllUsersAndUpdateUI();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtém todos os usuários e atualiza a interface de usuário.
     */
    private void getAllUsersAndUpdateUI() {
        userController.getAllUsers(new Callback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
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

                        // Configura o clique nos itens do RecyclerView
                        userAdapter.setClickListener((view, position) -> {
                            User user = users.get(position);
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("userId", user.getId());
                            startActivity(intent);
                        });
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

    /**
     * Gerencia os resultados das atividades iniciadas.
     *
     * @param requestCode Código de solicitação da atividade.
     * @param resultCode  Código de resultado retornado pela atividade.
     * @param data        Dados retornados pela atividade.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CREATE_USER && resultCode == RESULT_OK) {
            TextView tvEmptyListMessage = findViewById(R.id.tvEmptyListMessage);
            try {
                getAllUsersAndUpdateUI();
            } catch (Exception e) {
                Log.e("Error", "Erro no resultado da activity: " + e.getMessage());
            }
        }
    }
}
