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

/**
 * UserProfileActivity - Tela de perfil do usuário, exibindo o nome do usuário
 * a configuração ativa(caso tenha) e o dispositivo Bluetooth conectado (caso exista).
 */

public class UserProfileActivity extends AppCompatActivity {
    // Elementos da interface do usuário
    private TextView tvUserName, tvActiveSetting, tvBluetoothDevice;

    //Controladores
    private UserController userController;
    private EqualizerSettingsController equalizerController;

    //Adapter para trabalhar com o Bluetooth
    private BluetoothAdapter bluetoothAdapter;

    //Objetos de Models User e EqualizerSettings
    private User user;
    private EqualizerSettings settings;

    private int userId;

    /**
     * Método de ciclo de vida chamado quando a Activity é criada.
     * Configura a interface de usuário, inicializa os controladores e objetos necessários,
     * e realiza o carregamento das informações do perfil do usuário.
     *
     * @param savedInstanceState Contém o estado salvo da Activity, caso exista.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile); // Define o layout da Activity

        //Inicialização dos elementos de UI
        tvUserName = findViewById(R.id.tvUserName);
        tvActiveSetting = findViewById(R.id.tvActiveSetting);
        tvBluetoothDevice = findViewById(R.id.tvBluetoothDevices);

        //Inicializa o texto padrão
        tvBluetoothDevice.setText("Não há dispositivos bluetooth conectados.");
        tvActiveSetting.setText("Não há configuração ativa.");

        //Inicialização dos controladores e objetos.
        userController = new UserController(getApplicationContext());
        equalizerController = new EqualizerSettingsController(getApplicationContext());
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //Recebe o ID do usuário através da Intent
        userId = getIntent().getIntExtra("userId", -1);

        //Verifica se o ID é válido
        if (userId == -1) {
            finish();
            return;
        }
        // Verifica a versão para acessar o Bluetooth caso a versão necessite
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
            }
        }

        // Carrega as informações do perfil do usuário
        loadUserProfile(userId);

        // Filtro para monitorar a conexão e desconexão de dispostivos Bluetooth
        IntentFilter bluetoothFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        bluetoothFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(bluetoothReceiver, bluetoothFilter);

    }

    // Broadcast Receiver para monitorar dispositivos Bluetooth conectados e desconectados
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

    /**
     * Carrega o perfil do usuário e a configuração ativa.
     * @param userId
     */
    private void loadUserProfile(int userId) {
        // Carrega o perfil do usuário
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
        // Carrega a configuração ativa do usuário
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

    /**
     * Lida com o resultado da solicitação de permissões.
     * @param requestCode O código da solicitação passado em {@link #requestPermissions(
     * android.app.Activity, String[], int)}
     * @param permissions As permissões solicitadas. Nunca é nulo.
     * @param grantResults Os resultados da concessão para as permissões correspondentes,
     *     que podem ser {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     ou {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Nunca é nulo.
     *
     */
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

    /**
     * Método de ciclo de vida chamado quando a Activity é destruída.
     * Cancela o registro do BroadcastReceiver para evitar vazamento de memória.
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(bluetoothReceiver);
    }

}




