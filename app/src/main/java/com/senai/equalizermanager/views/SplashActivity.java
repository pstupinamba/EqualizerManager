package com.senai.equalizermanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.senai.equalizermanager.MainActivity;
import com.senai.equalizermanager.R;

/**
 * SplashActivity - Tela de introdução que exibe uma animação ou logo por um breve período
 * antes de redirecionar o usuário para a MainActivity.
 */
public class SplashActivity extends AppCompatActivity {

    // Constante para definir o tempo de exibição da tela de splash (2 segundos)
    private static final int SPLASH_DELAY = 2000;

    /**
     * Método de ciclo de vida chamado quando a Activity é criada.
     * Aqui, a SplashActivity é configurada e exibe uma tela de introdução por um tempo definido.
     *
     * @param savedInstanceState Contém o estado salvo da Activity, caso exista.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Cria um delay para exibir a tela de splash por um tempo determinado antes de redirecionar
        new Handler().postDelayed(() -> {
            // Cria uma intent para navegar para a MainActivity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);  // Inicia a MainActivity
            finish();  // Finaliza a SplashActivity para que não volte para ela ao pressionar 'voltar'
        }, SPLASH_DELAY); // Define o tempo do delay
    }
}
