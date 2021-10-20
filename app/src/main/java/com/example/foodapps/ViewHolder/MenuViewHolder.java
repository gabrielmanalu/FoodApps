package com.example.foodapps.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapps.Interface.ItemClickListener;

import butterknife.OnClick;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtMenuName;
    public ImageView imageView;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAbsoluteAdapterPosition(), false);
    }
}
