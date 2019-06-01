    package com.accurate.dialdali.adapter;

    import android.content.Context;
    import android.os.Bundle;
    import android.support.v4.app.FragmentTransaction;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import layout.categorylist_fragment;

    import com.accurate.dialdali.HomeActivity;
    import com.accurate.dialdali.model.Category;
    import com.accurate.dialdali.R;
    import com.accurate.dialdali.utility.Constants;
    import com.accurate.dialdali.utility.UtilClass;

    import java.util.List;

    public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
        private List<Category> categoriesList ;
        private Context context;
        public CategoriesAdapter(List<Category> list, Context ctx)
        {
             context = ctx;
            categoriesList = list;
        }

        public List<Category> getCategoriesList()
        {
            return categoriesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorybox,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position)
        {
            final Category category = categoriesList.get(position);
            holder.title.setText(category.getCategory_name());
            holder.rowItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.value = Constants.CATEGORYLIST_VALUE;
                    categorylist_fragment categorylist_fragment = new categorylist_fragment();
                    Bundle args = new Bundle();
                    args.putString("title",category.getCategory_name());
                    categorylist_fragment.setArguments(args);
                    FragmentTransaction fragmentTransaction = ((HomeActivity)context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.frag_display,categorylist_fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return categoriesList.size();
        }

        public void updateList(List<Category> categoryList) {
            this.categoriesList=categoryList;
            notifyDataSetChanged();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder
        {
             private TextView title;
            private View rowItem;
            MyViewHolder(View itemView) {
                super(itemView);
                rowItem=itemView;
                title = (TextView)itemView.findViewById(R.id.categorytitle);
                title.setTypeface(UtilClass.setCustomFont(context));

            }
        }
    }
