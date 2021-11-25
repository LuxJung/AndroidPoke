package com.shootinggame.gameshooting;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerVIewAdapter_Book extends RecyclerView.Adapter<RecyclerVIewAdapter_Book.MyViewHolder>implements ItemBookClickListener {
    Context context;
    ArrayList<ClassPoketMon> list;
    ItemBookClickListener mListener;

    public RecyclerVIewAdapter_Book(Context context, ArrayList<ClassPoketMon> list) {
        super();
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_book, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.image.setImageResource(list.get(position).Image);
        holder.name.setText(list.get(position).Name);
        holder.pokenum.setText(Integer.toString(list.get(position).Pokenum));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //인터페이스부분
    //리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(ItemBookClickListener listener) {
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

                        /*
                        // 데이터 리스트로부터 아이템 데이터 참조.
                      //  Toast myToast = Toast.makeText(context, "아이템클릭 "+pos, Toast.LENGTH_SHORT);
                       // myToast.show();
                        Log.e("클릭은됨",String.valueOf(pos));
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {


                            Pair<View, String> pair_IMG = Pair.create(itemView, itemView.getTransitionName());
                            ActivityOptionsCompat optionsCompat =
                                    ActivityOptionsCompat.makeSceneTransitionAnimation((Activity_5Book) Activity_5Book.activity, pair_IMG);

                            Intent intent = new Intent(v.getContext(), Activity_5Book_Detail.class);
                            intent.putExtra("number", pos);
                            intent.putExtra("pokenum", list.get(pos).pokenum);
                            intent.putExtra("name", list.get(pos).name);
                            Bitmap bitmap = BitmapFactory.decodeResource(v.getResources(), list.get(pos).image);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            intent.putExtra("img", byteArray);
                            v.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), optionsCompat.toBundle());
                        }else{
                            Intent intent = new Intent(v.getContext(), Activity_5Book_Detail.class);
                            intent.putExtra("number", pos);
                            intent.putExtra("pokenum", list.get(pos).pokenum);
                            intent.putExtra("name", list.get(pos).name);
                            Bitmap bitmap = BitmapFactory.decodeResource(v.getResources(), list.get(pos).image);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            intent.putExtra("img", byteArray);
                            v.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        Activity_5Book Book = (Activity_5Book) Activity_5Book.activity;




                        Book.finish();*/

                    }
                }
            });

        }
    }
}
