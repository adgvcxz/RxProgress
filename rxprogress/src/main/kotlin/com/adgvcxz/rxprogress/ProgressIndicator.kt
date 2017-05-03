package com.adgvcxz.rxprogress

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * zhaowei
 * Created by zhaowei on 2017/5/3.
 */

class ProgressToken<T>(observable: Observable<T>): ObservableSource<T> by observable

class ProgressIndicator {

    private val number: Subject<Int> = PublishSubject.create<Int>().toSerialized()

    private var count = 0

    private val loading: Observable<Boolean>

    init {
        loading = number
                .map { it > 0 }
                .distinctUntilChanged()
    }

    fun <T> trackObservable(observable: Observable<T>): Observable<T> {
        return Observable.using({
            increment()
            ProgressToken(observable)
        }, { it }, { decrement() })
    }

    fun increment() {
        number.onNext(++count)
    }

    fun decrement() {
        number.onNext(--count)
    }

    fun toObservable(): Observable<Boolean> = this.loading
}

fun <T> Observable<T>.trackProgress(indicator: ProgressIndicator): Observable<T> {
    return indicator.trackObservable(this)
}