package com.accurate.dialdali;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accurate.dialdali.utility.Constants;
import com.accurate.dialdali.utility.UtilClass;

import layout.categories_fragment;
import layout.categorylist_fragment;
import layout.todaynews_fragment;

public class HomeActivity extends AppCompatActivity {

    TextView home,todaynews,newstitle;
    ImageView menu;
    RelativeLayout frameLayout;
    static EditText searchbar;
    FragmentManager manager = getSupportFragmentManager();
    categories_fragment categories_fragment;
    public categorylist_fragment categorylist_fragment = new categorylist_fragment();
    todaynews_fragment todaynews_fragment;
    public interface OnSearch
    {
        public void search(String text);
    }
    private void initView( )
    {
        menu = (ImageView)findViewById(R.id.home_menu);
        searchbar = (EditText)findViewById(R.id.searchbar);
        home = (TextView)findViewById(R.id.hometextview);
        home.setTypeface(UtilClass.setCustomFont(getApplicationContext()));
        todaynews = (TextView)findViewById(R.id.todaynewstextview);
        todaynews.setTypeface(UtilClass.setCustomFont(getApplicationContext()));
        newstitle = (TextView)findViewById(R.id.newstitle);
        newstitle.setTypeface(UtilClass.setCustomFont(getApplicationContext()));
        frameLayout = (RelativeLayout)findViewById(R.id.frag_display);
        categories_fragment = new categories_fragment();
        todaynews_fragment = new todaynews_fragment();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        setColor();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                PopupMenu popup = new PopupMenu(HomeActivity.this, v);
                popup.getMenuInflater().inflate(R.menu.homemenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        switch (item.getItemId()) {
                            case R.id.rateapp:
                            {
                                UtilClass.rateUsOnPlayStore(getApplicationContext());

                                break;
                            }
                            case R.id.shareapp:
                            {
                                UtilClass.shareAppWithOthers(getApplicationContext());
                                break;
                            }

                            case R.id.developerinfo:
                            {
                                startActivity(new Intent(HomeActivity.this,developer_info.class));
                                break;
                            }

                            default:
                                break;
                        }

                        return false;
                    }
                });
                popup.show();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(Constants.value != Constants.CATEGORIES_VALUE  )
                {
                    Constants.value = Constants.CATEGORIES_VALUE;
                    searchbar.setVisibility(View.VISIBLE);
                    searchbar.setHint("Search Here");
                    newstitle.setVisibility(View.INVISIBLE);
                    setFragment(categories_fragment);
                    setColor();
                }


            }
        });
        todaynews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.value != Constants.TODAYNEWS_VALUE)
                {
                    Constants.value = Constants.TODAYNEWS_VALUE;
                    searchbar.setVisibility(View.INVISIBLE);
                    newstitle.setVisibility(View.VISIBLE);
                    setFragment(todaynews_fragment);
                    setColor();
                }

            }
        });
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!categorylist_fragment.isVisible())
                {
                    Bundle args = new Bundle();
                    args.putString("title", "search here");
                    categorylist_fragment.setArguments(args);
                    setFragment(categorylist_fragment);

                }
                categorylist_fragment.search(searchbar.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        setFragment(categories_fragment);
        startActivity(new Intent(HomeActivity.this,Advertisement.class));
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if(manager.getBackStackEntryCount() == 0)
        {
            finish();
        }
        else {
            if (todaynews_fragment.isVisible()) {
                Constants.value = Constants.TODAYNEWS_VALUE;
                searchbar.setVisibility(View.INVISIBLE);
                newstitle.setVisibility(View.VISIBLE);
                setColor();
            }
            else if (categorylist_fragment.isVisible())
            {
                Constants.value = Constants.CATEGORYLIST_VALUE;
                searchbar.setVisibility(View.INVISIBLE);
                newstitle.setVisibility(View.VISIBLE);
                setColor();
            }
            else {
                Constants.value = Constants.CATEGORIES_VALUE;
                searchbar.setVisibility(View.VISIBLE);
                searchbar.setHint("Search Here");
                newstitle.setVisibility(View.INVISIBLE);
                setColor();
            }
        }
    }

    public void setFragment(Fragment fragment)
    {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName,0);
        if(!fragmentPopped)
        {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(R.id.frag_display,fragment).addToBackStack(backStateName);
            fragmentTransaction.commit();
        }

    }
    public  void setColor()
    {
        if(Constants.value == Constants.CATEGORIES_VALUE || Constants.value == Constants.CATEGORYLIST_VALUE)
        {
            home.setTextColor(ContextCompat.getColor(this,R.color.colorNavbarSelectColor));
            todaynews.setTextColor(ContextCompat.getColor(this,R.color.colorTextSelected));
        }
        else
        {
            todaynews.setTextColor(ContextCompat.getColor(this,R.color.colorNavbarSelectColor));
            home.setTextColor(ContextCompat.getColor(this,R.color.colorTextSelected));
        }
    }
    public void showDialog(String message,String text)
    {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(HomeActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_layout,null);
        mbuilder.setView(mview);
        final AlertDialog dialog = mbuilder.create();
        dialog.setCancelable(false);
        ImageView close = (ImageView)mview.findViewById(R.id.dialogclosemark);
        TextView textview = (TextView)mview.findViewById(R.id.dialogtext);
        TextView texttitle = (TextView)mview.findViewById(R.id.dialogtitle);
        textview.setText(message);
        texttitle.setText(getString(R.string.Error_heading));
        Button button = (Button)mview.findViewById(R.id.dialogbutton);
        button.setText(text);
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorCardView));
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public static void setHint(String hint) {
        searchbar.setHint(hint);
    }
}
