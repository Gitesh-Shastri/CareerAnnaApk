package com.careeranna.careeranna.fragement;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.careeranna.careeranna.R;
import com.careeranna.careeranna.helper.RecyclerViewAdapter;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class TutorialFragment extends Fragment {

    VideoView videoView;
    ArrayList<String> names;
    ArrayList<String> urls;

    private String[] imageUrls = new String[] {
        "https://cdn2.iconfinder.com/data/icons/web-and-apps-interface/32/OK-512.png",
            "https://cdn2.iconfinder.com/data/icons/web-and-apps-interface/32/OK-512.png",
            "https://3mxwfc45nzaf2hj9w92hd04y-wpengine.netdna-ssl.com/wp-content/uploads/2015/03/inside-page-style-blank-3.jpg",
    };

    public TutorialFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);

        videoView = view.findViewById(R.id.videoView);
        names = new ArrayList<>();
        urls = new ArrayList<>();
        String videoPath = "android.resource://com.careeranna.careeranna/"+R.raw.video;
        Uri uri = Uri.parse(videoPath);

        MediaController mediaController = new MediaController(getContext());
        videoView.setVideoPath(uri.toString());
        videoView.setMediaController(mediaController);

        videoView.start();

        urls.add(imageUrls[0]);
        names.add("Week 1");
        urls.add(imageUrls[1]);
        names.add("Week 2");
        urls.add(imageUrls[2]);
        names.add("Week 3");
        urls.add(imageUrls[2]);
        names.add("Week 4");
        urls.add(imageUrls[2]);
        names.add("Week 5");
        urls.add(imageUrls[2]);
        names.add("Week 6");
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = view.findViewById(R.id.week);
        recyclerView.setLayoutManager(linearLayoutManager1);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(names, urls, getApplicationContext());
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

}
