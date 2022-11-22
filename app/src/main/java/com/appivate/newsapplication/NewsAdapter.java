package com.appivate.newsapplication;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<NewsModel> newsModelArrayList;
    Context mContext;
    private OnItemClickListener onClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView newsTopic;
        TextView newsDateTextView;
        TextView trailText;
        ImageView newsImage;
        TextView newsAuthor;

        public NewsViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            newsTopic = itemView.findViewById(R.id.newsTopic);
            newsDateTextView = itemView.findViewById(R.id.publicationDate);
            trailText = itemView.findViewById(R.id.trailText);
            newsImage = itemView.findViewById(R.id.imageView);
            newsAuthor = itemView.findViewById(R.id.newsAuthor);

            itemView.setOnClickListener(v -> {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
        });
    }
}

    public NewsAdapter(ArrayList<NewsModel> exampleList) {
        if (exampleList == null) {
            newsModelArrayList = new ArrayList<>();
        } else {
            newsModelArrayList = exampleList;
        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_views, parent, false);
        return new NewsViewHolder(v, onClickListener);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsModel currentItem = newsModelArrayList.get(position);
        Glide.with(mContext).load(currentItem.getThumbnail()).placeholder(R.drawable.ic_baseline_broken_image_24).into(holder.newsImage);
        holder.newsTopic.setText(currentItem.getNewsTitle());
        holder.trailText.setText(currentItem.getTrailText());
        holder.newsDateTextView.setText(currentItem.getNewsDateAsString());
        holder.newsAuthor.setText(currentItem.getAuthor());
    }

    public void emptyList() {
        newsModelArrayList.clear();
        notifyDataSetChanged();
    }

    public void updateList(List<NewsModel> newsModels) {
        newsModelArrayList.clear();
        newsModelArrayList.addAll(newsModels);
    }

    @Override
    public int getItemCount() {
        if (newsModelArrayList == null) {
            return 0;
        } else {
            return newsModelArrayList.size();
        }
    }
}