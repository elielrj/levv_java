package com.example.levv.model.DAO.meioDeTransporte;


import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.bo.meioDeTransporte.MeioDeTransporte;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MeioDeTransporteDAO implements ICrudDAO<MeioDeTransporte> {

    private CollectionReference meioDeTransporte;
    // private FirebaseAuth mAuth;


    private final static String NAME_COLLECTION_DATABASE = "/meioDeTransporte";

    public MeioDeTransporteDAO() {

        meioDeTransporte = ConfiguracaoFirebase.getFirebaseFirestore().collection(NAME_COLLECTION_DATABASE);
        // mAuth = ConfiguracaoFirebase.getFirebaseAuth();

    }

    @Override
    public void create(MeioDeTransporte objeto) {

        meioDeTransporte.document(objeto.getDescricao()).set(objeto);
        /*

        db.collection(NAME_COLLECTION_DATABASE)
                .document(obj.getDescricao())
                .set(obj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.MEIODETRANSPORTEDAO, " incluido com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Tag.MEIODETRANSPORTEDAO, " não incluido");
                    }
                });*/

    }

    @Override
    public void update(MeioDeTransporte objeto) {

        meioDeTransporte.document(objeto.getDescricao()).set(objeto);

        /*
        db.collection(NAME_COLLECTION_DATABASE)
                .document(obj.getDescricao())
                .set(obj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.MEIODETRANSPORTEDAO, "Update c/ sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.MEIODETRANSPORTEDAO, "Falha ao atualizar!", e);
                    }
                });*/

    }

    @Override
    public void delete(MeioDeTransporte objeto) {
        /*

        db.collection(NAME_COLLECTION_DATABASE)
                .document(obj.getDescricao())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Tag.MEIODETRANSPORTEDAO, "deletado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Tag.MEIODETRANSPORTEDAO, "Erro ao deletar", e);
                    }
                });*/

        meioDeTransporte.document(objeto.getDescricao()).delete();

    }

    @Override
    public List<MeioDeTransporte> list() {

        List<MeioDeTransporte> meios = new ArrayList<>();

       FirebaseFirestore.getInstance().collection("/meioDeTransporte")
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
                   @Override
                   public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                       if(error != null){

                           Log.e("Teste", error.getMessage(), error);
                           return;
                       }

                       List<DocumentSnapshot> docs = value.getDocuments();

                       for(DocumentSnapshot doc : docs){
                           MeioDeTransporte meio = doc.toObject(MeioDeTransporte.class);
                           meios.add(meio);
                           Log.d("Teste", doc.getId());
                       }
                   }
               });

        return meios;
    }

    public List<MeioDeTransporte> listar(CollectionReference collectionReference) {

        List<MeioDeTransporte> meioDeTransportes = new ArrayList<>();


        collectionReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            MeioDeTransporte m = documentSnapshot.toObject(MeioDeTransporte.class);

                            meioDeTransportes.add(m);


                        }
                    }
                });

        return meioDeTransportes;
    }

    }
