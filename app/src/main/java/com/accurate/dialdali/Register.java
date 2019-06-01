package com.accurate.dialdali;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.accurate.dialdali.model.ApiInterface;
import com.accurate.dialdali.model.RegisterResponse;
import com.accurate.dialdali.utility.UtilClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.accurate.dialdali.R.string.ok_text;

public class Register extends AppCompatActivity {
    EditText name,email,phone;
    TextView submit;
    String emal,names,num;
    boolean namevalue,numbervalue,emailvalue;
    public void initView()
    {
        name = (EditText)findViewById(R.id.editname);
        email = (EditText)findViewById(R.id.editemail);
        phone= (EditText)findViewById(R.id.editnumber);
        submit = (TextView)findViewById(R.id.submitbutton);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        if(!UtilClass.isNetworkAvailable(getApplicationContext()))
        {
            showCustomDialog(getString(R.string.text_nonetwork),getString(ok_text));
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namevalue = isValidName();numbervalue = isValidNumber();emailvalue = isValidEmail();
                if(namevalue && numbervalue && emailvalue)
                {
                    final ProgressDialog dialog = new ProgressDialog(Register.this);
                    dialog.setMessage("Sending data Please wait.....");
                    dialog.show();
                    Retrofit retrofit;
                    retrofit= com.accurate.dialdali.utility.AppUtil.getRetrofitObj();
                    ApiInterface api = retrofit.create(ApiInterface.class);
                    Call<List<RegisterResponse>> call = api.registerUser(names,num,emal);
                    call.enqueue(new Callback<List<RegisterResponse>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<RegisterResponse>> call, @NonNull Response<List<RegisterResponse>> response)
                        {
                            List<RegisterResponse> registerresponse = response.body();
                            if (registerresponse != null) {
                                for(RegisterResponse r : registerresponse)
                                {
                                   UtilClass.saveDataInPref(getApplicationContext(),"IsUser_Registered",r.getRegister_status());
                                   UtilClass.saveDataInPref(getApplicationContext(),"YouTube_Link",r.getYoutube_link());
                                }
                            dialog.dismiss();
                            startActivity(new Intent(Register.this,HomeActivity.class));
                            finish();}
                            else
                            {
                                showCustomDialog(getString(R.string.error_text),getString(ok_text));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<RegisterResponse>> call, @NonNull Throwable t)
                        {showCustomDialog(getString(R.string.error_text),getString(ok_text));
                        }
                    });
                }
                else
                {
                    errorHandler();
                }
            }
        });
    }

    private void errorHandler()
    {
        if(!namevalue) {
            if (names.isEmpty()) {
                showCustomDialog(getString(R.string.no_name), getString(ok_text));}
            else if (names.length() < 4) {
                showCustomDialog(getString(R.string.lesslength_name), getString(ok_text));}
            else {
                showCustomDialog(getString(R.string.invalid_name), getString(ok_text));}
        }
        else if(!numbervalue) {
            if(num.isEmpty()) {
                showCustomDialog(getString(R.string.no_number), getString(ok_text));}
            else if(num.length() < 10) {
                showCustomDialog(getString(R.string.lesslength_number), getString(ok_text));}
            else {
                showCustomDialog(getString(R.string.invalid_number), getString(ok_text));}
        }
        else {
            showCustomDialog(getString(R.string.invalid_email), getString(ok_text));}
    }

    private boolean isValidEmail()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        emal = email.getText().toString().trim();
        if(emal.isEmpty())
        {
            return true;
        }
        else if(emal.matches(emailPattern) && emal.length() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isValidNumber()
    {
        String regexStr = "^[0-9]*$";
        num = phone.getText().toString().trim();
        return num.matches(regexStr) && num.length() == 10 && (num.startsWith("9") || num.startsWith("8") || num.startsWith("7") || num.startsWith("6"));
    }

    private boolean isValidName()
    {
        String regexStr = "[a-zA-Z ]+";
        names = name.getText().toString().trim();
       if(names.matches(regexStr) && name.length() > 4)
           return true;
        else
           return false;
    }
    public void showCustomDialog(String message,String text)
    {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Register.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_layout,null);
        mbuilder.setView(mview);
        final AlertDialog dialog = mbuilder.create();
        dialog.setCancelable(false);
        ImageView close = (ImageView)mview.findViewById(R.id.dialogclosemark);
        TextView textview = (TextView)mview.findViewById(R.id.dialogtext);
        TextView texttitle = (TextView)mview.findViewById(R.id.dialogtitle);
        textview.setText(message);
        texttitle.setText(R.string.Error_heading);
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
}
