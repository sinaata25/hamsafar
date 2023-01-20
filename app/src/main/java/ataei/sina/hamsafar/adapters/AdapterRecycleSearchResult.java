package ataei.sina.hamsafar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.model.Advertisment;

public class AdapterRecycleSearchResult extends RecyclerView.Adapter<AdapterRecycleSearchResult.ViewHolder> {

    List<Advertisment>result_ads;
    Context ctx;

    public AdapterRecycleSearchResult(List<Advertisment> ads, Context ctx) {
        this.result_ads = ads;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterRecycleSearchResult.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.search_result_items,parent,false);
        AdapterRecycleSearchResult.ViewHolder viewHolder = new AdapterRecycleSearchResult.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleSearchResult.ViewHolder holder, int position) {
        Advertisment advertisment = result_ads.get(position);
        holder.result_text_price.setText(String.valueOf(advertisment.getPrice() + " تومان"));
        holder.result_text_location.setText(String.valueOf(advertisment.getOrigin() + " - " + advertisment.getDestination()));
        holder.result_text_date.setText(String.valueOf(advertisment.getDate()));
        holder.result_driver_name.setText(String.valueOf(advertisment.getName()));
    }

    @Override
    public int getItemCount() {
        return result_ads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView result_rate , result_driver_name , result_text_location , result_text_date , result_text_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            result_rate = itemView.findViewById(R.id.result_rate);
            result_driver_name = itemView.findViewById(R.id.result_driver_name);
            result_text_location = itemView.findViewById(R.id.result_text_location);
            result_text_date = itemView.findViewById(R.id.result_text_date);
            result_text_price = itemView.findViewById(R.id.result_text_price);


        }
    }
}
