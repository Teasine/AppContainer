package com.teasine.wecycle.appcontainer;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// -----------------------------------------------------------------------------------
// @author: Jordi Bataller i Mascarell
// -----------------------------------------------------------------------------------
public class PeticionarioREST extends AsyncTask<Void, Void, Boolean> {

    // --------------------------------------------------------------------
    // --------------------------------------------------------------------
    public interface Callback {
        void respuestaRecibida(int codigo, String cuerpo);
    }

    // --------------------------------------------------------------------
    // --------------------------------------------------------------------
    private String elMetodo;
    private String urlDestino;
    private String elCuerpo = null;
    private Callback elCallback;

    private String tipoDeCargaQueEnviamos; // = "text/plain; charset=utf-8";

    private int codigoRespuesta;
    private String cuerpoRespuesta = "";

    // --------------------------------------------------------------------
    // --------------------------------------------------------------------
    public void hacerPeticionREST (String metodo, String urlDestino, String cuerpo, Callback  elCallback ) {
       this.hacerPeticionREST( metodo, urlDestino, cuerpo, elCallback, "text/plain; charset=utf-8");
    } // ()

    // --------------------------------------------------------------------
    // --------------------------------------------------------------------
    public void hacerPeticionREST (String metodo, String urlDestino, String cuerpo, Callback  elCallback, String tipoDeCarga  ) {
        this.elMetodo = metodo;
        this.urlDestino = urlDestino;
        this.elCuerpo = cuerpo;
        this.elCallback = elCallback;

        this.tipoDeCargaQueEnviamos = tipoDeCarga;

        super.execute(); // otro thread ejecutará doInBackground()
    } // ()

    // --------------------------------------------------------------------
    // --------------------------------------------------------------------
    public PeticionarioREST() {
        Log.d("peticionariorestandroid", "constructor()");
    } // ()

    // --------------------------------------------------------------------
    // --------------------------------------------------------------------
    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d("peticionariorestandroid", "doInBackground()");

        try {

            // envio la peticion

            // pagina web para hacer pruebas: URL url = new URL("https://httpbin.org/html");
            // ordinador del despatx 158.42.144.126 // OK URL url = new URL("http://158.42.144.126:8080");

            Log.d("peticionariorestandroid", "doInBackground() me conecto a >" + urlDestino + "<");

            URL url = new URL(urlDestino);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(this.elMetodo);

            // connection.setRequestProperty("Accept", "*/*);

            // connection.setUseCaches(false);
            connection.setDoInput(true);

            if ( ! this.elMetodo.equals("GET") && this.elCuerpo != null ) {
                Log.d("peticionariorestandroid", "doInBackground(): no es get, pongo cuerpo");

                connection.setRequestProperty("Content-Type", this.tipoDeCargaQueEnviamos );

                connection.setDoOutput(true);
                // si no es GET, pongo el cuerpo que me den en la peticin
                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                dos.writeBytes(this.elCuerpo);
                dos.flush();
                dos.close();
            }

            // ya he enviado la peticin
            Log.d("peticionariorestandroid", "doInBackground(): peticin enviada ");

            // ahora obtengo la respuesta

            int rc = connection.getResponseCode();
            String rm = connection.getResponseMessage();
            String respuesta = "" + rc + " : " + rm;
            Log.d("peticionariorestandroid", "doInBackground() recibo respuesta = " + respuesta);
            this.codigoRespuesta = rc;

            try {

                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                Log.d("peticionariorestandroid", "leyendo cuerpo");
                StringBuilder acumulador = new StringBuilder();
                String linea;
                while ( (linea = br.readLine()) != null) {
                    Log.d("peticionariorestandroid", linea);
                    acumulador.append(linea);
                }
                Log.d("peticionariorestandroid", "FIN leyendo cuerpo");

                this.cuerpoRespuesta = acumulador.toString();
                Log.d("peticionariorestandroid", "cuerpo recibido=" + this.cuerpoRespuesta);

                connection.disconnect();

            } catch (IOException ex) {
                // dispara excepcin cuando la respuesta REST no tiene cuerpo y yo intento getInputStream()
                Log.d("peticionariorestandroid", "doInBackground() : parece que no hay cuerpo en la respuesta");
            }

            return true; // doInBackground() termina bien

        } catch (Exception ex) {
            Log.d("peticionariorestandroid", "doInBackground(): ocurrio alguna otra excepcion: " + ex.getMessage());
        }

        return false; // doInBackground() NO termina bien
    } // doInBackground ()

    // --------------------------------------------------------------------
    // --------------------------------------------------------------------
    protected void onPostExecute(Boolean comoFue) {
        // llamado tras doInBackground()
        Log.d("peticionariorestandroid", "onPostExecute() comoFue = " + comoFue);

        // llamo al callback del código usuario
        this.elCallback.respuestaRecibida( this.codigoRespuesta, this.cuerpoRespuesta );
    }

} // class

// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
