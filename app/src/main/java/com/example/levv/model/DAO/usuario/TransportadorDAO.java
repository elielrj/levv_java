package com.example.levv.model.DAO.usuario;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.DAO.endereco.EnderecoDAO;
import com.example.levv.model.bo.usuario.Login;
import com.example.levv.model.bo.usuario.Transportador;
import com.example.levv.support.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TransportadorDAO implements ICrudDAO<Transportador> {

    public final static String NAME_COLLECTION_DATABASE = "usuarios";
    public final static String NAME_DOCUMENT_DATABASE = "pessoas";
    public final static String NAME_SUBCOLLECTION_DATABASE = "transportador";

    public TransportadorDAO() {
    }

    @Override
    public void create(Transportador objeto) {


        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(NAME_DOCUMENT_DATABASE)
                .collection(NAME_SUBCOLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.TRANSPORTADORDAO, " incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.TRANSPORTADORDAO, " não incluido");
                    }
                });

        DocumentReference referenciaDoTransportador = FirebaseFirestore.getInstance().document(
                NAME_COLLECTION_DATABASE + "/" +
                NAME_DOCUMENT_DATABASE + "/" +
                NAME_SUBCOLLECTION_DATABASE + "/" +
                FirebaseAuth.getInstance().getCurrentUser().getEmail()
        );

        FirebaseFirestore.getInstance()
                .document(LoginDAO.NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .update("referenciaDaPessoa",referenciaDoTransportador)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.TRANSPORTADORDAO, " incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.TRANSPORTADORDAO, " não incluido");
                    }
                });

    }

    @Override
    public void update(Transportador objeto) {

        objeto.setReferenciaEndereco(FirebaseFirestore.getInstance().document(EnderecoDAO.NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail()));

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(NAME_DOCUMENT_DATABASE)
                .collection(NAME_SUBCOLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.TRANSPORTADORDAO, "Update c/ sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.TRANSPORTADORDAO, "Falha ao atualizar!", e);
                    }
                });
    }

    public void updateGeoPoint(GeoPoint geoPoint) {



        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(NAME_DOCUMENT_DATABASE)
                .collection(NAME_SUBCOLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .update("geoPoint", geoPoint)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.TRANSPORTADORDAO, "Update c/ sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.TRANSPORTADORDAO, "Falha ao atualizar GeoPoint!", e);
                    }
                });
    }

    @Override
    public void delete(Transportador objeto) {


        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(NAME_DOCUMENT_DATABASE)
                .collection(NAME_SUBCOLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.TRANSPORTADORDAO, "deletado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.TRANSPORTADORDAO, "Erro ao deletar", e);
                    }
                });
    }

    @Override
    public List<Transportador> list() {

        List<Transportador> transportadores = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(NAME_DOCUMENT_DATABASE)
                .collection(NAME_SUBCOLLECTION_DATABASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Tag.TRANSPORTADORDAO, "Listagem com sucesso!");
                                transportadores.add(document.toObject(Transportador.class));
                            }
                        } else {
                            Log.w(Tag.TRANSPORTADORDAO, "Não foi possível listar!", task.getException());
                        }
                    }
                });

        return transportadores;

    }
}