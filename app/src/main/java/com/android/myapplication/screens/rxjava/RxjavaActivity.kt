package com.android.myapplication.screens.rxjava

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.R
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class RxjavaActivity : AppCompatActivity() {
    private lateinit var etRx: EditText
    private lateinit var etRx2: EditText

    private lateinit var  tvLearn:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rx_java_activity)
        etRx = findViewById(R.id.etRx)
        etRx2 = findViewById(R.id.etRx2)

        tvLearn = findViewById(R.id.tvLearn)
        addPropertiesEditText()
        val disponse = exerciseObservable().debounce (1000,TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
           it?.let {
               tvLearn.text = it
           }
        }

        exercise2();
        observe()
    }

    private fun addPropertiesEditText() {

        val value = RxTextView.textChanges(etRx).filter {
            it.length > 3
        }.delay(3, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe {
            it?.let {
                println("final result $it")
            }
        }
    }
    private fun exerciseObservable() =
        RxTextView.textChanges(etRx).filter {
            it.length>7
        }

    private fun exercise2(){
      val firstEditTextObservable =   RxTextView.textChanges(etRx)
        val secondEditTextObservable =  RxTextView.textChanges(etRx2)
      val resultantObserver =   Observable.combineLatest(firstEditTextObservable,
          secondEditTextObservable, BiFunction { a,b-> "$a $b" })
        resultantObserver.observeOn(AndroidSchedulers.mainThread()).subscribe {
            it?.let {
                tvLearn.text = it
            }
        }
    }

    fun justOperatorObservable()  =
        Observable.just(1,2,3,5,6,7,8,9,10)

    val array = arrayOf(1,2,3,4,5,6,7)
    val mutableList =  mutableListOf(1,2,3,4,5)
    fun fromArrayOperatorObservable() = Observable.fromArray(array)
    fun fromMutableListIterableObservable() = Observable.fromIterable(mutableList)

    fun observe(){
        justOperatorObservable().subscribe(object : Observer<Int>{
            override fun onSubscribe(d: Disposable) {
                println("onSubscribe")
            }

            override fun onNext(t: Int) {
                println("onNext $t")
            }

            override fun onError(e: Throwable) {
                println("onError")
            }

            override fun onComplete() {
                println("onComplete")
            }

        })

        fromArrayOperatorObservable().subscribe(object :Observer<Array<Int>>{
            override fun onSubscribe(d: Disposable) {
                println("onSubscribe")
            }


            override fun onError(e: Throwable) {
                println("onError")
            }

            override fun onComplete() {
                println("onComplete")
            }

            override fun onNext(t: Array<Int>) {
                println("onNext $t")
            }

        })
        fromMutableListIterableObservable().subscribe{
            println("result $it")
    }
    }
}