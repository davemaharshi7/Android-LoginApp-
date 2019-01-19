package com.travel.complete_loginapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.travel.complete_loginapp.R;


public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public SliderAdapter(Context context){
        this.context = context;

    }
    public int[] slide_bg = {
            R.color.intro1,
            R.color.intro2,
            R.color.intro3,


    };

    public int[] slide_images = {
            R.drawable.web_hi_res_512,
            R.drawable.web_hi_res_512,
            R.drawable.web_hi_res_512

    };

    public String[] slideHeading = {
            "Here you can get complete solution to login app",
            "Forgot password recovery option available",
            "So lets get Started..."
    };

    @Override
    public int getCount() {
        return slideHeading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (ConstraintLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_layout,container,false);

        //view.setBackgroundColor(slide_bg[position]);

        ImageView imageView = (ImageView) view.findViewById(R.id.slideImage);
        TextView textView = (TextView) view.findViewById(R.id.slide_heading);

        imageView.setImageResource(slide_images[position]);
        textView.setText(slideHeading[position]);

        //view.setBackgroundColor(slide_bg[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);

    }


}
