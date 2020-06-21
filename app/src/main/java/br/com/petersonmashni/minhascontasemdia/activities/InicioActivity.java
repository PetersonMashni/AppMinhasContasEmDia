package br.com.petersonmashni.minhascontasemdia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

import br.com.petersonmashni.minhascontasemdia.R;
import br.com.petersonmashni.minhascontasemdia.dao.DespesaDAO;
import br.com.petersonmashni.minhascontasemdia.dao.DespesaFixaDAO;
import br.com.petersonmashni.minhascontasemdia.holders.DespesaFixaViewHolder;
import br.com.petersonmashni.minhascontasemdia.holders.DespesaViewHolder;
import br.com.petersonmashni.minhascontasemdia.models.Despesa;
import br.com.petersonmashni.minhascontasemdia.models.DespesaFixa;

public class InicioActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        recyclerView = findViewById(R.id.inicio_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fetch();

        FloatingActionButton fab = findViewById(R.id.inicio_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, DespesaEdicaoActivity.class);
                intent.putExtra("acao", "novo");
                startActivity( intent );
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_inicio_logout : logout();
            case R.id.menu_inicio_despesa_fixa : despesaFixa();
        }
        return super.onOptionsItemSelected(item);
    }

    private void despesaFixa() {
        Intent intent = new Intent(InicioActivity.this, DespesaFixaActivity.class);
        startActivity( intent );
    }

    private void logout ()
    {
        FirebaseAuth.getInstance().signOut();
        InicioActivity.this.finish();
    }

    private void fetch() {
        //findViewById(R.id.xlogin_loading).setVisibility(View.VISIBLE);
        Query query = DespesaDAO.getColecao();

        FirebaseRecyclerOptions<Despesa> options =
                new FirebaseRecyclerOptions.Builder<Despesa>()
                        .setQuery(query, Despesa.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Despesa, DespesaViewHolder>(options) {
            @Override
            public DespesaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View viewItem = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_inicio, parent, false);

                return new DespesaViewHolder(viewItem);
            }

            @Override
            protected void onBindViewHolder(final DespesaViewHolder holder, final int position, Despesa model) {
                holder.bindDespesa(position, model);
                final Despesa despesa = model;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*Intent intent = new Intent(InicioActivity.this, DespesaEdicaoActivity.class);
                        intent.putExtra("acao", "editar");
                        intent.putExtra("despesaId", despesa.getId() );
                        startActivity( intent );*/
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        final AlertDialog.Builder alerta = new AlertDialog.Builder(InicioActivity.this);
                        alerta.setTitle("Atenção!");
                        alerta.setIcon(android.R.drawable.ic_dialog_alert);
                        alerta.setMessage("Confirma a exclusão da Despesa '"+ despesa.getNome() + "'?");
                        alerta.setNeutralButton("Cancelar", null);

                        alerta.setPositiveButton("EXCLUIR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DespesaDAO dao = new DespesaDAO();
                                dao.excluir(despesa);
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
        // O startListening foi comentado, pois cheguei no ponto de ter uma data com valor null no firebase e
        // começou a apresentar erro, que não pôde ser resolvido até a entrega
        //adapter.startListening();
    }

}
