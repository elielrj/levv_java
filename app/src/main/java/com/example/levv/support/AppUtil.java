package com.example.levv.support;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppUtil {

    public static final String LATITUDE_ENTREGA = "latitudeEntrega";
    public static final String LONGITUDE_ENTREGA = "longitudeEntrega";
    public static final String LATITUDE_COLETA = "latitudeColeta";
    public static final String LONGITUDE_COLETA = "longitudeColeta";
    public static final String ENDERECO_COLETA_DO_PEDIDO = "enderecoColetaDoPedido";
    public static final String ENDERECO_ENTREGA_PEDIDO = "enderecoEntregaDoPedido";
    public static final String TELA_ORIGEM = "tela_origem";
    public static final String TELA_ACOMPANHAR_PEDIDO = "acompanhar";
    public static final String TELA_ENTREGAR_PEDIDO = "entregar";
    public static final double LATITUDE_TUBARAO = -28.48155;
    public static final double LONGITUDE_TUBARAO = -49.00693;
    public static final String ERRO_AO_BUSCAR_TELA_ORIGEM = "erro";
    public static final String ERRO_AO_BUSCAR_ENDERECO_COLETA = "erro ao buscar endereço de coleta";
    public static final String ERRO_AO_BUSCAR_ENDERECO_ENTREGA = "Erro ao buscar endereço de entrega";
    public static final String ERRO_AO_BUSCAR_NUMERO_DO_PEDIDO = "Erro ao buscar endereço Nr Pedido";
    public static final String NUMERO_DO_PEDIDO = "numeroPedido";
    public static final String MAKER_MINHA_LOCALIZACAO = "Minha Localização";
    public static final String MAKER_COLETA = "Coleta";
    public static final String MAKER_ENTREGA = "Entrega";
    public static final String SHARED_PREFERENCES_NAME_APP = "datalevv";
    public static final String VALOR_DO_PEDIDO = "valorPedido";
    public static final String TIPO_DE_USUARIO = "tipoDeUsuario";
    public static final String ERRO_AO_BUSCAR_TIPO_DE_USUARIO = "Erro tipo de usuário";

    /**
     * @PrimeiraLetra em Mausculas
     */
    public static String upperCaseFirst(String val) {

        val = val.toLowerCase();

        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String dataAtual() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        return formatterData.format(agora);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String horaAtual() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        return formatterHora.format(agora);
    }

    public static List<String> extrairNomeESobrenome(String nomeCompleto) {

        String[] textoSeparado = nomeCompleto.split(" ");
        String name = "";
        String sobrename = "";

        for (int i = 0; i < textoSeparado.length; i++) {
            if (i == 0) {
                name += AppUtil.upperCaseFirst(textoSeparado[i]);
            } else {
                if (textoSeparado[i].length() == 2) {
                    sobrename += " " + textoSeparado[i];
                } else {
                    sobrename += " " + AppUtil.upperCaseFirst(textoSeparado[i]);
                }
            }
        }

        List<String> lista = new ArrayList<>();
        lista.add(name);
        lista.add(sobrename);

        return lista;
    }

    public static String removerMascaraCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
        return cpf;
    }

    public static String removerMascaraCnpj(String cnpj) {
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("-", "");
        cnpj = cnpj.replace("/", "");
        return cnpj;
    }

    public static String removerMascaraCep(String cep) {
        cep = cep.replace("-", "");
        return cep;
    }

    public static float valorDecimal(String valor) {
        valor = valor.replace(",", ".");
        float valorFormatado = Float.parseFloat(valor);
        return valorFormatado;
    }

    public static int pesoEstimado(int selectedItemPosition) {

        switch (selectedItemPosition) {
            case 0:
                return 1;
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return 15;
            case 4:
                return 20;
            default:
                return 0;
        }
    }

    public static int volumeDoPedido(int selectedItemPosition) {

        switch (selectedItemPosition) {
            case 1:
                return 1;
            case 3:
                return 2;

            case 5:
                return 3;

            default:
                return 0;

        }
    }

    public static int quantidadeDeItensSelecionado(int selectedItemPosition) {

        switch (selectedItemPosition) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 8;
            case 8:
                return 9;
            case 9:
                return 10;
            default:
                return 0;

        }
    }

    public static String calculaDistancia(double latitudeColeta, double longitideColeta, double latitudeEntrega, double longitideEntrega) {
        //double earthRadius = 3958.75;//miles
        double earthRadius = 6371;//kilometers
        double dLat = Math.toRadians(latitudeEntrega - latitudeColeta);
        double dLng = Math.toRadians(longitideEntrega - longitideColeta);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(latitudeColeta))
                * Math.cos(Math.toRadians(latitudeEntrega));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return formatarGeopoint(dist); //em Km
    }

    public static String formatarGeopoint(double valor){
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        return decimalFormat.format(valor);
    }

    public static String formatarValorEmReais(double valor){
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        return decimalFormat.format(valor);
    }
}
