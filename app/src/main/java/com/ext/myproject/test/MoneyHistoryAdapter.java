package com.ext.myproject.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ext.myproject.R;

import java.util.List;

public class MoneyHistoryAdapter extends RecyclerView.Adapter<MoneyHistoryAdapter.MyAdapter> {

    private List<HistoratyBean> list;

    public MoneyHistoryAdapter(List<HistoratyBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = ( (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_money_historaty,parent,false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, int position) {
        holder.money.setText(list.get(position).getMoney());
        holder.date.setText(list.get(position).getDate());
        holder.method.setText(list.get(position).getPayMethod());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyAdapter extends RecyclerView.ViewHolder{

        TextView money;
        TextView date;
        TextView method;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            money = itemView.findViewById(R.id.tv_money);
            date = itemView.findViewById(R.id.tv_date);
            method = itemView.findViewById(R.id.tv_pay_method);
        }
    }
}
