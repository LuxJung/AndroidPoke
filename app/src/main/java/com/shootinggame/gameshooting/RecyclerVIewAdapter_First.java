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

public class RecyclerVIewAdapter_First extends RecyclerView.Adapter<RecyclerVIewAdapter_First.MyViewHolder>implements ItemFirstClickListener {
    Context context;
    ArrayList<ClassPoketMon> flist;
    ItemFirstClickListener mListener;

    public RecyclerVIewAdapter_First(Context context, ArrayList<ClassPoketMon> flist) {
        super();
        this.context = context;
        this.flist = flist;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_first_choice, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.fname.setText(flist.get(position).Name);
        holder.fpokenum.setText(Integer.toString(flist.get(position).Pokenum));
        holder.fimage.setImageResource(flist.get(position).Image);

    }

    @Override
    public int getItemCount() {
        return flist.size();
    }

    //인터페이스부분
    //리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(ItemFirstClickListener listener) {
        this.mListener = listener;
    }
    @Override
    public void onItemClick(MyViewHolder holder, View view, int position) {
        if(mListener != null){
            mListener.onItemClick(holder,view,position);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView fimage;
        TextView fname, fpokenum;

        public MyViewHolder(@NonNull final View view) {
            super(view);
            fname = view.findViewById(R.id.recylcerview_first_name);
            fpokenum = view.findViewById(R.id.recylcerview_first_num);
            fimage = view.findViewById(R.id.recylcerview_first_image);
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
