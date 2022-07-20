package com.nour.beautyapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nour.beautyapp.Interface.ItemClickListener;
import com.nour.beautyapp.R;


public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtItemTitle, txtItemDetails , txtItemNotes;
    public ImageView imageView;
    public ItemClickListener listener;


    public ItemViewHolder(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.item_layout_image);
        txtItemTitle = (TextView) itemView.findViewById(R.id.item_layout_title);
        txtItemDetails = (TextView) itemView.findViewById(R.id.item_layout_details);
        txtItemNotes = (TextView) itemView.findViewById(R.id.item_layout_notes);
     }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
