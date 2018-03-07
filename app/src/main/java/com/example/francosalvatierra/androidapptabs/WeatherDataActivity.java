package com.example.francosalvatierra.androidapptabs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.francosalvatierra.androidapptabs.DataModel.WeatherDbHelper;
import com.example.francosalvatierra.androidapptabs.Entities.WeatherData;

import org.w3c.dom.Text;

public class WeatherDataActivity extends AppCompatActivity {

    WeatherData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_data);



        Bundle bundle = getIntent().getExtras();

        Long i = (Long)bundle.get("Datos");

        WeatherDbHelper conn = new WeatherDbHelper(this, "WeatherDB", null, WeatherDbHelper.DATABASE_VERSION);

        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Weather WHERE _id = " + i, null);

        if(c.moveToFirst())
        {
            data = new WeatherData(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));
        }

        TextView ciudad_tv = (TextView)findViewById(R.id.weather_name);
        ciudad_tv.setText(data.getCiudad());

        TextView desc_tv = (TextView)findViewById(R.id.weather_desc);
        desc_tv.setText(data.getDesc());

        TextView temp_tv = (TextView)findViewById(R.id.weather_temp);
        temp_tv.setText(data.getTemp());

        TextView min_tv = (TextView)findViewById(R.id.weather_tempmin);
        min_tv.setText(data.getMin());

        TextView max_tv = (TextView)findViewById(R.id.weather_tempmax);
        max_tv.setText(data.getMax());

        TextView humidity_tv = (TextView)findViewById(R.id.weather_humidity);
        humidity_tv.setText(data.getHumedad());
    }
}
