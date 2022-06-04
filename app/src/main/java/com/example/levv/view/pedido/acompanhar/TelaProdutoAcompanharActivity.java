package com.example.levv.view.pedido.acompanhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.levv.R;
import com.example.levv.controller.tela.TelaProdutoAcompanharController;

import com.example.levv.view.funcional.TelaMainActivity;
import com.example.levv.view.pedido.BotaoTipoDePedidoFragment;
import com.example.levv.view.pedido.ListarPedidosFragment;
import com.example.levv.view.pedido.entregar.TelaProdutoEntregarActivity;

public class TelaProdutoAcompanharActivity extends AppCompatActivity
        implements BotaoTipoDePedidoFragment.OnFragmentInterctionListenerOptionButton,
        ListarPedidosFragment.OnFragmentInterctionListenerOptionListaDePedidos{

    private static final String STATE_FRAGMENT_BOTOES = "state_of_fragment_botoes";
    private static final String STATE_CHOICE_BOTOES = "user_choice_botoes";


    private TelaProdutoAcompanharController controller;




    /**
     * A - fragment botão
     */

    //valor p/ botão ativos
    private int PENDENTES = 2;
    private int NONE = 3;

    //botão padrão
    private int mButtonDefault = NONE;

    //botão escolhido
    private int mChosenButton = PENDENTES;

    private boolean isFragmentDisplayedBotoes = false;

    //FRAGMENT B
    private int mWishListDefault = NONE;
    private boolean isFragmentDisplayedListaDePedidos = false;

    private int mChosenWishList = PENDENTES;
    private static final String STATE_FRAGMENT_LISTAR_PEDIDOS = "statr_of_fragment_listar_pedidos";
    private static final String STATE_CHOICE_LISTAR_PEDIDOS = "user_choice_listar_pedidos";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_produto_acompanhar);

        this.controller = new TelaProdutoAcompanharController(this);

        displayFragmentBotaoTipoDePedido();




        if(savedInstanceState != null){
            isFragmentDisplayedBotoes = savedInstanceState.getBoolean(STATE_FRAGMENT_BOTOES);
            mChosenButton = savedInstanceState.getInt(STATE_CHOICE_BOTOES);

            isFragmentDisplayedListaDePedidos = savedInstanceState.getBoolean(STATE_FRAGMENT_LISTAR_PEDIDOS);
            mChosenWishList = savedInstanceState.getInt(STATE_CHOICE_LISTAR_PEDIDOS);

            if(isFragmentDisplayedBotoes){
                //cores dos botões
                Toast.makeText(this, "Teste mudar cor botao!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {

            startActivity(new Intent(TelaProdutoAcompanharActivity.this, TelaMainActivity.class));

    }

    @Override
    public void onButtonOption(int option) {
        mChosenButton = option;
        mChosenWishList = option;
    }

    @Override
    public void onToUpdateWishList(int chosen) {
        displayFragmentListarPedidos(chosen);
    }

    private void displayFragmentBotaoTipoDePedido(){

        //BUSCA A INSTÂNCIA DO FRAGMENT BOTÕES, SE HOUVER
        BotaoTipoDePedidoFragment fragment = (BotaoTipoDePedidoFragment)getSupportFragmentManager().findFragmentById(R.id.flBotoesTelaAcompanhar);

        //SE FRAGMENT NÃO EXISTIR CRIA
        if(fragment == null){
            fragment = BotaoTipoDePedidoFragment.newInstance(mButtonDefault);
            getSupportFragmentManager().beginTransaction().add(R.id.flBotoesTelaAcompanhar, fragment).addToBackStack(null).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.flBotoesTelaAcompanhar,fragment).commit();
        }

        isFragmentDisplayedBotoes = true;
    }

    private void displayFragmentListarPedidos(int chosen){

        //BSCAR INSTÂNCIA DO FRAGMENT, SE HOUVER
        ListarPedidosFragment fragment = (ListarPedidosFragment) getSupportFragmentManager().findFragmentById(R.id.flListaDePedidosTelaAcompanhar);

        //SE FRAGMENT NÃO EXISTIR CRIA
        if(fragment == null){
            fragment = ListarPedidosFragment.newInstance(chosen);
            getSupportFragmentManager().beginTransaction().add(R.id.flListaDePedidosTelaAcompanhar, fragment).addToBackStack(null).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.flListaDePedidosTelaAcompanhar,fragment).commit();
            fragment.listarPedidosDoFragmente(chosen);
        }

        isFragmentDisplayedListaDePedidos = true;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the fragment (true=open, false=closed).
        //
        //FRAGMENT A - BOTÕES
        savedInstanceState.putBoolean(STATE_FRAGMENT_BOTOES, isFragmentDisplayedBotoes);
        savedInstanceState.putInt(STATE_CHOICE_BOTOES, mChosenButton);

        //FRAGMENT B - LISTAR PEDIDOS
        savedInstanceState.putBoolean(STATE_FRAGMENT_LISTAR_PEDIDOS, isFragmentDisplayedListaDePedidos);
        savedInstanceState.putInt(STATE_CHOICE_LISTAR_PEDIDOS, mChosenWishList);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onOptionListaDePedidos(int optionListaDePedidos) {

        mChosenWishList = optionListaDePedidos;
        displayFragmentListarPedidos(optionListaDePedidos);

    }
}