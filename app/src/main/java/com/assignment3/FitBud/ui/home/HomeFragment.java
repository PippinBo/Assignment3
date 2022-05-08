package com.assignment3.fitbud.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.assignment3.fitbud.R;
import com.assignment3.fitbud.databinding.FragmentHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        weatherApi();
        return view;
    }

    public void weatherApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiInterface weatherApiInterface = retrofit.create(WeatherApiInterface.class);

        Call<Root> call = weatherApiInterface.getWeather();

        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                double temp = root.getMain().getTemp() - 273.15;
                String weather = root.getWeather().get(0).getDescription();
                int weatherId = root.getWeather().get(0).getId();

                int temps = (int) temp;
                binding.tempTextView.setText(String.valueOf((int)temp + "°C"));
                binding.tempDesc.setText(weather);

                if(weatherId == 800){
                    binding.weatherIcon.setImageResource(R.drawable.flood1);
//                    wIcon.src = "https://bmcdn.nl/assets/weather-icons/v3.0/line/svg/clear-day.svg";
                }else if(weatherId >= 200 && weatherId <= 232){
                    //                   wIcon.src = "https://bmcdn.nl/assets/weather-icons/v3.0/line/svg/thunderstorms.svg";
                }else if(weatherId >= 600 && weatherId <= 622){
//                    wIcon.src = "https://bmcdn.nl/assets/weather-icons/v3.0/line/svg/snow.svg";
                }else if(weatherId >= 300 && weatherId <= 321){
//                    wIcon.src = "https://bmcdn.nl/assets/weather-icons/v3.0/line/svg/drizzle.svg";
                }else if(weatherId >= 701 && weatherId <= 781){
                    //                   wIcon.src = "https://bmcdn.nl/assets/weather-icons/v3.0/line/svg/overcast-haze.svg";
                }else if(weatherId >= 801 && weatherId <= 804){

                    binding.weatherIcon.setImageResource(R.drawable.thunderstorms);
//                    Picasso.get().load("https://github.com/basmilius/weather-icons/blob/dev/production/fill/png/512/thunderstorms.png?raw=true").into(addBinding.weatherIcon);

                    //                   wIcon.src = "https://bmcdn.nl/assets/weather-icons/v3.0/line/svg/cloudy.svg";
                }else if((weatherId >= 500 && weatherId <= 531) || (weatherId >= 300 && weatherId <= 321)){
                    //                   wIcon.src = "https://bmcdn.nl/assets/weather-icons/v3.0/line/svg/rain.svg";
                }


                if (temps == 15) {
                    binding.tempSuggestions.setText("GOOD");
                }
                else {
                    binding.tempSuggestions.setText("bad");
                }
                System.out.println();

            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}