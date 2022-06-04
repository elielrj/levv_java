package com.example.levv.model.DAO.endereco;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.bo.endereco.Bairro;
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

public class BairroDAO implements ICrudDAO<Bairro> {

    public final static String NAME_COLLECTION_DATABASE = "bairros";

    public BairroDAO() {
    }

    @Override
    public void create(Bairro objeto) {

        objeto.setReferencia(FirebaseFirestore.getInstance().document(CidadeDAO.NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail()));

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.BAIRRODAO, " Incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.BAIRRODAO, "não incluido");
                    }
                });
    }

    @Override
    public void update(Bairro objeto) {

        objeto.setReferencia(FirebaseFirestore.getInstance().document(CidadeDAO.NAME_COLLECTION_DATABASE + "/" + objeto.getCidade().getNome()));

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getNome())
                .set(objeto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.BAIRRODAO, "Update c/ sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.BAIRRODAO, "Falha ao atualizar!", e);
                    }
                });

    }

    @Override
    public void delete(Bairro objeto) {


        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .document(objeto.getNome())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.BAIRRODAO, "deletado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.BAIRRODAO, "Erro ao deletar", e);
                    }
                });
        
    }

    @Override
    public List<Bairro> list() {

        List<Bairro> bairros = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(NAME_COLLECTION_DATABASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Tag.BAIRRODAO, "Listagem com sucesso!");
                                bairros.add(document.toObject(Bairro.class));
                            }
                        } else {
                            Log.w(Tag.BAIRRODAO, "Não foi possível listar!", task.getException());
                        }
                    }
                });

        return bairros;
        
    }

}
