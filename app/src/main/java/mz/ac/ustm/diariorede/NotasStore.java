package mz.ac.ustm.diariorede;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Guarda e lê as notas em SharedPreferences (formato JSON).
 * Cada nota contém: data/hora automática, estado da rede e texto.
 */
public class NotasStore {

    private static final String PREFS = "diario_rede";
    private static final String KEY = "notas";

    public static void guardar(Context ctx, String texto, String estado) {
        try {
            SharedPreferences sp = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            JSONArray arr = new JSONArray(sp.getString(KEY, "[]"));

            JSONObject nota = new JSONObject();
            nota.put("data", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                    Locale.getDefault()).format(new Date()));
            nota.put("estado", estado);
            nota.put("texto", texto);
            arr.put(nota);

            sp.edit().putString(KEY, arr.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** Devolve as notas formatadas, da mais recente para a mais antiga. */
    public static List<String> listar(Context ctx) {
        List<String> lista = new ArrayList<>();
        try {
            SharedPreferences sp = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            JSONArray arr = new JSONArray(sp.getString(KEY, "[]"));
            for (int i = arr.length() - 1; i >= 0; i--) {
                JSONObject o = arr.getJSONObject(i);
                lista.add(o.getString("data") + "  •  " + o.getString("estado")
                        + "\n" + o.getString("texto"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
