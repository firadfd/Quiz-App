package fd.firad.geniuszone.api

import fd.firad.geniuszone.model.MainResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("api.php")
    fun getQuiz(
        @Query("amount") amount: Int,
        @Query("type") type: String
    ): Call<MainResponse>
}