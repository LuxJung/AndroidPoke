package com.shootinggame.gameshooting;

import android.view.View;

//도감리사이클러뷰 관련 인터페이스
//액티비티에서 클릭포지션을 받을 수 있는 것이 큰 장점이다!
//애니메이션이 가장 큰 목적
public interface ItemStoreClickListener {
    public void onItemClick(RecyclerVIewAdapter_Shop.MyViewHolder holder, View view, int position);
}
