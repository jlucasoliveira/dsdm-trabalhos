package br.ufc.quixada.dsdm.trabalho3.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit instance;

    public static Retrofit getInstance(String url) {
        if (instance == null)
            instance = new Retrofit.Builder().baseUrl(url)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
        return instance;
    }

}
