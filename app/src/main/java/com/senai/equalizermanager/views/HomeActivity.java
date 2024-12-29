package com.senai.equalizermanager.views;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.senai.equalizermanager.MainActivity;

import com.senai.equalizermanager.R;
import com.senai.equalizermanager.controllers.EqualizerSettingsController;
import com.senai.equalizermanager.controllers.UserController;
import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.models.User;
import com.senai.equalizermanager.receivers.BluetoothReceiver;
import com.senai.equalizermanager.utils.Callback;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private EqualizerSettingsController equalizerController;
    private int userId;
    private Button btnChooseConfiguration, btnCreateConfiguration, btnChangeUser, btnUserProfile;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        equalizerController = new EqualizerSettingsController(getApplicationContext());

        tvWelcome = findViewById(R.id.tvWelcome);
        btnChooseConfiguration = findViewById(R.id.btnChooseConfiguration);
        btnCreateConfiguration = findViewById(R.id.btnCreateConfiguration);
        btnChangeUser = findViewById(R.id.btnChangeUser);
        btnUserProfile = findViewById(R.id.btnUserProfile);

        userId = getIntent().getIntExtra("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Erro: ID do usuário não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        equalizerController.getSettingsByUserId(new Callback<List<EqualizerSettings>>() {
            @Override
            public void onSuccess(List<EqualizerSettings> settings) {
                runOnUiThread(() -> {
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

        btnChooseConfiguration.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EqualizerActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        btnCreateConfiguration.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CreateEqualizerActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        btnChangeUser.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnUserProfile.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
    }

//    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String deviceName = intent.getStringExtra("DeviceName");
//            if(deviceName != null && !deviceName.isEmpty()){
//                tvBluetoothDevice.setText("Conectado a: " + deviceName);
//                Toast.makeText(HomeActivity.this, "Dispositivo conectado: " + deviceName, Toast.LENGTH_SHORT).show();
//            }else{
//                tvBluetoothDevice.setText("Nenhum dispositivo conectado.");
//            }
//        }
//    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(bluetoothReceiver);
    }
}
