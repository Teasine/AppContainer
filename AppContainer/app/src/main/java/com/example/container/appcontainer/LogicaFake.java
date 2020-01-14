// .....................................................................
// Autor: Santiago Pérez Torres
// Fecha inicio: 24/10/2019
// Última actualización: 29/12/2019
// LogicaFake.js
// .....................................................................

package com.example.container.appcontainer;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogicaFake {

    private final String TAG = "---LogicaDebug---";

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    interface RespuestaAPreguntarAlgo {
        public void respuesta(String respuesta);

    } // interface


    private String urlServidor = "http://192.168.1.139:8080/";


    // -------------------------------------------------------------------------------------------------------
    //                      --> obtenerContenedoresValencia() --> elCallback: Callback
    // -------------------------------------------------------------------------------------------------------
    public void obtenerContenedoresValencia(PeticionarioREST.Callback elCallback) {

        PeticionarioREST elPeticionario = new PeticionarioREST();

        Map<String, String> params = new HashMap<String, String>();
        JSONObject eljson = new JSONObject(params);

        elPeticionario.hacerPeticionREST("GET", this.urlServidor + "obtenerContenedoresValencia", eljson.toString(), elCallback,
                "application/json; charset=utf-8"
        );
    }

} // class

