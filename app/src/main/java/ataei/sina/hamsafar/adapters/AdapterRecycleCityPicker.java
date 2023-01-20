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
import ataei.sina.hamsafar.model.City_Province;

public class AdapterRecycleCityPicker extends RecyclerView.Adapter<AdapterRecycleCityPicker.ViewHolder>{

    List<City_Province> list;
    List<City_Province> list_province;
    Context context;

    public AdapterRecycleCityPicker(List<City_Province> list , Context context,List<City_Province>list_province) {
        this.list = list;
        this.context = context;
        this.list_province=list_province;
    }

    @NonNull
    @Override
    public AdapterRecycleCityPicker.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        AdapterRecycleCityPicker.ViewHolder viewHolder = new AdapterRecycleCityPicker.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleCityPicker.ViewHolder holder, int position) {
        City_Province city_province=list.get(position);
        if(city_province.getParent()==0){
            holder.city.setText(city_province.getTitle());
        }else {
            holder.city.setText(city_province.getTitle()+" - ");
        }
        String par="";
        if(city_province.getParent()!=0){

            for (int i = 0; i < list_province.size(); i++) {
                if( city_province.getParent()==list_province.get(i).getId()){
                    par=list_province.get(i).getTitle();
                    break;
                }
            }

        }
                holder.province.setText(par);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView city,province;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            city=itemView.findViewById(R.id.textView17);
            province=itemView.findViewById(R.id.textView9);


        }
    }
}
