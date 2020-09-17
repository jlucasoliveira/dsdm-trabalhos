package br.ufc.quixada.dsdm.trabalho3.network;

import br.ufc.quixada.dsdm.trabalho3.models.Carro;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CarroService {

    @GET("carros")
    Call<List<Carro>> list();

    @GET("carros/{id}")
    Call<Carro> retrieve(@Path("id") Integer id);

    @POST("carros")
    Call<Carro> register(@Body Carro carro);

    @PUT("carros/{id}")
    Call<Carro> update(@Path("id") Integer id, @Body Carro carro);

}
