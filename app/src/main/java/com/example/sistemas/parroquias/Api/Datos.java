package com.example.sistemas.parroquias.Api;

import com.example.sistemas.parroquias.Models.Parroquias;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sistemas on 31/10/17.
 */

public interface Datos {
    @GET("uzy7-cgii.json")
    Call<List<Parroquias>> obtenerListaParroquia();

}
