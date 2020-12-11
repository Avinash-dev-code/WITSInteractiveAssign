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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class Games_Tab extends Fragment {

    ListView listView;
    private static final String XML_URL = "https://www.wits-interactive.com/ftp/test/games.xml";
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
        View view= inflater.inflate(R.layout.fragment_games__tab, container, false);
        listView=view.findViewById(R.id.dataListforgames);

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

                            for (int i = 0; i < parse.getElementsByTagName("game").getLength(); i++) {
                                String Type = parse.getElementsByTagName("Type").item(i).getTextContent();
                                String title = parse.getElementsByTagName("Title").item(i).getTextContent();
                                String Platform = parse.getElementsByTagName("Platform").item(i).getTextContent();

                                String imageforg = parse.getElementsByTagName("ThumbURL").item(i).getTextContent();

                                HashMap<Object, Object> map = new HashMap<>();
                                map.put("type", Type);
                                map.put("title", title);
                                map.put("platform", Platform);

                                map.put("image",imageforg);


                                List.add(map);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        CustomAdapterforGames c=new CustomAdapterforGames(getActivity(),List);
                        listView.setAdapter(c);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        //adding string request to request queue
        requestQueue.add(stringRequest);
    }
}





