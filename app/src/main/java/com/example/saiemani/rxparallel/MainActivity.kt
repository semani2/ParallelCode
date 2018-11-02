package com.example.saiemani.rxparallel

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.saiemani.rxparallel.models.DataModel
import com.example.saiemani.rxparallel.models.Post
import com.example.saiemani.rxparallel.models.Todo
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var mService: GetDataService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mService = RetrofitClientIntance.getRetrofitInstance().create(GetDataService::class.java)

        button2.setOnClickListener {
            doParallelStuff()
        }

        button.setOnClickListener { fetchInSerial() }
    }

    @SuppressLint("SetTextI18n")
    fun doParallelStuff() {
        val callList = mutableListOf<() -> Any>()
        callList.add(::getAllPosts)
        callList.add(::getAllTodos)
        callList.add(::doNothing)
        val startTime = System.currentTimeMillis()
        val resultList = performCallsParallel(callList)
        val endTime = System.currentTimeMillis()

        textView2.text = "Time to fetch in serial: " + (endTime - startTime) + " (ms)"
        textView.text = ("Posts " + (resultList[0] as List<*>).size
                + ", Todos: " + (resultList[0] as List<*>).size)

    }

    private fun getAllPosts(): List<Post> {
        Log.d("Hello", "Getting all Posts")
        val posts: List<Post> = mService.allPosts.toBlocking().value()
        Log.d("Hello", "Posts fetched, size: " + posts.size)
        return posts
    }

    private fun getAllTodos(): List<Todo> {
        Log.d("Hello", "Getting all Todos")
        val todos: List<Todo> = mService.allTodos.toBlocking().value()
        Log.d("Hello", "Todo fetched, size: " + todos.size)
        return todos
    }

    private fun doNothing(): Unit {
        Log.d("Hello", "I do nothing")
    }

    @SuppressLint("SetTextI18n")
    fun fetchInSerial() {
        val startTime = System.currentTimeMillis()
        val dm = DataModel()

        mService.allPosts.subscribeOn(Schedulers.io())
                .subscribe {
                    posts ->  dm.setmPostList(posts)
                    mService.allTodos
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                todos ->  dm.setmTodoList(todos)
                                textView.text = ("Posts " + dm.getmPostList().size
                                        + ", Todos: " + dm.getmTodoList().size)
                                val endTime = System.currentTimeMillis()

                                textView2.text = "Time to fetch in serial: " + (endTime - startTime) + " (ms)"
                            }
                }
    }
}
