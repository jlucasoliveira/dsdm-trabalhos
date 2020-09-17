package br.ufc.quixada.dsdm.trabalho3.network;

public class API {

    public static final String API_URL = "http://192.168.0.11:3000/api/v1/";

    public static CarroService getCarroService() {
        return RetrofitClient.getInstance(API_URL).create(CarroService.class);
    }

}
