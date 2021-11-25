package com.shootinggame.gameshooting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerVIewAdapter_Profile extends RecyclerView.Adapter<RecyclerVIewAdapter_Profile.MyViewHolder> implements ItemProfileClickListener{
    Context context;
    ArrayList<ClassPoketMon> list;
    ItemProfileClickListener mListener;

    public RecyclerVIewAdapter_Profile(Context context, ArrayList<ClassPoketMon> list) {
        super();
        this.context = context;
        this.list = list;
        //this.list = Activity_3Main.getList();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_profile, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.image.setImageResource(list.get(position).Profile);
    //    holder.name.setText(list.get(position).name);
    //    holder.pokenum.setText(list.get(position).pokenum);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //인터페이스부분
    //리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(ItemProfileClickListener listener) {
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

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.recylcerview_row_profileimage);
        //    name = itemView.findViewById(R.id.recylcerview_row_name);
        //    pokenum = itemView.findViewById(R.id.recylcerview_row_num);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos!=RecyclerView.NO_POSITION){
                        //포지션을 전달하기위해 클릭리스너를 주고
                        //현재 홀더를 참조하여 뷰에 포지션값을 넘겨준다
                        mListener.onItemClick(MyViewHolder.this,v,pos);
                    }
                }
            });

        }
    }
}
