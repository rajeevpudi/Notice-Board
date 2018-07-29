package com.noticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


public class FacultyNotify extends Fragment {
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product;
    JSONParser jsonParser;
    private ProgressDialog pDialog;
    EditText pwd;
    boolean status;
    String uname;
    EditText unm;

    /* renamed from: com.bharat.institute.FacultyNotify.1 */
    class click implements OnClickListener {
        click() {
        }

        public void onClick(View v) {
            FacultyNotify.this.startActivity(new Intent(FacultyNotify.this.getActivity(), AddNotify1.class));
        }
    }

    static {
        url_create_product = "http://www.ctcorphyd.com/notifications/facultylogin.php";
    }

    public FacultyNotify() {
        this.jsonParser = new JSONParser();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.facultyhome, container, false);
        ((ImageView) view.findViewById(R.id.addnotify)).setOnClickListener(new click());
        return view;
    }
}
