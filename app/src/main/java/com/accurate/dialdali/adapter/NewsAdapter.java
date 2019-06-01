package com.accurate.dialdali.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accurate.dialdali.model.News;
import com.accurate.dialdali.R;
import com.accurate.dialdali.utility.UtilClass;
import com.squareup.picasso.Picasso;

import java.util.List;
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CustomView>
{
    private List<News> newsList;
    private ImageView picture;
    Context context;
    public NewsAdapter(Context context,List<News> newsList) {
        this.context = context;this.newsList = newsList;
    }

    @Override
    public CustomView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsrow,parent,false);
        return new NewsAdapter.CustomView(view);
    }

    @Override
    public void onBindViewHolder(CustomView holder, int position) {
        News news= newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        Picasso.with(context).load(news.getImage_src()).into(picture);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
    public void updateList(List<News> List) {
        this.newsList = List;
        notifyDataSetChanged();
    }


    public class CustomView extends RecyclerView.ViewHolder
    {
        TextView title,description;
        CustomView(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.newstitle);
            description = (TextView)itemView.findViewById(R.id.newsdesc);
            picture = (ImageView)itemView.findViewById(R.id.newsimg);
            title.setTypeface(UtilClass.setCustomFont(context));
            description.setTypeface(UtilClass.setCustomFont(context));
        }
    }
}
