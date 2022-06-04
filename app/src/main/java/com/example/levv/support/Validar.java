package com.example.levv.support;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.levv.model.bo.usuario.Arquivo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validar {

    public static boolean celular(String numero) {
        if (numero.length() >= 15) {
            return true;
        } else
            return false;
    }

    public static boolean nome(String nome) {
        if (nome.length() >= 3)
            return true;
        else
            return false;
    }

    public static boolean logradouro(String logradouro) {
        if (logradouro.toString().length() >= 3)
            return true;
        else
            return false;
    }

    public static boolean numeroLogradouro(String numero) {
        if (numero.toString().length() >= 3)
            return true;
        else
            return false;
    }

    public static boolean bairro(String bairro) {
        if (bairro.toString().length() >= 3)
            return true;
        else
            return false;
    }

    public static boolean cidade(String cidade) {
        if (cidade.toString().length() >= 3)
            return true;
        else
            return false;
    }

    public static boolean estado(String estado) {
        if (estado.toString().length() >= 3)
            return true;
        else
            return false;
    }

    public static boolean cep(String cep) {
        if (cep.length() == 9)
            return true;
        else
            return false;
    }

    public static boolean placa(String placa) {

        placa = replacesAll(placa);

        if (placa.length() == 8 || placa.length() == 9)
            return true;
        else
            return false;
    }

    public static boolean renavam(String renavam){

        renavam = replacesAll(renavam);

        if (renavam.length() == 11)
            return true;
        else
            return false;
    }

    public static boolean documentoIdentificacao(String documento){

        documento = replacesAll(documento);

        if (documento.length() >= 9 && documento.length() <= 13)
            return true;
        else
            return false;
    }

    public static String replacesAll(String string){

        string.replace(".","");
        string.replace(",","");
        string.replace("-","");
        string.replace(" ","");

        return string;
    }

    public static String celularBuscarNumero(String celular) {

        int i = 0;

        String numero = "";

        celular = celular.replace(" ", "");
        celular = celular.replace("-","");
        celular = celular.replace("(","");
        celular = celular.replace(")","");

        for(char c: celular.toCharArray()){
            if(i > 1){
                numero = numero +  c;
            }
            i++;
        }
        return numero;
    }

    public static String celularBuscarDdd(String celular) {

        int i = 0;

        String numero = "";

        celular = celular.replace(" ", "");
        celular = celular.replace("-","");
        celular = celular.replace("(","");
        celular = celular.replace(")","");

        for(char c: celular.toCharArray()){
            if(i < 2) {
                numero = numero + c;
            }
            i++;
        }
        return numero;
    }

    public static boolean senhas(String senha1, String senha2) {
        return !senha1.equals("") && senha1.equals(senha2);
    }

    public static boolean senha(String senha) {
        return !senha.equals("") && (senha.length() >= 6);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate converterStringEmData(String dataRecebida) {

        return LocalDate.parse(dataRecebida, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isDateValid(String strDate) {
        String dateFormat = "dd/MM/uuuu";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate date = LocalDate.parse(strDate, dateTimeFormatter);
            if (date.isAfter(LocalDate.now())) {
                return false;
            } else if (date.isBefore(LocalDate.now().minusYears(120))) {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }



    public static boolean validarEmail(String email) {

        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validarCPF(String CPF) {

        CPF = CPF.replace(".", "");
        CPF = CPF.replace("-", "");

        if (CPF.equals("00000000000") || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || CPF.length() != 11 || CPF.length() == 14) {

            return (false);
        } else {

            char dig10, dig11;
            int sm, i, r, num, peso;

            // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
            try {
                // Calculo do 1o. Digito Verificador
                sm = 0;
                peso = 10;
                for (i = 0; i < 9; i++) {
                    // converte o i-esimo caractere do CPF em um numero:
                    // por exemplo, transforma o caractere '0' no inteiro 0
                    // (48 eh a posicao de '0' na tabela ASCII)
                    num = (int) (CPF.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11)) {
                    dig10 = '0';
                } else {
                    dig10 = (char) (r + 48); // converte no respectivo caractere numerico
                }
                // Calculo do 2o. Digito Verificador
                sm = 0;
                peso = 11;
                for (i = 0; i < 10; i++) {
                    num = (int) (CPF.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11)) {
                    dig11 = '0';
                } else {
                    dig11 = (char) (r + 48);
                }

                // Verifica se os digitos calculados conferem com os digitos informados.
                if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {

                    return true;
                } else {

                    return (false);
                }
            } catch (InputMismatchException erro) {

                return (false);
            }

        }
    }

    /**
     * Realiza a validação de um cnpj
     *
     * @param cnpj String - o CNPJ pode ser passado no formato 99.999.999/9999-99 ou 99999999999999
     * @return boolean
     */
    public static boolean isCnpj(String cnpj) {
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("-", "");
        cnpj = cnpj.replace("/", "");

        try{
            Long.parseLong(cnpj);
        } catch(NumberFormatException e){
            return false;
        }

        // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111")
                || cnpj.equals("22222222222222") || cnpj.equals("33333333333333")
                || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
                || cnpj.equals("66666666666666") || cnpj.equals("77777777777777")
                || cnpj.equals("88888888888888") || cnpj.equals("99999999999999") || (cnpj.length() != 14))
            return (false);
        char dig13, dig14;
        int sm, i, r, num, peso; // "try" - protege o código para eventuais
        // erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                // converte o i-ésimo caractere do CNPJ em um número: // por
                // exemplo, transforma o caractere '0' no inteiro 0 // (48 eh a
                // posição de '0' na tabela ASCII)
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else
                dig13 = (char) ((11 - r) + 48);

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }
            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else
                dig14 = (char) ((11 - r) + 48);
            // Verifica se os dígitos calculados conferem com os dígitos
            // informados.
            if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
                return (true);
            else
                return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }


    public static boolean validarMarca(String marca) {
        if (marca.length() >= 3)
            return true;
        else
            return false;
    }

    public static boolean validarModelo(String modelo) {
        if (modelo.length() >= 3)
            return true;
        else
            return false;
    }

    public static boolean validarRenavam(String renavam) {
        if (renavam.length() >= 9 && renavam.length() <= 11)
            return true;
        else
            return false;
    }

    public static boolean validarPlaca(String placa) {
        if (placa.length() >= 7 && placa.length() <= 8)
            return true;
        else
            return false;
    }

    public static boolean validarValorDecimal(String valorDecimal) {

        if(valorDecimal.equals(""))
            return false;

       float valor = AppUtil.valorDecimal(valorDecimal);

       if(valor > 0.00f)
           return true;

        return false;
    }
}
