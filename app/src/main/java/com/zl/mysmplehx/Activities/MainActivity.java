package com.zl.mysmplehx.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.zl.mysmplehx.Fragments.MineFra;
import com.zl.mysmplehx.Fragments.MyContactFra;
import com.zl.mysmplehx.Fragments.MyConversionFra;
import com.zl.mysmplehx.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bar;
    private MyConversionFra myConversionFra;
    private MyContactFra myContactFra;
    private FragmentManager fragmentManager;
    private MineFra mineFra;
    private Fragment currentFra=new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();
        bar= (BottomNavigationBar) findViewById(R.id.bar);
        bar.setTabSelectedListener(this);
        mInit();

    }

    private void mInit() {
        if(myConversionFra==null){
            myConversionFra=new MyConversionFra();
        }
        FragmentTransaction ft= fragmentManager.beginTransaction();
        addOrShow(myConversionFra,ft);
        myConversionFra.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
                Toast.makeText(MainActivity.this, "跳转到聊天界面", Toast.LENGTH_SHORT).show();

            }
        });

        myContactFra=new MyContactFra();
        mineFra=new MineFra();

        myContactFra.setContactsMap(getContacts());
        //*********************************
        bar.setMode(BottomNavigationBar.MODE_FIXED);
        bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bar.addItem(new BottomNavigationItem(R.mipmap.session,"会话").setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.friends,"通讯录").setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.mine,"我的").setActiveColor(R.color.orange))
                .setFirstSelectedPosition(0).initialise();


    }

    @Override
    public void initViews() {

    }

    @Override
    public void setUpView() {

    }

    private void addOrShow(Fragment fr,FragmentTransaction ft){
        if(currentFra==fr){
            return;
        }
        if(fr.isAdded()){
            ft.hide(currentFra).show(fr).commit();

        }else {
            ft.add(R.id.main_switch,fr).hide(currentFra).commit();
        }
        currentFra=fr;

    }

    @Override
    public void onTabSelected(int position) {
        switch(position){
            case 0:
                if(myConversionFra==null){
                    myConversionFra=new MyConversionFra();
                }
                FragmentTransaction ft01= fragmentManager.beginTransaction();
                addOrShow(myConversionFra,ft01);


            break;
            case 1:
                if(myContactFra==null){
                    myContactFra=new MyContactFra();

                }
                FragmentTransaction ft02= fragmentManager.beginTransaction();
                addOrShow(myContactFra,ft02);

                myContactFra.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
                    @Override
                    public void onListItemClicked(EaseUser user) {
                        startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
                       // Toast.makeText(MainActivity.this, "跳转到聊天界面", Toast.LENGTH_SHORT).show();

                    }
                });
                break;
            case 2:
                if(mineFra==null){
                    mineFra=new MineFra();

                }
                FragmentTransaction ft03= fragmentManager.beginTransaction();
                addOrShow(mineFra,ft03);
                break;
            default:
            break;


        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * prepared users, password is "123456"
     * you can use these user to test
     * @return
     */
    private Map<String, EaseUser> getContacts(){
        final Map<String, EaseUser> contacts = new HashMap<String, EaseUser>();
     /*   for(int i = 1; i <= 10; i++){
            EaseUser user = new EaseUser("easeuitest" + i);
            contacts.put("easeuitest" + i, user);
        }*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    if(usernames.size()>0){
                        for (String name:usernames) {
                            EaseUser easeUser=new EaseUser(name);
                            contacts.put(name,easeUser);
                        }
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.i("MySml","获取联系人错误"+e.getMessage());
                }
            }
        }).start();

        return contacts;
    }
}
