package com.example.marvel;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FragmentB extends Fragment{
    private static final int[] title_ids={R.id.title,R.id.title1,R.id.title2,R.id.title3,R.id.title4,R.id.title5,
            R.id.title6, R.id.title7,R.id.title8,R.id.title9
    };

    private static final int[] date_ids={R.id.date,R.id.date1,R.id.date2,R.id.date3,R.id.date4,R.id.date5,
            R.id.date6, R.id.date7,R.id.date8,R.id.date9};

    private TextView[] textView = new TextView[title_ids.length];
    private TextView[] textView2 = new TextView[date_ids.length];
    TextView Cname=null;
    ImageView CimageView=null;
    TextView Cdescription=null;
    TextView TittleNotFound=null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container2, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_b,container2,false);

        Cname=v.findViewById(R.id.name);
        Cdescription=v.findViewById(R.id.description);
        CimageView=v.findViewById(R.id.imageView);



        Bundle b=getArguments();
        String name=b.getString("name");
        String description=b.getString("description");
        String imageUrl=b.getString("imageUrl");
        ArrayList<String> listTitle=new ArrayList<>();
        listTitle=b.getStringArrayList("title");
        ArrayList<String> dateList=new ArrayList<>();
        dateList=b.getStringArrayList("dates");

        if(listTitle.isEmpty()==true){
            TittleNotFound=v.findViewById(R.id.title);
            TittleNotFound.setText("No comics found for this character");
        }

        Cname.setText(name);
        Cdescription.setText(description);
        Picasso.with(this.getActivity())
                .load(imageUrl)
                .resize(150,200)
                .into(CimageView);
        int tittle_count=0;
        if(listTitle.size()>=10)
        {
            tittle_count=10;
        }
        else{
            tittle_count=listTitle.size();
        }

        for (int i=0;i<tittle_count;i++){
            textView[i]=(TextView)v.findViewById(title_ids[i]);
            textView2[i]=(TextView)v.findViewById(date_ids[i]);
            textView[i].setText("Comic Name:");
            textView2[i].setText("Date:");


            textView[i].setText(textView[i].getText()+listTitle.get(i));
            textView2[i].setHeight(textView[i].getHeight());
            textView2[i].setText(textView2[i].getText()+dateList.get(i));
            textView[i].setBackgroundColor(0xfff00000);
            textView2[i].setBackgroundColor(0xFFFFFF00);
        }

        return v;
    }
}
