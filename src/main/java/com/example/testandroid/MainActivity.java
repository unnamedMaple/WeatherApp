package com.example.testandroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testandroid.fragment.CityFrag;
import com.example.testandroid.fragment.DateFrag;
import com.example.testandroid.fragment.myPlansFrag;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Button hm;
    private Button pl;
    private Button da;

    private Button qu;



    private myPlansFrag plans;

    private DateFrag date;
    private CityFrag city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hm = (Button)findViewById(R.id.homeB);
        hm.setOnClickListener(new MainButtonListener());

        pl = (Button)findViewById(R.id.planB);
        pl.setOnClickListener(new MainButtonListener());

        da = (Button)findViewById(R.id.dateB);
        da.setOnClickListener(new MainButtonListener());




        qu = (Button)findViewById(R.id.quitB);
        qu.setOnClickListener(new MainButtonListener());


        FragmentManager fm1=getFragmentManager();
        FragmentTransaction transaction1=fm1.beginTransaction();
        city = new CityFrag();
        SharedPreferences share = getSharedPreferences("account",Context.MODE_PRIVATE);
        int i = share.getInt("i",0);
        String defaultCity = share.getString("defaultCity","");
        String defaultCityCode = share.getString("defaultCityCode","");
        Bundle bundle = new Bundle();
        bundle.putString("defaultCity",defaultCity);
        bundle.putString("defaultCityCode",defaultCityCode);
        city.setArguments(bundle);
        transaction1.replace(R.id.fragment,city);
        transaction1.commit();





    }



    public class MainButtonListener  implements View.OnClickListener {
        public void onClick(View view) {
            FragmentManager fm1=getFragmentManager();
            FragmentTransaction transaction1=fm1.beginTransaction();

            switch(view.getId()){
                case R.id.homeB:
                    Log.v("Main","homeB");
                    if(city == null)
                    {
                        city = new CityFrag();
                    }

                    SharedPreferences share = getSharedPreferences("account",Context.MODE_PRIVATE);
                    int i = share.getInt("i",0);
                    String defaultCity = share.getString("defaultCity","");
                    String defaultCityCode = share.getString("defaultCityCode","");
                    Bundle bundle = new Bundle();
                    bundle.putString("defaultCity",defaultCity);
                    bundle.putString("defaultCityCode",defaultCityCode);
                    city.setArguments(bundle);
                    transaction1.replace(R.id.fragment,city);
                    transaction1.commit();
                    break;
                case R.id.planB:
                    Log.v("Main","planB");
                    if(plans == null){
                        plans = new myPlansFrag();
                    }

                    transaction1.replace(R.id.fragment,plans);
                    transaction1.commit();
                    break;
                case R.id.dateB:
                    Log.v("Main","dateB");
                    if(date == null)
                    {
                        date = new DateFrag();
                    }
                    transaction1.replace(R.id.fragment,new DateFrag());
                    transaction1.commit();
                    break;
                default:
                    Log.v("Main","quitB");



                    Toast.makeText(MainActivity.this,"bye",Toast.LENGTH_SHORT);
                    finish();
                    break;
            }


        }
    }

    public  void send(String message){

        try {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("smsto:"));
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", 123);
            smsIntent.putExtra("sms_body", message);
            startActivity(smsIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
