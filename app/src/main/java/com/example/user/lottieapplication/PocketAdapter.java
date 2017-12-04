package com.example.user.lottieapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by user on 11/27/2017.
 */

public class PocketAdapter extends BaseAdapter {

    private Context context;
    private Integer[] pocketItems = {
            R.drawable.filefolder,
            R.drawable.receipt,
            R.drawable.idcard,
            R.drawable.cash,
            R.drawable.insurancecard,
            R.drawable.priscription
    };

    public PocketAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return pocketItems.length;
    }

    @Override
    public Object getItem(int i) {
        return pocketItems[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(pocketItems[i]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
        return imageView;
    }
}
