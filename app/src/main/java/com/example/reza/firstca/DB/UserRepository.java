package com.example.reza.firstca.DB;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.reza.firstca.AppExecutors;
import com.example.reza.firstca.Data;
import com.example.reza.firstca.Response;
import com.example.reza.firstca.Rest.ApiInterface;
import com.example.reza.firstca.Search;
import com.example.reza.firstca.ServiceGenerator;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

public class UserRepository {

    private UserInfoDao userInfoDao;
    private LiveData<List<UserInfo>> users;
    AppExecutors appExecutors;
    private ApiInterface client;
    LiveData<Response<List<Search>>> mymovie;

    public UserRepository(Application application) throws Exception {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        userInfoDao = db.userInfoDao();
        users = userInfoDao.getuser();
        appExecutors = new AppExecutors();
        client = ServiceGenerator.create(ApiInterface.class);
        mymovie = GetMovie();
    }

    public LiveData<List<UserInfo>> getAlluser() {
        return users;
    }

    public void insert(final UserInfo user) {
        //new insertAsyncTask(mWordDao).execute(word);
        appExecutors.diskIO().execute(() -> userInfoDao.insert(user));
    }

    public void update(final int id, String fname, String lname) {
        //new insertAsyncTask(mWordDao).execute(word);
        appExecutors.diskIO().execute(() -> userInfoDao.update(id, fname, lname));
    }


    public LiveData<Response<List<Search>>> GrtMovie() {
        return mymovie;
    }
    public LiveData<Response<List<Search>>> GetMovie() throws Exception {
        final MutableLiveData<Response<List<Search>>> data = new MutableLiveData<>();

        client.getMovieList()
                .subscribeOn(rx.schedulers.Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Error", "Error Happened");
                        Response<List<Search>> response = new Response<>();
                        response.setData(null);
                        response.setStatusCode(400);
                        data.setValue(response);
                    }

                    @Override
                    public void onNext(Data result) {
                        Response<List<Search>> response = new Response<>();
                        response.setData(result.getSearch());
                        response.setStatusCode(200);
                        data.setValue(response);
                    }
                });
        return data;
    }


}
