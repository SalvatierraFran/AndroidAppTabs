package com.example.francosalvatierra.androidapptabs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.francosalvatierra.androidapptabs.DataModel.WeatherAdapter;
import com.example.francosalvatierra.androidapptabs.DataModel.WeatherDbHelper;
import com.example.francosalvatierra.androidapptabs.Entities.WeatherData;

import java.util.ArrayList;

public class DbFragment extends Fragment {
    ArrayList<WeatherData> lista = new ArrayList<WeatherData>();

    WeatherData data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //this.getActivity().setContentView(R.layout.fragment_db);

        View v = inflater.inflate(R.layout.fragment_db, container, false);

        lista.removeAll(lista);

//        li = (ListView)this.getActivity().findViewById(R.id.bd_lv);

        ListView listview = (ListView) v.findViewById(R.id.bd_lv);

        WeatherDbHelper conn = new WeatherDbHelper(this.getContext(), "WeatherDB", null, WeatherDbHelper.DATABASE_VERSION);

        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Weather", null);

        if (c.moveToFirst()) {
            do {
                data = new WeatherData(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));
                lista.add(data);
            } while (c.moveToNext());
        }

        WeatherAdapter adap = new WeatherAdapter(this.getContext(), R.layout.row_layout, lista);
        listview.setAdapter(adap);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id = lista.get(position).getId();
                Intent myDataIntent = new Intent(getActivity(), WeatherDataActivity.class);
                myDataIntent.putExtra("Datos", id);
                startActivity(myDataIntent);
            }
        });

        //return inflater.inflate(R.layout.fragment_db, container, false);
        return v;
    }
}
