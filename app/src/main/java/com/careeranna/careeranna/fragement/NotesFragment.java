package com.careeranna.careeranna.fragement;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.careeranna.careeranna.R;
import com.careeranna.careeranna.adapter.ExpandableListAdapter;
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

    PDFView pdfView;
    ExpandableListView listView;
    ExpandableListAdapter listAdapter;
    HashMap<String, List<String>> listHash;

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

        pdfView = view.findViewById(R.id.pdfViewer);
        pdfView.fromAsset("pdfview.pdf").load();
        return view;
    }

    class Retrieve extends AsyncTask<String,Void,InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if(httpURLConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream);
        }
    }

}
