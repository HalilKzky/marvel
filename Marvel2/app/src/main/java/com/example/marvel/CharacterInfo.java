package com.example.marvel;



import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CharacterInfo {
    final String Public_Api_Key="06c77cedadded9ae99130e6c59533002";
    final String Private_Api_Key="018c4343ed885635c31e787a056a18c0f17d456d";
    final String Ts="1";
    String finalApiHashingString=Ts+Private_Api_Key+Public_Api_Key;
    String hashedValue=GenerateMD5Hash.digest(finalApiHashingString);
    final String HOSTNAME="https://gateway.marvel.com/";
    //final String SERVICE2="v1/public/characters";


    final String SERVICE="v1/public/";


    String url;
    ArrayList<String> namesList = new ArrayList<>();
    ArrayList<String> imageUrlList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    ArrayList<String> descriptionList = new ArrayList<>();
    ArrayList<String> str= new ArrayList<>();
    int state=0;
    int a=1;
    MainActivity main;


    private String TAG = CharacterInfo.class.getSimpleName();
    String letterString;

    public CharacterInfo(MainActivity main2) {
    main=main2;
    getResponse();

    }

    private void getResponse(){

        char letter='a';

    for(letter='a';letter<='z';letter++) {

    letterString = String.valueOf(letter);
    String url2 = "characters?nameStartsWith=" + letterString + "&ts=" + Ts + "&apikey=" + Public_Api_Key + "&hash=" + hashedValue;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HOSTNAME + SERVICE)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    ApiInterface api = retrofit.create(ApiInterface.class);
    Call<String> call = api.getString(url2);

    call.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, retrofit2.Response<String> response) {
            Log.v("Responsestring", response.body().toString());

            if (response.isSuccessful()) {
                if (response.body() != null) {
                    Log.i("onSuccess", response.body().toString());

                    String jsonResponse = response.body().toString();

                    str.add(jsonResponse);
                    setA(getA() + 1);
                    if(getA()==27){
                        show();
                    }


                } else {
                    Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {

        }
    });

}

    }

    public void show(){

       for(int i=0;i<str.size();i++){
            CharacterInfoLists(str.get(i));
        }
       main.getCharacterInfoList(namesList,imageUrlList,descriptionList,idList);
    }


    public void  CharacterInfoLists(String jsonStr) {

    if (jsonStr != null) {

        try {

            int index = jsonStr.indexOf('[');
            jsonStr = jsonStr.substring(index);

            String newStr = "{" + (char) 34 + "results" + (char) 34 + ":" + jsonStr;
            JSONObject jsonObj = new JSONObject(newStr);
            JSONArray json = jsonObj.getJSONArray("results");

            for (int i = 0; i < json.length(); i++) {


                JSONObject c = json.getJSONObject(i);
                String name = c.getString("name");
                String description = "Description not found";
                if (c.getString("description").equals("") == false) {
                    description = c.getString("description");
                }
                String id = c.getString("id");
                String p = c.getString("thumbnail");

                p = "{" + (char) 34 + "thumbnail" + (char) 34 + ":" + p + "}";
                JSONObject emp = (new JSONObject(p)).getJSONObject("thumbnail");
                String path = emp.getString("path");
                String extension = emp.getString("extension");
                String newUrl = path + "." + extension;
                newUrl = newUrl.replace("http", "https");

                namesList.add(name);
                imageUrlList.add(newUrl);
                descriptionList.add(description);
                idList.add(id);
            }

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

    }

    }


    public int getA() { return a; }

    public void setA(int a) {
        this.a = a;
    }
}
