package ataei.sina.hamsafar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.model.Advertisment;

public class AdapterRecycleAds extends RecyclerView.Adapter<AdapterRecycleAds.ViewHolder> {
    List<Advertisment> ads;
    Context ctx;
    public AdapterRecycleAds(List<Advertisment> ads , Context context) {
        this.ads = ads;
        ctx = context;
    }

    @NonNull
    @Override
    public AdapterRecycleAds.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.home_page_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleAds.ViewHolder holder, int position) {
        Advertisment advertisment = ads.get(position);
        holder.price_text.setText(String.valueOf(advertisment.getPrice() + " تومان"));
        holder.location_text.setText(String.valueOf(advertisment.getOrigin() + " - " + advertisment.getDestination()));
        holder.time_text.setText(String.valueOf(advertisment.getTime()));
        holder.date_text.setText(String.valueOf(advertisment.getDate()));
        holder.driver_name.setText(String.valueOf(advertisment.getName()));




    }

    @Override
    public int getItemCount() {
        return ads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView driver_name , rate , location_text , date_text , time_text , car_text , price_text ,hamsafarshoo;
        ImageView profile_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            driver_name = itemView.findViewById(R.id.driver_name);
            rate = itemView.findViewById(R.id.rate);
            location_text = itemView.findViewById(R.id.location_text);
            date_text = itemView.findViewById(R.id.date_text);
            time_text = itemView.findViewById(R.id.time_text);
            car_text = itemView.findViewById(R.id.car_text);
            price_text = itemView.findViewById(R.id.price_text);
            hamsafarshoo = itemView.findViewById(R.id.hamsafarshoo);
        }
    }
}
