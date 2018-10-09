package com.example.vera.multilanguagetest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.os.Build.VERSION.SDK;
import static android.os.Build.VERSION.SDK_INT;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //读取SharedPreferences数据，初始化语言设置
        setLanguage(MainActivity.this);

        setContentView(R.layout.activity_main);
        Button setLanguageButton = (Button)findViewById(R.id.set_language_button);
        setLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建单选框
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setSingleChoiceItems(new String[]{"Auto", "简体中文", "English"}, getSharedPreferences("language", Context.MODE_PRIVATE).getInt("language", 0),
                        new DialogInterface.OnClickListener() {
                            //单机单选框某一项以后
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                //将选中的选项存入SharedPreferences，以便重启应用后读取设置
                                SharedPreferences preferences = getSharedPreferences("language", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("language", which);
                                editor.apply();
                                dialog.dismiss();

                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                        });
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void setLanguage(Context context){
        SharedPreferences preferences = getSharedPreferences("language", Context.MODE_PRIVATE);
        int language = preferences.getInt("language", 0);
        //根据读取到的数据进行设置
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale userLocale = Locale.getDefault();
        switch (language){
            case 0:
                userLocale = Locale.getDefault();
                break;
            case 1:
                userLocale = Locale.CHINESE;
                break;
            case 2:
                userLocale = Locale.ENGLISH;
                break;
            default:
                break;
        }
        if(!userLocale.equals(getCurrentLocale(context))) {
            Log.i("MultiLanguage","need to change locale");
            if (SDK_INT >= 17) {
                configuration.setLocale(userLocale);
                context.createConfigurationContext(configuration);
            } else {
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                configuration.locale = userLocale;
                resources.updateConfiguration(configuration, displayMetrics);
            }
        }
    }

    private Locale getCurrentLocale(Context context){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale currentLocale;
        if(SDK_INT >= 24) {
            currentLocale = configuration.getLocales().get(0);
        }else{
            currentLocale = configuration.locale;
        }
        Log.i("MultiLanguage", currentLocale.getLanguage());
        return currentLocale;
    }
}
