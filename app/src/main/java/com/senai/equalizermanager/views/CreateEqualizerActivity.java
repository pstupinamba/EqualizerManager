    package com.senai.equalizermanager.views;

    import android.annotation.SuppressLint;
    import android.content.Intent;
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

    public class CreateEqualizerActivity extends AppCompatActivity {
        private int userId;
        private EqualizerSettingsController equalizerController;
        private SeekBar seekBarLowFreq, seekBarMidFreq, seekBarHighFreq;
        private TextView tvLowFreqValue, tvMidFreqValue, tvHighFreqValue;
        private Button btnSave;
        private EditText etName;

        @SuppressLint("NewApi")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_equalizer);

            userId = getIntent().getIntExtra("userId", -1);
            equalizerController = new EqualizerSettingsController(getApplicationContext());

            this.etName = findViewById(R.id.etName);
            seekBarLowFreq = findViewById(R.id.seekBarLowFreq);
            seekBarMidFreq = findViewById(R.id.seekBarMidFreq);
            seekBarHighFreq = findViewById(R.id.seekBarHighFreq);
            tvLowFreqValue = findViewById(R.id.tvLowFreqValue);
            tvMidFreqValue = findViewById(R.id.tvMidFreqValue);
            tvHighFreqValue = findViewById(R.id.tvHighFreqValue);
            btnSave = findViewById(R.id.btnSave);


            seekBarLowFreq.setMin(60);
            seekBarLowFreq.setMax(250);
            seekBarMidFreq.setMin(250);
            seekBarMidFreq.setMax(4000);
            seekBarHighFreq.setMin(4000);
            seekBarHighFreq.setMax(16000);
            runOnUiThread(() ->{
                seekBarLowFreq.setProgress(125);
                seekBarMidFreq.setProgress(2000);
                seekBarHighFreq.setProgress(8000);
                tvLowFreqValue.setText(String.valueOf(seekBarLowFreq.getProgress()));
                tvMidFreqValue.setText(String.valueOf(seekBarMidFreq.getProgress()));
                tvHighFreqValue.setText(String.valueOf(seekBarHighFreq.getProgress()));
            });

            seekBarLowFreq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    runOnUiThread(() -> {
                        tvLowFreqValue.setText(String.valueOf(progress));
                    });
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            seekBarMidFreq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    runOnUiThread(() -> {
                        tvMidFreqValue.setText(String.valueOf(progress));
                    });
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            seekBarHighFreq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    runOnUiThread(() -> {
                        tvHighFreqValue.setText(String.valueOf(progress));
                    });
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            btnSave.setOnClickListener(v -> {
                String name = etName.getText().toString().trim();
                if (name.isEmpty()) {
                    runOnUiThread(() ->{
                        Toast.makeText(this, "Por favor, insira um nome para a configuração.", Toast.LENGTH_SHORT).show();
                    });
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
            });

        }
    }

