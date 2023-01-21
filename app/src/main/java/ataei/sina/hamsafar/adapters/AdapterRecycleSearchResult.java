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

public class AdapterRecycleSearchResult extends RecyclerView.Adapter<AdapterRecycleSearchResult.ViewHolder> {

    List<Advertisment>result_ads;
    Context ctx;
    List<City_Province>list_city;
    List<City_Province>list_province;

    public AdapterRecycleSearchResult(List<Advertisment> ads, Context ctx,List<City_Province>list_city, List<City_Province>list_province) {
        this.result_ads = ads;
        this.ctx = ctx;
        this.list_city=list_city;
        this.list_province=list_province;
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

        String img= findImg(advertisment.getDestination());
        if(!img.equals("")){
            Picasso.get()
                    .load(img)
                    .fit()
                    .into(holder.ad_img);
        }



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx, AdDetails.class);
                intent.putExtra("adv",advertisment);
                intent.putExtra("image",img);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        return result_ads.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView result_rate , result_driver_name , result_text_location , result_text_date , result_text_price;

        CardView cardView;

        ImageView ad_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            result_rate = itemView.findViewById(R.id.result_rate);
            result_driver_name = itemView.findViewById(R.id.result_driver_name);
            result_text_location = itemView.findViewById(R.id.result_text_location);
            result_text_date = itemView.findViewById(R.id.result_text_date);
            result_text_price = itemView.findViewById(R.id.result_text_price);
            ad_img=itemView.findViewById(R.id.result_img_ad);
            cardView=itemView.findViewById(R.id.result_card);



        }
    }
}
