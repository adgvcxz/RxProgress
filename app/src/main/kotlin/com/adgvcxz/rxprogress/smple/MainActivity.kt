package com.adgvcxz.rxprogress.smple

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adgvcxz.rxprogress.ProgressIndicator
import com.adgvcxz.rxprogress.trackProgress
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.functions.Consumer
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
        val dialog = ProgressDialog(this)
        indicator.toObservable().subscribe(dialog.isShow())

        findViewById(R.id.button).clicks().flatMap {
            Observable.interval(1, TimeUnit.SECONDS).take(3).trackProgress(indicator)
        }.subscribe()
    }
}

fun Dialog.isShow(): Consumer<Boolean> {
    return Consumer {
        if (it) {
            this.show()
        } else {
            this.dismiss()
        }
    }
}