package fd.firad.geniuszone.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fd.firad.geniuszone.R
import fd.firad.geniuszone.databinding.ActivityMainBinding
import fd.firad.geniuszone.repository.QuizRepository
import fd.firad.geniuszone.utils.Utility
import fd.firad.geniuszone.viewmodel.QuizViewFactory
import fd.firad.geniuszone.viewmodel.QuizViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: QuizViewModel
    private lateinit var factory: QuizViewFactory
    private lateinit var quizArrayList: ArrayList<fd.firad.geniuszone.model.Result>
    private lateinit var selectedAns: String
    private lateinit var modelQuiz: fd.firad.geniuszone.model.Result
    private lateinit var dialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        dialog = ProgressDialog(this@MainActivity).apply {
            setTitle("Loading")
            setMessage("Please wait a few seconds")
            setCancelable(false)
        }
        dialog.show()
        quizArrayList = ArrayList()
        selectedAns = ""

        val isOnline = Utility.isOnline(this@MainActivity)

        if (isOnline) {
            factory = QuizViewFactory(QuizRepository())
            viewModel = ViewModelProvider(this@MainActivity, factory)[QuizViewModel::class.java]

            viewModel.quotes.observe(this@MainActivity, Observer {
                quizArrayList.addAll(it.results)
                shuffleArray()
            })
            binding.score.text = "Score = ${viewModel.score}"

            binding.cardOption1.setOnClickListener {
                selectedAns = binding.option1.text.toString()
                binding.option1Check.visibility = View.VISIBLE
                binding.option2Check.visibility = View.INVISIBLE
                binding.option3Check.visibility = View.INVISIBLE
                binding.option4Check.visibility = View.INVISIBLE
            }
            binding.cardOption2.setOnClickListener {
                selectedAns = binding.option2.text.toString()
                binding.option2Check.visibility = View.VISIBLE
                binding.option1Check.visibility = View.INVISIBLE
                binding.option3Check.visibility = View.INVISIBLE
                binding.option4Check.visibility = View.INVISIBLE
            }
            binding.cardOption3.setOnClickListener {
                selectedAns = binding.option3.text.toString()
                binding.option3Check.visibility = View.VISIBLE
                binding.option1Check.visibility = View.INVISIBLE
                binding.option2Check.visibility = View.INVISIBLE
                binding.option4Check.visibility = View.INVISIBLE
            }
            binding.cardOption4.setOnClickListener {
                selectedAns = binding.option4.text.toString()
                binding.option4Check.visibility = View.VISIBLE
                binding.option1Check.visibility = View.INVISIBLE
                binding.option2Check.visibility = View.INVISIBLE
                binding.option3Check.visibility = View.INVISIBLE
            }

            binding.next.setOnClickListener {
                checkAnswer()
            }
        } else {
            Toast.makeText(
                this@MainActivity,
                "Please Turn On Your Internet Connection",
                Toast.LENGTH_SHORT
            ).show()
            binding.root.visibility = View.INVISIBLE
        }

    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer() {
        if (selectedAns == "") {
            Toast.makeText(this@MainActivity, "select answer", Toast.LENGTH_SHORT).show()
        } else {
            if (selectedAns == modelQuiz.correct_answer) {
                binding.option1Check.visibility = View.INVISIBLE
                binding.option2Check.visibility = View.INVISIBLE
                binding.option3Check.visibility = View.INVISIBLE
                binding.option4Check.visibility = View.INVISIBLE
                viewModel.score++
                binding.score.text = "Score = ${viewModel.score}"
                viewModel.index++
                selectedAns = ""
                modelQuiz = quizArrayList[viewModel.index]
                setAllData()

            } else {
                binding.option1Check.visibility = View.INVISIBLE
                binding.option2Check.visibility = View.INVISIBLE
                binding.option3Check.visibility = View.INVISIBLE
                binding.option4Check.visibility = View.INVISIBLE
                binding.score.text = "Score = ${viewModel.score}"
                viewModel.index++
                selectedAns = ""
                modelQuiz = quizArrayList[viewModel.index]
                setAllData()

            }
        }
    }

    private fun shuffleArray() {
//        quizArrayList.shuffle()
        modelQuiz = quizArrayList[viewModel.index]
        setAllData()
        dialog.dismiss()
    }

    private fun setAllData() {
        binding.question.text = modelQuiz.question
        when ((1..4).random()) {
            1 -> {
                binding.option1.text = modelQuiz.correct_answer
                binding.option2.text = modelQuiz.incorrect_answers[0]
                binding.option3.text = modelQuiz.incorrect_answers[1]
                binding.option4.text = modelQuiz.incorrect_answers[2]
            }
            2 -> {
                binding.option2.text = modelQuiz.correct_answer
                binding.option1.text = modelQuiz.incorrect_answers[0]
                binding.option3.text = modelQuiz.incorrect_answers[1]
                binding.option4.text = modelQuiz.incorrect_answers[2]

            }
            3 -> {
                binding.option3.text = modelQuiz.correct_answer
                binding.option2.text = modelQuiz.incorrect_answers[0]
                binding.option1.text = modelQuiz.incorrect_answers[1]
                binding.option4.text = modelQuiz.incorrect_answers[2]

            }
            4 -> {
                binding.option4.text = modelQuiz.correct_answer
                binding.option2.text = modelQuiz.incorrect_answers[0]
                binding.option3.text = modelQuiz.incorrect_answers[1]
                binding.option1.text = modelQuiz.incorrect_answers[2]
            }
        }

    }
}