package com.example.reza.firstca.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reza.firstca.DB.Link;
import com.example.reza.firstca.DB.Word;
import com.example.reza.firstca.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    public ExecutorService executorService;

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView, rich_link_url, rich_link_title;


        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textview);
            rich_link_url = itemView.findViewById(R.id.rich_link_url);
            rich_link_title = itemView.findViewById(R.id.rich_link_title);
            executorService = Executors.newCachedThreadPool();
        }
    }

    private final LayoutInflater mInflater;
    private List<Link> mLink; // Cached copy of words

    public WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rich_link_view, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mLink != null) {
            Link current = mLink.get(position);
            holder.wordItemView.setText(current.getTitle());
            holder.rich_link_url.setText(current.getAddress());
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e("Pool", "poll called");
                    try {
                        Document doc = Jsoup.connect(current.getAddress()).get();
                        String title = doc.title();
                        holder.rich_link_title.setText(title);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    public void setWords(List<Link> links) {
        mLink = links;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mLink != null)
            return mLink.size();
        else return 0;
    }


    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}