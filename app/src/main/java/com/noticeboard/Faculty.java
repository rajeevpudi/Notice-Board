package com.noticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Faculty extends Fragment {
    private static final String TAG_SUCCESS = "success";
    private static String url_facultylogin;
    JSONParser jsonParser;
    private ProgressDialog pDialog;
    EditText pwd;
    String pwd1;
    boolean status;
    EditText unm;
    String unm1;

    class onclick implements OnClickListener {
        onclick() {
        }

        public void onClick(View v) {
            Faculty.this.unm1 = Faculty.this.unm.getText().toString().trim();
            Faculty.this.pwd1 = Faculty.this.pwd.getText().toString().trim();
            if (Faculty.this.unm1 == null || Faculty.this.unm1.trim().length() == 0) {
                Faculty.this.unm.setError("Enter Your Username");
                Faculty.this.unm.requestFocus();
            } else if (Faculty.this.pwd1 == null || Faculty.this.pwd1.trim().length() == 0) {
                Faculty.this.pwd.setError("Enter Your Password");
                Faculty.this.pwd.requestFocus();
            } else {
                new verify().execute(new String[0]);
            }
        }
    }

    class onclicks implements OnClickListener {
        onclicks() {
        }

        public void onClick(View v) {
            Faculty.this.startActivity(new Intent(Faculty.this.getActivity(), FacultytReg.class));
        }
    }

    class verify extends AsyncTask<String, String, String> {
        verify() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Faculty.this.pDialog = new ProgressDialog(Faculty.this.getActivity());
            Faculty.this.pDialog.setMessage("Processing...");
            Faculty.this.pDialog.setIndeterminate(false);
            Faculty.this.pDialog.setCancelable(true);
            Faculty.this.pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("name", Faculty.this.unm1));
            params.add(new BasicNameValuePair("pwd", Faculty.this.pwd1));
            JSONObject json = Faculty.this.jsonParser.makeHttpRequest(Faculty.url_facultylogin, "POST", params);
            Log.d("Response for Login", json.toString());
            try {
                if (json.getInt(Faculty.TAG_SUCCESS) == 1) {
                    Intent i = new Intent(Faculty.this.getActivity(), FacultyHome.class);
                    i.putExtra("uname", Faculty.this.unm1);
                    Faculty.this.startActivity(i);
                } else {
                    Faculty.this.status = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            if (Faculty.this.status) {
                Faculty.this.status = false;
                Toast.makeText(Faculty.this.getActivity(), "Invalid Credentials..!", Toast.LENGTH_LONG).show();
                Faculty.this.startActivity(new Intent(Faculty.this.getActivity(), MainActivity.class));
            }
            Faculty.this.pDialog.dismiss();
        }
    }

    static {
        url_facultylogin = "http://www.ctcorphyd.com/notifications/facultylogin.php";
    }

    public Faculty() {
        this.jsonParser = new JSONParser();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.facultylogin, container, false);
        this.unm = (EditText) view.findViewById(R.id.unm);
        this.pwd = (EditText) view.findViewById(R.id.pwd);
        ((Button) view.findViewById(R.id.login)).setOnClickListener(new onclick());
        ((TextView) view.findViewById(R.id.reg)).setOnClickListener(new onclicks());
        return view;
    }
}
