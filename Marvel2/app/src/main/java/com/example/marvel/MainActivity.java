package com.example.marvel;




import androidx.appcompat.app.AppCompatActivity;

import android.app.*;


import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivity extends AppCompatActivity {
    final String Public_Api_Key="06c77cedadded9ae99130e6c59533002";
    final String Private_Api_Key="018c4343ed885635c31e787a056a18c0f17d456d";
    final String Ts="1";
    String finalApiHashingString=Ts+Private_Api_Key+Public_Api_Key;
    String hashedValue=GenerateMD5Hash.digest(finalApiHashingString);
    //final String HOSTNAME="https://gateway.marvel.com/";
    //final String SERVICE="v1/public/";

    //String url = HOSTNAME + SERVICE + "?nameStartsWith=a" + "&ts=" + Ts + "&apikey=" + Public_Api_Key + "&hash=" + hashedValue;

    ArrayList<String> nameList= new ArrayList<>();
    ArrayList<String> urlImageList= new ArrayList<>();
    ArrayList<String> idlist=new ArrayList<>();
    ArrayList<String> descriptionList=new ArrayList<>();
    ArrayList<String> titleList=new ArrayList<>();
    ArrayList<String> datelist=new ArrayList<>();
    int indexofCharacter=0;

    private int index=0;
    FragmentManager menager=getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        getCharacterInfo();


    }



    public void getCharacterInfo(){

        CharacterInfo characterInfo=new CharacterInfo(MainActivity.this);

    }
    public void getCharacterInfoList(ArrayList<String> name,ArrayList<String> urls,ArrayList<String> descriptions,ArrayList<String> ids){

        setNameList(name);
        setUrlImageList(urls);
        setDescriptionList(descriptions);
        setIdlist(ids);

        showMarvelCharacters();

    }


    public void showMarvelCharacters(){

        Fragment fragment=new FragmentA();
        Bundle bundle=new Bundle();

        bundle.putStringArrayList("listurl",urlImageList);
        bundle.putStringArrayList("namelist",nameList);
        bundle.putInt("index",getIndex());
        fragment.setArguments(bundle);

        FragmentTransaction transaction=menager.beginTransaction();
        transaction.add(R.id.conteiner,fragment,"Fragment A");
        transaction.commit();

    }

    public void nextPage(View view){

        if(getIndex()+30<nameList.size()){
            setIndex(getIndex()+30);
        }

        if(nameList.size()>getIndex()){
            removeFragmentA();
            showMarvelCharacters();
        }

    }



    public void imageButtonClicked(View v){

        FragmentA f=new FragmentA();
        f.myClickMetod(v);
        int indexOfList=f.getIndex();
        getComicInfo(indexOfList);
    }

    public void getComicInfo(int characterIndex){

        int idIndex=getIndex()+characterIndex;
        String id=idlist.get(idIndex);

        String path= "comics?orderBy=-onsaleDate"+"&ts=" + Ts +"&apikey="+Public_Api_Key+"&hash=" + hashedValue;
        //String url2 = id+"/comics?orderBy=-onsaleDate"+"&ts=" + Ts +"&apikey="+Public_Api_Key+"&hash=" + hashedValue;
        //String url2 = HOSTNAME+SERVICE+"characters/"+id+"/comics?orderBy=-onsaleDate"+"&ts=" + Ts +"&apikey="+Public_Api_Key+"&hash=" + hashedValue;
        ComicInfo comicInfo=new ComicInfo(id,path,MainActivity.this);
        
        characterIndex=getIndex()+characterIndex;
        setIndexofCharacter(characterIndex);



    }
    public void getComicInfoList(ArrayList<String> tittle,ArrayList<String> date){
      setTitleList(tittle);
      setDatelist(date);
      showCharacter();
    }

    public void showCharacter(){

        removeFragmentA();


        Fragment fragmentb=new FragmentB();
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("title",titleList);
        bundle.putStringArrayList("dates",datelist);
        bundle.putString("name", nameList.get(getIndexofCharacter()));
        bundle.putString("description",descriptionList.get(getIndexofCharacter()));
        bundle.putString("imageUrl",urlImageList.get(getIndexofCharacter()));
        fragmentb.setArguments(bundle);
        FragmentTransaction transaction2=menager.beginTransaction();
        transaction2.add(R.id.conteiner,fragmentb,"Fragment B");
        transaction2.commit();

    }
    public void removeFragmentA(){
        FragmentA fragmentA1=(FragmentA) menager.findFragmentByTag("Fragment A");

        FragmentTransaction transaction=menager.beginTransaction();
        transaction.remove(fragmentA1);
        transaction.commit();
    }

    public int getIndexofCharacter() {
        return indexofCharacter;
    }

    public void setIndexofCharacter(int indexofCharacter) {
        this.indexofCharacter = indexofCharacter;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public void setNameList(ArrayList<String> nameList) {
        this.nameList = nameList;
    }

    public ArrayList<String> getUrlImageList() {
        return urlImageList;
    }

    public void setUrlImageList(ArrayList<String> urlImageList) {
        this.urlImageList = urlImageList;
    }

    public ArrayList<String> getIdlist() {
        return idlist;
    }

    public void setIdlist(ArrayList<String> idlist) {
        this.idlist = idlist;
    }

    public ArrayList<String> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(ArrayList<String> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public ArrayList<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(ArrayList<String> titleList) {
        this.titleList = titleList;
    }

    public ArrayList<String> getDatelist() {
        return datelist;
    }

    public void setDatelist(ArrayList<String> datelist) {
        this.datelist = datelist;
    }
}
