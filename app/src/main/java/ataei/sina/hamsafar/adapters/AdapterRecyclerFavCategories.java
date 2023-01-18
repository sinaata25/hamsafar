package ataei.sina.hamsafar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.model.Favourite_Categories;

public class AdapterRecyclerFavCategories extends RecyclerView.Adapter<AdapterRecyclerFavCategories.ViewHolder> {
        List<List<Favourite_Categories>>list=new ArrayList<>();
        Context context;
    public AdapterRecyclerFavCategories(List<List<Favourite_Categories>>list,Context context){
    this.list=list;
    this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_recycler_logup3_fragment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Favourite_Categories>sec_list=list.get(position);
        holder.textView_1.setText(sec_list.get(0).getName());
        holder.textView_2.setText(sec_list.get(1).getName());
        holder.textView_3.setText(sec_list.get(2).getName());
        Picasso.get()
                .load(sec_list.get(0).getUrl())
                .into(holder.imageView_1);

        Picasso.get()
                .load(sec_list.get(1).getUrl())
                .into(holder.imageView_2);

        Picasso.get()
                .load(sec_list.get(2).getUrl())
                .into(holder.imageView_3);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView imageView_1,imageView_2,imageView_3;
            TextView textView_1,textView_2,textView_3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_1=itemView.findViewById(R.id.imgview_1_logup3);
            imageView_2=itemView.findViewById(R.id.imgview_2_logup3);
            imageView_3=itemView.findViewById(R.id.imgview_3_logup3);
            textView_1=itemView.findViewById(R.id.textview_1_logup3);
            textView_2=itemView.findViewById(R.id.textview_2_logup3);
            textView_3=itemView.findViewById(R.id.textview_3_logup3);
        }
    }


}
