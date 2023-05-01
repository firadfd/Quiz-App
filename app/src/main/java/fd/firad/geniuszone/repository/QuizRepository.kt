package fd.firad.geniuszone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fd.firad.geniuszone.api.ApiService.apiService
import fd.firad.geniuszone.model.MainResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizRepository {
    private val _quiz = MutableLiveData<MainResponse>()

    val quiz: LiveData<MainResponse>
        get() = _quiz

    fun getQuiz(int: Int, type: String) {
        apiService.getQuiz(int, type).enqueue(object : Callback<MainResponse?> {
            override fun onResponse(call: Call<MainResponse?>, response: Response<MainResponse?>) {
                _quiz.postValue(response.body())
            }

            override fun onFailure(call: Call<MainResponse?>, t: Throwable) {

            }
        })
    }
}