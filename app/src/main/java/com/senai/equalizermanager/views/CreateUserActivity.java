package com.senai.equalizermanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.senai.equalizermanager.R;
import com.senai.equalizermanager.controllers.UserController;

/**
 * CreateUserActivity - Tela para criação de um novo usuário.
 */
public class CreateUserActivity extends AppCompatActivity {
    private EditText edtUsername; // Campo de entrada para o nome do usuário
    private Button btnSaveUser; // Botão para salvar o usuário
    private UserController userController; // Controlador para gerenciar operações relacionadas a usuários

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        // Inicializa o controlador de usuários e os elementos da interface do usuário
        userController = new UserController(getApplicationContext());
        edtUsername = findViewById(R.id.edtUsername);
        btnSaveUser = findViewById(R.id.btnSaveUser);

        // Define a ação do botão para salvar um novo usuário
        btnSaveUser.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();

            if (!username.isEmpty()) {
                // Cria o usuário e exibe uma mensagem de sucesso
                try {
                    userController.createUser(username);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(this, "Usuário " + username + " criado!", Toast.LENGTH_SHORT).show();

                // Retorna o nome do usuário criado para a Activity anterior
                Intent resultIntent = new Intent();
                resultIntent.putExtra("username", username);
                setResult(RESULT_OK, resultIntent);

                finish();
            } else {
                // Exibe uma mensagem caso o nome do usuário esteja vazio
                Toast.makeText(this, "Por favor, insira um nome de usuário", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
