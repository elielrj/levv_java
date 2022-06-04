package com.example.levv.model.DAO.usuario;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.bo.pedido.Pedido;
import com.example.levv.model.bo.usuario.Login;
import com.example.levv.support.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoginDAO implements ICrudDAO<Login> {

    public final static String NAME_COLLECTION_DATABASE = "login";

    public LoginDAO() {

    }

    @Override
    public void create(Login objeto) {


        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.LOGINDAO, " incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.LOGINDAO, " não incluido");
                    }
                });

    }

    @Override
    public void update(Login objeto) {

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.LOGINDAO, "Update c/ sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.LOGINDAO, "Falha ao atualizar!", e);
                    }
                });

    }

    @Override
    public void delete(Login objeto) {

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.LOGINDAO, "deletado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.LOGINDAO, "Erro ao deletar", e);
                    }
                });

    }

    @Override
    public List<Login> list() {

        List<Login> logins = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Tag.LOGINDAO, "Listagem com sucesso!");
                                logins.add(document.toObject(Login.class));
                            }
                        } else {
                            Log.w(Tag.LOGINDAO, "Não foi possível listar!", task.getException());
                        }
                    }
                });

        return logins;
    }



}
