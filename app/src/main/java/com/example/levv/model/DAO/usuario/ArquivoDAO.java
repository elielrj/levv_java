package com.example.levv.model.DAO.usuario;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ICrudDAO;
import com.example.levv.model.bo.usuario.Arquivo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ArquivoDAO implements ICrudDAO<Arquivo> {


    public ArquivoDAO() {

    }

    @Override
    public void create(Arquivo objeto) {

        StorageReference mountainsRef =FirebaseStorage
                .getInstance()
                .getReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .child(objeto.getDescricao());

        objeto.getImageView().setDrawingCacheEnabled(true);
        objeto.getImageView().buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) objeto.getImageView().getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("AQUIVODAO","Upload de imagem " + objeto.getDescricao() + " falhou!");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i("AQUIVODAO","Upload de imagem " + objeto.getDescricao() + "com sucesso!");
            }
        });


    }

    @Override
    public void update(Arquivo objeto) {

    }

    @Override
    public void delete(Arquivo objeto) {

    }

    @Override
    public List<Arquivo> list() {
        return null;
    }


}
