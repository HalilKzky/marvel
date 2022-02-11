package com.example.marvel;



import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ComicInfo {
    String url;
    private String TAG = ComicInfo.class.getSimpleName();
    ArrayList<String> titleList=new ArrayList<>();
    ArrayList<String> dateList=new ArrayList<>();
    String id;
    final String HOSTNAME="https://gateway.marvel.com/";
    final String SERVICE="v1/public/characters/";
    MainActivity main2;
    public ComicInfo(String id1,String path,MainActivity main) {
        url=path;
        main2=main;
        id=id1;
        getComicResponse();
    }

   private void getComicResponse(){
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(HOSTNAME + SERVICE+id+"/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface Comicapi = retrofit2.create(ApiInterface.class);
        Call<String> call1 = Comicapi.getString(url);

        call1.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.v("Responsestring", response.body().toString());
                //Toast.makeText()

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        getComic(jsonresponse);

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

public void getComic(String jsonStr){

    if (jsonStr != null) {

        try {
            int index=jsonStr.indexOf('[');
            jsonStr=jsonStr.substring(index);
            String newStr="{"+(char)34+"results"+(char)34+":"+jsonStr;
            JSONObject jsonObj = new JSONObject(newStr);
            JSONArray json = jsonObj.getJSONArray("results");


            for(int i=0;i<json.length();i++){

                JSONObject c = json.getJSONObject(i);
                String title=c.getString("title");

                String p=c.getString("dates");
                String p1="{"+(char)34+"dates"+(char)34+":"+p+"}";

                JSONObject emp = new JSONObject(p1);
                JSONArray jsonDates = emp.getJSONArray("dates");
                String date="";
                for(int j=0;j<jsonDates.length();j++){
                    JSONObject k = jsonDates.getJSONObject(j);
                    String type = k.getString("type");

                    if(type.equals("onsaleDate")==true) {
                        date = k.getString("date");
                    }
                }

                if(date.compareTo("2005")>=0){
                    titleList.add(title);
                    dateList.add(date);
                }
            }

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

        main2.getComicInfoList(titleList,dateList);
}






}
