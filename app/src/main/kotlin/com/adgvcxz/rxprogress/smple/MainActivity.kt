package com.adgvcxz.rxprogress.smple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adgvcxz.rxprogress.ProgressIndicator
import com.adgvcxz.rxprogress.filter
import com.adgvcxz.rxprogress.trackProgress
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * zhaowei
 * Created by zhaowei on 2017/5/3.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val indicator = ProgressIndicator()


        findViewById(R.id.button_1).clicks().flatMap {
            Observable.interval(1, TimeUnit.SECONDS).take(3).trackProgress(indicator)
        }.subscribe()

        findViewById(R.id.button_2).clicks()
                .filter(!indicator)
                .subscribe {
                    Toast.makeText(this, "Click Button 2", Toast.LENGTH_SHORT).show()
                }
    }
}