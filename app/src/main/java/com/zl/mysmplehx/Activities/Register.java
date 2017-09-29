package com.zl.mysmplehx.Activities;

import android.app.FragmentBreadCrumbs;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zl.mysmplehx.R;

/**
 * Created by Administrator on 2017/9/24.
 */

public class Register extends BaseActivity {
    private EditText reg_name;
    private EditText reg_pwd;
    private Button reg_btn;
    private int resultCode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_register);
    }

    @Override
    public void initViews() {
        reg_name=(EditText) findViewById(R.id.reg_name);
        reg_pwd=(EditText) findViewById(R.id.reg_pwd);
        reg_btn=(Button) findViewById(R.id.reg_btn);
    }

    @Override
    public void setUpView() {

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg_method();
            }
        });



    }

    /****
     * 注册
     */
    private void reg_method() {
        if(TextUtils.isEmpty(reg_name.getText().toString().trim())){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(reg_pwd.getText().toString().trim())){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(reg_name.getText().toString().trim(),
                            reg_pwd.getText().toString().trim());
                    Log.i("MySmple","注册成功");

                    Intent intent=new Intent(Register.this,Login.class);
                    intent.putExtra("account",reg_name.getText().toString().trim());
                    intent.putExtra("pwd",reg_pwd.getText().toString().trim());
                    Register.this.setResult(resultCode,intent);
                    EMClient.getInstance().logout(true, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            Log.i("logout","onSuccess");
                        }

                        @Override
                        public void onError(int i, String s) {
                            Log.i("logout","onError");
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                    finish();

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.i("MySmple","注册失败");
                }
            }
        }).start();
    }
}
