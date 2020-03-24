package com.example.retrofit_coroutines

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    lateinit var result: TextView
    lateinit var insert: EditText
    lateinit var converti: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insert = findViewById(R.id.insert)
        result = findViewById(R.id.string)
        converti = findViewById(R.id.button)

        val converterDetailViewModel: ConvertDetailViewModel =
            ViewModelProviders.of(this).get(ConvertDetailViewModel::class.java)

        converti.setOnClickListener {
            converterDetailViewModel.sendEvent(ConvertDetailEvent.Load(insert.text.toString()))
        }

        converterDetailViewModel.state.observe(this, Observer { state ->

            when (state) {
                is ConvertDetailState.InProgress -> showProgress()
                is ConvertDetailState.Error -> {
                    showError()
                }
                is ConvertDetailState.Success -> {
                    result.text = state.newString
                }
            }
        })
    }

    private fun showProgress() {
        result.text = "waiting...."
    }

    private fun showError() {
        result.text = "error"
    }
}
