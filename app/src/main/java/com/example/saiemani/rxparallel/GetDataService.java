package com.example.saiemani.rxparallel;

import com.example.saiemani.rxparallel.models.Post;
import com.example.saiemani.rxparallel.models.Todo;
import com.example.saiemani.rxparallel.models.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Single;

public interface GetDataService {

    @GET("/posts")
    Single<List<Post>> getAllPosts();

    @GET("/posts")
    Single<Post> getPost(@Query("id") int id);

    @GET("/todos")
    Single<List<Todo>> getAllTodos();

    @GET("/users")
    Single<List<User>> getAllUsers();
}
