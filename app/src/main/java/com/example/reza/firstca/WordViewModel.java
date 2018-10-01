package com.example.reza.firstca;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.reza.firstca.DB.Link;
import com.example.reza.firstca.DB.Word;
import com.example.reza.firstca.DB.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    private LiveData<List<Word>> mAllWords;
    private LiveData<List<Link>> mAllLink;

    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
        mAllLink = mRepository.getAllLink();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    LiveData<List<Link>> getAllLink() {
        return mAllLink;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    public void insert(Link link) {
        mRepository.insert(link);
    }

}
