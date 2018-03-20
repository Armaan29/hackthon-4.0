package com.example.meg2tron.digitalentrypass.ViewHolder;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meg2tron.digitalentrypass.Interface.ItemClickListener;
import com.example.meg2tron.digitalentrypass.R;

/**
 * Created by meg2tron on 16/3/18.
 */

public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public TextView txtcityname;
   public ImageView cityImage;



   private ItemClickListener itemClickListener;

    public CityViewHolder(View itemView) {
        super(itemView);
        txtcityname=(TextView)itemView.findViewById(R.id.city);
        cityImage=(ImageView)itemView.findViewById(R.id.city_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }


    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
