package com.careeranna.careeranna.fragement;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.careeranna.careeranna.R;
import com.careeranna.careeranna.adapter.ExpandableListAdapter;
import com.careeranna.careeranna.helper.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class TutorialFragment extends Fragment {

    VideoView videoView;
    ArrayList<String> names;
    ArrayList<String> urls;

    ExpandableListView listView;
    ExpandableListAdapter listAdapter;
    HashMap<String, List<String>> listHash;

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
        names.add("Unit 1");
        urls.add(imageUrls[1]);
        names.add("Unit 2");
        urls.add(imageUrls[2]);
        names.add("Unit 3");
        urls.add(imageUrls[2]);
        names.add("Unit 4");
        urls.add(imageUrls[2]);
        names.add("Unit 5");
        urls.add(imageUrls[2]);
        names.add("Unit 6");

        listView = view.findViewById(R.id.expandableunit);

        listHash = new HashMap<>();
        List<String> unit1 = new ArrayList<>();
        unit1.add("Topic 1");

        List<String> unit2 = new ArrayList<>();
        unit2.add("Topic 1");
        unit2.add("Topic 2");

        List<String> unit3 = new ArrayList<>();
        unit3.add("Topic 1");
        unit3.add("Topic 2");

        List<String> unit4 = new ArrayList<>();
        unit4.add("Topic 1");

        List<String> unit5 = new ArrayList<>();
        unit5.add("Topic 1");
        unit5.add("Topic 2");


        List<String> unit6 = new ArrayList<>();
        unit6.add("Topic 1");
        unit6.add("Topic 2");
        unit6.add("Topic 3");

        listHash.put(names.get(0), unit1);
        listHash.put(names.get(1), unit2);
        listHash.put(names.get(2), unit3);
        listHash.put(names.get(3), unit4);
        listHash.put(names.get(4), unit5);
        listHash.put(names.get(5), unit6);

        listAdapter = new ExpandableListAdapter(getApplicationContext(), names ,listHash);
        listView.setAdapter(listAdapter);

        return view;
    }

}
