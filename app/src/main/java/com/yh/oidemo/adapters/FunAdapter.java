package com.yh.oidemo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yh.oidemo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FunAdapter extends RecyclerView.Adapter<FunAdapter.ViewHolder> {

    private List<String> tempData;

    public FunAdapter(@NonNull List<String> tempData) {
        this.tempData = tempData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        String data = tempData.get(pos);
        holder.itemTv.setText(data);
    }


    @Override
    public int getItemCount() {
        return tempData == null ? 0 : tempData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTv = itemView.findViewById(R.id.item_tv);
        }

    }
}
