package com.noticeboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNotify1 extends Activity {
    private static final String TAG_SUCCESS = "success";
    private static String notifys;
    private static String temp;
    String dt;
    JSONParser jsonParser;
    EditText notification;
    String notify;
    private ProgressDialog pDialog;
    int sts;
    String type;
    String uname;

    class Notify extends AsyncTask<String, String, String> {
        Notify() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            AddNotify1.this.pDialog = new ProgressDialog(AddNotify1.this);
            AddNotify1.this.pDialog.setMessage("Processing...");
            AddNotify1.this.pDialog.setIndeterminate(false);
            AddNotify1.this.pDialog.setCancelable(true);
            AddNotify1.this.pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("message", AddNotify1.this.notify));
            params.add(new BasicNameValuePair("name", AddNotify1.this.uname));
            params.add(new BasicNameValuePair("type", AddNotify1.this.type));
            params.add(new BasicNameValuePair("date", AddNotify1.this.dt));
            JSONObject json = AddNotify1.this.jsonParser.makeHttpRequest(AddNotify1.notifys, "POST", params);
            Log.d("Response for Register", json.toString());
            try {
                if (json.getInt(AddNotify1.TAG_SUCCESS) == 1) {
                    AddNotify1.this.startActivity(new Intent(AddNotify1.this.getApplicationContext(), HODHome.class));
                    AddNotify1.this.sts = 1;
                    AddNotify1.this.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            Toast.makeText(AddNotify1.this.getApplicationContext(), "Notication Added  Successfully..!", Toast.LENGTH_LONG).show();
            AddNotify1.this.pDialog.dismiss();
        }
    }

    class temp extends AsyncTask<String, String, String> {
        temp() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            AddNotify1.this.pDialog = new ProgressDialog(AddNotify1.this);
            AddNotify1.this.pDialog.setMessage("Loading...");
            AddNotify1.this.pDialog.setIndeterminate(false);
            AddNotify1.this.pDialog.setCancelable(true);
            AddNotify1.this.pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("name", AddNotify1.this.uname));
            JSONObject json = AddNotify1.this.jsonParser.makeHttpRequest(AddNotify1.temp, "GET", params);
            Log.d("Temp :", json.toString());
            try {
                if (json.getInt(AddNotify1.TAG_SUCCESS) == 1) {
                    AddNotify1.this.uname = json.getString("uname");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            AddNotify1.this.pDialog.dismiss();
        }
    }

    public AddNotify1() {
        this.sts = 0;
        this.jsonParser = new JSONParser();
        this.type = "Faculty";
    }

    static {
        temp = "http://www.ctcorphyd.com/notifications/temp1.php";
        notifys = "http://www.ctcorphyd.com/notifications/addnotify.php";
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnotify);
        this.notification = (EditText) findViewById(R.id.notification);
        new temp().execute(new String[0]);
    }

    public void onNothingSelected(AdapterView arg0) {
    }

    public void addNotification(View v) {
        this.dt = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        this.notify = this.notification.getText().toString().trim();
        if (this.notify == null || this.notify.trim().length() == 0) {
            this.notification.setError("Please type the Text");
            this.notification.requestFocus();
            return;
        }
        new Notify().execute(new String[0]);
    }
}
