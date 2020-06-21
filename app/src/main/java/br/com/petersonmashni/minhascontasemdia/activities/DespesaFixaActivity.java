package br.com.petersonmashni.minhascontasemdia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.Query;

import br.com.petersonmashni.minhascontasemdia.R;
import br.com.petersonmashni.minhascontasemdia.holders.DespesaFixaViewHolder;
import br.com.petersonmashni.minhascontasemdia.dao.DespesaFixaDAO;
import br.com.petersonmashni.minhascontasemdia.models.DespesaFixa;

public class DespesaFixaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa_fixa);

        /* inclusão do layout via código
        create a inflater:

        LayoutInflater inflater = (LayoutInflater)      this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflate the child layout:

        View childLayout = inflater.inflate(R.layout.child,
            (ViewGroup) findViewById(R.id.child_id));
        add it into parent:

        parentLayout.addView(childLayout);
        */
        recyclerView = findViewById(R.id.despesa_fixa_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fetch();

        FloatingActionButton fab = findViewById(R.id.despesa_fixa_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DespesaFixaActivity.this, DespesaFixaEdicaoActivity.class);
                intent.putExtra("acao", "novo");
                startActivity(intent);
            }
        });
    }

    private void fetch() {
        findViewById(R.id.xlogin_loading).setVisibility(View.VISIBLE);
        Query query = DespesaFixaDAO.getColecao();

        FirebaseRecyclerOptions<DespesaFixa> options =
                new FirebaseRecyclerOptions.Builder<DespesaFixa>()
                        .setQuery(query, DespesaFixa.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<DespesaFixa, DespesaFixaViewHolder>(options) {
            @Override
            public DespesaFixaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View viewItem = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_despesa_fixa, parent, false);

                return new DespesaFixaViewHolder(viewItem);
            }

            @Override
            protected void onBindViewHolder(final DespesaFixaViewHolder holder, final int position, DespesaFixa model) {
                holder.bindDespesaFixa(position, model);
                final DespesaFixa despesaFixa = model;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DespesaFixaActivity.this, DespesaFixaEdicaoActivity.class);
                        intent.putExtra("acao", "editar");
                        intent.putExtra("despesaFixaId", despesaFixa.getId());
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        final AlertDialog.Builder alerta = new AlertDialog.Builder(DespesaFixaActivity.this);
                        alerta.setTitle("Atenção!");
                        alerta.setIcon(android.R.drawable.ic_dialog_alert);
                        alerta.setMessage("Confirma a exclusão da Despesa Fixa '" + despesaFixa.getNome() + "'?");
                        alerta.setNeutralButton("Cancelar", null);

                        alerta.setPositiveButton("EXCLUIR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DespesaFixaDAO dao = new DespesaFixaDAO();
                                dao.excluir(despesaFixa);
                            }
                        });
                        alerta.show();
                        return true;
                    }
                });
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                findViewById(R.id.xlogin_loading).setVisibility(View.GONE);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}
