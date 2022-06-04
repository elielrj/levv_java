package com.example.levv.support;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    private static final int APP_PERMISSOES_ID = 2022;
    private boolean permissaoParaLocalizar;
    private boolean permissãoParaLerEEscrever;
    protected Activity activity;
    private static final String[] PERMISSOES_PARA_LOCALIZACAO =
            {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
            };
    private static final String[] PERMISSOES_PARA_LER_E_ESCREVER =
            {
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            };

    public Permissao(Activity activity) {
        this.activity = activity;
    }


    private boolean solicitarPermissaoParaLocalizarApp() {


            List<String> permissoesNegadas = new ArrayList<>();

            int permissaoNegada = 0;

            for (String permissao : PERMISSOES_PARA_LOCALIZACAO) {

                permissaoNegada = ContextCompat.checkSelfPermission(activity, permissao);

                if (permissaoNegada != PackageManager.PERMISSION_GRANTED) {
                    permissoesNegadas.add(permissao);
                }
            }

            if (!permissoesNegadas.isEmpty()) {

                ActivityCompat.requestPermissions(activity, permissoesNegadas.toArray(new String[permissoesNegadas.size()]), APP_PERMISSOES_ID);


                return permissaoParaLocalizar = false;

            } else {
                return permissaoParaLocalizar = true;
            }

    }

    private boolean solicitarPermissaoParaLerEEscrever() {


            List<String> permissoesNegadas = new ArrayList<>();

            int permissaoNegada = 0;

            for (String permissao : PERMISSOES_PARA_LER_E_ESCREVER) {

                permissaoNegada = ContextCompat.checkSelfPermission(activity, permissao);

                if (permissaoNegada != PackageManager.PERMISSION_GRANTED) {
                    permissoesNegadas.add(permissao);
                }
            }

            if (!permissoesNegadas.isEmpty()) {

                ActivityCompat.requestPermissions(activity,
                        permissoesNegadas.toArray(new String[permissoesNegadas.size()]),
                        APP_PERMISSOES_ID);

                permissãoParaLerEEscrever = false;

            } else {
                permissãoParaLerEEscrever = true;
            }

        return permissãoParaLerEEscrever;

    }

    public boolean isPermissaoParaLocalizar() {

        if(!permissaoParaLocalizar){

            solicitarPermissaoParaLocalizarApp();

        }

        return permissaoParaLocalizar;

    }

    public boolean isPermissaoParaLerEEscrever(){

        if(!permissãoParaLerEEscrever){

            solicitarPermissaoParaLerEEscrever();

        }

        return permissãoParaLerEEscrever;
    }

}
