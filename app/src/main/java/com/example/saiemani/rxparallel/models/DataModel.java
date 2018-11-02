package com.example.saiemani.rxparallel.models;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

    private List<Post> mPostList = new ArrayList<>();
    private List<Todo> mTodoList = new ArrayList<>();
    private List<User> mUserList = new ArrayList<>();

    public DataModel() {

    }

    public DataModel(List<Post> mPostList, List<Todo> mTodoList, List<User> mUserList) {
        this.mPostList = mPostList;
        this.mTodoList = mTodoList;
        this.mUserList = mUserList;
    }

    public List<Post> getmPostList() {
        return mPostList;
    }

    public void setmPostList(List<Post> mPostList) {
        this.mPostList = mPostList;
    }

    public List<Todo> getmTodoList() {
        return mTodoList;
    }

    public void setmTodoList(List<Todo> mTodoList) {
        this.mTodoList = mTodoList;
    }

    public List<User> getmUserList() {
        return mUserList;
    }

    public void setmUserList(List<User> mUserList) {
        this.mUserList = mUserList;
    }
}
