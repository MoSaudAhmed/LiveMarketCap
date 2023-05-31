package com.mgdapps.livemarketcap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 12/7/2017.
 */

public class FragmentNews extends android.support.v4.app.Fragment implements InterfaceNews {

    List<GetSetNews> getSetNewsList = new ArrayList<>();
    RecyclerView rv_newsRecyclerview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        rv_newsRecyclerview = (RecyclerView) view.findViewById(R.id.rv_newsRecyclerview);
        MainActivity activity = (MainActivity) getActivity();
        getSetNewsList = activity.getSetlistNews;

        if (getSetNewsList != null) {

            mNewsRecyclerClass newsRecyclerClass = new mNewsRecyclerClass();
            rv_newsRecyclerview.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rv_newsRecyclerview.setLayoutManager(layoutManager);
            rv_newsRecyclerview.setAdapter(newsRecyclerClass);
        }

        return view;
    }


    @Override
    public void NewsCallback(List<GetSetNews> getSetNewses) {

    }

    private class mNewsRecyclerClass extends RecyclerView.Adapter<mNewsRecyclerClass.ViewHolderClass> {

        @Override
        public ViewHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.row_news, parent, false);

            ViewHolderClass viewHolderClass = new ViewHolderClass(view);
            return viewHolderClass;
        }

        @Override
        public void onBindViewHolder(ViewHolderClass holder, final int position) {

            holder.tv_newsCardview_title.setText(getSetNewsList.get(position).getTitle());
            holder.tv_newscardview_date.setText(getSetNewsList.get(position).getPubDate());

            final String content = getSetNewsList.get(position).getContent();
            String img;
            if (getSetNewsList.get(position).getImage() != null) {
                img = getSetNewsList.get(position).getImage();
                Picasso.with(getActivity()).load(img).fit().error(R.drawable.crypto_news_default).into(holder.img_newsCardView);
            } else {
                Picasso.with(getActivity()).load(R.drawable.crypto_news_default).fit().into(holder.img_newsCardView);
            }

            holder.tv_Newscarddview_description.setText(content);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), SingleActivityNews.class);
                    intent.putExtra("link", getSetNewsList.get(position).getLink());
                    intent.putExtra("image", getSetNewsList.get(position).getImage());
                    intent.putExtra("author", getSetNewsList.get(position).getAuthor());
                    intent.putExtra("title", getSetNewsList.get(position).getTitle());
                    intent.putExtra("date", getSetNewsList.get(position).getPubDate());
                    intent.putExtra("description", content);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return getSetNewsList.size();
        }

        public class ViewHolderClass extends RecyclerView.ViewHolder {
            TextView tv_newsCardview_title, tv_newscardview_date, tv_Newscarddview_description;
            ImageView img_newsCardView;

            public ViewHolderClass(View itemView) {
                super(itemView);
                tv_newsCardview_title = (TextView) itemView.findViewById(R.id.tv_newsCardview_title);
                tv_Newscarddview_description = (TextView) itemView.findViewById(R.id.tv_newsCardview_description);
                img_newsCardView = (ImageView) itemView.findViewById(R.id.img_newsRow);
            tv_newscardview_date = (TextView) itemView.findViewById(R.id.tv_newscardview_date);
            }
        }
    }
}
