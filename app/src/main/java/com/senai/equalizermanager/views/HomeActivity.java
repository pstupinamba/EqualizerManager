package com.senai.equalizermanager.views;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.senai.equalizermanager.MainActivity;
import com.senai.equalizermanager.R;
import com.senai.equalizermanager.controllers.EqualizerSettingsController;
import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.utils.Callback;

import java.util.List;

/**
 * HomeActivity - Tela inicial após login, permitindo ao usuário interagir com configurações do equalizador.
 */
public class HomeActivity extends AppCompatActivity {
    private EqualizerSettingsController equalizerController; // Controlador para gerenciar configurações do equalizador
    private int userId; // ID do usuário
    private Button btnChooseConfiguration, btnCreateConfiguration, btnChangeUser, btnUserProfile; // Botões para navegação
    private TextView tvWelcome; // TextView para exibir a mensagem de boas-vindas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializa o controlador de configurações do equalizador
        equalizerController = new EqualizerSettingsController(getApplicationContext());

        // Inicializa os elementos da interface
        tvWelcome = findViewById(R.id.tvWelcome);
        btnChooseConfiguration = findViewById(R.id.btnChooseConfiguration);
        btnCreateConfiguration = findViewById(R.id.btnCreateConfiguration);
        btnChangeUser = findViewById(R.id.btnChangeUser);
        btnUserProfile = findViewById(R.id.btnUserProfile);

        // Obtém o ID do usuário a partir da Intent
        userId = getIntent().getIntExtra("userId", -1);

        // Valida se o ID do usuário é válido
        if (userId == -1) {
            Toast.makeText(this, "Erro: ID do usuário não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Carrega as configurações do equalizador para o usuário
        equalizerController.getSettingsByUserId(new Callback<List<EqualizerSettings>>() {
            @Override
            public void onSuccess(List<EqualizerSettings> settings) {
                runOnUiThread(() -> {
                    // Desabilita o botão "Escolher Configuração" caso não haja configurações disponíveis
                    if (settings.isEmpty()) {
                        btnChooseConfiguration.setEnabled(false);
                        btnChooseConfiguration.setText("Nenhuma configuração disponível");
                    } else {
                        btnChooseConfiguration.setEnabled(true);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Erro ao carregar configurações", Toast.LENGTH_SHORT).show();
                });
            }
        }, userId);

        // Configura o clique do botão para escolher a configuração
        btnChooseConfiguration.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EqualizerActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        // Configura o clique do botão para criar uma nova configuração
        btnCreateConfiguration.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CreateEqualizerActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        // Configura o clique do botão para mudar o usuário
        btnChangeUser.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Configura o clique do botão para acessar o perfil do usuário
        btnUserProfile.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarrega as configurações sempre que a atividade for retomada
        loadSettings();
    }

    /**
     * Método para carregar as configurações do equalizador para o usuário.
     */
    private void loadSettings() {
        equalizerController.getSettingsByUserId(new Callback<List<EqualizerSettings>>() {
            @Override
            public void onSuccess(List<EqualizerSettings> settings) {
                runOnUiThread(() -> {
                    // Atualiza o botão de "Escolher Configuração" de acordo com a disponibilidade de configurações
                    if (settings.isEmpty()) {
                        btnChooseConfiguration.setEnabled(false);
                        btnChooseConfiguration.setText("Nenhuma configuração disponível");
                    } else {
                        btnChooseConfiguration.setEnabled(true);
                        btnChooseConfiguration.setText("Escolher Configuração");
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Erro ao carregar configurações", Toast.LENGTH_SHORT).show();
                });
            }
        }, userId);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        // Adicione código de limpeza aqui, caso necessário.
    }
}
