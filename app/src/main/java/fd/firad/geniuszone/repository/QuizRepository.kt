package fd.firad.geniuszone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fd.firad.geniuszone.api.ApiInterface
import fd.firad.geniuszone.model.MainResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class QuizRepository @Inject constructor(private val apiInterface: ApiInterface) {
    private val _quiz = MutableLiveData<MainResponse>()

    val quiz: LiveData<MainResponse>
        get() = _quiz

    fun getQuiz(int: Int, type: String) {
        apiInterface.getQuiz(int, type).enqueue(object : Callback<MainResponse?> {
            override fun onResponse(call: Call<MainResponse?>, response: Response<MainResponse?>) {
                _quiz.postValue(response.body())
            }

            override fun onFailure(call: Call<MainResponse?>, t: Throwable) {

            }
        })
    }
}