package com.example.marvel;




import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;


public class FragmentA extends Fragment {
    private static final int[] character_imageButton_ids = {
            R.id.imageButton,R.id.imageButton2,R.id.imageButton3,R.id.imageButton4,R.id.imageButton5,
            R.id.imageButton6,R.id.imageButton7,R.id.imageButton8,R.id.imageButton9,R.id.imageButton10,
            R.id.imageButton11,R.id.imageButton12,R.id.imageButton13,R.id.imageButton14,R.id.imageButton15,
            R.id.imageButton16,R.id.imageButton17,R.id.imageButton18,R.id.imageButton19,R.id.imageButton20,
            R.id.imageButton21,R.id.imageButton22,R.id.imageButton23,R.id.imageButton24,R.id.imageButton25,
            R.id.imageButton26,R.id.imageButton27,R.id.imageButton28,R.id.imageButton29,R.id.imageButton30,
    };

    private static final int[] character_textView_ids={ R.id.textview,R.id.textview2,R.id.textview3,R.id.textview4,R.id.textview5,
            R.id.textview6,R.id.textview7,R.id.textview8,R.id.textview9,R.id.textview10,
            R.id.textview11,R.id.textview12,R.id.textview13,R.id.textview14,R.id.textview15,
            R.id.textview16,R.id.textview17,R.id.textview18,R.id.textview19,R.id.textview20,
            R.id.textview21,R.id.textview22,R.id.textview23,R.id.textview24,R.id.textview25,
            R.id.textview26,R.id.textview27,R.id.textview28,R.id.textview29,R.id.textview30};


    private TextView[] textView = new TextView[character_textView_ids.length];
    private ImageButton[] imageButton = new ImageButton[character_imageButton_ids.length];
    private int listIndex;
    private int index;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_a,container,false);

        Bundle args=getArguments();
        ArrayList<String> listurl=new ArrayList<>();
        listurl=args.getStringArrayList("listurl");
        ArrayList<String> listname=new ArrayList<>();
        listname=args.getStringArrayList("namelist");
        listIndex=args.getInt("index");
        int character_count=0;
        if(listname.size()-listIndex<30){
            character_count=listname.size()-listIndex;
        }
        else {
            character_count=30;

        }

        for(int i=0;i<character_count;i++){
            textView[i]=(TextView)view.findViewById(character_textView_ids[i]);
            imageButton[i]=(ImageButton)view.findViewById(character_imageButton_ids[i]);

            textView[i].setText(listname.get(listIndex));

            Picasso.with(this.getActivity())
                    .load(listurl.get(listIndex))
                    .resize(80,120)
                    .into(imageButton[i]);
            listIndex++;
        }

        return view;

    }

    public void myClickMetod(View v){

        for(int i=0;i<character_imageButton_ids.length;i++){
            if(v.getId()==character_imageButton_ids[i]){
                setIndex(i);
            }
        }


    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getListIndex() {
        return listIndex;
    }

    public void setListIndex(int listIndex) {
        this.listIndex = listIndex;
    }
}
