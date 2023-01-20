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

public class AdapterRecycleAds extends RecyclerView.Adapter<AdapterRecycleAds.ViewHolder> {
    List<Advertisment> ads;
    Context ctx;
    List<City_Province>list_city;
    List<City_Province>list_province;
    public AdapterRecycleAds(List<Advertisment> ads , Context context, List<City_Province>list_city,List<City_Province>list_province) {
        this.ads = ads;
        ctx = context;
        this.list_province=list_province;
        this.list_city=list_city;
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
        String img= findImg(advertisment.getDestination());
        if(!img.equals("")){
            Picasso.get()
                    .load(img)
                    .fit()
                    .into(holder.ad_img);
        }

        holder.hamsafarshoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx,AdDetails.class);
                intent.putExtra("adv",advertisment);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                ctx.startActivity(intent);

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
        return ads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView driver_name , rate , location_text , date_text , time_text , car_text , price_text ,hamsafarshoo;
        ImageView ad_img;
        CardView cardView;
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
            ad_img=itemView.findViewById(R.id.imageView_home);
            cardView=itemView.findViewById(R.id.items_home);
        }
    }
}
