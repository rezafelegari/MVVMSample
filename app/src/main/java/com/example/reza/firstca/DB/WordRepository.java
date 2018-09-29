package com.example.reza.firstca.DB;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.reza.firstca.AppExecutors;

import java.util.List;
import java.util.concurrent.Executor;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;
    AppExecutors appExecutors;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
        appExecutors = new AppExecutors();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert(final Word word) {
        //new insertAsyncTask(mWordDao).execute(word);
        appExecutors.diskIO().execute(() -> mWordDao.insert(word));
    }

//    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
//
//        private WordDao mAsyncTaskDao;
//
//        insertAsyncTask(WordDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final Word... params) {
//            mAsyncTaskDao.insert(params[0]);
//            return null;
//        }
//    }
}