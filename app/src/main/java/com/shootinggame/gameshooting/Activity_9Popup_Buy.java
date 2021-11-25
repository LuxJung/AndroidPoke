package com.shootinggame.gameshooting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Activity_9Popup_Buy extends AppCompatActivity implements PurchasesUpdatedListener {
    private ImageButton ib_Yes_Buy, ib_No_Buy, ib_Close_Buy;
    private String tag;
    private Intent intent;
    Boolean GGOBUGI = true;
    Boolean GGOBUGI_PROFILE = true;
    Context mContext;

    Gson gson = new Gson();
    Activity_3Main Main = (Activity_3Main) Activity_3Main.activity;
    ///////////////////////////////////////////////////////
    // 필요상수
    final String TAG = "IN_APP_BILLING";

    // 초기화 시 입력 받거나 생성되는 멤버 변수
    private BillingClient mBillingClient;
    private Activity mActivity;
    private ConsumeResponseListener mConsumResListnere;
    SkuDetails skuDetails100dia;
    String skuID100dia = "100dia";
    Purchase purchase;


    //현재 접속 상태를 나타냄
    public enum connectStatusTypes {waiting, connected, disconnected, fail}

    public BillingManager.connectStatusTypes connectStatus = BillingManager.connectStatusTypes.waiting;

    //결제를 위해 가져온 상품 정보를 저장하는 변수
    public List<SkuDetails> mSkuDetailsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9popup_buy);
        hideNaviUI();
        popFindID_Buy();
        buy();
        intent = getIntent();

        mContext = Activity_9Popup_Buy.this;
        mBillingClient = BillingClient.newBuilder(mContext).setListener(this).enablePendingPurchases().build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The billing client is ready. You can query purchases here.
                    List<String> skuList = new ArrayList<>();
                    skuList.add(skuID100dia);
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    mBillingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> skuDetailsList) {
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                                connectStatus = BillingManager.connectStatusTypes.connected;
                                Log.d(TAG, "구글 결제 서버에 접속을 성공하였습니다.");
                                for (SkuDetails skuDetails : skuDetailsList) {
                                    String sku = skuDetails.getSku();
                                    String price = skuDetails.getPrice();
                                    if (skuID100dia.equals(sku)) {
                                        skuDetails100dia = skuDetails;
                                    }
                                }
                            } else {
                                connectStatus = BillingManager.connectStatusTypes.fail;
                                Log.d(TAG, "구글 결제 서버에 접속을 실패하였습니다.\n오류코드:" + billingResult.getResponseCode());
                            }
                        }

                    });
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
            }
            @Override
            public void onBillingServiceDisconnected() {
                connectStatus = BillingManager.connectStatusTypes.disconnected;
                Log.d(TAG, "구글 결제 서버와 접속이 끊어졌습니다.");
            }

/*

            billingManager.
                    billingManager =new

            BillingManager(this);
        billingManager.get_Sku_Detail_List();
            //bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkpRonk/WcejsATM74aaasd2aeAVbuQ4v0wG7kP66828fVtZ/3J3zVlZLNypVjH0GYCOlX/kru2HGSaRacSFim90OmWzznPAjsvfQ3vd347SSK9uvN+wm6eoP4U0zFam42Z4ajKZ8oxpXHSGUHN4S7BYxdoTcbs+JfiIZ3EZaI/3fGv8loI9S7pVFWjjjtvEJW4qxVgjm2ferts3a/vYOpa1GjJK47S7gC0N9pBHW3DYStzsVQaAmieMGYCXQcVtcbtH/fE77PLJxeNdL2qodQevxanZjI3SiV/pC8zQczWO1sOs0EXbhapVJ/Tkm4mlwXwBT3BJn8URuGMnbgo/A3QIDAQAB", this);
            bp =new

            BillingProcessor(this,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnmhBjpgdtJdiFAlQF9ztCglqVMOHd0v6fgsQT3/Oi9CS2aCQ1QjHO1qUO0Wc4t86WxNcfwjmEEKltPAQ6OovvPgNrk2DOture+OibCgJzSZfAieJrnbQQMjXOwJypKWpWIULjtXUGYqg+feQH/arokXyVxSFMYbE5nlSO/qXMiuy/hCND0BGHN4OmrgvB85bkw7WjufXJg8K3RGByXOKLfBjipR5tui1hcjysdlIsfAXFa5dY330aYX2Mxbout2u++jxCmDc2wftD2n9bCEM+BzgyHzobKVgUvbYR59SrC8/wuprIvEc2ZDd8NH5h1Rzlb1LAwQMcyzGDBGOBaffYQIDAQAB",this);
        bp.initialize();*/
