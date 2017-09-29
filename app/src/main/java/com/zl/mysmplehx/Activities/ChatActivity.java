package com.zl.mysmplehx.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.zl.mysmplehx.R;

/**
 * Created by Administrator on 2017/9/25.
 */

public class ChatActivity extends BaseActivity {

    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.lay_chat);
        activityInstance = this;
        //user or group id
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        chatFragment = new EaseChatFragment();
        //set arguments
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

    }

    @Override
    public void initViews() {


        /****
         * 将动态权限的申请放在启动界面
         * 第一步：检查权限
         */
        boolean isGranted=checkedAllPermission(new String[]{
                Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA
        });

        if(isGranted){
            //如果请求权限被赋予，执行一下代码：
            Log.i("xzy","yyyyyyyyyyyyyyyyyyyyy");
            return;
        }

        /***
         * 第二步：请求权限
         */

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA
        },102);



    }

    @Override
    public void setUpView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    public String getToChatUsername(){
        return toChatUsername;
    }

    /****
     * 检查是否有数组中的权限
     * @param strings
     * @return
     */
    private boolean checkedAllPermission(String[] strings) {
        for (String p:strings) {
            if(ContextCompat.checkSelfPermission(this,p)!= PackageManager.PERMISSION_GRANTED){
                //有一个没授予权限，就返回false
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //请求吗正确
        if(102==requestCode){
            boolean isGranded=true;

            for (int permissionCode:grantResults) {
                if(permissionCode!=PackageManager.PERMISSION_GRANTED){
                    //权限被拒绝
                    isGranded=false;
                    Log.i("xzy","aaaaaaaaaaaaaaa");
                    break;

                }
            }
            if(isGranded){
                Log.i("xzy","在启动页的权限请求成功");
                //请求权限被赋予


            }else {
                //权限被拒绝
                Log.i("xzy","在启动页的权限请求失败");
            }

        }


    }


}
