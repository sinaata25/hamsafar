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

import com.squareup.picasso.Picasso;

import java.util.List;

import ataei.sina.hamsafar.AdDetails;
import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.model.Advertisment;
import ataei.sina.hamsafar.model.City_Province;

public class AdapterRecycleSuggested extends RecyclerView.Adapter<AdapterRecycleSuggested.ViewHolder>{

    List<Advertisment> list;
    Context context;
    List<City_Province>list_city;
    List<City_Province>list_province;

    public AdapterRecycleSuggested(List<Advertisment> list , Context context, List<City_Province>list_city, List<City_Province>list_province) {
        this.list = list;
        this.context = context;
        this.list_city=list_city;
        this.list_province=list_province;
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
        holder.name.setText(advertisment.getName());

        String img= findImg(advertisment.getDestination());
        if(!img.equals("")){
            Picasso.get()
                    .load(img)
                    .fit()
                    .into(holder.imageView);
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AdDetails.class);
                intent.putExtra("adv",advertisment);
                intent.putExtra("image",img);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }


    String findImg(String destination){
        String url="";
        int h = 0;
        for (int i = 0; i < list_city.size(); i++) {
            if(destination.equals(list_city.get(i).getTitle())){
                h=i;
                break;
            }
        }

        for (int i = 0; i < list_province.size(); i++) {
            if(list_city.get(h).getParent()==list_province.get(i).getId()){
                url= list_province.get(i).getImage();
            }
        }
        return url;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,origin_destiantion,date,price;
        ImageView imageView;

        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.price_text_suggestion);
            name=itemView.findViewById(R.id.driver_name_suggestion);
            origin_destiantion=itemView.findViewById(R.id.location_text_suggestion);
            date=itemView.findViewById(R.id.date_text_suggestion);
            cardView=itemView.findViewById(R.id.suggestion_card);
            imageView=itemView.findViewById(R.id.imageView7);


        }
    }










}
