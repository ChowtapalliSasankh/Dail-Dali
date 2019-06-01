package com.accurate.dialdali;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class developer_info extends AppCompatActivity {
    private TextView number1,number2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_info);
        number1 = (TextView)findViewById(R.id.number1);
        number2 = (TextView)findViewById(R.id.number2);
        if(!number1.getPaint().isUnderlineText())
        {number1.setPaintFlags(number1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        number2.setPaintFlags(number2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);}
        number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+(number1.getText().toString().trim())));
                startActivity(i);

            }
        });
        number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+(number2.getText().toString().trim())));
                startActivity(i);
            }
        });
    }
}
