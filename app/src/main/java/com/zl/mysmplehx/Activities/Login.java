package com.zl.mysmplehx.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zl.mysmplehx.R;

/**
 * Created by Administrator on 2017/9/24.
 */

public class Login extends BaseActivity {
    private EditText loginName_ed;
    private EditText loginPwd_ed;
    private TextView gotoReg_tv;
    private Button login_btn;
    private int requestCode=0;
    private boolean autoLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_login);
        if (EMClient.getInstance().isLoggedInBefore()) {
            autoLogin = true;
            startActivity(new Intent(Login.this, MainActivity.class));

            return;
        }
    }

    @Override
    public void initViews() {
        loginName_ed=(EditText) findViewById(R.id.login_name);
        loginPwd_ed=(EditText) findViewById(R.id.login_pwd);
        gotoReg_tv=(TextView) findViewById(R.id.gotoReg_tv);
        login_btn= (Button) findViewById(R.id.login_btn);

    }

    @Override
    public void setUpView() {

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginMethod();
            }
        });


        gotoReg_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivityForResult(intent,requestCode);
            }
        });

    }

    /***
     * 登录
     * @return
     */
    private boolean loginMethod() {
        if(TextUtils.isEmpty(loginName_ed.getText().toString().trim())){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(TextUtils.isEmpty(loginPwd_ed.getText().toString().trim())){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }

        EMClient.getInstance().login(loginName_ed.getText().toString().trim(), loginPwd_ed.getText().toString().trim(),
                new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.i("MySmple","登录成功");
                        startActivity(new Intent(Login.this,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.i("MySmple","登录失败 "+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null){
            String account= data.getStringExtra("account");
            String pwd= data.getStringExtra("pwd");
           if(!TextUtils.isEmpty(account)){
               loginName_ed.setText(account);
           }
            if(!TextUtils.isEmpty(pwd)){
                loginName_ed.setText(pwd);
            }
        }
    }
}
