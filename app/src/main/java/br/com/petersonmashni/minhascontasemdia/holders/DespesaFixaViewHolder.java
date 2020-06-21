package br.com.petersonmashni.minhascontasemdia.holders;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.petersonmashni.minhascontasemdia.models.DespesaFixa;
import br.com.petersonmashni.minhascontasemdia.R;

public class DespesaFixaViewHolder extends RecyclerView.ViewHolder {
    TextView tvNome;
    TextView tvStatus;
    LinearLayout layout;
    View viewItem;
    public DespesaFixa despesaFixa;

    public DespesaFixaViewHolder(@NonNull View viewItem) {
        super(viewItem);

        this.tvNome = (TextView) itemView.findViewById(R.id.layout_despesa_fixa_tvNome);
        this.tvStatus = (TextView) itemView.findViewById(R.id.layout_despesa_fixa_tvStatus);
        this.layout = (LinearLayout) itemView.findViewById(R.id.layout_despesa_fixa);
        this.viewItem = viewItem;
    }

    public void bindDespesaFixa(int position, DespesaFixa despesaFixa) {

        tvNome.setText(despesaFixa.getNome());
        tvStatus.setText(despesaFixa.getAtivo() ?
                itemView.getResources().getString(R.string.layout_despesa_fixa_tvStatus_ativa) :
                itemView.getResources().getString(R.string.layout_despesa_fixa_tvStatus_inativa));

        if (position % 2 == 0) {
            itemView.setBackgroundColor(Color.WHITE);
        } else {
            itemView.setBackgroundColor(Color.rgb(230, 230, 230));
        }
        this.despesaFixa = despesaFixa;
    }
}
