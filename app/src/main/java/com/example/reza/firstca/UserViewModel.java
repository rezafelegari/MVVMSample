package com.example.reza.firstca;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.reza.firstca.DB.UserInfo;
import com.example.reza.firstca.DB.UserRepository;
import com.example.reza.firstca.DB.Word;
import com.example.reza.firstca.DB.WordRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private LiveData<List<UserInfo>> malluser;

    private LiveData<Response<List<Search>>> movies;

    final MutableLiveData<Integer> Size;

    public UserViewModel(@NonNull Application application) throws Exception {
        super(application);
        mRepository = new UserRepository(application);
        malluser = mRepository.getAlluser();
        movies = mRepository.GrtMovie();
        Size = new MutableLiveData<>();
    }


    LiveData<List<UserInfo>> getAlluser() {
        return malluser;
    }

    public void insert(UserInfo user) {
        mRepository.insert(user);
    }

    public void update(int id, String fname, String lname) {
        mRepository.update(id, fname, lname);
    }

    LiveData<Response<List<Search>>> getmovie() {
        Size.setValue(movies.getValue().getData().size());
        return movies;
    }

    public LiveData<Integer> Getsize() {
        return Size;
    }

}
