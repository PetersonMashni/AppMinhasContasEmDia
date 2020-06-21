package br.com.petersonmashni.minhascontasemdia.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAO {
    static public DatabaseReference getColecao(String nomeColecao) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String nomeUsuario = auth.getCurrentUser().getEmail().replace("@", "_").replace(".", "_");
        return db.child("/" + nomeUsuario + "/" + nomeColecao);
    }
}
