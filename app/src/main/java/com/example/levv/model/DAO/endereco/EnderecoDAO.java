package com.example.levv.model.DAO.endereco;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.bo.endereco.Endereco;
import com.example.levv.model.bo.pedido.Pedido;
import com.example.levv.support.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO implements ICrudDAO<Endereco> {


    public final static String NAME_COLLECTION_DATABASE = "enderecos";

    public EnderecoDAO() {
    }

    @Override
    public void create(Endereco objeto) {

        objeto.setReferencia(FirebaseFirestore.getInstance().document(BairroDAO.NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail()));

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.ENDERECODAO, "Incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.ENDERECODAO, "Erro ao incluir");
                    }
                });


    }

    @Override
    public void update(Endereco objeto) {

        objeto.setReferencia(FirebaseFirestore.getInstance().document(BairroDAO.NAME_COLLECTION_DATABASE + "/" + objeto.getBairro().getNome()));

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.ENDERECODAO, "Update c/ sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.ENDERECODAO, "Falha ao atualizar!", e);
                    }
                });

    }

    @Override
    public void delete(Endereco objeto) {



        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.ENDERECODAO, "deletado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.ENDERECODAO, "Erro ao deletar", e);
                    }
                });
    }

    @Override
    public List<Endereco> list() {

        List<Endereco> enderecos = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                enderecos.add(document.toObject(Endereco.class));

                                Log.d(Tag.ENDERECODAO, "Listagem com sucesso!" + document.getData());

                            }
                        } else {
                            Log.w(Tag.ENDERECODAO, "ERRO AO LISTAR!", task.getException());
                        }
                    }
                });

        return enderecos;
    }


}
