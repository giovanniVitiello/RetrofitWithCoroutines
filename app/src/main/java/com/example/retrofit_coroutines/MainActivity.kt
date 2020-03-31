package com.example.retrofit_coroutines

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    lateinit var resultEuro: TextView
    lateinit var resultSterlina: TextView
    lateinit var insert: EditText
    lateinit var converti: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insert = findViewById(R.id.insert)
        resultEuro = findViewById(R.id.euro)
        resultSterlina = findViewById(R.id.sterlina)
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
                    showConversions(state.money)
                }
            }
        })
    }

    private fun showProgress() {
        resultEuro.text = "waiting...."
        resultSterlina.text = "waiting...."
    }

    private fun showError() {
        resultEuro.text = "error"
        resultSterlina.text = "error"
    }

    private fun showConversions(moneyUtil: MoneyUtil) {
        val valueToDouble = insert.text.toString().toDouble()
        resultEuro.text = valueToDouble.let { moneyUtil.rates?.eUR?.times(it).toString() }
        resultSterlina.text = valueToDouble.let { moneyUtil.rates?.gBP?.times(it).toString() }
    }
}
