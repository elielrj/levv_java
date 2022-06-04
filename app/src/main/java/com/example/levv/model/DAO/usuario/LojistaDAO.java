package com.example.levv.model.DAO.usuario;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.DAO.endereco.EnderecoDAO;
import com.example.levv.model.bo.pedido.Pedido;
import com.example.levv.model.bo.usuario.Lojista;
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

public class LojistaDAO implements ICrudDAO<Lojista> {

    public final static String NAME_COLLECTION_DATABASE = "usuarios";
    public final static String NAME_DOCUMENT_DATABASE = "pessoas";
    public final static String NAME_SUBCOLLECTION_DATABASE = "lojista";

    public LojistaDAO() {
    }

    @Override
    public void create(Lojista objeto) {

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(NAME_DOCUMENT_DATABASE)
                .collection(NAME_SUBCOLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.LOJISTADAO, "LojistaDAO incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.LOJISTADAO, "LojistaDAO não incluido");
                    }
                });
        DocumentReference referenciaDoLogin = FirebaseFirestore.getInstance().document(LoginDAO.NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail());
        DocumentReference referenciaDoLojista = FirebaseFirestore.getInstance().document(
                NAME_COLLECTION_DATABASE+ "/" +
                NAME_DOCUMENT_DATABASE+ "/" +
                NAME_SUBCOLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail());

        referenciaDoLogin.update("referenciaDaPessoa", referenciaDoLojista)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.LOJISTADAO, " incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.LOJISTADAO, " não incluido");
                    }
                });

    }

    @Override
    public void update(Lojista objeto) {


        DocumentReference endereco = FirebaseFirestore.getInstance().document(EnderecoDAO.NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail());
        objeto.setReferenciaEndereco(endereco);

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(NAME_DOCUMENT_DATABASE)
                .collection(NAME_SUBCOLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.LOJISTADAO, "Update c/ sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.LOJISTADAO, "Falha ao atualizar!", e);
                    }
                });

    }

    @Override
    public void delete(Lojista objeto) {

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(NAME_DOCUMENT_DATABASE)
                .collection(NAME_SUBCOLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.LOJISTADAO, "deletado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.LOJISTADAO, "Erro ao deletar", e);
                    }
                });

    }

    @Override
    public List<Lojista> list() {

        List<Lojista> lojistas = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(NAME_DOCUMENT_DATABASE)
                .collection(NAME_SUBCOLLECTION_DATABASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Tag.LOJISTADAO, "Listagem com sucesso!");
                                lojistas.add(document.toObject(Lojista.class));
                            }
                        } else {
                            Log.w(Tag.LOJISTADAO, "Não foi possível listar!", task.getException());
                        }
                    }
                });

        return lojistas;
    }


}
