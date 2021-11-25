package com.shootinggame.gameshooting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerVIewAdapter_Shop extends RecyclerView.Adapter<RecyclerVIewAdapter_Shop.MyViewHolder> implements ItemStoreClickListener {
    Context context;
    ArrayList<ClassStore> list;
    ItemStoreClickListener mListener;

    public RecyclerVIewAdapter_Shop(Context context, ArrayList<ClassStore> list) {
        super();
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_shop, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.image.setImageResource(list.get(position).image);
        holder.name.setText(list.get(position).name);
        //holder.pokenum.setText(Integer.toString(list.get(position).pokenum));
        //   holder.dia.setText(Integer.toString(list.get(position).dia));
        // holder.coin.setText(Integer.toString(list.get(position).coin));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnShopItemClickListener(ItemStoreClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onItemClick(MyViewHolder holder, View view, int position) {
        if (mListener != null) {
            mListener.onItemClick(holder, view, position);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image ;
        TextView name, pokenum, dia, coin;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            //dia = itemView.findViewById(R.id.recylcerview_row_shopitemdia);
            //coin = itemView.findViewById(R.id.recylcerview_row_shopitemcoin);
            image = itemView.findViewById(R.id.recylcerview_row_shopimage);
            name = itemView.findViewById(R.id.recylcerview_row_shopitemname);

            //pokenum = itemView.findViewById(R.id.recylcerview_row_num);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Bitmap bitmap = BitmapFactory.decodeResource(v.getResources(), list.get(pos).image);
                        Activity_6Store Store = (Activity_6Store) Activity_6Store.activity;
                        Store.shopImg(bitmap);
                        Store.shopTextName(list.get(pos).name);
                        Store.shopTextDia(Integer.toString(list.get(pos).dia));
                        Store.shopTextCoin(Integer.toString(list.get(pos).coin));
                        if(list.get(pos).name.equals("100다이아")){
                            Store.coinimgChange(1);
                        }
                        //mListener.onItemClick(MyViewHolder.this, v, pos);

                        // 데이터 리스트로부터 아이템 데이터 참조.
                        //  Toast myToast = Toast.makeText(context, "아이템클릭 "+pos, Toast.LENGTH_SHORT);
                        // myToast.show();
//                        Intent intent = new Intent(v.getContext(), Activity_5Book_Detail_Linear.class);
//                        intent.putExtra("number",pos);
//                        intent.putExtra("pokenum",list.get(pos).pokenum);
//                        intent.putExtra("name",list.get(pos).name);
                        //                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        byte[] byteArray = stream.toByteArray();
                        //intent.putExtra("img",byteArray);
//                        v.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                        Activity_5Book Book = (Activity_5Book) Activity_5Book.activity;
//                        Book.finish();

                    }
                }
            });

        }
    }
}
