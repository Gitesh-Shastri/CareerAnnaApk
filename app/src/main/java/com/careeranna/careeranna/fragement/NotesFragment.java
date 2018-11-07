package com.careeranna.careeranna.fragement;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.careeranna.careeranna.R;
import com.careeranna.careeranna.adapter.ExpandableList_Adapter;
import com.careeranna.careeranna.data.Topic;
import com.careeranna.careeranna.data.Unit;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    ExpandableListView listView;
    ExpandableList_Adapter listAdapter;

    ArrayList<Unit> mUnits;
    ArrayList<String> names;
    ArrayList<String> urls;

    private String[] imageUrls = new String[] {
            "https://cdn2.iconfinder.com/data/icons/web-and-apps-interface/32/OK-512.png",
            "https://cdn2.iconfinder.com/data/icons/web-and-apps-interface/32/OK-512.png",
            "https://3mxwfc45nzaf2hj9w92hd04y-wpengine.netdna-ssl.com/wp-content/uploads/2015/03/inside-page-style-blank-3.jpg",
    };

    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        names = new ArrayList<>();
        urls = new ArrayList<>();
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
        ArrayList<Topic> topic1 = new ArrayList<>();
        topic1.add(new Topic("Topic 1", true));

        ArrayList<Topic> topic2 = new ArrayList<>();
        topic2.add(new Topic("Topic 1", true));
        topic2.add(new Topic("Topic 2", true));

        ArrayList<Topic> topic3 = new ArrayList<>();
        topic3.add(new Topic("Topic 1", true));
        topic3.add(new Topic("Topic 2", false));

        ArrayList<Topic> topic4 = new ArrayList<>();
        topic4.add(new Topic("Topic 1", false));

        ArrayList<Topic> topic5 = new ArrayList<>();
        topic5.add(new Topic("Topic 1", false));
        topic5.add(new Topic("Topic 2", false));

        ArrayList<Topic> topic6 = new ArrayList<>();
        topic6.add(new Topic("Topic 1", false));
        topic6.add(new Topic("Topic 2", false));
        topic6.add(new Topic("Topic 3", false));

        mUnits = new ArrayList<>();
        mUnits.add(new Unit("Unit 1"));
        mUnits.get(0).topics = topic1;
        mUnits.add(new Unit("Unit 2"));
        mUnits.get(1).topics = topic2;
        mUnits.add(new Unit("Unit 3"));
        mUnits.get(2).topics = topic3;
        mUnits.add(new Unit("Unit 4"));
        mUnits.get(3).topics = topic4;
        mUnits.add(new Unit("Unit 5"));
        mUnits.get(4).topics = topic5;
        mUnits.add(new Unit("Unit 6"));
        mUnits.get(5).topics = topic6;

        listAdapter = new ExpandableList_Adapter(getApplicationContext(), mUnits);
        listView.setAdapter(listAdapter);

        return view;
    }

}
