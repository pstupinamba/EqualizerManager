package com.senai.equalizermanager.receivers;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Receiver para monitorar eventos de conexão e desconexão de dispositivos Bluetooth.
 */
public class BluetoothReceiver extends BroadcastReceiver {

    /**
     * Método chamado quando um evento de broadcast é recebido.
     *
     * @param context o contexto da aplicação.
     * @param intent  o intent contendo informações sobre o evento.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // Verifica se a ação é de conexão de dispositivo Bluetooth
        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (device != null) {
                // Verifica se a permissão de conexão Bluetooth foi concedida
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    String deviceName = device.getName(); // Obtém o nome do dispositivo conectado
                    Toast.makeText(context, "Dispositivo conectado: " + deviceName, Toast.LENGTH_SHORT).show();

                    // Envia um broadcast local informando sobre a conexão
                    Intent localIntent = new Intent("BluetoothConnection");
                    localIntent.putExtra("DeviceName", deviceName);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
                } else {
                    // Notifica que a permissão de Bluetooth não foi concedida
                    Toast.makeText(context, "Permissão de Bluetooth não concedida!", Toast.LENGTH_SHORT).show();
                }
            }
            // Verifica se a ação é de desconexão de dispositivo Bluetooth
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            Toast.makeText(context, "Dispositivo Bluetooth desconectado!", Toast.LENGTH_SHORT).show();
        }
    }
}
