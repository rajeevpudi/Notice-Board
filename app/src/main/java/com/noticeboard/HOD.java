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

public class HOD extends Fragment {
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product;
    JSONParser jsonParser;
    private ProgressDialog pDialog;
    EditText pwd;
    String pwd1;
    boolean status;
    EditText unm;
    String unm1;

    /* renamed from: com.bharat.institute.HOD.1 */
    class click implements OnClickListener {
        click() {
        }

        public void onClick(View v) {
            HOD.this.unm1 = HOD.this.unm.getText().toString().trim();
            HOD.this.pwd1 = HOD.this.pwd.getText().toString().trim();
            if (HOD.this.unm1 == null || HOD.this.unm1.trim().length() == 0) {
                HOD.this.unm.setError("Enter Your Username");
                HOD.this.unm.requestFocus();
            } else if (HOD.this.pwd1 == null || HOD.this.pwd1.trim().length() == 0) {
                HOD.this.pwd.setError("Enter Your Password");
                HOD.this.pwd.requestFocus();
            } else {
                new Register().execute(new String[0]);
            }
        }
    }

    /* renamed from: com.bharat.institute.HOD.2 */
    class clicks implements OnClickListener {
        clicks() {
        }

        public void onClick(View v) {
            HOD.this.startActivity(new Intent(HOD.this.getActivity(), HODReg.class));
        }
    }

    class Register extends AsyncTask<String, String, String> {
        Register() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            HOD.this.pDialog = new ProgressDialog(HOD.this.getActivity());
            HOD.this.pDialog.setMessage("Processing...");
            HOD.this.pDialog.setIndeterminate(false);
            HOD.this.pDialog.setCancelable(true);
            HOD.this.pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("name", HOD.this.unm1));
            params.add(new BasicNameValuePair("pwd", HOD.this.pwd1));
            JSONObject json = HOD.this.jsonParser.makeHttpRequest(HOD.url_create_product, "POST", params);
            Log.d("Response for Login", json.toString());
            try {
                if (json.getInt(HOD.TAG_SUCCESS) == 1) {
                    Intent i = new Intent(HOD.this.getActivity(), HODHome.class);
                    i.putExtra("uname", HOD.this.unm1);
                    HOD.this.startActivity(i);
                } else {
                    HOD.this.status = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            if (HOD.this.status) {
                HOD.this.status = false;
                Toast.makeText(HOD.this.getActivity(), "Invalid Credentials..!", Toast.LENGTH_LONG).show();
                HOD.this.startActivity(new Intent(HOD.this.getActivity(), MainActivity.class));
            }
            HOD.this.pDialog.dismiss();
        }
    }

    static {
        url_create_product = "http://www.ctcorphyd.com/notifications/hodlogin.php";
    }

    public HOD() {
        this.jsonParser = new JSONParser();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hodlogin, container, false);
        this.unm = (EditText) view.findViewById(R.id.unm);
        this.pwd = (EditText) view.findViewById(R.id.pwd);
        ((Button) view.findViewById(R.id.login)).setOnClickListener(new click());
        ((TextView) view.findViewById(R.id.reg)).setOnClickListener(new clicks());
        return view;
    }
}
