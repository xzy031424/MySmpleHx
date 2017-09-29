package com.zl.mysmplehx.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zl.mysmplehx.Activities.Login;
import com.zl.mysmplehx.R;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MineFra extends BaseFra {
    private View vv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       vv=inflater.inflate(R.layout.lay_mine,null);
        mInit();
        return vv;
    }

    private void mInit() {

        vv.findViewById(R.id.quite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.i("TAG","onSuccess");
                        startActivity(new Intent(getActivity(),Login.class));
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.i("TAG","onError");
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        Log.i("TAG","onProgress");
                    }
                });
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setUpView() {

    }
}