/*
        billingManager = new BillingManager(this);
        billingManager.get_Sku_Detail_List();*/
        });
    }
    ConsumeResponseListener consumeListener = new ConsumeResponseListener() {
        @Override
        public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                // Handle the success of the consume operation.
                // For example, increase the number of coins inside the user's basket.
            }
        }
    };
    private void doBillingFlow(SkuDetails skuDetails) {
        BillingFlowParams flowParams;
        int responseCode;
        String purchaseToken;
       // purchaseToken = purchase.getPurchaseToken();
        //mBillingClient.consumeAsync(purchaseToken, consumeListener);

        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build();
        //responseCode = mBillingClient.launchBillingFlow(Activity_9Popup_Buy.this, flowParams);
        mConsumResListnere.onConsumeResponse(mBillingClient.launchBillingFlow(Activity_9Popup_Buy.this, flowParams),"");
        /*if(responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED) {
            Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
            onPurchasesUpdated(BillingClient.BillingResponse.OK, purchasesResult.getPurchasesList());
        }*/
    }
/*
    private void handlePurchase(Purchase purchase) {
        String purchaseToken;
        purchaseToken = purchase.getPurchaseToken();
        mBillingClient.consumeAsync(purchaseToken, consumeListener);
    }
    ConsumeResponseListener consumeListener = new ConsumeResponseListener() {
        @Override
        public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                // Handle the success of the consume operation.
                // For example, increase the number of coins inside the user's basket.
            }
        }
    };*/
    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        //결제에 성공한 경우
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
            Log.d(TAG, "결제에 성공하였습니다. 다음과 같은 상품이 나열됩니다.");
            for (Purchase _pur : list) {
                Log.d(TAG, "결제에 대하여 응답 받은 데이터 :" + _pur.getOriginalJson());
            }
        }
        //사용자가 결제를 취소한 경우
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d(TAG, "사용자에 의해 결제가 취소 되었습니다.");
        }
        //그 외에 다른 결제 실패 이유,
        else {
            Log.d(TAG, "결제가 취소 되었습니다. 종료코드 : " + billingResult.getResponseCode());
        }
    }
