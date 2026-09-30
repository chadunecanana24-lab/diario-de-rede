package mz.ac.ustm.diariorede;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity principal: mostra o estado real da ligação de rede (Wi-Fi, dados
 * móveis ou sem ligação), permite escrever uma nota e abrir a lista de notas.
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvEstado;
    private EditText etNota;
    private ConnectivityManager cm;
    private ConnectivityManager.NetworkCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvEstado = findViewById(R.id.tvEstado);
        etNota = findViewById(R.id.etNota);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnVerNotas = findViewById(R.id.btnVerNotas);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        btnGuardar.setOnClickListener(v -> {
            String texto = etNota.getText().toString().trim();
            if (texto.isEmpty()) {
                etNota.setError(getString(R.string.erro_nota_vazia));
                return;
            }
            NotasStore.guardar(this, texto, getEstadoRede());
            etNota.setText("");
            Toast.makeText(this, R.string.nota_guardada, Toast.LENGTH_SHORT).show();
        });

        // Intent explícito para ligar as duas Activities
        btnVerNotas.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, NotasActivity.class)));

        // Actualiza o estado automaticamente quando a rede muda
        callback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                runOnUiThread(() -> atualizarEstado());
            }

            @Override
            public void onLost(Network network) {
                runOnUiThread(() -> atualizarEstado());
            }

            @Override
            public void onCapabilitiesChanged(Network network, NetworkCapabilities caps) {
                runOnUiThread(() -> atualizarEstado());
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        atualizarEstado();
        cm.registerDefaultNetworkCallback(callback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cm.unregisterNetworkCallback(callback);
    }

    /** Actualiza o texto e a cor do estado (verde, laranja ou vermelho). */
    private void atualizarEstado() {
        String estado = getEstadoRede();
        tvEstado.setText(estado);
        int cor;
        if (estado.equals(getString(R.string.estado_wifi))) {
            cor = 0xFF2E7D32;       // verde
        } else if (estado.equals(getString(R.string.estado_movel))) {
            cor = 0xFFEF6C00;       // laranja
        } else {
            cor = 0xFFC62828;       // vermelho
        }
        tvEstado.setTextColor(cor);
    }

    /** Devolve "Wi-Fi", "Dados móveis" ou "Sem ligação". */
    private String getEstadoRede() {
        Network rede = cm.getActiveNetwork();
        if (rede == null) {
            return getString(R.string.estado_sem);
        }
        NetworkCapabilities caps = cm.getNetworkCapabilities(rede);
        if (caps == null) {
            return getString(R.string.estado_sem);
        }
        if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return getString(R.string.estado_wifi);
        }
        if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return getString(R.string.estado_movel);
        }
        return getString(R.string.estado_sem);
    }
  }
