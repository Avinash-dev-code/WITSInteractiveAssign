package com.example.assignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Music_Tab extends Fragment {



    ListView listView;
    private static final String XML_URL = "https://www.wits-interactive.com/ftp/test/music.xml";
    ArrayList<HashMap<Object,Object>> List = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loaddata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_music__tab, container, false);
        listView=view.findViewById(R.id.dataListofmusic);

        return view;


    }
        private void loaddata() {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, XML_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Data", response + "");

                            try {

                                DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

                                Document parse = newDocumentBuilder.parse(new ByteArrayInputStream(response.getBytes()));

                                for (int i = 0; i < parse.getElementsByTagName("itune").getLength(); i++) {
                                    String artist = parse.getElementsByTagName("Artist").item(i).getTextContent();
                                    String title = parse.getElementsByTagName("Title").item(i).getTextContent();
                                    String FolgeNo = parse.getElementsByTagName("FolgeNo").item(i).getTextContent();
                                    String image = parse.getElementsByTagName("ThumbURL").item(i).getTextContent();

                                    HashMap<Object, Object> map = new HashMap<>();
                                    map.put("artist", artist);
                                    map.put("title", title);
                                    map.put("firstName", FolgeNo);
                                    map.put("image",image);


                                    List.add(map);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            CustomAdapterforMusic c=new CustomAdapterforMusic(getActivity(),List);
                            listView.setAdapter(c);



                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


            requestQueue.add(stringRequest);
        }
    }





