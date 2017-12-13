package com.wallet.zorro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class NavActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_welcome:
                    WelcomeActivity welcomeFragment=new WelcomeActivity();
                    FragmentTransaction welcomeTrans=getSupportFragmentManager().beginTransaction();
                    welcomeTrans.replace(R.id.content,welcomeFragment,"FragmentName");
                    welcomeTrans.commit();
                    return true;
                case R.id.navigation_addMoney:

                    AddMoneyActivity addMoneyFragment=new AddMoneyActivity();
                    FragmentTransaction addMoneyTrans=getSupportFragmentManager().beginTransaction();
                    addMoneyTrans.replace(R.id.content,addMoneyFragment,"FragmentName");
                    addMoneyTrans.commit();

                    return true;
                case R.id.navigation_sendMoney:
                    SendMoneyActivity sendMoneyFragment=new SendMoneyActivity();
                    FragmentTransaction sendMoneyTrans=getSupportFragmentManager().beginTransaction();
                    sendMoneyTrans.replace(R.id.content,sendMoneyFragment,"FragmentName");
                    sendMoneyTrans.commit();
                    return true;
                case R.id.navigation_passBook:
                    PassBookActivity passBookFragment=new PassBookActivity();
                    FragmentTransaction passBookTrans=getSupportFragmentManager().beginTransaction();
                    passBookTrans.replace(R.id.content,passBookFragment,"FragmentName");
                    passBookTrans.commit();
                    return  true;

            }

            return false;

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        WelcomeActivity welcomeFragment=new WelcomeActivity();
        FragmentTransaction welcomeTrans=getSupportFragmentManager().beginTransaction();
        welcomeTrans.replace(R.id.content,welcomeFragment,"FragmentName");
        welcomeTrans.commit();
    }


}
