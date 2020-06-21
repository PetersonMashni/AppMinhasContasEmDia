package br.com.petersonmashni.minhascontasemdia.holders;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.petersonmashni.minhascontasemdia.R;
import br.com.petersonmashni.minhascontasemdia.models.Despesa;
import br.com.petersonmashni.minhascontasemdia.models.DespesaFixa;

public class DespesaViewHolder extends RecyclerView.ViewHolder {
    TextView tvNome;
    TextView tvVencimento;
    TextView tvPagamento;
    TextView tvValor;
    LinearLayout layout;
    View viewItem;
    public Despesa despesa;

    public DespesaViewHolder(@NonNull View viewItem) {
        super(viewItem);

        this.tvNome = (TextView) itemView.findViewById(R.id.layout_inicio_tvNome);
        this.tvVencimento = (TextView) itemView.findViewById(R.id.layout_inicio_tvVencimento);
        this.tvPagamento = (TextView) itemView.findViewById(R.id.layout_inicio_tvPagamento);
        this.tvValor = (TextView) itemView.findViewById(R.id.layout_inicio_tvValor);
        this.layout = (LinearLayout) itemView.findViewById(R.id.layout_inicio);
        this.viewItem = viewItem;
    }

    public void bindDespesa(int position, Despesa despesa) {
        tvNome.setText(despesa.getNome());
        tvVencimento.setText(despesa.getVencimento().toString());
        if (despesa.getPagamento() == null)
            tvPagamento.setText("---");
        else
            tvPagamento.setText(despesa.getPagamento().toString());

        tvValor.setText(despesa.getValor().toString());

        if (position % 2 == 0) {
            itemView.setBackgroundColor(Color.WHITE);
        } else {
            itemView.setBackgroundColor(Color.rgb(230, 230, 230));
        }
        this.despesa = despesa;
    }
}
