package com.adgvcxz.rxprogress

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * zhaowei
 * Created by zhaowei on 2017/5/3.
 */

class ProgressToken<T>(observable: Observable<T>) : ObservableSource<T> by observable

class ProgressIndicator {

    private val number: Subject<Int> = PublishSubject.create<Int>().toSerialized()

    private var count = 0

    private val loading: Observable<Boolean>

    init {
        loading = number
                .map { it > 0 }
                .distinctUntilChanged()
                .share()
    }

    fun <T> trackObservable(observable: Observable<T>): Observable<T> {
        return Observable.using({
            increment()
            ProgressToken(observable)
        }, { it }, { decrement() })
    }

    fun increment() {
        synchronized(this) {
            count++
            number.onNext(count)
        }
    }

    fun decrement() {
        synchronized(this) {
            count--
            number.onNext(count)
        }
    }

    fun toObservable(): Observable<Boolean> = this.loading

    operator fun not(): IndicatorFilter<ProgressIndicator> {
        return IndicatorFilter(this, false)
    }
}

data class IndicatorFilter<out T>(val value: T, val filter: Boolean = true)

fun <T> Observable<T>.trackProgress(indicator: ProgressIndicator): Observable<T> {
    return indicator.trackObservable(this)
}

fun <T> Observable<T>.filter(indicator: ProgressIndicator, filter: Boolean = true): Observable<T> {
    return this.withLatestFrom(indicator.toObservable().startWith(false),
            BiFunction { t1: T, t2: Boolean -> IndicatorFilter(t1, t2) })
            .filter { it.filter == filter }
            .map { it.value }
}

fun <T> Observable<T>.filter(indicator: IndicatorFilter<ProgressIndicator>): Observable<T> {
    return filter(indicator.value, indicator.filter)
}