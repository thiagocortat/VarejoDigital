package com.varejodigital.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.varejodigital.R;
import com.varejodigital.model.Funcionario;

import java.util.List;


/**
 * Created by thiagocortat on 1/15/15.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {

    private List<Funcionario> recordList;

    public RecordAdapter(List<Funcionario> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_adapter, viewGroup, false);
        return new RecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int i) {

        Funcionario record = recordList.get(i);
        holder.vTitle.setText(record.getNome());
        holder.vName.setText(record.getCargo());

//        Picasso.with(holder.vImageView.getContext()).cancelRequest(holder.vImageView);
//        Picasso.with(holder.vImageView.getContext()).load(record.getThumbnailUrl()).into(holder.vImageView);

        holder.itemView.setTag(record);
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }
}
