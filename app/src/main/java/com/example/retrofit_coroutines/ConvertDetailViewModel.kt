package com.example.retrofit_coroutines

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed class ConvertDetailEvent() {
    data class Load(val newString: String) : ConvertDetailEvent()
}

sealed class ConvertDetailState() {
    object InProgress : ConvertDetailState()
    data class Error(val error: Throwable) : ConvertDetailState()
    data class Success(val money: MoneyUtil) : ConvertDetailState()
}

sealed class MoneyUtilResult {
    data class Error(val error: Throwable) : MoneyUtilResult()
    data class Success(val money: MoneyUtil) : MoneyUtilResult()
}

class ConvertDetailViewModel : ViewModel() {

    private val retrofiteService = RetrofiteService()

    private val _result = MutableLiveData<String>()

    val state: MutableLiveData<ConvertDetailState> = MutableLiveData()

    init {
        _result.value = ""
    }

    val result: LiveData<String> = _result

    fun sendEvent(event: ConvertDetailEvent) {
        when (event) {
            is ConvertDetailEvent.Load -> loadContent()
        }
    }

    private fun loadContent() {
        state.value = ConvertDetailState.InProgress

        viewModelScope.launch {
            try {
                val moneyDetail = retrofiteService.moneyDetail()
                Log.d("GifDetailViewModel", "result: $moneyDetail")
                state.value = moneyDetail?.let { ConvertDetailState.Success(it) }
            } catch (exception: Throwable) {
                state.value = ConvertDetailState.Error(exception)
            }
        }
    }
}
