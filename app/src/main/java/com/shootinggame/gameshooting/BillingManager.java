package com.shootinggame.gameshooting;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

public class BillingManager implements PurchasesUpdatedListener {
    // 필요상수
    final String TAG = "IN_APP_BILLING";

    // 초기화 시 입력 받거나 생성되는 멤버 변수
    private BillingClient billingClient;
    private Activity mActivity;
    private ConsumeResponseListener mConsumResListnere;

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        //결제에 성공한 경우
        if(billingResult.getResponseCode() ==BillingClient.BillingResponseCode.OK && purchases != null){
            Log.d(TAG, "결제에 성공하였습니다. 다음과 같은 상품이 나열됩니다.");
            for(Purchase _pur : purchases){
                Log.d(TAG, "결제에 대하여 응답 받은 데이터 :" + _pur.getOriginalJson());
            }
        }
        //사용자가 결제를 취소한 경우
        else if(billingResult.getResponseCode() ==BillingClient.BillingResponseCode.USER_CANCELED){
            Log.d(TAG, "사용자에 의해 결제가 취소 되었습니다.");
        }
        //그 외에 다른 결제 실패 이유,
        else{
            Log.d(TAG, "결제가 취소 되었습니다. 종료코드 : " + billingResult.getResponseCode());
        }
    }

    //현재 접속 상태를 나타냄
    public enum connectStatusTypes{waiting, connected, disconnected, fail}
    public connectStatusTypes connectStatus = connectStatusTypes.waiting;

    //결제를 위해 가져온 상품 정보를 저장하는 변수
    public List<SkuDetails> mSkuDetailsList;



    //생성자
    public BillingManager(Activity _activity){

        // 초기화 시 입력 받은 값들을 넣어줌
        mActivity = _activity;
        Log.d(TAG, "구글 결제 매니저를 초기화 하고 있습니다.");

        // 결제를 위한, 빌링 클라이언트를 생성
        billingClient = BillingClient.newBuilder(mActivity).setListener(new PurchasesUpdatedListener(){
            //결제 처리를 하는 메소드
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
                //결제에 성공한 경우
                if(billingResult.getResponseCode() ==BillingClient.BillingResponseCode.OK && purchases != null){
                    Log.d(TAG, "결제에 성공하였습니다. 다음과 같은 상품이 나열됩니다.");
                    for(Purchase _pur : purchases){
                        Log.d(TAG, "결제에 대하여 응답 받은 데이터 :" + _pur.getOriginalJson());
                    }
                }
                //사용자가 결제를 취소한 경우
                else if(billingResult.getResponseCode() ==BillingClient.BillingResponseCode.USER_CANCELED){
                    Log.d(TAG, "사용자에 의해 결제가 취소 되었습니다.");
                }
                //그 외에 다른 결제 실패 이유,
                else{
                    Log.d(TAG, "결제가 취소 되었습니다. 종료코드 : " + billingResult.getResponseCode());
                }
            }
        }).build();
        //구글 플레이와 연결을 시도
        billingClient.startConnection(new BillingClientStateListener() {
            //결제 작업 완료 가능한 상태
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                //접속이 성공한 경우, 처리.
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    connectStatus = connectStatusTypes.connected;
                    Log.d(TAG, "구글 결제 서버에 접속을 성공하였습니다.");
                }
                //접속이 실패한 경우, 처리.
                else{
                    connectStatus = connectStatusTypes.fail;
                    Log.d(TAG, "구글 결제 서버에 접속을 실패하였습니다.\n오류코드:"+billingResult.getResponseCode());
                }
            }

            //결제 작업 중, 구글 서버와 연결이 끊어진 상태.
            @Override
            public void onBillingServiceDisconnected() {
                connectStatus = connectStatusTypes.disconnected;
                Log.d(TAG, "구글 결제 서버와 접속이 끊어졌습니다.");
            }
        });


        // 소모성 상품을 소모한 후, 응답받는 메소드
        mConsumResListnere = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String purchaseToken) {
                //성공적으로 아이템을 소모한 경우
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    Log.d(TAG, "상품을 성공적으로 소모하였습니다. 소모된 상품" + purchaseToken);
                    return;
                }
                //아이템 소모를 실패한 경우
                else{
                    Log.d(TAG, "상품 소모에 실패하였습니다. 오류코드 ("+ billingResult.getResponseCode()+"),대상 상품코드: "+purchaseToken);
                    return;
                }
            }
        };
    }
    // 구입가능한 상품의 리스트를 받아오는 메소드
    public void get_Sku_Detail_List(){
        // 구글의 상품 정보들의 ID를 만들어 줌
        List<String>skuList  = new ArrayList<>();
        skuList.add("100dia");
        Log.d(TAG, "상품 ID "+skuList .get(0));
        // SkuDetailsParam 객체를 만들어 준다 (1회성 소모품에 대한)
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList ).setType(BillingClient.SkuType.INAPP);

        //비동기 상태로 앱의, 정보를 가지고 온다.
        billingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> skuDetailsList) {
                //상품 정보를 가지고 오지 못한 경우, 오류를 반환하고 종료한다,.
                if( billingResult.getResponseCode() != BillingClient.BillingResponseCode.OK){
                    Log.d(TAG,"상품 정보를 가지고 오던 중 오류를 만났습니다. 오류코드 : "+ billingResult.getResponseCode());
                    return;
                }
                //하나의 상품 정보도 가지고 오지 못했습니다.
                if(skuDetailsList == null){
                    Log.d(TAG, "상품 정보가 존재하지 않습니다.");
                    return;
                }
                //응답 받은 데이터들의 숫자를 출력 한다.
                Log.d(TAG, "응답 받은 데이터 숫자: "+ skuDetailsList.size());
                for(int _sku_index =0; _sku_index<skuDetailsList.size(); _sku_index++){
                    //해당 인덱스의 객체를 가지고 온다.
                    SkuDetails _skuDetail = skuDetailsList.get(_sku_index);

                    //해당 인덱스의 상품 정보를 출력하도록 한다.
                    Log.d(TAG, _skuDetail.getSku() + " : " + _skuDetail.getTitle() + ", " +_skuDetail.getPrice());
                }
                //받은 값을 멤버 변수로 저장한다.
                mSkuDetailsList = skuDetailsList;
            }
        });
    }
    //실제 구입 처지를 하는 메소드
    public void purchase(SkuDetails skuId){
        /*BillingFlowParams.Builder builder = BillingFlowParams.newBuilder()
                .build().setType(BillingClient.SkuType.INAPP);
        mBillingClient.launchBillingFlow(mActivity, builder.build());-**/

        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuId)
                .build();
        mConsumResListnere.onConsumeResponse(billingClient.launchBillingFlow(mActivity,flowParams),"100dia");
    }














}
