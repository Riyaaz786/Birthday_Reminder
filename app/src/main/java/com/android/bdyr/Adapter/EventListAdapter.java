package com.android.bdyr.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bdyr.Activities.AddEvent;
import com.android.bdyr.Database.Entities;
import com.android.bdyr.Holder.EventHolder;
import com.android.bdyr.R;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Entities> arrayList;
    int count=0,empty=1,not_empty=2;
    String[] months;

    {
        months = new String[]{ "Jan", "Feb", "Mar", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec" };
    }
    public EventListAdapter(Context context, ArrayList<Entities> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == empty){
            return new Empty(LayoutInflater.from(context).inflate(R.layout.no_event,parent,false));
        }else if (viewType == not_empty){
            return new Not_empty(LayoutInflater.from(context).inflate(R.layout.birthday_row_item,parent,false));
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint ("RecyclerView") int position) {
        if (holder.getClass() == Empty.class){
            Empty container1=(Empty) holder;
            container1.textView.setText("No event are stored now!\nAdd new event by clicking + button\nor pull down to refresh!");
        }else if (holder.getClass() == Not_empty.class){
            Not_empty container=(Not_empty) holder;
            container.flag.setVisibility(View.GONE);
            String cat=arrayList.get(position).getCategory();
            String nam=arrayList.get(position).getName();
            String num=arrayList.get(position).getNumber();
            String dat=arrayList.get(position).getDate();
            String text=arrayList.get(position).getText();
            container.name.setText(nam);
            String s=dat.split(":")[1];
            if (s.startsWith("0")){
                switch (s) {
                    case "01":
                        s = "1";
                        break;
                    case "02":
                        s = "2";
                        break;
                    case "03":
                        s = "3";
                        break;
                    case "04":
                        s = "4";
                        break;
                    case "05":
                        s = "5";
                        break;
                    case "06":
                        s = "6";
                        break;
                    case "07":
                        s = "7";
                        break;
                    case "08":
                        s = "8";
                        break;
                    case "09":
                        s = "9";
                        break;
                }
            }
            String month = months[Integer.parseInt(s.trim())-1];
            container.date.setText(String.format("%s %s %s , %s",cat,dat.split(":")[0],month,dat.split(":")[2]));
            container.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cat=arrayList.get(position).getCategory();
                    String nam=arrayList.get(position).getName();
                    String num=arrayList.get(position).getNumber();
                    String dat=arrayList.get(position).getDate();
                    String text=arrayList.get(position).getText();
                    Intent intent=new Intent(context, AddEvent.class);
                    intent.putExtra("cat",cat);
                    intent.putExtra("nam",nam);
                    intent.putExtra("dat",dat);
                    intent.putExtra("num",num);
                    intent.putExtra("text",text);
                    intent.putExtra("flag",true);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList.size() == 0){
            count = -1;
            return count+2;
        }else {
            count=arrayList.size();
            return count;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (count == -1){
            return empty;
        }else {
            return not_empty;
        }
    }
    public static class Empty extends RecyclerView.ViewHolder{
        TextView textView;
        public Empty(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.no_event_text);
        }

    }
    public static class Not_empty extends RecyclerView.ViewHolder{
        TextView name,flag,date;
        ImageView whatsApp,Call,Message;
        public Not_empty(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            flag=itemView.findViewById(R.id.flag);
            date=itemView.findViewById(R.id.date);
            whatsApp=itemView.findViewById(R.id.whatsapp);
            Call=itemView.findViewById(R.id.call);
            Message=itemView.findViewById(R.id.message);
        }

    }
}