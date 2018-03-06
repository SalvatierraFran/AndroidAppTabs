package com.example.francosalvatierra.androidapptabs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.francosalvatierra.androidapptabs.DataModel.WeatherDbHelper;
import com.example.francosalvatierra.androidapptabs.Entities.WeatherData;

public class WeatherDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_data);

        WeatherData data;

        Bundle bundle = getIntent().getExtras();

        long i = (int) bundle.get("Datos");

        i = i+1;

        WeatherDbHelper conn = new WeatherDbHelper(this, "WeatherDB", null, WeatherDbHelper.DATABASE_VERSION);

        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Weather WHERE _id = " + i, null);

        data = new WeatherData(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));
    }
}
