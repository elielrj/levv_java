package com.example.levv.model.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.FirebaseInstallations;

public class ConfiguracaoFirebase {
   // private static DatabaseReference referenciaFirebase;
  //  private static FirebaseDatabase database;
    private static FirebaseAuth autenticacao;
    private FirebaseAuth auth;
    private static FirebaseInstallations instalacao;
    private static FirebaseUser currentUser;
    private static FirebaseFirestore db;
    private  FirebaseFirestore bancoDeDadosFirestore;

    public ConfiguracaoFirebase() {
    }

    /*
        public static DatabaseReference getFirebase() {
            if (referenciaFirebase == null) {
                referenciaFirebase = FirebaseDatabase.getInstance().getReference();
            }
            return referenciaFirebase;
        }

        public static FirebaseDatabase getFirebaseDatabase() {
            if (database == null) {
                database = FirebaseDatabase.getInstance();
            }
            return database;
        }
    */
    synchronized public static FirebaseAuth getFirebaseAuth() {
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

    synchronized public FirebaseAuth getAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static FirebaseInstallations getFirebaseInstallations() {
        if (instalacao == null) {
            instalacao = FirebaseInstallations.getInstance();
        }
        return instalacao;
    }

    public static FirebaseUser getCurrentUser() {
        if (currentUser == null) {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        return currentUser;
    }

    public static FirebaseFirestore getFirebaseFirestore() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }
        return db;
    }

    public  FirebaseFirestore getBancoDeDadosFirestore() {
        if (bancoDeDadosFirestore == null) {
            bancoDeDadosFirestore = FirebaseFirestore.getInstance();
        }
        return bancoDeDadosFirestore;
    }


}
