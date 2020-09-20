package com.exchange.almulla.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exchange.almulla.R;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ToDoViewHolder> {

    private Context ctx;
    private ArrayList<ToDoPOJO> toList = new ArrayList<>();

    public TodoAdapter(Context ctx, ArrayList<ToDoPOJO> toList) {
        this.ctx = ctx;
        this.toList = toList;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_todo, null);
        return new ToDoViewHolder(view, ctx, toList);

    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        ToDoPOJO obj=toList.get(position);
        holder.txt_title.setText(obj.getmTitle());
        holder.txt_time.setText(obj.getMdate());
    }

    @Override
    public int getItemCount() {
        if(toList!=null){
            return toList.size();
        }else {
            return 0;
        }
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder {

        public final TextView txt_title;
        public final TextView txt_time;

        public ToDoViewHolder(@NonNull View itemView, Context ctx, ArrayList<ToDoPOJO> toList) {
            super(itemView);
            txt_title= (TextView) itemView.findViewById(R.id.txt_title);
            txt_time= (TextView) itemView.findViewById(R.id.txt_time);
        }
    }
}
