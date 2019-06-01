package layout;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.accurate.dialdali.HomeActivity;
import com.accurate.dialdali.R;
import com.accurate.dialdali.adapter.CategoriesAdapter;
import com.accurate.dialdali.model.ApiInterface;
import com.accurate.dialdali.model.Category;
import com.accurate.dialdali.model.ImagesliderImage;
import com.accurate.dialdali.utility.UtilClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class categories_fragment extends Fragment implements BaseSliderView.OnSliderClickListener{
    RecyclerView rvCategories;
    View view;
    private SliderLayout mDemoSlider;
    Button tryagain;
    ProgressBar progressBar;
    CategoriesAdapter categoriesAdapter;
    List<Category> categoriesList;
    List<ImagesliderImage> imageList;
    HashMap<String,String> file_maps;
    private Retrofit retrofit;
    private void initView(View view)
    {
        rvCategories = (RecyclerView)view.findViewById(R.id.GridLayout);
        tryagain = (Button)view.findViewById(R.id.categoriestryagainbutton);
        progressBar = (ProgressBar)view.findViewById(R.id.categoriesProgress);
        mDemoSlider = (SliderLayout) view.findViewById(R.id.imgslider);
        categoriesList = new ArrayList<>();
        imageList = new ArrayList<>();
        file_maps = new HashMap<String, String>();
        categoriesAdapter = new CategoriesAdapter(categoriesList,getContext());
        retrofit= com.accurate.dialdali.utility.AppUtil.getRetrofitObj();
    }
    public categories_fragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.categories_fragment, container, false);
        initView(view);
        setData();
        rvCategories.setLayoutManager(new GridLayoutManager(getContext(),4));
        rvCategories.setAdapter(categoriesAdapter);
        setImages();
        return view;
    }

    @Override
    public void onStop()
    {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onResume() {
        mDemoSlider.startAutoCycle();
        super.onResume();
    }

    public void setData()
    {

        if(!UtilClass.isNetworkAvailable(getContext()))
        {

            ((HomeActivity)getActivity()).showDialog(getString(R.string.text_nonetwork),getString(R.string.ok_text));

        }
        else{
            progressBar.setVisibility(View.VISIBLE);
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<List<Category>> call = api.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, Response<List<Category>> response) {
                categoriesList = response.body();
                categoriesAdapter.updateList(categoriesList);
                rvCategories.setAdapter(categoriesAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t)
            {
                progressBar.setVisibility(View.INVISIBLE);
                tryagain.setVisibility(View.VISIBLE);
                tryagain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                       tryagain.setVisibility(View.INVISIBLE);
                        setData();
                        setImages();
                    }
                });

            }
        });}
    }
     public void setImages()
     {

         ApiInterface api = retrofit.create(ApiInterface.class);
         Call<List<ImagesliderImage>> call = api.getAdvertisement();
         call.enqueue(new Callback<List<ImagesliderImage>>() {
             @Override
             public void onResponse(@NonNull Call<List<ImagesliderImage>> call, Response<List<ImagesliderImage>> response)
             {
                 imageList = response.body();
                 if (imageList != null) {
                     for(ImagesliderImage i : imageList)
                     {
                         file_maps.put(i.getTitle(),i.getImage());
                     }
                 }
                 for(String name : file_maps.keySet()){
                     TextSliderView textSliderView = new TextSliderView(getContext());
                     // initialize a SliderLayout
                     textSliderView
                             .description(name)
                             .image(file_maps.get(name))
                             .setScaleType(BaseSliderView.ScaleType.Fit);

                     //add your extra information
                     textSliderView.bundle(new Bundle());
                     textSliderView.getBundle()
                             .putString("extra",name);

                     mDemoSlider.addSlider(textSliderView);
                 }
                 mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                 mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                 mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                 mDemoSlider.setDuration(4000);
             }

             @Override
             public void onFailure(@NonNull Call<List<ImagesliderImage>> call, @NonNull Throwable t)
             {

             }
         });
     }
    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
