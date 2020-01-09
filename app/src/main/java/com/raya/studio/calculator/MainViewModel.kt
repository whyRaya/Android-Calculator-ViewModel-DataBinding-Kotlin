package com.raya.studio.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.fathzer.soft.javaluator.DoubleEvaluator

/**
 * If you need a context, you can use AndroidViewModel
 * MainViewModel(application: Application) : AndroidViewModel(application)
 * */
class MainViewModel: ViewModel() {

    // Two way data binding, the value will change according to user input
    val input = MutableLiveData<String>()

    /**
     * Transform the value of MutableLiveData / LiveData
     * for every changes, calculate/evaluate the input
     *
     * When the input is wrong or or can't be evaluated, use the old value
     * **/
    val result: LiveData<String> = Transformations.map(input) {
        if (it.isEmpty())
             value = ""
        else {
            try {
                // sample "(2^3-1)*sin(pi/4)/ln(pi^2)"
                val result = DoubleEvaluator().evaluate(it)
                value = if (result % 1 == 0.0) result.toInt().toString() else result.toString()
            } catch (e: Exception) { e.printStackTrace() }
        }
        value
    }

    private var value = ""

}