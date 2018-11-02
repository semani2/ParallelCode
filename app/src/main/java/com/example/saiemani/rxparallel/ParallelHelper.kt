package com.example.saiemani.rxparallel

import rx.Single
import rx.schedulers.Schedulers

fun performCallsParallel(callList: List<() -> Any>): List<Any> {
    val singleList: MutableList<Single<Any>> = mutableListOf();

    for (call in callList) {
        singleList.add(Single.create<Any> { singleSubscriber ->
            singleSubscriber.onSuccess(call())
        }.subscribeOn(Schedulers.newThread()))
    }

    return Single.zip(singleList) {
        args: Array<out Any>? -> args!!.toList()
    }.toBlocking().value()
}