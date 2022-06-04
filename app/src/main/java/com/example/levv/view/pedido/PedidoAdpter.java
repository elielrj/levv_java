package com.example.levv.view.pedido;

import static com.example.levv.support.AppUtil.TIPO_DE_USUARIO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.levv.R;
import com.example.levv.controller.pedido.PedidoController;
import com.example.levv.model.DAO.usuario.TransportadorDAO;
import com.example.levv.model.bo.pedido.Pedido;
import com.example.levv.model.bo.usuario.Transportador;
import com.example.levv.support.AppUtil;
import com.example.levv.support.Tag;
import com.example.levv.view.funcional.TelaMainActivity;
import com.example.levv.view.localizar.TelaLocalizacaoActivity;
import com.example.levv.view.pedido.acompanhar.TelaProdutoAcompanharActivity;

import com.example.levv.view.pedido.entregar.TelaProdutoEntregarActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PedidoAdpter extends RecyclerView.Adapter<PedidoAdpter.ViewHolder> {

    private static final String ERRO_TIPO_USER = "erro";
    private List<Pedido> aPedidos;
    private List<Pedido> pedidosComFiltro;
    private List<Pedido> todosPedidos;
    private Context aContext;

    private static final int ATIVOS = 0;
    private static final int FINALIZADOS = 1;
    private static final int PENDENTES = 2;
    private static final int NONE = 3;
    private int chosenButton;

    private static final String TRANSPORTADOR = "Transportador";
    private static final String TELA_ORIGEM = "tela_origem";
    private static final String TELA_ACOMPANHAR_PEDIDO = "acompanhar";
    private static final String TELA_ENTREGAR_PEDIDO = "entregar";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor dados;


    public PedidoAdpter(List<Pedido> todosPedidos, Context aContext, int chosenButton) {
        this.todosPedidos = todosPedidos;
        this.aContext = aContext;
        this.chosenButton = chosenButton;

        //0
        sharedPreferences = aContext.getSharedPreferences(AppUtil.SHARED_PREFERENCES_NAME_APP, aContext.MODE_PRIVATE);
        dados = sharedPreferences.edit();
        //1
        filtrarPedidosDoUsuarioCorrenteQuandoForEntregador();
        //2
        filtrarAcompanhamentoDePedidosOriginadosApenasDoUserCorrente();
        //3
        pedidos(chosenButton);


    }

    public void pedidos(int chosen) {
        switch (chosen) {
            case ATIVOS:
                aPedidos = listarPedidosAtivos();
                break;
            case PENDENTES:
                aPedidos = listarPedidosPendentes();
                break;
            case FINALIZADOS:
                aPedidos = listarPedidosFinalizados();
                break;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View linhaView = inflater.inflate(R.layout.linha_detalhe_pedido, parent, false);

        ViewHolder viewHolder = new ViewHolder(linhaView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdpter.ViewHolder holder, int position) {

        Pedido objetoDaLinha = aPedidos.get(position);

        TextView txtNumeroDoPedido = holder.rvNumeroDoPedido;
        txtNumeroDoPedido.setText(objetoDaLinha.getNumero());

        TextView txtValorDoPedido = holder.rvValorDoPedido;
        txtValorDoPedido.setText(String.valueOf("R$ " + objetoDaLinha.getValor()));

        TextView txtOrigemDoPedido = holder.rvOrigemDoPedido;
        txtOrigemDoPedido.setText(objetoDaLinha.getColeta().toString());

        TextView txtDestinatarioDoPedido = holder.rvDestinoDoPedido;
        txtDestinatarioDoPedido.setText(objetoDaLinha.getEntrega().toString());

        String distancia = AppUtil.calculaDistancia(
                objetoDaLinha.getLatLngColeta().getLatitude(),
                objetoDaLinha.getLatLngColeta().getLongitude(),
                objetoDaLinha.getLatLngEntrega().getLatitude(),
                objetoDaLinha.getLatLngEntrega().getLongitude()
        );

        distancia.replace(".", ",");

        TextView txtDistanciaDoPedido = holder.rvDistanciaDoPedido;
        txtDistanciaDoPedido.setText(distancia + " Km");

        TextView txtAceitarPedidoOuAcompanharPedido = holder.rvAceitarPedidoOuAcompanharPedido;

        if (aContext instanceof TelaProdutoEntregarActivity) {

            if (chosenButton == ATIVOS) {

                txtAceitarPedidoOuAcompanharPedido
                        .setText(R.string.pedido_adapter_continuar);

            } else if (chosenButton == FINALIZADOS) {

                txtAceitarPedidoOuAcompanharPedido
                        .setText(R.string.pedido_adapter_visualizar);

            } else if (chosenButton == PENDENTES) {

                txtAceitarPedidoOuAcompanharPedido
                        .setText(R.string.pedido_adapter_aceitar);

            }

        } else if (aContext instanceof TelaProdutoAcompanharActivity) {

            if (chosenButton == ATIVOS) {

                txtAceitarPedidoOuAcompanharPedido
                        .setText(R.string.acompanhar);

            } else if (chosenButton == FINALIZADOS) {

                txtAceitarPedidoOuAcompanharPedido
                        .setText(R.string.pedido_adapter_visualizar);

            } else if (chosenButton == PENDENTES) {

                txtAceitarPedidoOuAcompanharPedido
                        .setText(R.string.pedido_adapter_visualizar);

            }
        }
    }

    @Override
    public int getItemCount() {
        return aPedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView rvNumeroDoPedido;
        public TextView rvValorDoPedido;
        public TextView rvOrigemDoPedido;
        public TextView rvDestinoDoPedido;
        public TextView rvDistanciaDoPedido;
        public TextView rvAceitarPedidoOuAcompanharPedido;
        public TextView txtPedidoEntregue;
        private boolean fragmentoAberto = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rvNumeroDoPedido = itemView.findViewById(R.id.txtNumeroDoPedido);
            rvValorDoPedido = itemView.findViewById(R.id.txtValorDoPedido);
            rvOrigemDoPedido = itemView.findViewById(R.id.txtOrigemDoPedido);
            rvDestinoDoPedido = itemView.findViewById(R.id.txtDestinoDoPedido);
            rvDistanciaDoPedido = itemView.findViewById(R.id.txtDistanciaDoPedido);
            rvAceitarPedidoOuAcompanharPedido = itemView.findViewById(R.id.txtAceitarPedidoOuAcompanharPedido);

            txtPedidoEntregue = itemView.findViewById(R.id.txtPedidoEntregue);
            if (chosenButton == ATIVOS && (aContext instanceof TelaProdutoEntregarActivity)) {
                txtPedidoEntregue.setText("Entregue");
                txtPedidoEntregue.setPaddingRelative(0,16,0,16);
                txtPedidoEntregue.setPadding(16, 16, 16, 32);
                txtPedidoEntregue.setOnClickListener(this);

                rvAceitarPedidoOuAcompanharPedido.setPadding(16, 16, 16, 16);
            } else {
                txtPedidoEntregue.setPadding(0, 0, 0, 0);
            }

            //itemView.setOnClickListener(this);

            rvAceitarPedidoOuAcompanharPedido.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            Bundle bundle = new Bundle();

            int position = getAdapterPosition();

            Pedido pedidoSelecionado = aPedidos.get(position);

            if (position != RecyclerView.NO_POSITION) {


                //TELA  "ENTREGAR"
                if (aContext instanceof TelaProdutoAcompanharActivity) {

                    try {
                        Intent intent = new Intent(aContext, TelaLocalizacaoActivity.class);

                        //NR DO PEDIDO
                        bundle.putString(AppUtil.NUMERO_DO_PEDIDO, pedidoSelecionado.getNumero());
                        bundle.putDouble(AppUtil.VALOR_DO_PEDIDO, pedidoSelecionado.getValor());
                        bundle.putString(AppUtil.ENDERECO_COLETA_DO_PEDIDO, pedidoSelecionado.getColeta());
                        bundle.putString(AppUtil.ENDERECO_ENTREGA_PEDIDO, pedidoSelecionado.getEntrega());

                        //COORDENADAS DA COLETA
                        bundle.putDouble(AppUtil.LATITUDE_COLETA, pedidoSelecionado.getLatLngColeta().getLatitude());
                        bundle.putDouble(AppUtil.LONGITUDE_COLETA, pedidoSelecionado.getLatLngColeta().getLongitude());

                        //COORDENADAS DA ENTREGA
                        bundle.putDouble(AppUtil.LATITUDE_ENTREGA, pedidoSelecionado.getLatLngEntrega().getLatitude());
                        bundle.putDouble(AppUtil.LONGITUDE_ENTREGA, pedidoSelecionado.getLatLngEntrega().getLongitude());

                        bundle.putString(TELA_ORIGEM, TELA_ACOMPANHAR_PEDIDO);

                        intent.putExtras(bundle);

                        aContext.startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(aContext, Tag.ERRO_AO_ACOMPANHAR_PEDIDO, Toast.LENGTH_SHORT).show();
                        Log.i(Tag.TELA_PEDIDO_ADAPTER, Tag.ERRO_AO_ACOMPANHAR_PEDIDO, e);
                    }

                    //TELA  "ACOMPANAR"
                } else if (aContext instanceof TelaProdutoEntregarActivity) {


                    if (chosenButton == ATIVOS && (v.getId() == txtPedidoEntregue.getId())) {


                        PedidoController pedidoController = new PedidoController();



                        AlertDialog.Builder msgBox = new AlertDialog.Builder(aContext);
                        msgBox.setTitle("Pedido entregue");
                        msgBox.setIcon(R.drawable.icon_drawable_pin);
                        msgBox.setMessage("Deseja confirmar que o pedido foi entregue?");

                        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pedidoSelecionado.setEntregue(true);
                                pedidoController.pedidoEntregue(pedidoSelecionado);
                                aContext.startActivity(new Intent(aContext, TelaMainActivity.class));

                            }
                        });

                        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        msgBox.show();






                    } else {


                        try {


                            boolean entregadoPossuiUmPedidoAtivo = false;
                            AlertDialog.Builder msgBox = new AlertDialog.Builder(aContext);
                            PedidoController pedidoController = new PedidoController();

                            if (chosenButton == PENDENTES) {


                                if (listarPedidosAtivos().size() > 0) {
                                    entregadoPossuiUmPedidoAtivo = true;
                                }

                                if (!entregadoPossuiUmPedidoAtivo) {



                                    String tipoDeUsuario = sharedPreferences.getString(TIPO_DE_USUARIO, AppUtil.ERRO_AO_BUSCAR_TIPO_DE_USUARIO);

                                    DocumentReference referenciaDoransportador = FirebaseFirestore.getInstance()
                                            .document("/usuarios/pessoas/" + tipoDeUsuario.toLowerCase(Locale.ROOT) + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                    pedidoSelecionado.setReferenciaTransportador(referenciaDoransportador);



                                    msgBox.setTitle("Aceitar pedido");
                                    msgBox.setIcon(R.drawable.icon_drawable_pin);
                                    msgBox.setMessage("Deseja aceitar este pedido?");






                                } else {
                                    Toast.makeText(aContext, Tag.ENTREGADO_POSSUI_UM_OUTRO_PEDIDO_ATIVO, Toast.LENGTH_SHORT).show();
                                }

                            }

                            if (!(chosenButton == PENDENTES) || !entregadoPossuiUmPedidoAtivo) {

                                Intent intent = new Intent(aContext, TelaLocalizacaoActivity.class);

                                //NR DO PEDIDO
                                bundle.putString(AppUtil.NUMERO_DO_PEDIDO, pedidoSelecionado.getNumero());
                                bundle.putDouble(AppUtil.VALOR_DO_PEDIDO, pedidoSelecionado.getValor());
                                bundle.putString(AppUtil.ENDERECO_COLETA_DO_PEDIDO, pedidoSelecionado.getColeta());
                                bundle.putString(AppUtil.ENDERECO_ENTREGA_PEDIDO, pedidoSelecionado.getEntrega());

                                //COORDENADAS DA COLETA
                                bundle.putDouble(AppUtil.LATITUDE_COLETA, pedidoSelecionado.getLatLngColeta().getLatitude());
                                bundle.putDouble(AppUtil.LONGITUDE_COLETA, pedidoSelecionado.getLatLngColeta().getLongitude());

                                //COORDENADAS DA ENTREGA
                                bundle.putDouble(AppUtil.LATITUDE_ENTREGA, pedidoSelecionado.getLatLngEntrega().getLatitude());
                                bundle.putDouble(AppUtil.LONGITUDE_ENTREGA, pedidoSelecionado.getLatLngEntrega().getLongitude());

                                bundle.putString(TELA_ORIGEM, TELA_ENTREGAR_PEDIDO);

                                intent.putExtras(bundle);

                                if(chosenButton == PENDENTES && !entregadoPossuiUmPedidoAtivo){

                                    msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            pedidoSelecionado.setDisponivel(false);
                                            pedidoController.aceitarPedido(pedidoSelecionado)  ;
                                            aContext.startActivity(intent);
                                        }
                                    });

                                    msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    msgBox.show();

                                } else{
                                    aContext.startActivity(intent);

                                }
                            }

                        } catch (Exception e) {

                            Toast.makeText(aContext, Tag.FALHA_AO_ACOMPANHAR_ENTREGA, Toast.LENGTH_SHORT).show();
                            Log.i(Tag.TELA_PEDIDO_ADAPTER, Tag.ERRO_AO_ACOMPANHAR_PEDIDO, e);

                        }
                    }

                } else {
                    Toast.makeText(aContext, Tag.ERRO_TELA_ENTREGAR_PRODUTO, Toast.LENGTH_LONG).show();
                }


            }


        }
    }

    private List<Pedido> listarPedidosAtivos() {

        List<Pedido> pedidosAtivos = new ArrayList<>();

        for (Pedido p : pedidosComFiltro) {

            if (!p.isDisponivel() && p.isPago() && !p.isEntregue()) {
                pedidosAtivos.add(p);
            }
        }
        return pedidosAtivos;
    }

    private List<Pedido> listarPedidosPendentes() {

        List<Pedido> pedidosPendentes = new ArrayList<>();

        for (Pedido p : pedidosComFiltro) {

            if (p.isDisponivel() && p.isPago() && !p.isEntregue()) {
                pedidosPendentes.add(p);
            }
        }
        return pedidosPendentes;
    }

    private List<Pedido> listarPedidosFinalizados() {

        List<Pedido> pedidosFinalizados = new ArrayList<>();

        for (Pedido p : pedidosComFiltro) {

            if (!p.isDisponivel() && p.isPago() && p.isEntregue()) {
                pedidosFinalizados.add(p);
            }
        }
        return pedidosFinalizados;
    }

    private void filtrarPedidosDoUsuarioCorrenteQuandoForEntregador() {

        String tipoDeUser = sharedPreferences.getString(AppUtil.TIPO_DE_USUARIO, ERRO_TIPO_USER);

        if (tipoDeUser.equalsIgnoreCase(ERRO_TIPO_USER)) {
            tipoDeUser = TRANSPORTADOR;
        }


        if (aContext instanceof TelaProdutoEntregarActivity && tipoDeUser.equalsIgnoreCase(TRANSPORTADOR)) {

            DocumentReference documentReference = FirebaseFirestore.getInstance().document(TransportadorDAO.NAME_COLLECTION_DATABASE + "/" +
                    TransportadorDAO.NAME_DOCUMENT_DATABASE + "/" +
                    TransportadorDAO.NAME_SUBCOLLECTION_DATABASE + "/" +
                    FirebaseAuth.getInstance().getCurrentUser().getEmail());

            pedidosComFiltro = new ArrayList<>();

            // 1 - listar todos os pedidos que o transportador atual não enviou
            for (Pedido p : todosPedidos) {

                if (!p.getReferenciaUsuarioDoPedido().toString().equalsIgnoreCase(documentReference.toString())) {

                    pedidosComFiltro.add(p);

                }
            }

            // 2 - listar só os pedidos selecionados pelo Transportador corrente
            if (chosenButton != PENDENTES) {

                DocumentReference documentReferenceDoTransportador = FirebaseFirestore.getInstance().document(TransportadorDAO.NAME_COLLECTION_DATABASE + "/" +
                        TransportadorDAO.NAME_DOCUMENT_DATABASE + "/" +
                        TransportadorDAO.NAME_SUBCOLLECTION_DATABASE + "/" +
                        FirebaseAuth.getInstance().getCurrentUser().getEmail());

                List<Pedido> pedidosAtivosEFinalizadosDoTransportador = new ArrayList<>();

                for (Pedido p : pedidosComFiltro) {
                    if (p.getReferenciaTransportador() != null) {
                        if (p.getReferenciaTransportador().toString().equalsIgnoreCase(documentReferenceDoTransportador.toString())) {
                            pedidosAtivosEFinalizadosDoTransportador.add(p);
                        }
                    }
                }

                pedidosComFiltro = pedidosAtivosEFinalizadosDoTransportador;

            }

        } else {
            pedidosComFiltro = todosPedidos;
        }
    }

    private void filtrarAcompanhamentoDePedidosOriginadosApenasDoUserCorrente() {

        String tipoDeUser = sharedPreferences.getString(TIPO_DE_USUARIO, ERRO_TIPO_USER);

        tipoDeUser = tipoDeUser.toLowerCase(Locale.ROOT);

        if (aContext instanceof TelaProdutoAcompanharActivity) {

            DocumentReference documentReference = FirebaseFirestore.getInstance().document(TransportadorDAO.NAME_COLLECTION_DATABASE + "/" +
                    TransportadorDAO.NAME_DOCUMENT_DATABASE + "/" +
                    tipoDeUser + "/" +
                    FirebaseAuth.getInstance().getCurrentUser().getEmail());

            List<Pedido> pedidoParaTelaAcompanhar = new ArrayList<>();

            for (Pedido p : pedidosComFiltro) {

                if (p.getReferenciaUsuarioDoPedido().toString().equalsIgnoreCase(documentReference.toString())) {

                    pedidoParaTelaAcompanhar.add(p);

                }
            }

            pedidosComFiltro = pedidoParaTelaAcompanhar;

        }

    }



}
