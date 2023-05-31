package com.mgdapps.livemarketcap;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements InterfaceNetwork, InterfaceNews, InterfaceMarket, Animation.AnimationListener, InterfaceHome {

//TODO: Start non Sticky Service to pull data every 10 mins

    BottomNavigationView bottomBar;
    FrameLayout mframeLayout;
    Snackbar snackbar;
    RelativeLayout layout;
    NetworkBroadcast mNetworkBroadcast;
    ProgressBar progressDialogue;
    TextView tv_maintext;
    List<GetSetNews> getSetlistNews = new ArrayList<>();
    List<GetSetMarket> mMarketlist = new ArrayList();
    List<GetSetHome> mHomelist = new ArrayList();
    ImageView img_mainLogo;
    List<String> homefavlist = null;

    Animation animationFadein, animationFadeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mframeLayout = (FrameLayout) findViewById(R.id.fl_framelayout_main);
        layout = (RelativeLayout) findViewById(R.id.rv_mainlayout);
        tv_maintext = (TextView) findViewById(R.id.tv_mainText);
        progressDialogue = (ProgressBar) findViewById(R.id.pd_mainProgressDialogue);
        animationFadein = AnimationUtils.loadAnimation(this, R.anim.fadein_animation);
        animationFadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout_animation);
        img_mainLogo = (ImageView) findViewById(R.id.img_mainLogo);
        animationFadein.setAnimationListener(this);
        animationFadeout.setAnimationListener(this);
        img_mainLogo.startAnimation(animationFadein);

        mNetworkBroadcast = new NetworkBroadcast();
        getBottomNavigationbar();
        ConnectivityManager connecti = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connecti.getActiveNetworkInfo();
        boolean CheckNetwork = (networkinfo != null && networkinfo.isConnectedOrConnecting());
        if (CheckNetwork) {

            showSnackbar(2);
        } else {
            showSnackbar(1);
        }

        final SharedPreferences sharedPreferences = getSharedPreferences("favlist", MODE_PRIVATE);
        Set<String> myset = sharedPreferences.getStringSet("mylist", null);
        if (myset != null) {
            homefavlist = new ArrayList<>(myset);
            BackgroundTaskHome backgroundTaskHome = new BackgroundTaskHome(this, homefavlist);
            backgroundTaskHome.execute();
        }

        BackgroundTaskMarket backgroundTaskMarket = new BackgroundTaskMarket(this, tv_maintext, progressDialogue, "https://api.coinmarketcap.com/v1/ticker/?start=0&limit=250");
        backgroundTaskMarket.execute();
        BackgroundTaskNews n = new BackgroundTaskNews(this, "https://newsapi.org/v2/everything?sources=crypto-coins-news&apiKey=cfc05b57695c47ab8b9e74e84285d695");
        n.execute();

        setText();
    }

    private void getBottomNavigationbar() {

        bottomBar = (BottomNavigationView) findViewById(R.id.bb_mybottombar);
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.tab_home) {
                    FragmentHome fragmentHome = new FragmentHome();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_framelayout_main, fragmentHome).commit();
                } else if (item.getItemId() == R.id.tab_market) {
                    FragmentMarket fragmentMarket = new FragmentMarket();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_framelayout_main, fragmentMarket).commit();
                } else if (item.getItemId() == R.id.tab_news) {
                    FragmentNews fragmentNews = new FragmentNews();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_framelayout_main, fragmentNews).commit();
                } else if (item.getItemId() == R.id.tab_profile) {
                    FragmentProfile fragmentProfile = new FragmentProfile();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_framelayout_main, fragmentProfile).commit();
                }

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(mNetworkBroadcast, intentfilter);
    }


    public void showSnackbar(int msg) {
        if (msg == 1) {
            tv_maintext.setVisibility(View.VISIBLE);
            progressDialogue.setVisibility(View.VISIBLE);

            snackbar = Snackbar.make(layout, "No Internet Available...", Snackbar.LENGTH_LONG);
            snackbar.setAction("Settings", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent1 = new Intent(Settings.ACTION_SETTINGS);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }
            });
            snackbar.show();
        } else if (msg == 2) {
            {
                BackgroundTaskMain m = new BackgroundTaskMain(this, tv_maintext, progressDialogue, "https://api.coinmarketcap.com/v1/ticker/?start=0&limit=250");
                m.execute();
            }
        }

    }

    @Override
    public void Callback(int str) {
        showSnackbar(str);
    }

    public void setText() {

        final TextView textView = tv_maintext;
        final String[] array = {"“The future of money is digital currency.” - Bill Gates",
                "“Not afraid of heights - afraid of widths.” - Thomas St Germain",
                "“Bitcoin is unstoppable.” - Roger Ver aka “Bitcoin Jesus” Voluntaryist",
                "“Bitcoin is Cash with Wings” - Charlie Shrem",
                "“Cryptocurrency is such a powerful concept that it can almost overturn governments.” - Charles Lee",
                "“It’s money 2.0, a huge, huge, huge deal.” - Chamath Palihapitiya"};
        textView.post(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                textView.setText(array[i]);
                i++;
                if (i == 5)
                    i = 0;
                textView.postDelayed(this, 2500);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkBroadcast);
    }

    @Override
    public void NewsCallback(List<GetSetNews> getSetNewses) {
        getSetlistNews = getSetNewses;
    }

    @Override
    public void MarketCallback(List<GetSetMarket> marketlist) {

        FragmentHome fragmentHome = new FragmentHome();
        mMarketlist = marketlist;
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_framelayout_main, fragmentHome).commit();
        bottomBar.setSelectedItemId(R.id.tab_home);

    }

    @Override
    public void HomeCallback(List<GetSetHome> marketlist) {
        mHomelist = marketlist;
        FragmentHome fragmentHome = new FragmentHome();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_framelayout_main, fragmentHome).commit();
        bottomBar.setSelectedItemId(R.id.tab_home);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (animation == animationFadein) {
            img_mainLogo.startAnimation(animationFadeout);
        } else if (animation == animationFadeout) {
            img_mainLogo.startAnimation(animationFadein);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder alertdialogue = new AlertDialog.Builder(this);
            alertdialogue.create();
            alertdialogue.setTitle("Exit?");
            alertdialogue.setMessage("Are you sure you want to exit?");
            alertdialogue.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        return super.onKeyDown(keyCode, event);
    }


}
