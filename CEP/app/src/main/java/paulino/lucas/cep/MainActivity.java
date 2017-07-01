package paulino.lucas.cep;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnBusca;
    private TextView info;
    private EditText cep;
    private ConnectivityManager connManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = (TextView) findViewById(R.id.text_info);
        cep = (EditText) findViewById(R.id.txt_cep);

        btnBusca = (Button) findViewById(R.id.btn_busca);
        btnBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Teste", cep.getText().toString());
                if (checkConnection()){
                    CEPAsyncTask c = new CEPAsyncTask(info, cep.getText().toString());
                    c.execute();
                }else {
                    int duracao = Toast.LENGTH_SHORT;
                    Context contexto = getApplicationContext();
                    String texto = "Verifique sua conexão com a Internet";
                    Toast toast = Toast.makeText(contexto, texto,duracao);
                    toast.show();
                }
            }
        });
    }

    public boolean checkConnection() {
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
