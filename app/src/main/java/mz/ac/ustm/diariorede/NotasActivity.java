package mz.ac.ustm.diariorede;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Segunda Activity: apresenta a lista de notas guardadas. */
public class NotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        ListView lvNotas = findViewById(R.id.lvNotas);
        TextView tvVazio = findViewById(R.id.tvVazio);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        lvNotas.setEmptyView(tvVazio);
        lvNotas.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, NotasStore.listar(this)));

        btnVoltar.setOnClickListener(v -> finish());
    }
}
