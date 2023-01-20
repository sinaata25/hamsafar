package ataei.sina.hamsafar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ataei.sina.hamsafar.AdDetails;
import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.model.Advertisment;

public class AdapterRecycleSuggested extends RecyclerView.Adapter<AdapterRecycleSuggested.ViewHolder>{

    List<Advertisment> list;
    Context context;

    public AdapterRecycleSuggested(List<Advertisment> list , Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRecycleSuggested.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.suggestion_items,parent,false);
        AdapterRecycleSuggested.ViewHolder viewHolder = new AdapterRecycleSuggested.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleSuggested.ViewHolder holder, int position) {
        Advertisment advertisment = list.get(position);
        holder.date.setText(advertisment.getDate());
        holder.price.setText(advertisment.getPrice()+" تومان");
        holder.origin_destiantion.setText(advertisment.getOrigin()+" - "+advertisment.getDestination());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AdDetails.class);
                intent.putExtra("adv",advertisment);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,origin_destiantion,date,price;

        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.price_text_suggestion);
            name=itemView.findViewById(R.id.driver_name_suggestion);
            origin_destiantion=itemView.findViewById(R.id.location_text_suggestion);
            date=itemView.findViewById(R.id.date_text_suggestion);
            cardView=itemView.findViewById(R.id.suggestion_card);


        }
    }
}
