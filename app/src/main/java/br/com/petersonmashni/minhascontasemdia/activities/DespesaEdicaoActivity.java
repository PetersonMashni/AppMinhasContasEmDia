package br.com.petersonmashni.minhascontasemdia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.petersonmashni.minhascontasemdia.R;
import br.com.petersonmashni.minhascontasemdia.dao.DespesaDAO;
import br.com.petersonmashni.minhascontasemdia.dao.DespesaFixaDAO;
import br.com.petersonmashni.minhascontasemdia.models.Despesa;
import br.com.petersonmashni.minhascontasemdia.models.DespesaFixa;

public class DespesaEdicaoActivity extends AppCompatActivity
        implements ValueEventListener {
    private Spinner spTipo;
    private EditText etNome;
    private EditText etVencimento;
    private EditText etPagamento;
    private EditText etValor;
    private EditText etValorPago;

    Button btnSalvar;
    String acao;
    String despesaId;
    DespesaDAO dao;
    Despesa despesa;

    DateFormat dateFormat;
    DateFormat mesAnoFormat;
    DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa_edicao);

        dao = new DespesaDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mesAnoFormat = new SimpleDateFormat("yyyy-MM");
        decimalFormat = new DecimalFormat();

        etNome = findViewById(R.id.despesa_edicao_etNome);
        spTipo = findViewById(R.id.despesa_edicao_spTipo);
        etVencimento = findViewById(R.id.despesa_edicao_etVencimento);
        etPagamento = findViewById(R.id.despesa_edicao_etPagamento);
        etValor = findViewById(R.id.despesa_edicao_etValor);
        etValorPago = findViewById(R.id.despesa_edicao_etValorPago);

        btnSalvar = findViewById(R.id.despesa_edicao_btnSalvar);

        acao = getIntent().getStringExtra("acao");

        if (acao.equals("editar")) {
            despesaId = getIntent().getStringExtra("despesaId");
            dao.buscaPorID(this, despesaId);
        } else
            despesa = new Despesa();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                despesa.setNome(etNome.getText().toString());
                if (spTipo.getSelectedItem() == null)
                    despesa.setTipo(null);
                else
                    despesa.setTipo(spTipo.getSelectedItem().toString());

                Date vencimento = null;
                try {
                    vencimento = dateFormat.parse(etVencimento.getText().toString());
                } catch (
                        ParseException ex) {
                    Toast.makeText(DespesaEdicaoActivity.this, "Por favor, informe uma data de vencimento válida!", Toast.LENGTH_LONG).show();
                    return;
                }
                despesa.setVencimento(vencimento);
                despesa.setMesAno(mesAnoFormat.format(vencimento));

                Number valor = 0;
                try {
                    valor = (decimalFormat.parse(etValor.getText().toString()));
                } catch (
                        ParseException ex) {
                }
                if (valor.floatValue() < 0) {
                    Toast.makeText(DespesaEdicaoActivity.this, "Por favor, informe um valor maior que zero!", Toast.LENGTH_LONG).show();
                    return;
                }
                despesa.setValor(valor);

                Date pagamento = null;
                try {
                    pagamento = dateFormat.parse(etPagamento.getText().toString());
                } catch (
                        ParseException ex) {
                }
                despesa.setPagamento(pagamento);

                Number valorPago = 0;
                try {
                    valorPago = (decimalFormat.parse(etValorPago.getText().toString()));
                } catch (
                        ParseException ex) {
                }
                despesa.setValorPago(valorPago);

                dao.salvar(despesa);
                finish();
            }
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        despesa = dataSnapshot.getValue(Despesa.class);

        etNome.setText(despesa.getNome());
//        despesa.getTipo(spTipo.setSelectedItem().toString());
        etVencimento.setText(dateFormat.format(despesa.getVencimento()));
        etValor.setText(decimalFormat.format(despesa.getValor()));
        etPagamento.setText(dateFormat.format(despesa.getPagamento()));
        etValorPago.setText(decimalFormat.format(despesa.getValorPago()));
    }


    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
}
