package com.example.kasurbazar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{







    WebView kasurbazarView;
    private String WebUrl="https://kasurbazar.com/";
    ProgressBar progressbarweb;
    ProgressDialog webdialogue;
    RelativeLayout relativeLayout;
    Button btnNetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


















        kasurbazarView=(WebView)findViewById(R.id.kasurbazarweb);
        WebSettings kasurbazarsettings=kasurbazarView.getSettings();
        kasurbazarsettings.setJavaScriptEnabled(true);



        relativeLayout=(RelativeLayout)findViewById(R.id.relativelayout);
        btnNetConnection=(Button)findViewById(R.id.btn_NoConn);

        btnNetConnection.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckConnection();

            }



        });


        progressbarweb=(ProgressBar)findViewById(R.id.progressbar);

        webdialogue=new ProgressDialog(this);
        webdialogue.setMessage("Loading Please Wait  ....");

       // kasurbazarView.loadUrl(WebUrl);
        kasurbazarView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String Url)
            {
                view.loadUrl(Url);
                return true;

            }
        });

        kasurbazarView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {

                progressbarweb.setVisibility(View.VISIBLE);
                progressbarweb.setProgress(newProgress);
                //setTitle("Loading....");
                webdialogue.show();

                if(newProgress==100)
                {
                    progressbarweb.setVisibility(View.GONE);
                    //setTitle(view.getTitle());
                    webdialogue.dismiss();

                }
                super.onProgressChanged(view, newProgress);
            }
        });

        CheckConnection();


    }



    @Override
    public void onBackPressed()
    {
        if(kasurbazarView.canGoBack())
        {
            kasurbazarView.goBack();
        }
        else
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this );
            builder.setMessage("Are you Sure You Want to Exit")
                    .setNegativeButton("No",null )
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                            finishAffinity();
                        }
                    }).show();

        }
    }


    public void CheckConnection()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo MobileNet=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifi.isConnected())
        {
            kasurbazarView.loadUrl(WebUrl);
            kasurbazarView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        else if(MobileNet.isConnected())
        {
            kasurbazarView.loadUrl(WebUrl);
            kasurbazarView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);

        }
        else
        {
            kasurbazarView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_privious:
                onBackPressed();
                break;
            case R.id.nav_next:

                if(kasurbazarView.canGoForward())
                {
                    kasurbazarView.goForward();
                }
                break;

            case R.id.nav_refresh:
                CheckConnection();
                break;


        }


        return super.onOptionsItemSelected(item);
    }



}
