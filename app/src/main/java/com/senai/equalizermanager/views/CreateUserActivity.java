package com.senai.equalizermanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.senai.equalizermanager.R;
import com.senai.equalizermanager.controllers.UserController;

public class CreateUserActivity extends AppCompatActivity {
    private EditText edtUsername;
    private Button btnSaveUser;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        userController = new UserController(getApplicationContext());

        edtUsername = findViewById(R.id.edtUsername);
        btnSaveUser = findViewById(R.id.btnSaveUser);

        btnSaveUser.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();

            if (!username.isEmpty()) {
                try {
                    userController.createUser(username);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(this, "Usuário " + username + " criado!", Toast.LENGTH_SHORT).show();
                // Configura o resultado para a MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("username", username); // Opcional, caso queira enviar o nome
                setResult(RESULT_OK, resultIntent);

                finish();
            } else {
                Toast.makeText(this, "Por favor, insira um nome de usuário", Toast.LENGTH_SHORT).show();
            }
        });
    }
}