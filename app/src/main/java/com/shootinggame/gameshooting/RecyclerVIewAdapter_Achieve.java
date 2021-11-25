package com.shootinggame.gameshooting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerVIewAdapter_Achieve extends RecyclerView.Adapter<RecyclerVIewAdapter_Achieve.myViewHolder> implements ItemAchieveClickListener {
    Context context;
    ArrayList<ClassAchieveData> list;
    ItemAchieveClickListener mListener;
    Activity_2Join join = (Activity_2Join) Activity_2Join.activity;


    public RecyclerVIewAdapter_Achieve(Context context, ArrayList<ClassAchieveData> list) {
        super();
        this.context = context;
        this.list = join.getAchieve_thislist();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_achieve, parent, false);
        return new myViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.title.setText(join.getAchieve_thislist().get(position).title);
        holder.gold.setText(Integer.toString(join.getAchieve_thislist().get(position).achievegold));
        holder.dia.setText(Integer.toString(join.getAchieve_thislist().get(position).achievedia));
        holder.progress.setText(Integer.toString(join.getAchieve_thislist().get(position).progress));
        holder.progressmax.setText(Integer.toString(join.getAchieve_thislist().get(position).progressmax));
        holder.progressBar.setProgress(join.getAchieve_thislist().get(position).progress);
        holder.progressBar.setMax(join.getAchieve_thislist().get(position).progressmax);


    }

    @Override
    public int getItemCount() {
        return join.getAchieve_thislist().size();
    }
    public void setOnItemClickListener(ItemAchieveClickListener listener) {
        this.mListener = listener;
    }
    @Override
    public void onItemClick(myViewHolder holder, View view, int position) {
        if(mListener != null){
            mListener.onItemClick(holder,view,position);
        }
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView title , gold, dia, progress, progressmax;
        ProgressBar progressBar;
        ImageButton get;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.achieve_title);
            gold = itemView.findViewById(R.id.achieve_gold);
            dia = itemView.findViewById(R.id.achieve_dia);
            progress = itemView.findViewById(R.id.achieve_progress);
            progressmax = itemView.findViewById(R.id.achieve_progressmax);
            progressBar = itemView.findViewById(R.id.achieve_progressbar);
            get = itemView.findViewById(R.id.achieve_get);
            get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        notifyDataSetChanged();
                        mListener.onItemClick(RecyclerVIewAdapter_Achieve.myViewHolder.this,v, pos);

                    }
                }
            });
        }
    }
}
