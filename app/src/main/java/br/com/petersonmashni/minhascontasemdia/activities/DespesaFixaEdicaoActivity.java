package br.com.petersonmashni.minhascontasemdia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import br.com.petersonmashni.minhascontasemdia.R;
import br.com.petersonmashni.minhascontasemdia.dao.DespesaFixaDAO;
import br.com.petersonmashni.minhascontasemdia.models.DespesaFixa;

public class DespesaFixaEdicaoActivity extends AppCompatActivity
implements ValueEventListener {
    EditText etNome;
    CheckBox cbAtivo;
    Button btnSalvar;
    String acao;
    String despesaFixaId;
    DespesaFixaDAO dao;
    DespesaFixa despesaFixa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa_fixa_edicao);

        dao = new DespesaFixaDAO();

        etNome = findViewById(R.id.despesa_fixa_edicao_etNome);
        cbAtivo = findViewById(R.id.despesa_fixa_edicao_cbAtivo);
        btnSalvar = findViewById(R.id.despesa_fixa_edicao_btnSalvar);

        acao = getIntent().getStringExtra("acao");

        if (acao.equals("editar")) {
            despesaFixaId = getIntent().getStringExtra("despesaFixaId");
            dao.buscaPorID(this, despesaFixaId);
        }
        else
            despesaFixa = new DespesaFixa();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                despesaFixa.setNome(etNome.getText().toString());
                despesaFixa.setAtivo(cbAtivo.isChecked());
                dao.salvar(despesaFixa);
                finish();
            }
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        despesaFixa = dataSnapshot.getValue(DespesaFixa.class);
        etNome.setText(despesaFixa.getNome());
        cbAtivo.setChecked(despesaFixa.getAtivo());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {}
}