/*
    public BillingManager(Activity _activity) {

        // 초기화 시 입력 받은 값들을 넣어줌
        mActivity = _activity;
        Log.d(TAG, "구글 결제 매니저를 초기화 하고 있습니다.");

        // 결제를 위한, 빌링 클라이언트를 생성
        billingClient = BillingClient.newBuilder(mActivity).setListener(new PurchasesUpdatedListener() {
            //결제 처리를 하는 메소드
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
                //결제에 성공한 경우
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
                    Log.d(TAG, "결제에 성공하였습니다. 다음과 같은 상품이 나열됩니다.");
                    for (Purchase _pur : purchases) {
                        Log.d(TAG, "결제에 대하여 응답 받은 데이터 :" + _pur.getOriginalJson());
                    }
                }
                //사용자가 결제를 취소한 경우
                else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                    Log.d(TAG, "사용자에 의해 결제가 취소 되었습니다.");
                }
                //그 외에 다른 결제 실패 이유,
                else {
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
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    connectStatus = BillingManager.connectStatusTypes.connected;
                    Log.d(TAG, "구글 결제 서버에 접속을 성공하였습니다.");
                }
                //접속이 실패한 경우, 처리.
                else {
                    connectStatus = BillingManager.connectStatusTypes.fail;
                    Log.d(TAG, "구글 결제 서버에 접속을 실패하였습니다.\n오류코드:" + billingResult.getResponseCode());
                }
            }

            //결제 작업 중, 구글 서버와 연결이 끊어진 상태.
            @Override
            public void onBillingServiceDisconnected() {
                connectStatus = BillingManager.connectStatusTypes.disconnected;
                Log.d(TAG, "구글 결제 서버와 접속이 끊어졌습니다.");
            }
        });


        // 소모성 상품을 소모한 후, 응답받는 메소드
        mConsumResListnere = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String purchaseToken) {
                //성공적으로 아이템을 소모한 경우
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "상품을 성공적으로 소모하였습니다. 소모된 상품" + purchaseToken);
                    return;
                }
                //아이템 소모를 실패한 경우
                else {
                    Log.d(TAG, "상품 소모에 실패하였습니다. 오류코드 (" + billingResult.getResponseCode() + "),대상 상품코드: " + purchaseToken);
                    return;
                }
            }
        };
    }*/

    /* class AppStorage {
         private Context context;
         private SharedPreferences pref;
         private String PURCHASED_REMOVE_ADS = "remove_ads";

         public AppStorage(Context context) {
             pref = context.getSharedPreferences("app_storage", Context.MODE_PRIVATE);
             this.context = context;
         }

         public boolean purchasedRemoveAds() {
             return pref.getBoolean(PURCHASED_REMOVE_ADS, false);
         }

         public void setPurchasedRemoveAds(boolean flag) {
             SharedPreferences.Editor editor = pref.edit();
             editor.putBoolean(PURCHASED_REMOVE_ADS, flag);
             editor.apply();
         }
     }*/
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bp != null) {
            bp.release();
        }
    }*/
    public void buy() {
        ib_Yes_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = "유저정보";
                //아이템 이름 넘겨받아서 이름이 골드일 경우
                //유저의 코인을 증가 시킬 수 있도록 예외 처리
                //구매에 성공 하였을 경우 구매했다는 것을 알 수 있도록 메시지주기
                //구매에 실패 하였을 경우 구매에 실패 했다는 메시지 던져주기
                //2020/6/3 위 고려사항은 아직 준비되지 않고
                //다이아 및 코인만 감소 됩니다.
                //구매하기위해 6store에서 intent로 가격정보만 받아옴
                String ShopName = intent.getStringExtra("name");
                int ShopDia = intent.getIntExtra("dia", 0);
                Log.e(tag + "상점가 다이아", String.valueOf(ShopDia));

                int ShopCoin = intent.getIntExtra("coin", 0);
                Log.e(tag + "상점가 코인", String.valueOf(ShopCoin));

                SharedPreferences pref = getSharedPreferences("UserInstance", MODE_PRIVATE);//쉐어드 선언 Key : User
                SharedPreferences.Editor editor = pref.edit();//쉐어드 사용할 'editor'선언
                //현재 유저가 가지고 있는 재화
                int UserDia = pref.getInt("UserDia", ClassUserData.getInstance().getDiamond());
                Log.e(tag + "유저의 다이아", String.valueOf(UserDia));

                int UserCoin = pref.getInt("UserGold", ClassUserData.getInstance().getGold());
                Log.e(tag + "유저의 코인", String.valueOf(UserCoin));

                // 유저의 재화가 클 경우 구매 가능
                if (UserDia >= ShopDia && UserCoin >= ShopCoin) {


                    if (ShopName.equals("꼬부기")) {
                        for (int i = 0; i < Main.UserPoketmon.size(); i++) {
                            if (Main.UserPoketmon.get(i).Name.equals("꼬부기")) {
                                GGOBUGI = false;
                            }
                        }
                        for (int i = 0; i < Main.ONLY_PROFILE_UserPoketmon.size(); i++) {
                            if (Main.ONLY_PROFILE_UserPoketmon.get(i).Name.equals("꼬부기")) {
                                GGOBUGI_PROFILE = false;
                            }
                        }
                        if (GGOBUGI) {
                            Main.UserPoketmon.add(new ClassPoketMon("꼬부기", R.drawable.ggobugi, R.drawable.ggobugiprofile, 7, 100, 500, 1, 1, 5, 1, 5, "꼬부기 입니다.", 1, 1, 5, 5, 5));
                            String ToJson = gson.toJson(Main.getUserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                            editor.putString("User_Poketmon", ToJson);
                            int AfterCostDia = UserDia - ShopDia;
                            int AfterCostCoin = UserCoin - ShopCoin;
                            Log.e(tag + "유저의 남은 다이아", String.valueOf(AfterCostDia));
                            Log.e(tag + "유저의 남은 코인", String.valueOf(AfterCostCoin));
                            editor.putInt("UserDia", AfterCostDia);
                            editor.putInt("UserGold", AfterCostCoin);
                            editor.commit();
                            Log.e("Shared저장값", pref.getString("User_Poketmon", ""));
                            Toast.makeText(getApplicationContext(), "구입완료", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "구입실패", Toast.LENGTH_SHORT).show();

                        }
                        if (GGOBUGI_PROFILE) {//꼬부기 프로필이 있을 경우 넣지 않는다.
                            Main.ONLY_PROFILE_UserPoketmon.add(new ClassPoketMon("꼬부기", R.drawable.ggobugi, R.drawable.ggobugiprofile, 7, 100, 500, 1, 1, 5, 1, 5, "꼬부기 입니다.", 1, 1, 5, 5, 5));
                            String ToJson3 = gson.toJson(Main.getONLY_PROFILE_UserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                            editor.putString("User_Poketmon_PROFILE", ToJson3);
                            editor.commit();
                            Log.e("Shared저장값", pref.getString("User_Poketmon", ""));
                        }
                    }
                    //////////////////////////위 코드 반복해서 구현할것
                    else if (ShopName.equals("파이리")) {
                        Main.UserPoketmon.add(new ClassPoketMon("파이리", R.drawable.pairi, R.drawable.pairiprofile, 4, 100, 500, 1, 1, 5, 1, 5, "파이리 입니다.", 1, 1, 5, 5, 5));
                        String ToJson1 = gson.toJson(Main.getUserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        editor.putString("User_Poketmon", ToJson1);

                        Main.ONLY_PROFILE_UserPoketmon.add(new ClassPoketMon("파이리", R.drawable.pairi, R.drawable.pairiprofile, 4, 100, 500, 1, 1, 5, 1, 5, "파이리 입니다.", 1, 1, 5, 5, 5));
                        String ToJson3 = gson.toJson(Main.getONLY_PROFILE_UserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        editor.putString("User_Poketmon_PROFILE", ToJson3);

                        editor.commit();
                        Log.e("Shared저장값", pref.getString("User_Poketmon", ""));
                    } else if (ShopName.equals("이상해씨")) {
                        Main.UserPoketmon.add(new ClassPoketMon("이상해씨", R.drawable.esangheassi, R.drawable.esangheassiprofile, 1, 100, 500, 1, 1, 5, 1, 5, "이상해씨 입니다.", 1, 1, 5, 5, 5));
                        String ToJson2 = gson.toJson(Main.getUserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        editor.putString("User_Poketmon", ToJson2);

                        Main.ONLY_PROFILE_UserPoketmon.add(new ClassPoketMon("이상해씨", R.drawable.esangheassi, R.drawable.esangheassiprofile, 1, 100, 500, 1, 1, 5, 1, 5, "이상해씨 입니다.", 1, 1, 5, 5, 5));
                        String ToJson3 = gson.toJson(Main.getONLY_PROFILE_UserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        editor.putString("User_Poketmon_PROFILE", ToJson3);

                        editor.commit();
                        Log.e("Shared저장값", pref.getString("User_Poketmon", ""));
                    } else if (ShopName.equals("피츄")) {
                        Main.UserPoketmon.add(new ClassPoketMon("피츄", R.drawable.pichu, R.drawable.pichuprofile, 10, 100, 500, 1, 1, 5, 1, 5, "피츄 입니다.", 1, 1, 5, 5, 5));
                        String ToJson2 = gson.toJson(Main.getUserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        editor.putString("User_Poketmon", ToJson2);

                        Main.ONLY_PROFILE_UserPoketmon.add(new ClassPoketMon("피츄", R.drawable.pichu, R.drawable.pichuprofile, 10, 100, 500, 1, 1, 5, 1, 5, "피츄 입니다.", 1, 1, 5, 5, 5));
                        String ToJson3 = gson.toJson(Main.getONLY_PROFILE_UserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        editor.putString("User_Poketmon_PROFILE", ToJson3);

                        editor.commit();
                        Log.e("Shared저장값", pref.getString("User_Poketmon", ""));
                    } else if (ShopName.equals("피카츄")) {
                        Main.UserPoketmon.add(new ClassPoketMon("피카츄", R.drawable.picachu, R.drawable.picachuprofile, 11, 200, 1000, 5, 5, 10, 5, 10, "피카츄 입니다.", 5, 5, 10, 10, 10));
                        String ToJson2 = gson.toJson(Main.getUserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        Main.ONLY_PROFILE_UserPoketmon.add(new ClassPoketMon("피카츄", R.drawable.picachu, R.drawable.picachuprofile, 11, 200, 1000, 5, 5, 10, 5, 10, "피카츄 입니다.", 5, 5, 10, 10, 10));
                        String ToJson3 = gson.toJson(Main.getONLY_PROFILE_UserPoketmon());//gson을 통해 ArrayList<ClassPoketMon> list 를 JSON 형태로변환
                        editor.putString("User_Poketmon_PROFILE", ToJson3);
                        editor.putString("User_Poketmon", ToJson2);

                        editor.commit();
                        Log.e("Shared저장값", pref.getString("User_Poketmon", ""));
                    } else if (ShopName.equals("5000코인")) {
                        int AfterCostDia2 = UserDia - ShopDia;
                        int AfterCostCoin2 = UserCoin + 5000;
                        Log.e(tag + "유저의 남은 다이아", String.valueOf(AfterCostDia2));
                        Log.e(tag + "유저의 남은 코인", String.valueOf(AfterCostCoin2));
                        editor.putInt("UserDia", AfterCostDia2);
                        editor.putInt("UserGold", AfterCostCoin2);
                        editor.commit();
                        Log.e("Shared저장값", pref.getString("User_Poketmon", ""));
                    } else if (ShopName.equals("100다이아")) {

                        doBillingFlow(skuDetails100dia);
                        //  Log.d("billingManager.mSkuDetailsList.get(0).getSku()",String.valueOf(billingManager.mSkuDetailsList.get(0).getSku()));
                        //  Log.d("billingManager.mSkuDetailsList.get(0).getSku()",String.valueOf(billingManager.mSkuDetailsList.get(0).getPrice()));
                        //  Log.d("billingManager.mSkuDetailsList.get(0).getSku()",String.valueOf(billingManager.mSkuDetailsList.get(0).getOriginalJson()));
                        //  Log.d("billingManager.mSkuDetailsList.get(0).getSku()",String.valueOf(billingManager.mSkuDetailsList.get(0).getTitle()));
                        //  billingManager.purchase(billingManager.mSkuDetailsList.get(0).getSku());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "구매 실패!", Toast.LENGTH_SHORT).show();

                }
                finish();
            }
        });

        ib_No_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "구매 취소!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ib_Close_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideNaviUI();
        tag = "구매PopUP";
        Log.e(tag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNaviUI();
        tag = "구매PopUP";
        Log.e(tag, "onResume");

    }

    public void popFindID_Buy() {
        ib_Yes_Buy = findViewById(R.id.popup_buy_Yes);
        ib_No_Buy = findViewById(R.id.popup_buy_No);
        ib_Close_Buy = findViewById(R.id.popup_buy_Close);
    }

    public void hideNaviUI() {
        int newUiOptions = 0;
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}