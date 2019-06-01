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
import android.widget.Toast;

import com.accurate.dialdali.HomeActivity;
import com.accurate.dialdali.R;
import com.accurate.dialdali.adapter.CategoryListAdapter;
import com.accurate.dialdali.model.ApiInterface;
import com.accurate.dialdali.model.CategoryRow;
import com.accurate.dialdali.utility.UtilClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.accurate.dialdali.R.string.nomatchitems_text;

/**
 * A simple {@link Fragment} subclass.
 */
public class categorylist_fragment extends Fragment implements HomeActivity.OnSearch {
    String title;
    View view;
    Button tryagain;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    CategoryListAdapter categoryListAdapter;
    private Retrofit retrofit;
    private List<CategoryRow> categoryList = new ArrayList<>();
    HomeActivity.OnSearch search;
    public categorylist_fragment()
    {
        // Required empty public constructor
    }

    private void initView(View view)
    {

        title = getArguments().getString("title");
        tryagain = (Button)view.findViewById(R.id.cateegorylisttryagainbutton);
        HomeActivity.setHint(title);
        recyclerView = (RecyclerView)view.findViewById(R.id.categoryrcv);
        progressBar = (ProgressBar)view.findViewById(R.id.cateegorylistprogress);
        categoryListAdapter = new CategoryListAdapter(categoryList,getContext());
        retrofit= com.accurate.dialdali.utility.AppUtil.getRetrofitObj();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.categorylist_fragment, container, false);
        initView(view);
        setData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(categoryListAdapter);
        return view;
    }
    public void setData( )
    {

        if(!UtilClass.isNetworkAvailable(getContext()))
        {
            ((HomeActivity)getActivity()).showDialog(getString(R.string.text_nonetwork),getString(R.string.ok_text));
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface api = retrofit.create(ApiInterface.class);
            Call<List<CategoryRow>> call = api.getInfoList(title);
            call.enqueue(new Callback<List<CategoryRow>>() {
                @Override
                public void onResponse(@NonNull Call<List<CategoryRow>> call, Response<List<CategoryRow>> response) {
                    categoryList = response.body();
                    categoryListAdapter.updateList(categoryList);
                    recyclerView.setAdapter(categoryListAdapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(@NonNull Call<List<CategoryRow>> call, Throwable t)
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    tryagain.setVisibility(View.VISIBLE);
                    tryagain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            tryagain.setVisibility(View.INVISIBLE);
                            setData();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void search(String text)
    {
        if(retrofit == null)
        {
            retrofit= com.accurate.dialdali.utility.AppUtil.getRetrofitObj();
        }
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<List<CategoryRow>> call = api.getInfoList(text);
        call.enqueue(new Callback<List<CategoryRow>>() {
            @Override
            public void onResponse(@NonNull Call<List<CategoryRow>> call, Response<List<CategoryRow>> response) {
                categoryList = response.body();

                if (categoryList == null || categoryList.size() == 0)
                {
                    Toast.makeText(getContext(), getString(nomatchitems_text),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    categoryListAdapter.updateList(categoryList);
                    recyclerView.setAdapter(categoryListAdapter);
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<CategoryRow>> call, @NonNull Throwable t) {

            }
        });
    }
}
