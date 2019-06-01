package layout;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.accurate.dialdali.HomeActivity;
import com.accurate.dialdali.R;
import com.accurate.dialdali.adapter.NewsAdapter;
import com.accurate.dialdali.model.ApiInterface;
import com.accurate.dialdali.model.News;
import com.accurate.dialdali.utility.UtilClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class todaynews_fragment extends Fragment {
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    Button tryagain;
    private List<News> newsList;
    ProgressBar progressBar;
    private Retrofit retrofit;
    public todaynews_fragment() {
        // Required empty public constructor
    }
    private void initView(View view)
    {
        recyclerView = (RecyclerView)view.findViewById(R.id.todaynewsrcv);
        progressBar = (ProgressBar)view.findViewById(R.id.todaynewsprogress);
        tryagain = (Button)view.findViewById(R.id.todaynewstryagainbutton);
        newsList = new ArrayList<News>();
        newsAdapter = new NewsAdapter(getContext(),newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        retrofit= com.accurate.dialdali.utility.AppUtil.getRetrofitObj();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.todaynews_fragment, container, false);
        initView(view);
        setNews();
        return  view;
    }
    public void setNews()
    {
        if(!UtilClass.isNetworkAvailable(getContext()))
        {

            ((HomeActivity)getActivity()).showDialog(getString(R.string.text_nonetwork),getString(R.string.ok_text));

        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface api = retrofit.create(ApiInterface.class);
            Call<List<News>> call = api.getNews();
            call.enqueue(new Callback<List<News>>() {
                @Override
                public void onResponse(@NonNull Call<List<News>> call, Response<List<News>> response) {
                    newsList = response.body();
                    newsAdapter.updateList(newsList);
                    recyclerView.setAdapter(newsAdapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(@NonNull Call<List<News>> call, @NonNull Throwable t)
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    tryagain.setVisibility(View.VISIBLE);
                    tryagain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            tryagain.setVisibility(View.INVISIBLE);
                            setNews();
                        }
                    });
                }
            });}
    }
}
