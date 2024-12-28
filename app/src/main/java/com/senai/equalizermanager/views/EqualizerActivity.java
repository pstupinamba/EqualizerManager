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

public class EqualizerActivity extends AppCompatActivity {
    private int userId;
    private RecyclerView recyclerViewSettings;
    private EqualizerSettingsController equalizerController;
    private UserController userController;
    private Button btnAddNewSetting;
    private User user;
    private static int REQUEST_CREATE_SETTING = 1;
    private List<EqualizerSettings> settings;
    SettingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equalizer);

        recyclerViewSettings = findViewById(R.id.recyclerViewSettings);
        recyclerViewSettings.setLayoutManager(new LinearLayoutManager(this));
        settings = new ArrayList<>();
        userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            runOnUiThread(() -> {
                Toast.makeText(this, "Erro: ID do usuário não encontrado", Toast.LENGTH_SHORT).show();
            });
            finish();
            return;
        }
        adapter = new SettingsAdapter(getApplicationContext(), settings, userId, new SettingsAdapter.OnSettingActivatedListener() {
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
        equalizerController = new EqualizerSettingsController(getApplicationContext());
        userController = new UserController(getApplicationContext());

        btnAddNewSetting = findViewById(R.id.btnAddNewSetting);
        TextView tvWelcome = findViewById(R.id.tvWelcome);

        try {
            getUser(userId, tvWelcome);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(user != null){
            runOnUiThread(() ->{
                tvWelcome.setText("Bem-vindo, " + user.getUsername());
            });
        }

        btnAddNewSetting.setOnClickListener(v -> {
            Intent intent = new Intent(EqualizerActivity.this, CreateEqualizerActivity.class);
            intent.putExtra("userId", userId);
            startActivityForResult(intent, REQUEST_CREATE_SETTING);
        });

        try {
            getAllSettingsAndUpdateUI();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        adapter.setClickListener((view, position) -> {
            EqualizerSettings selectedSetting = settings.get(position);

            equalizerController.setActiveSetting(userId, selectedSetting.getId(), new Callback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    runOnUiThread(() ->{
                        Toast.makeText(EqualizerActivity.this, "Configuração ativada: " + selectedSetting.getName(), Toast.LENGTH_SHORT).show();
                    });
                    getAllSettingsAndUpdateUI();
                }

                @Override
                public void onFailure(Exception e) {
                    runOnUiThread(() ->{
                        Toast.makeText(EqualizerActivity.this, "Erro ao ativar configuração: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    private void getAllSettingsAndUpdateUI() {
        equalizerController.getSettingsByUserId(new Callback<List<EqualizerSettings>>() {
            @Override
            public void onSuccess(List<EqualizerSettings> settings) {
                runOnUiThread(() -> {
                    if (settings.isEmpty()) {
                        Toast.makeText(EqualizerActivity.this, "Nenhuma configuração encontrada. Adicione uma nova!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Aqui, estamos passando o listener na criação do adapter
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
