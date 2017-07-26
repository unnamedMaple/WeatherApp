package com.example.testandroid.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import com.example.testandroid.MainActivity;
import com.example.testandroid.R;
import com.example.testandroid.bean.Weather;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by ASUS on 2017/7/24.
 */

public class CityFrag extends Fragment {

    private View rootview;


    private Button click;

    private String[] province = new String[] {"直辖市", "黑龙江","云南","安徽"};
    private String[] city = new String[]{"北京","上海","天津","重庆"};
    private String[][] pandc = new String[][]{{"北京","上海","天津","重庆"},{"哈尔滨","大庆","齐齐哈尔","鹤岗"},{"昆明","玉溪","曲靖","大理"},{"合肥","太湖","铜陵","凤台"}};
    private String[][] cityCode = new String[][]{{"WX4EQ2XJD7V2","WTW3SJ5ZBJUY","WWGQDCW6TBW1","WM7B0X53DZW2"},{"YB1UX38K6DY1","YB2BVPSH4JM5","YB2JK81THR6Z","YBKTK25TPP2R"},{"WK3N92NQV6RQ","WK2FT8F9H0YR","WK9C440U729C","WHX38U36B3BU"},{"WTEMH46Z5N09","WT6TJG95X75S","WT7ZCN9UKCZS","WTG400BGM1U4"}};
    private Spinner sp;
    private Spinner sp2;







    ArrayAdapter<String> adapter ;

    ArrayAdapter<String> adapter2;

    private int p = 0;
    private int c =0;
    private String selectedCity = "北京";
    private String selectedCityCode;
    private String defaultCity;
    private String defaultCityCode;



    private Button refresh;
    private Button share;
    private TextView cityView;
    private TextView todayT;
    private TextView todayS;
    private TextView todayWD;

    private TextView todayUV;
    private TextView todayOutAdvice;
    private TextView todayEatAdvice;

    private TextView tomorrowWeather;
    private TextView thirdWeather;


