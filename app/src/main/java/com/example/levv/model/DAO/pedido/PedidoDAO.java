package com.example.levv.model.DAO.pedido;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.bo.pedido.Pedido;
import com.example.levv.support.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements ICrudDAO<Pedido> {


    private final static String NAME_COLLECTION_DATABASE = "pedidos";



    public PedidoDAO() {

    }

    @Override
    public void create(Pedido objeto) {

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getNumero())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.PEDIDODAO, "Pedidos incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.PEDIDODAO, "Pedido não incluido!");
                    }
                });

    }

    @Override
    public void update(Pedido objeto) {

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getNumero())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.PEDIDODAO, "Pedidos alterado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.PEDIDODAO, "Pedido não alterado!");
                    }
                });
    }

    @Override
    public void delete(Pedido objeto) {

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getNumero())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.PEDIDODAO, "Pedidos deletado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.PEDIDODAO, "Pedido não deletado!");
                    }
                });

    }

    @Override
    public List<Pedido> list() {


        List<Pedido> pedidos = new ArrayList<>();


        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Tag.PEDIDODAO, "Listagem com sucesso!");
                                pedidos.add(document.toObject(Pedido.class));
                            }
                        } else {
                            Log.w(Tag.PEDIDODAO, "Não foi possível listar!", task.getException());
                        }
                    }
                });


        return pedidos;
    }



    public void aceitarPedido(Pedido pedidoSelecionado) {


        FirebaseFirestore.getInstance().document(NAME_COLLECTION_DATABASE + "/" + pedidoSelecionado.getNumero())
                .update("referenciaTransportador", pedidoSelecionado.getReferenciaTransportador(),
                        "disponivel", pedidoSelecionado.isDisponivel())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.PEDIDODAO, "Atualizado pedido sucesso!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.PEDIDODAO, "Atualizacao falou");
                    }
                });


    }


    public void pedidoEntregue(Pedido pedidoSelecionado) {

        FirebaseFirestore.getInstance().document(NAME_COLLECTION_DATABASE + "/" + pedidoSelecionado.getNumero())
                .update("entregue", pedidoSelecionado.isEntregue())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.PEDIDODAO, "Atualizado pedido sucesso!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.PEDIDODAO, "Atualizacao falou");
                    }
                });
    }
}
