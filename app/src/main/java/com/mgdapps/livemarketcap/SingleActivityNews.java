package com.mgdapps.livemarketcap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleActivityNews extends AppCompatActivity {

    ImageView img_newsSingle;
    TextView tv_singleNews_description,tv_singleNews_date,tv_singleNews_author,tv_singleNews_title;
    Button btn_singleNews_link;
    String title, author, image, date, link,description;
    Toolbar tb_news_single_toolbar;
    CollapsingToolbarLayout st_news_collapsing_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_single);
        img_newsSingle = (ImageView) findViewById(R.id.img_newsSingle);
        tb_news_single_toolbar=(Toolbar)findViewById(R.id.tb_news_single_toolbar);
        st_news_collapsing_toolbar=(CollapsingToolbarLayout)findViewById(R.id.st_news_collapsing_toolbar);
        btn_singleNews_link=(Button)findViewById(R.id.btn_singleNews_link);
        tv_singleNews_description=(TextView)findViewById(R.id.tv_singleNews_description);
        tv_singleNews_date=(TextView)findViewById(R.id.tv_singleNews_date);
        tv_singleNews_author=(TextView)findViewById(R.id.tv_singleNews_author);
        tv_singleNews_title=(TextView)findViewById(R.id.tv_singleNews_title);

        getSupportActionBar().hide();
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            author = intent.getStringExtra("author");
            image = intent.getStringExtra("image");
            date = intent.getStringExtra("date");
            link = intent.getStringExtra("link");
            description=intent.getStringExtra("description");

        }

        if (image==null)
        {
            Picasso.with(this).load(R.drawable.news_icon).fit().centerCrop().into(img_newsSingle);
        }
        else
        {
            Picasso.with(this).load(image).fit().centerCrop().error(R.drawable.news_icon).into(img_newsSingle);
        }

        img_newsSingle.setContentDescription(title);
        tv_singleNews_description.setText(description);
        tv_singleNews_date.setText("Date: "+date);
        tv_singleNews_author.setText("Author: "+author);
        tv_singleNews_title.setText(title);

        btn_singleNews_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);

            }
        });

/*
        st_news_collapsing_toolbar.setTitle(title);
        st_news_collapsing_toolbar.setTitleEnabled(true);
        st_news_collapsing_toolbar.setCollapsedTitleTextColor(getResources().getColor(android.R.color.black));
        st_news_collapsing_toolbar.setExpandedTitleColor(getResources().getColor(android.R.color.black));
*/
/*        st_news_collapsing_toolbar.setContentScrimColor(getResources().getColor(android.R.color.black));
        st_news_collapsing_toolbar.setStatusBarScrimColor(getResources().getColor(android.R.color.black));*/

    }
}
