package com.example.francosalvatierra.androidapptabs.Entities;

/**
 * Created by franco.salvatierra on 02/03/2018.
 */

public class WeatherData {
    private Integer id;
    private String ciudad;
    private String desc;
    private String temp;
    private String min;
    private String max;
    private String humedad;

    public WeatherData(Integer Id, String Ciudad, String Desc, String Temp, String Min, String Max, String Humedad)
    {
        this.id = Id;
        this.ciudad = Ciudad;
        this.desc = Desc;
        this.temp = Temp;
        this.min = Min;
        this.max = Max;
        this.humedad = Humedad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
