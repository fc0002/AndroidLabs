package com.example.androidlabs;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class DetailFragment extends Fragment {

    private Bundle dataFromActivity;
    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();

        View result = inflater.inflate(R.layout.fragment_detail, container, false);

        //show the name
        TextView name = (TextView) result.findViewById(R.id.nameView2);
        name.setText(dataFromActivity.getString(MainActivity.NAME));

        //show the height
        TextView height = (TextView) result.findViewById(R.id.heightView2);
        height.setText(dataFromActivity.getString(MainActivity.HEIGHT));

        //show the mass
        TextView mass = (TextView) result.findViewById(R.id.massView2);
        mass.setText(dataFromActivity.getString(MainActivity.MASS));

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity) context;
    }
}