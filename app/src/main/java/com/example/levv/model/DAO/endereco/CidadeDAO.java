package com.example.levv.model.DAO.endereco;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.bo.endereco.Cidade;
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

public class CidadeDAO implements ICrudDAO<Cidade> {


    public final static String NAME_COLLECTION_DATABASE = "cidades";

    public CidadeDAO() {
    }

    @Override
    public void create(Cidade objeto) {

        objeto.setReferencia(FirebaseFirestore.getInstance().document(EstadoDAO.NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail()));

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.CIDADEDAO, " incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.CIDADEDAO, " não incluido");
                    }
                });

    }

    @Override
    public void update(Cidade objeto) {

        objeto.setReferencia(FirebaseFirestore.getInstance().document(EstadoDAO.NAME_COLLECTION_DATABASE + "/" + objeto.getEstado().getNome()));

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getNome())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.CIDADEDAO, "Update c/ sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.CIDADEDAO, "Falha ao atualizar!", e);
                    }
                });
    }

    @Override
    public void delete(Cidade objeto) {

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getNome())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.CIDADEDAO, "Deletado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.CIDADEDAO, "Erro ao deletar", e);
                    }
                });

    }

    @Override
    public List<Cidade> list() {

        List<Cidade> cidades = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                cidades.add(document.toObject(Cidade.class));

                                Log.d(Tag.PEDIDODAO, "Listagem com sucesso!");
                            }
                        } else {
                            Log.w(Tag.CIDADEDAO, "Erro ao listar!", task.getException());
                        }
                    }
                });

        return cidades;
    }


}
