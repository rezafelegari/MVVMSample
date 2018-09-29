package com.example.reza.firstca;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
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


    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UserRepository(application);
        malluser = mRepository.getAlluser();
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

    LiveData<Response<List<Search>>> getmovie() throws Exception {
        LiveData<Response<List<Search>>> moveis = mRepository.GrtMovie();
        return moveis;
    }
}
