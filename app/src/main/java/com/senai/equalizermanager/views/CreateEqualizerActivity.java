package com.senai.equalizermanager.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.senai.equalizermanager.R;
import com.senai.equalizermanager.controllers.EqualizerSettingsController;
import com.senai.equalizermanager.models.EqualizerSettings;

/**
 * CreateEqualizerActivity - Tela para criar e salvar configurações personalizadas de equalizador.
 */
public class CreateEqualizerActivity extends AppCompatActivity {
    private int userId; // ID do usuário atual
    private EqualizerSettingsController equalizerController; // Controlador de configurações de equalizador
    private SeekBar seekBarLowFreq, seekBarMidFreq, seekBarHighFreq; // Barras deslizantes para ajustar frequências
    private TextView tvLowFreqValue, tvMidFreqValue, tvHighFreqValue; // Exibe os valores atuais das frequências
    private Button btnSave; // Botão para salvar a configuração
    private EditText etName; // Campo de texto para o nome da configuração

    /**
     * Método chamado quando a Activity é criada. Inicializa os componentes da interface do usuário
     * e configura os parâmetros das barras deslizantes para o equalizador, além de configurar
     * os ouvintes de eventos.
     * @param savedInstanceState Estado salvo da Activity, caso haja.
     */
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_equalizer);

        // Inicializa os elementos da interface do usuário
        userId = getIntent().getIntExtra("userId", -1);
        equalizerController = new EqualizerSettingsController(getApplicationContext());

        etName = findViewById(R.id.etName);
        seekBarLowFreq = findViewById(R.id.seekBarLowFreq);
        seekBarMidFreq = findViewById(R.id.seekBarMidFreq);
        seekBarHighFreq = findViewById(R.id.seekBarHighFreq);
        tvLowFreqValue = findViewById(R.id.tvLowFreqValue);
        tvMidFreqValue = findViewById(R.id.tvMidFreqValue);
        tvHighFreqValue = findViewById(R.id.tvHighFreqValue);
        btnSave = findViewById(R.id.btnSave);

        // Configura os limites e valores iniciais das barras deslizantes
        seekBarLowFreq.setMin(60);
        seekBarLowFreq.setMax(250);
        seekBarMidFreq.setMin(251);
        seekBarMidFreq.setMax(4000);
        seekBarHighFreq.setMin(4001);
        seekBarHighFreq.setMax(16000);

        runOnUiThread(() -> {
            seekBarLowFreq.setProgress(125);
            seekBarMidFreq.setProgress(2000);
            seekBarHighFreq.setProgress(8000);
            tvLowFreqValue.setText(String.valueOf(seekBarLowFreq.getProgress()));
            tvMidFreqValue.setText(String.valueOf(seekBarMidFreq.getProgress()));
            tvHighFreqValue.setText(String.valueOf(seekBarHighFreq.getProgress()));
        });

        // Define ouvintes para atualizar os valores exibidos conforme o usuário ajusta as barras deslizantes
        configureSeekBar(seekBarLowFreq, tvLowFreqValue);
        configureSeekBar(seekBarMidFreq, tvMidFreqValue);
        configureSeekBar(seekBarHighFreq, tvHighFreqValue);

        // Define a ação do botão de salvar
        btnSave.setOnClickListener(v -> saveEqualizerSettings());
    }

    /**
     * Configura um ouvinte para a barra deslizante atualizar o valor exibido em tempo real.
     *
     * @param seekBar A barra deslizante.
     * @param textView O campo de texto para exibir o valor.
     */
    private void configureSeekBar(SeekBar seekBar, TextView textView) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                runOnUiThread(() -> textView.setText(String.valueOf(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    /**
     * Salva a configuração do equalizador com os valores inseridos pelo usuário.
     */
    private void saveEqualizerSettings() {
        String name = etName.getText().toString().trim();

        if (name.isEmpty()) {
            runOnUiThread(() ->
                    Toast.makeText(this, "Por favor, insira um nome para a configuração.", Toast.LENGTH_SHORT).show()
            );
            return;
        }

        int lowFreq = seekBarLowFreq.getProgress();
        int midFreq = seekBarMidFreq.getProgress();
        int highFreq = seekBarHighFreq.getProgress();
        EqualizerSettings settings = new EqualizerSettings(lowFreq, midFreq, highFreq, name);
        settings.setId_user(userId);

        try {
            equalizerController.createEqualizerSettings(settings);
            Toast.makeText(this, "Configuração salva!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
