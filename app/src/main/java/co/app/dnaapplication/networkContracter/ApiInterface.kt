package co.app.dnaapplication.networkContracter

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("search?")
    fun searchDnaList(
        @Query("q") q: String?
    ): Call<JsonObject>




}
