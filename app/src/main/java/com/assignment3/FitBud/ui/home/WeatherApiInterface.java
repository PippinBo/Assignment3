package com.assignment3.fitbud.ui.home;

import retrofit2.Call;
import retrofit2.http.GET;

//  https://api.openweathermap.org/data/2.5/weather?lat=-37.840935&lon=144.946457&appid=0891ee5dc748292618ab5b5fc73361bf

//"@string/weather_api_key"

public interface WeatherApiInterface {
    @GET("weather?lat=-37.840935&lon=144.946457&appid=0891ee5dc748292618ab5b5fc73361bf")
    Call<Root> getWeather();
}
