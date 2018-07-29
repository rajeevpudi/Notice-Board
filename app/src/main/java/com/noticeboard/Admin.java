package com.noticeboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Admin extends Fragment {

    class click implements OnClickListener {
        click() {
        }

        public void onClick(View v) {
            Toast.makeText(Admin.this.getActivity(), "hi", Toast.LENGTH_LONG).show();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addnotify, container, false);
        ((Button) view.findViewById(R.id.add)).setOnClickListener(new click());
        return view;
    }
}
