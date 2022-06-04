package com.example.levv.model.DAO.endereco;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.bo.endereco.Estado;
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

public class EstadoDAO implements ICrudDAO<Estado> {



    public final static String NAME_COLLECTION_DATABASE = "estados";


    public EstadoDAO() {
        
    }

    @Override
    public void create(Estado objeto) {

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.ESTADODAO, "EstadoDAO incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.ESTADODAO, "EstadoDAO não incluido");
                    }
                });

    }

    @Override
    public void update(Estado objeto) {


        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getNome())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.ESTADODAO, "Update c/ sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.ESTADODAO, "Falha ao atualizar!", e);
                    }
                });
    }

    @Override
    public void delete(Estado objeto) {

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getNome())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.ESTADODAO, "deletado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.ESTADODAO, "Erro ao deletar", e);
                    }
                });
    }

    @Override
    public List<Estado> list() {

        List<Estado> estados = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Tag.ESTADODAO, "Listagem com sucesso!");
                                estados.add(document.toObject(Estado.class));
                            }
                        } else {
                            Log.w(Tag.ESTADODAO, "Não foi possível listar!", task.getException());
                        }
                    }
                });

        return estados;
    }


}
