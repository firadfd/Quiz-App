package fd.firad.geniuszone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import fd.firad.geniuszone.model.MainResponse
import fd.firad.geniuszone.repository.QuizRepository

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    init {
        repository.getQuiz(100, "multiple")
    }

    val quotes: LiveData<MainResponse>
        get() = repository.quiz

    var index = 0
    var score = 0
}