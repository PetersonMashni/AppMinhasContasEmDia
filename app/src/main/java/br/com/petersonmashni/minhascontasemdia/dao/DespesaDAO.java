package br.com.petersonmashni.minhascontasemdia.dao;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.petersonmashni.minhascontasemdia.models.Despesa;
import br.com.petersonmashni.minhascontasemdia.models.DespesaFixa;

public class DespesaDAO {

    static public DatabaseReference getColecao() {
        return DAO.getColecao("Despesa");
    }

    public void salvar(Despesa despesa) {
        DatabaseReference colecao = getColecao();
        if (despesa.getId() == null)
            despesa.setId(colecao.push().getKey());
        colecao.child(despesa.getId()).setValue(despesa);
    }

    public void excluir(Despesa despesa) {
        if (despesa.getId() != null) {
            DatabaseReference colecao = getColecao();
            colecao.child(despesa.getId()).setValue(null);
        }
    }

    public void buscaPorID(Context contexto, String despesaID)
    {
        DatabaseReference colecao = getColecao().child(despesaID);
        colecao.addListenerForSingleValueEvent((ValueEventListener) contexto);
    }
}
