package com.shootinggame.gameshooting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerVIewAdapter_Team extends RecyclerView.Adapter<RecyclerVIewAdapter_Team.MyViewHolder>implements ItemTeamClickListener{
    Context context;
    ArrayList<ClassPoketMon> list;
    ItemTeamClickListener mListener;
    Activity_3Main Main = (Activity_3Main) Activity_3Main.activity;


    public RecyclerVIewAdapter_Team(Context context, ArrayList<ClassPoketMon> parse) {
        super();
        this.context = context;
        this.list = Main.getUserPoketmon();
        //this.list = Activity_3Main.getList();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_book, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.image.setImageResource(Main.getUserPoketmon().get(position).Image);
        holder.name.setText(Main.getUserPoketmon().get(position).Name);
        holder.pokenum.setText(Integer.toString(Main.getUserPoketmon().get(position).Pokenum));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //인터페이스부분
    //리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(ItemTeamClickListener listener) {
        this.mListener = listener;
    }
    @Override
    public void onItemClick(MyViewHolder holder, View view, int position) {
        if(mListener != null){
            mListener.onItemClick(holder,view,position);
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, pokenum,thumbView;

        public MyViewHolder(@NonNull final View view) {
            super(view);
            image = view.findViewById(R.id.recylcerview_row_image);
            name = view.findViewById(R.id.recylcerview_row_name);
            pokenum = view.findViewById(R.id.recylcerview_row_num);
            //thumbView = itemView.findViewById(R.id.iv_bookdetail_Poketmon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos!=RecyclerView.NO_POSITION){
                        mListener.onItemClick(MyViewHolder.this,v, pos);
                    }
                }
            });

        }
    }
}
