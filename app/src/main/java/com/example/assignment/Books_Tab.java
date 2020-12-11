package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


public class Books_Tab extends Fragment {


    ListView listView;
    private static final String XML_URL = "https://www.wits-interactive.com/ftp/test/books.xml";
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
        View view = inflater.inflate(R.layout.fragment_books__tab, container, false);
         listView=view.findViewById(R.id.dataList);

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

                            for (int i = 0; i < parse.getElementsByTagName("book").getLength(); i++) {
                                String user_id = parse.getElementsByTagName("uniqueID").item(i).getTextContent();
                                String title = parse.getElementsByTagName("Title").item(i).getTextContent();
                                String AutorFName1 = parse.getElementsByTagName("AutorFName1").item(i).getTextContent();
                                String AutorLName1 = parse.getElementsByTagName("AutorLName1").item(i).getTextContent();
                             //   String Erscheinungsjahr = parse.getElementsByTagName("Erscheinungsjahr").item(i).getTextContent();
                                String image = parse.getElementsByTagName("ThumbURL").item(i).getTextContent();

                                HashMap<Object, Object> map = new HashMap<>();
                                map.put("user_id", user_id);
                                map.put("title", title);
                                map.put("firstName", AutorFName1);
                                map.put("lastName", AutorLName1);
                                map.put("image",image);


                                List.add(map);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                       CustomAdapterforBook c=new CustomAdapterforBook(getActivity(),List);
                        listView.setAdapter(c);

//                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                if(position==0)
//                                {
//                                    HashMap<String, String> hashMap= (HashMap<String, String>) c.getItem(position);
//                                    Intent intent = new Intent(getActivity(), BookDetaills.class);
//                                    intent.putExtra("hashMap", hashMap);
//                                    startActivity(intent);
//                                }
//                            }
//                        });





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            requestQueue.add(stringRequest);



    }

//    @Override
//    public void onItemClick(int position) {
//        HashMap<String, String> hashMap= adapter.getItem(position);
//        Intent intent = new Intent(SourceActivity.this, DestinationActivity.class);
//        intent.putExtra("hashMap", hashMap);
//        startActivity(intent);
//
//    }
}





