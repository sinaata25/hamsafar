package ataei.sina.hamsafar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ataei.sina.hamsafar.AdDetails;
import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.model.Advertisment;

public class AdapterRecycleSpecial extends RecyclerView.Adapter<AdapterRecycleSpecial.ViewHolder>{

    List<Advertisment> specialAds;
    Context context;

    public AdapterRecycleSpecial(List<Advertisment> ads , Context context) {
        specialAds = ads;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRecycleSpecial.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.special_items,parent,false);
        AdapterRecycleSpecial.ViewHolder viewHolder = new AdapterRecycleSpecial.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleSpecial.ViewHolder holder, int position) {
        Advertisment advertisment = specialAds.get(position);
        holder.price_text_special.setText(String.valueOf(advertisment.getPrice() + " تومان"));
        holder.location_text_special.setText(String.valueOf(advertisment.getOrigin() + " - " + advertisment.getDestination()));
        holder.date_text_special.setText(String.valueOf(advertisment.getDate()));
        holder.driver_name_special.setText(String.valueOf(advertisment.getName()));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AdDetails.class);
                intent.putExtra("adv",advertisment);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return specialAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView price_text_special , date_text_special , location_text_special , driver_name_special , rate_special ;

        CardView cardView;

        ImageView ad_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price_text_special = itemView.findViewById(R.id.price_text_special);
            date_text_special = itemView.findViewById(R.id.date_text_special);
            location_text_special = itemView.findViewById(R.id.location_text_special);
            driver_name_special = itemView.findViewById(R.id.driver_name_special);
            rate_special = itemView.findViewById(R.id.rate_special);
            cardView=itemView.findViewById(R.id.special_card);
            ad_img=itemView.findViewById(R.id.result_img_ad);
        }
    }
}
