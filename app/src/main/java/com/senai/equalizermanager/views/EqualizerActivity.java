package com.senai.equalizermanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.senai.equalizermanager.R;
import com.senai.equalizermanager.controllers.EqualizerSettingsController;
import com.senai.equalizermanager.controllers.UserController;
import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.models.User;
import com.senai.equalizermanager.utils.Callback;
import com.senai.equalizermanager.views.adapters.SettingsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * EqualizerActivity - Tela que exibe as configurações do equalizador de um usuário.
 */
public class EqualizerActivity extends AppCompatActivity {
    private int userId; // ID do usuário
    private RecyclerView recyclerViewSettings; // RecyclerView para listar as configurações do equalizador
    private EqualizerSettingsController equalizerController; // Controlador para gerenciar configurações do equalizador
    private UserController userController; // Controlador para gerenciar o usuário
    private Button btnAddNewSetting; // Botão para adicionar uma nova configuração
    private User user; // Objeto que representa o usuário
    private static int REQUEST_CREATE_SETTING = 1; // Código de requisição para criação de configuração
    private List<EqualizerSettings> settings; // Lista de configurações de equalizador
    SettingsAdapter adapter; // Adapter para o RecyclerView

    /**
     * Método chamado quando a Activity é criada.
     * Inicializa os componentes de interface, configura os manipuladores de eventos e carrega as configurações de equalização do usuário.
     * @param savedInstanceState Contém o estado salvo da Activity, caso exista.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equalizer);

        // Inicialização da interface
        recyclerViewSettings = findViewById(R.id.recyclerViewSettings);
        recyclerViewSettings.setLayoutManager(new LinearLayoutManager(this));
        settings = new ArrayList<>();
        userId = getIntent().getIntExtra("userId", -1);

        // Valida se o ID do usuário foi passado corretamente
        if (userId == -1) {
            runOnUiThread(() -> {
                Toast.makeText(this, "Erro: ID do usuário não encontrado", Toast.LENGTH_SHORT).show();
            });
            finish();
            return;
        }

        // Configuração do Adapter para o RecyclerView
        adapter = new SettingsAdapter(getApplicationContext(), settings, userId, new SettingsAdapter.OnSettingActivatedListener() {
            @Override
            public void onSettingActivated(EqualizerSettings selectedSetting) {
                // Ativa a configuração do equalizador selecionada
                equalizerController.setActiveSetting(userId, selectedSetting.getId(), new Callback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        runOnUiThread(() -> {
                            Toast.makeText(EqualizerActivity.this, "Configuração ativada: " + selectedSetting.getName(), Toast.LENGTH_SHORT).show();
                        });
                        getAllSettingsAndUpdateUI();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(EqualizerActivity.this, "Erro ao ativar configuração: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });

        recyclerViewSettings.setAdapter(adapter);
        equalizerController = new EqualizerSettingsController(getApplicationContext());
        userController = new UserController(getApplicationContext());

        btnAddNewSetting = findViewById(R.id.btnAddNewSetting);
        TextView tvWelcome = findViewById(R.id.tvWelcome);

        // Busca e exibe o usuário
        try {
            getUser(userId, tvWelcome);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (user != null) {
            runOnUiThread(() -> {
                tvWelcome.setText("Bem-vindo, " + user.getUsername());
            });
        }

        // Ação do botão de adicionar nova configuração
        btnAddNewSetting.setOnClickListener(v -> {
            Intent intent = new Intent(EqualizerActivity.this, CreateEqualizerActivity.class);
            intent.putExtra("userId", userId);
            startActivityForResult(intent, REQUEST_CREATE_SETTING);
        });

        // Carrega as configurações do equalizador do usuário
        try {
            getAllSettingsAndUpdateUI();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Configuração do ouvinte de clique nos itens do RecyclerView
        adapter.setClickListener((view, position) -> {
            EqualizerSettings selectedSetting = settings.get(position);

            equalizerController.setActiveSetting(userId, selectedSetting.getId(), new Callback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    runOnUiThread(() -> {
                        Toast.makeText(EqualizerActivity.this, "Configuração ativada: " + selectedSetting.getName(), Toast.LENGTH_SHORT).show();
                    });
                    getAllSettingsAndUpdateUI();
                }

                @Override
                public void onFailure(Exception e) {
                    runOnUiThread(() -> {
                        Toast.makeText(EqualizerActivity.this, "Erro ao ativar configuração: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    /**
     * Método para atualizar a lista de configurações de equalizador do usuário.
     */
    private void getAllSettingsAndUpdateUI() {
        equalizerController.getSettingsByUserId(new Callback<List<EqualizerSettings>>() {
            @Override
            public void onSuccess(List<EqualizerSettings> settings) {
                runOnUiThread(() -> {
                    if (settings.isEmpty()) {
                        Toast.makeText(EqualizerActivity.this, "Nenhuma configuração encontrada. Adicione uma nova!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Atualiza o adapter com as configurações obtidas
                        adapter = new SettingsAdapter(EqualizerActivity.this, settings, userId, new SettingsAdapter.OnSettingActivatedListener() {
                            @Override
                            public void onSettingActivated(EqualizerSettings selectedSetting) {
                                equalizerController.setActiveSetting(userId, selectedSetting.getId(), new Callback<Void>() {
                                    @Override
                                    public void onSuccess(Void result) {
                                        runOnUiThread(() -> {
                                            Toast.makeText(EqualizerActivity.this, "Configuração ativada: " + selectedSetting.getName(), Toast.LENGTH_SHORT).show();
                                        });
                                        getAllSettingsAndUpdateUI();
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        runOnUiThread(() -> {
                                            Toast.makeText(EqualizerActivity.this, "Erro ao ativar configuração: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                });
                            }
                        });
                        recyclerViewSettings.setAdapter(adapter);
                        recyclerViewSettings.setLayoutManager(new LinearLayoutManager(EqualizerActivity.this));
                    }
                });
            }

            @Override
            public void onFailure(Exception ex) {
                Log.e("EqualizerActivity", "Erro ao buscar configurações: " + ex.getMessage());
                runOnUiThread(() -> {
                    Toast.makeText(EqualizerActivity.this, "Erro ao carregar configurações.", Toast.LENGTH_SHORT).show();
                });
            }
        }, userId);
    }

    /**
     * Método para buscar o usuário pelo ID e exibir o nome na interface.
     */
    private void getUser(int userId, TextView tvWelcome) {
        userController.getUserById(new Callback<User>() {
            @Override
            public void onSuccess(User result) {
                Log.d("UserController", "Usuário encontrado: " + result.getUsername());
                runOnUiThread(() -> {
                    user = result;
                    tvWelcome.setText("Bem-vindo, " + user.getUsername());
                });
            }

            @Override
            public void onFailure(Exception ex) {
                Log.e("UserController", "Erro ao buscar o usuário: " + ex.getMessage());
            }
        }, userId);
    }

    /**
     * Método para tratar o resultado da criação de uma nova configuração.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CREATE_SETTING && resultCode == RESULT_OK) {
            try {
                getAllSettingsAndUpdateUI();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
