package br.com.petersonmashni.minhascontasemdia.dao;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.petersonmashni.minhascontasemdia.models.DespesaFixa;

public class DespesaFixaDAO  {

    static public DatabaseReference getColecao() {
        return DAO.getColecao("DespesaFixa");
    }

    public void salvar(DespesaFixa despesaFixa) {
        DatabaseReference colecao = getColecao();
        if (despesaFixa.getId() == null)
            despesaFixa.setId(colecao.push().getKey());
        colecao.child(despesaFixa.getId()).setValue(despesaFixa);
    }

    public void excluir(DespesaFixa despesaFixa) {
        if (despesaFixa.getId() != null) {
            DatabaseReference colecao = getColecao();
            colecao.child(despesaFixa.getId()).setValue(null);
        }
    }

    public void buscaPorID(Context contexto, String despesaFixaID)
    {
        DatabaseReference colecao = getColecao().child(despesaFixaID);
        colecao.addListenerForSingleValueEvent((ValueEventListener) contexto);
    }
}