    private Weather today;
    private Weather tomorrow;
    private Weather third;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("frag","onCreateView noneplan");
        rootview = inflater.inflate(R.layout.citytest,container, false);
        return rootview;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        defaultCity = getArguments().getString("defaultCity");
        defaultCityCode = getArguments().getString("defaultCityCode");
        selectedCityCode = defaultCityCode;
        selectedCity = defaultCity;
        refresh = (Button)rootview.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeather(selectedCityCode);
            }
        });

        click = (Button)rootview.findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(rootview.getContext());//第一个模态框：选择城市

                builder.setTitle("请选择城市");
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View viewone = LayoutInflater.from(rootview.getContext()).inflate(R.layout.city_select, null);


                //设置adapter
                adapter = new ArrayAdapter<String>(rootview.getContext(),android.R.layout.simple_spinner_item, province);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp = (Spinner) viewone.findViewById(R.id.province);
                sp.setAdapter(adapter);
                sp.setOnItemSelectedListener(selectListener);

                adapter2 = new ArrayAdapter<String>(rootview.getContext(),android.R.layout.simple_spinner_item, city);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp2 = (Spinner) viewone.findViewById(R.id.city);
                sp2.setAdapter(adapter2);


                //设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(viewone);


                //确定完成，设置选择的城市，弹出第二个模态框
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {


                        //第二个模态框：是否设为默认城市
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(rootview.getContext());


                        //    设置Title的内容
                        builder2.setTitle("默认城市:"+defaultCity);
                        //    设置Content来显示一个信息
                        builder2.setMessage("是否设为默认城市");


                        //是，更改SharedPreferences
                        builder2.setPositiveButton("是", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                defaultCity = selectedCity;
                                defaultCityCode = selectedCityCode;
                                SharedPreferences SP = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);

                                final SharedPreferences.Editor editor = SP.edit();
                                editor.putString("defaultCity",defaultCity);
                                editor.putString("defaultCityCode",defaultCityCode);
                                editor.commit();


                                Toast.makeText(rootview.getContext(), "当前默认城市: " + defaultCity, Toast.LENGTH_SHORT).show();
                            }
                        });
                        //    设置一个NegativeButton
                        builder2.setNegativeButton("否", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                            }
                        });

                        Toast.makeText(rootview.getContext(), "城市: " +selectedCity, Toast.LENGTH_SHORT).show();
                        getWeather(selectedCityCode);
                        builder2.show();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });


                builder.show();
            }
        });


        bindUI();
        today = new Weather();
        tomorrow = new Weather();
        third = new Weather();
        getWeather(defaultCityCode);

        share = (Button)rootview.findViewById(R.id.shareB);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "亲爱的朋友，今天"+selectedCity+"温度是"+today.getTemperature()+"，给您的出行建议是:"+today.getOutAdvice();
                MainActivity m = (MainActivity)getActivity();
                m.send(message);
            }
        });


    }

    //设置一级下拉框监听器
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener(){
        public void onItemSelected(AdapterView parent, View v, int position, long id){
            int pos = sp.getSelectedItemPosition();
            p = pos;
            adapter2 = new ArrayAdapter<String>(rootview.getContext(),android.R.layout.simple_spinner_item, pandc[pos]);
            sp2.setAdapter(adapter2);
            sp2.setOnItemSelectedListener(selectcity);
        }

        public void onNothingSelected(AdapterView arg0){

        }

    };


    //设置二级下拉框监听器
    private AdapterView.OnItemSelectedListener selectcity = new AdapterView.OnItemSelectedListener(){
        public void onItemSelected(AdapterView parent, View v, int position, long id){
            int pos = sp2.getSelectedItemPosition();
            c = pos;
            selectedCity = pandc[p][c];
            selectedCityCode = cityCode[p][c];
        }

        public void onNothingSelected(AdapterView arg0){

        }

    };


    private void bindUI(){
        refresh = (Button)rootview.findViewById(R.id.refresh);
        cityView = (TextView)rootview.findViewById(R.id.cityname);
        todayT = (TextView)rootview.findViewById(R.id.todaytemperature);
        todayS = (TextView)rootview.findViewById(R.id.todaystatus);
        todayWD = (TextView)rootview.findViewById(R.id.todaywinddir);

        todayUV = (TextView)rootview.findViewById(R.id.todayUV);
        todayOutAdvice = (TextView)rootview.findViewById(R.id.outAdvice);
        todayEatAdvice = (TextView)rootview.findViewById(R.id.eatingAdvice);

        tomorrowWeather = (TextView)rootview.findViewById(R.id.tomorrowW);
        thirdWeather = (TextView)rootview.findViewById(R.id.thirdW);
    }



    private void getWeather(String citycode){
        ConnectHttp http= new ConnectHttp();
        String[] URLS={"",""};
        String APIone = "https://api.seniverse.com/v3/weather/daily.json?key=ggai7sz9xb7gxkje&location=";
        String Formatone="&language=zh-Hans&unit=c&start=0&days=5";
        String APItwo = "https://api.seniverse.com/v3/life/suggestion.json?key=ggai7sz9xb7gxkje&location=";
        String Formattwo="&language=zh-Hans";
        URLS[0] = APIone+citycode+Formatone;
        Log.v("URL1",URLS[0]);
        Log.v("URL2",URLS[1]);
        URLS[1] = APItwo+citycode+Formattwo;
        http.execute(URLS);
    }



    private void update(){

        cityView.setText(selectedCity);
        todayT.setText(today.getTemperature()+"C");
        todayS.setText("质量:"+today.getQuality());
        todayWD.setText("风:"+today.getWind_direction()+" "+today.getGetWind_direction_degree());

        todayUV.setText("紫外线:"+today.getUV());
        todayOutAdvice.setText(today.getOutAdvice());


        todayEatAdvice.setText(today.getEatingAdvice());
        Log.v("advice",today.getOutAdvice());
        Log.v("advice",today.getEatingAdvice());
        String TW = tomorrow.getTemperature()+"C\n"+tomorrow.getQuality();
        tomorrowWeather.setText(TW);
        String thW = third.getTemperature()+"C\n"+third.getQuality();
        thirdWeather.setText(thW);

    }







    private class ConnectHttp extends AsyncTask<String,Object,String[]> {

        protected void onPreExcecute(){
            super.onPreExecute();
        }

        private String downloadSingleFile(String Url){
            int code;
            int length=-1;
            String respone="";
            HttpURLConnection connection=null;
            try{
                URL url=new URL(Url);
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();
                code=connection.getResponseCode();
                if(code==200)
                {
                    InputStream inputStream=connection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    byte[] buf=new byte[2048];
                    while((length=inputStream.read(buf))!=-1)
                    {
                        byteArrayOutputStream.write(buf,0,length);
                    }
                    respone=new String(byteArrayOutputStream.toByteArray(),"utf-8");
                    return respone;
                }

            }catch (Exception e)
            {
                Log.d("Exception","下载异常了");
                e.printStackTrace();
            }
            return  null;
        }



        public ConnectHttp(){


        }



        protected String[]  doInBackground(String...param) {

            String []  value={"",""};
            String result="";
            result=downloadSingleFile(param[0]);
            value[0]=result;
            result=downloadSingleFile(param[1]);
            value[1]=result;

            return value;

        }

        protected  void onPostExecute(String[] along)
        {
            super.onPostExecute(along);
            JsonWeatherdaily(along[0])  ;
            JsonLifeSuggestion(along[1]);
            update();
        }

        public void JsonWeatherdaily(String JsonString) {
            //https://api.seniverse.com/v3/weather/daily.json?key=rhkvd3q7xaxeczav&location=beijing&language=zh-Hans&unit=c&start=0&days=5
            //这个方法用来获取未来三天中某城市的城市名，天气状况，温度，风向，风力等级
            JSONTokener jsonTokener = new JSONTokener(JsonString);



            try{
                JSONObject Json=(JSONObject)jsonTokener.nextValue();
                JSONArray resultList=Json.getJSONArray("results");
                JSONObject resultJect=resultList.getJSONObject(0);
                JSONObject Location=resultJect.getJSONObject("location");
                String CityName=Location.getString("name");
                today.setCity(CityName);
                JSONArray Daily=resultJect.getJSONArray("daily");


                Log.v("today",today.getCity());

                //今天
                JSONObject Today=Daily.getJSONObject(0);
                String TWeatherStatus=Today.getString("text_day");
                String TWeatherTemperature=Today.getString("low");
                String TWeatherDirection=Today.getString("wind_direction");
                String TWeatherDegree=Today.getString("wind_direction_degree");

                today.setQuality(TWeatherStatus);

                today.setTemperature(TWeatherTemperature);

                today.setWind_direction(TWeatherDirection);
                today.setGetWind_direction_degree(TWeatherDegree);
                Log.v("today",today.getQuality());
                Log.v("today",today.getTemperature());
                Log.v("today",today.getWind_direction());
                Log.v("today",today.getGetWind_direction_degree());



                //明天
                JSONObject NextDay=Daily.getJSONObject(1);
                String NWeatherStatus=NextDay.getString("text_day");
                String NWeatherTemputer=NextDay.getString("low");
                String NWeatherDriection=NextDay.getString("wind_direction");
                String NWeatherDegree=NextDay.getString("wind_direction_degree");
                tomorrow.setQuality(NWeatherStatus);
                tomorrow.setTemperature(NWeatherTemputer);
                tomorrow.setWind_direction(NWeatherDriection);
                tomorrow.setGetWind_direction_degree(NWeatherDegree);

                //后天
                JSONObject SecondDay=Daily.getJSONObject(2);
                String SWeatherStatus=SecondDay.getString("text_day");
                String SWeatherTemputer=SecondDay.getString("low");
                String SWeatherDriection=SecondDay.getString("wind_direction");
                String SWeatherDegree=SecondDay.getString("wind_direction_degree");
                third.setQuality(SWeatherStatus);
                third.setTemperature(SWeatherTemputer);
                third.setWind_direction(SWeatherDriection);
                third.setGetWind_direction_degree(SWeatherDegree);
            }catch (Exception e)
            {
                Log.d("JSON1","杰森1异常了!  "+e);
            }



        }

        public void JsonLifeSuggestion(String JsonString)
        {
            //此方法用来获取紫外线强度，饮食建议和出行建议
            JSONTokener jsonTokener=new JSONTokener(JsonString);

            try{
                JSONObject Json=(JSONObject)jsonTokener.nextValue();
                JSONArray resultList=Json.getJSONArray("results");
                JSONObject resultJect=resultList.getJSONObject(0);

                JSONObject suggestion=resultJect.getJSONObject("suggestion");
                JSONObject beer=suggestion.getJSONObject("beer");
                String DietaryAdvice=beer.getString("details");
                JSONObject sport=suggestion.getJSONObject("sport");
                String Traveladvice=sport.getString("details");
                JSONObject UVobject=suggestion.getJSONObject("uv");
                String UV=UVobject.getString("brief");
                today.setEatingAdvice(DietaryAdvice);
                today.setOutAdvice(Traveladvice);
                today.setUV(UV);
                Log.v("today",today.getUV());

            }catch (Exception e)
            {
                Log.d("JSON2","杰森2异常了!");
            }


        }


    }






}
