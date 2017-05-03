package com.adgvcxz.rxprogress.smple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.adgvcxz.rxprogress.ProgressIndicator
import com.adgvcxz.rxprogress.trackProgress
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

        indicator.toObservable().subscribe {
            Log.e("zhaow", "结束$it")
        }

        Observable.interval(1, TimeUnit.SECONDS).take(3).trackProgress(indicator)
                .subscribe {
                    Log.e("zhaow", "===$it====")
                }
    }
}