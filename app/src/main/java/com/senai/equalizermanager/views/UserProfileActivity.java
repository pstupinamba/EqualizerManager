package com.senai.equalizermanager.views;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.senai.equalizermanager.R;
import com.senai.equalizermanager.controllers.EqualizerSettingsController;
import com.senai.equalizermanager.controllers.UserController;
import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.models.User;
import com.senai.equalizermanager.utils.Callback;

import java.util.Set;

public class UserProfileActivity extends AppCompatActivity {
    private TextView tvUserName, tvActiveSetting, tvBluetoothDevice;
    private UserController userController;
    private EqualizerSettingsController equalizerController;
    private BluetoothAdapter bluetoothAdapter;
    private User user;
    private EqualizerSettings settings;
    private int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tvUserName = findViewById(R.id.tvUserName);
        tvActiveSetting = findViewById(R.id.tvActiveSetting);
        tvBluetoothDevice = findViewById(R.id.tvBluetoothDevices);

        tvBluetoothDevice.setText("Não há dispositivos bluetooth conectados.");


        userController = new UserController(getApplicationContext());
        equalizerController = new EqualizerSettingsController(getApplicationContext());
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        user = new User();
        settings = new EqualizerSettings();

        userId = getIntent().getIntExtra("userId", -1);

        if (userId == -1) {
            finish();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
            }
        }

        loadUserProfile(userId);

        IntentFilter bluetoothFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        bluetoothFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(bluetoothReceiver, bluetoothFilter);

    }

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device != null){
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        String deviceName = device.getName();
                        if (deviceName != null && !deviceName.isEmpty()) {
                            tvBluetoothDevice.setText("Conectado a: " + deviceName);
                            Toast.makeText(UserProfileActivity.this, "Dispositivo conectado: " + deviceName, Toast.LENGTH_SHORT).show();
                        } else {
                            tvBluetoothDevice.setText("Dispositivo conectado sem nome.");
                        }
                    }

                }
            }else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                tvBluetoothDevice.setText("Nenhum dispositivo conectado.");
                Toast.makeText(UserProfileActivity.this, "Dispositivo desconectado!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void loadUserProfile(int userId) {
        userController.getUserById(new Callback<User>() {
            @Override
            public void onSuccess(User result) {
                runOnUiThread(() -> {
                    user = result;
                    tvUserName.setText("Usuário : " + user.getUsername());
                });
            }

            @Override
            public void onFailure(Exception ex) {
                Log.e("UserController", "Erro ao buscar o usuário: " + ex.getMessage());
            }
        }, userId);
        equalizerController.getActiveSetting(userId, new Callback<EqualizerSettings>() {
            @Override
            public void onSuccess(EqualizerSettings result) {
                runOnUiThread(() -> {
                    settings = result;
                    tvActiveSetting.setText("Configuração Ativa: " + settings.getName());
                });
            }
            @Override
            public void onFailure(Exception ex) {
                Log.e("EqualizerController", "Erro ao buscar o usuário: " + ex.getMessage());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão Bluetooth concedida!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão Bluetooth negada!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
//        unregisterReceiver(activeSettingReceiver);
        unregisterReceiver(bluetoothReceiver);
    }

}




