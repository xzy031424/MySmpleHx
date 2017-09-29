package com.zl.mysmplehx.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/29.
 */

public class githubT extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mInit();
    }

    private void mInit() {
        Log.i("Tag","提交成功");
    }
}
