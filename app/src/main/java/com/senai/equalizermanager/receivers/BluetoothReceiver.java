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

public class BluetoothReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device != null) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    String deviceName = device.getName();
                    Toast.makeText(context, "Dispositivo conectado: " + deviceName, Toast.LENGTH_SHORT).show();
                    Intent localIntent = new Intent("BluetoothConnection");
                    localIntent.putExtra("DeviceName", deviceName);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
                } else {
                    Toast.makeText(context, "Permissão de Bluetooth não concedida!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            Toast.makeText(context, "Dispositivo Bluetooth desconectado!", Toast.LENGTH_SHORT).show();
        }
    }
}
