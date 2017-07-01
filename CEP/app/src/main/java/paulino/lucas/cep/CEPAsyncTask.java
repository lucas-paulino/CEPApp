package paulino.lucas.cep;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CEPAsyncTask extends AsyncTask<String, String, String> {
    private String cep;
    private TextView texto;
    private String content = "";

    public CEPAsyncTask(TextView t, String cep){
        this.texto = t;
        this.cep = cep;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        texto.setText("Vai começar Thread...");
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL("http://viacep.com.br/ws/"+cep+"/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String data = null;
            while ((data = reader.readLine()) != null) {
                content += data + "\n";
            }
        } catch (Exception e) { e.printStackTrace(); }
        return content;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(content != ""){
            texto.setText(ajeitaTexto(content));
        }else{
            texto.setText("Algo de errado não está certo \n Veja se o CEP foi digitado corretamente \n ¯\\_(ツ)_/¯ ");
        }

        Log.v("Teste", "Fim thread");
    }

    private String ajeitaTexto(String texto){
        texto = texto.replace("\"", "").replace("{\n", "").replace("\n}", "");
        return texto;
    }

}
