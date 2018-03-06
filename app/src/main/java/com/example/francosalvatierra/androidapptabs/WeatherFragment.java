package com.example.francosalvatierra.androidapptabs;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.francosalvatierra.androidapptabs.DataModel.WeatherContract;
import com.example.francosalvatierra.androidapptabs.DataModel.WeatherDbHelper;
import com.example.francosalvatierra.androidapptabs.Entities.WeatherData;
import com.example.francosalvatierra.androidapptabs.Utils.AsynkConnector;
import com.example.francosalvatierra.androidapptabs.Utils.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherFragment extends Fragment {
    BottomNavigationView mButtonNav;

    WeatherData data;
    ArrayList<WeatherData> lista = new ArrayList<WeatherData>();

    TabLayout miTab;

    String result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        //TODO: Consultar si estoy en este tab efectivamente.
        //Si estoy, entonces hago esto

        //getDataFromDB(v);

        //miTab = this.getActivity().findViewById(R.id.tabs);

    /*if(WeatherFragment.this.getUserVisibleHint())
    {*/
        if(GuardarHora()){
            getDataFromService();
        }else{
            getDataFromDB(v);
        }
    //}

        //Si no, no hago nada de esto.

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_weather, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getDataFromDB(View v) {

        WeatherDbHelper conn = new WeatherDbHelper(this.getContext(), "WeatherDB", null, WeatherDbHelper.DATABASE_VERSION);

        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Weather", null);

        if(c.moveToLast())
        {
            do{
                data = new WeatherData(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));
                lista.add(data);
            }while(c.moveToNext());
        }

        //this.getActivity().setContentView(R.layout.fragment_weather);

        TextView ciudadTv = (TextView)v.findViewById(R.id.weather_name);
        ciudadTv.setText(lista.get(0).getCiudad());

        TextView descTv = (TextView)v.findViewById(R.id.weather_desc);
        descTv.setText(lista.get(0).getDesc());

        TextView tempTv = (TextView)v.findViewById(R.id.weather_temp);
        tempTv.setText(lista.get(0).getTemp());

        TextView minTv = (TextView)v.findViewById(R.id.weather_tempmin);
        minTv.setText(lista.get(0).getMin());

        TextView maxTv = (TextView)v.findViewById(R.id.weather_tempmax);
        maxTv.setText(lista.get(0).getMax());

        TextView humedadTv = (TextView)v.findViewById(R.id.weather_humidity);
        humedadTv.setText(lista.get(0).getHumedad());


        /*this.getActivity().setContentView(R.layout.activity_main);
        mButtonNav = (BottomNavigationView)this.getActivity().findViewById(R.id.navigation);*/
    }

    public void getDataFromService()
    {
        String content = "{}";

        String param = "?id=3435910";

        AsynkConnector c = new AsynkConnector(AsynkConnector.WEATHER, param, content, new Callback(){
            private int dots = 0;

            @Override
            public void starting() {

            }

            @Override
            public void completed(String res, int responseCode) {
                if(responseCode == 200)
                {
                    parseResponseJSON(res);
                }
            }

            @Override
            public void completedWithErrors(Exception e) {

            }

            @Override
            public void update() {

            }
        });
        c.execute();
    }

    private void parseResponseJSON(String res)
    {
        try
        {
            System.out.println(res);

            JSONObject reader = new JSONObject(res);

            JSONObject d = reader.getJSONObject("coord");

            d = reader.getJSONObject("main");

            TextView name = (TextView)this.getActivity().findViewById(R.id.weather_name);
            name.setText(reader.getString("name"));

            TextView temp = (TextView)this.getActivity().findViewById(R.id.weather_temp);
            temp.setText(d.getString("temp"));

            TextView tempmin = (TextView)this.getActivity().findViewById(R.id.weather_tempmin);
            tempmin.setText(d.getString("temp_min"));

            TextView tempmax = (TextView)this.getActivity().findViewById(R.id.weather_tempmax);
            tempmax.setText(d.getString("temp_max"));

            TextView humidity = (TextView)this.getActivity().findViewById(R.id.weather_humidity);
            humidity.setText(d.getString("humidity"));

            JSONArray w = reader.getJSONArray("weather");

            TextView desc = (TextView)this.getActivity().findViewById(R.id.weather_desc);
            desc.setText(w.getJSONObject(0).getString("description"));

            GuardarHora();

            WeatherDbHelper conn = new WeatherDbHelper(this.getContext(), "WeatherDB", null, WeatherDbHelper.DATABASE_VERSION);

            SQLiteDatabase db = conn.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(WeatherContract.WeatherEntry.CAMPO_CIUDAD, reader.getString("name"));
            values.put(WeatherContract.WeatherEntry.CAMPO_DESC, w.getJSONObject(0).getString("description"));
            values.put(WeatherContract.WeatherEntry.CAMPO_HUMEDAD, d.getString("humidity"));
            values.put(WeatherContract.WeatherEntry.CAMPO_TEMP, d.getString("temp"));
            values.put(WeatherContract.WeatherEntry.CAMPO_TEMPMIN, d.getString("temp_min"));
            values.put(WeatherContract.WeatherEntry.CAMPO_TEMPMAX, d.getString("temp_max"));

            Long idResultante = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, WeatherContract.WeatherEntry._ID, values);

            Toast.makeText(this.getActivity().getApplicationContext(), "Id Registro: "+ idResultante, Toast.LENGTH_SHORT).show();

        }catch (JSONException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public boolean GuardarHora()
    {
        int delta = 30 *60*1000;
        long currentTime = System.currentTimeMillis();

        if(currentTime-MainActivity.lastUpdateTime >= delta){
            MainActivity.lastUpdateTime=currentTime;
            return true;
        }
        return false;

    }
}
