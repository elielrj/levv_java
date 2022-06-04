package com.example.levv.model.DAO.meioDeTransporte;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.bo.meioDeTransporte.Veiculo;
import com.example.levv.support.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO implements ICrudDAO<Veiculo> {

    private FirebaseFirestore db;

    private final static String NAME_COLLECTION_DATABASE = "veiculos";


    public VeiculoDAO() {
        db = ConfiguracaoFirebase.getFirebaseFirestore();
    }

    @Override
    public void create(Veiculo objeto) {

        String email = ConfiguracaoFirebase.getCurrentUser().getEmail();



        db.collection(NAME_COLLECTION_DATABASE)
                .document(email)
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.VEICULODAO, " incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.VEICULODAO, " não incluido");
                    }
                });
    }

    @Override
    public void update(Veiculo objeto) {

        String email = ConfiguracaoFirebase.getCurrentUser().getEmail();




        db.collection(NAME_COLLECTION_DATABASE)
                .document(email)
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.VEICULODAO, "Update c/ sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.VEICULODAO, "Falha ao atualizar!", e);
                    }
                });

    }

    @Override
    public void delete(Veiculo objeto) {

        String email = ConfiguracaoFirebase.getCurrentUser().getEmail();

        db.collection(NAME_COLLECTION_DATABASE)
                .document(email)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.VEICULODAO, "deletado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.VEICULODAO, "Erro ao deletar", e);
                    }
                });

    }

    @Override
    public List<Veiculo> list() {


        List<Veiculo> veiculos = new ArrayList<>();

        db.collection(NAME_COLLECTION_DATABASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Veiculo veiculo = new Veiculo();

                                veiculo.setModelo((String) document.getData().get("modelo"));
                                veiculo.setMarca((String) document.getData().get("marca"));
                                veiculo.setPlaca((String) document.getData().get("placa"));
                                veiculo.setRenavam((String) document.getData().get("renavam"));
                                veiculo.setStatus((Boolean) document.getData().get("status"));

                                veiculo.setReferenciaMeioDeTransporte((DocumentReference) document.getData().get("referencia"));

                                veiculos.add(veiculo);

                            }
                        } else {
                            Log.w(Tag.VEICULODAO, "ERRO AO LISTAR!", task.getException());
                        }
                    }
                });

        return veiculos;

    }


}
