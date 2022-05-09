package com.example.assignment3.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment3.R;
import com.example.assignment3.databinding.FragmentHomeBinding;

import java.util.Calendar;

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
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                Root root = response.body();
                assert root != null;
                double temp = root.getMain().getTemp() - 273.15;
                String weather = root.getWeather().get(0).getDescription();
                int weatherId = root.getWeather().get(0).getId();
                int temps = (int) temp;
                binding.tempTextView.setText(String.valueOf((int)temp + "°C"));
                binding.tempDesc.setText(weather);
                Calendar calendar = Calendar.getInstance();
                int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
                if(weatherId == 800 && (hour24hrs < 6 || hour24hrs > 20)) {
                    binding.weatherIcon.setImageResource(R.drawable.ic_clear_night);
                }
                else if (weatherId == 800 && (hour24hrs > 6 && hour24hrs <20)){
                    binding.weatherIcon.setImageResource(R.drawable.ic_clear_day);
                }
                else if(weatherId >= 200 && weatherId <= 232){
                    binding.weatherIcon.setImageResource(R.drawable.thunderstorms);
                }
                else if(weatherId >= 600 && weatherId <= 622){
                    binding.weatherIcon.setImageResource(R.drawable.ic_snow);
                }
                else if(weatherId >= 300 && weatherId <= 321){
                    binding.weatherIcon.setImageResource(R.drawable.ic_drizzle);
                }
                else if(weatherId >= 701 && weatherId <= 781){
                    binding.weatherIcon.setImageResource(R.drawable.ic_haze);
                 }
                else if(weatherId >= 801 && weatherId <= 804){
                    binding.weatherIcon.setImageResource(R.drawable.ic_cloudy);
                }
                else if(weatherId >= 500 && weatherId <= 531){
                    binding.weatherIcon.setImageResource(R.drawable.ic_rain);
                }

                if(hour24hrs < 6 || hour24hrs > 20) {
                    binding.tempSuggestions.setText(R.string.weather_night);
                }
                else if (temps > 10 && temps < 30  && weatherId == 800 || (weatherId >= 801 && weatherId <= 804)) {
                    binding.tempSuggestions.setText(R.string.weather_good);
                }
                else if(
                        temps < 9 || temps > 31 ||
                                ((weatherId >= 600 && weatherId <= 622) ||           //snow
                                        (weatherId >= 500 && weatherId <= 531) ||    //rain
                                        (weatherId >= 300 && weatherId <= 321) ||    //drizzle
                                        (weatherId >= 200 && weatherId <= 232) ||    //thunderstorms
                                        (weatherId >= 701 && weatherId <= 781)       //haze
                                ))
                { binding.tempSuggestions.setText(R.string.weathet_bad); }
            }

            @Override
            public void onFailure(@NonNull Call<Root> call, Throwable t) {
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