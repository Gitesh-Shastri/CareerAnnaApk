package com.careeranna.careeranna.fragement.profile_fragements;


import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.careeranna.careeranna.ParticularCourse;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.adapter.ExpandableList_Adapter;
import com.careeranna.careeranna.data.Topic;
import com.careeranna.careeranna.data.Unit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class TutorialFragment extends Fragment implements ExpandableList_Adapter.OnItemClickListener{

    VideoView videoView;

    String videoUrl;

    ExpandableListView listView;
    ExpandableList_Adapter listAdapter;
    ArrayList<Unit> mUnits;

    ProgressDialog progressDialog;

    public TutorialFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);

        videoView = view.findViewById(R.id.videoView);
        String videoPath = "android.resource://com.careeranna.careeranna/"+R.raw.video;
        Uri uri = Uri.parse(videoPath);

        MediaController mediaController = new MediaController(getContext());
        videoView.setVideoPath(uri.toString());
        videoView.setMediaController(mediaController);

        videoView.start();

        listView = view.findViewById(R.id.expandableunit);
        return view;
    }

    public void addCourseUnits(String names) {

        if(!names.equals(" ")) {

            String names1 = names.replace("[", "").replace("]", "").replace("\"", "");
            String[] units = names1.split(",");

            Drawable check = getApplicationContext().getResources().getDrawable(R.drawable.ic_check_circle_black_24dp);
            Drawable unCheck = getApplicationContext().getResources().getDrawable(R.drawable.ic_check_circle_black1_24dp);

            mUnits = new ArrayList<>();

            for (String unitsname : units) {
                char c = unitsname.charAt(0);
                if(!Character.isDigit(c)) {
                    Unit unit = new Unit(unitsname, check);
                    mUnits.add(unit);
                } else {
                    mUnits.get(mUnits.size() -1).topics.add(new Topic(unitsname, unCheck));
                }
            }

            listAdapter = new ExpandableList_Adapter(getApplicationContext(), mUnits);
            listView.setAdapter(listAdapter);
            listAdapter.setOnItemClicklistener(this);
        }

    }

    @Override
    public void onItemClick1(int position, int position2) {

        mUnits.get(position).topics.get(position2).setVideos(fetchVideo(mUnits.get(position).topics.get(position2).getName()));
    }

    private String fetchVideo(String id) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Materilal .. ");
        progressDialog.show();

        videoUrl = "";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://careeranna.in/getCourseVideos.php?id="+id;
        Log.i("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("videos", response);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            videoUrl = jsonObject.getString("video_url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                        videoView.setVideoURI(Uri.parse(videoUrl));
                        videoView.start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);

        return videoUrl.replace("\\", "");
    }
}
