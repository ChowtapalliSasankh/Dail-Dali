package com.accurate.dialdali.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accurate.dialdali.model.CategoryRow;
import com.accurate.dialdali.R;
import com.accurate.dialdali.utility.UtilClass;
import com.squareup.picasso.Picasso;

import java.util.List;
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CustomView>
{
    private List<CategoryRow> datalist;
    private ImageView picture;
    Context context;
    public CategoryListAdapter(List<CategoryRow> datalist, Context context)
    {
        this.datalist = datalist;
        this.context = context;
    }

    @Override
    public CustomView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow,parent,false);
        return new CategoryListAdapter.CustomView(view);
    }

    @Override
    public void onBindViewHolder(CustomView holder, int position)
    {
        final CategoryRow category = datalist.get(position);
        holder.name.setText(category.getName());
        holder.mobile.setText(String.valueOf(category.getMobile_no()));
        holder.address.setText(category.getAddress());
        Picasso.with(context).load(category.getImage1()).fit().into(picture);

    }

    @Override
    public int getItemCount()
    {
        return datalist.size();
    }

    public void updateList(List<CategoryRow> categoryList) {
        this.datalist=categoryList;
        notifyDataSetChanged();
    }
    public class CustomView extends RecyclerView.ViewHolder
    {
        private TextView name,mobile,address;
        public CustomView(View itemView)
        {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.textView2);
            mobile = (TextView)itemView.findViewById(R.id.textView4);
            address = (TextView)itemView.findViewById(R.id.textView6);
            picture = (ImageView)itemView.findViewById(R.id.imgvw);
            name.setTypeface(UtilClass.setCustomFont(context));
            mobile.setTypeface(UtilClass.setCustomFont(context));
            address.setTypeface(UtilClass.setCustomFont(context));
        }
    }

}
