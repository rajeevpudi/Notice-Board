package com.noticeboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.List;

public class HODReg extends Activity {
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product;
    SQLiteDatabase db;
    EditText email;
    String email1;
    JSONParser jsonParser;
    EditText mno;
    String mno1;
    private ProgressDialog pDialog;
    EditText pwd;
    String pwd1;
    EditText rno;
    String rno1;
    int sts;
    String subject;
    EditText unm;
    String unm1;

    class Register extends AsyncTask<String, String, String> {
        Register() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            HODReg.this.pDialog = new ProgressDialog(HODReg.this);
            HODReg.this.pDialog.setMessage("Registering...");
            HODReg.this.pDialog.setIndeterminate(false);
            HODReg.this.pDialog.setCancelable(true);
            HODReg.this.pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("name", HODReg.this.unm1));
            params.add(new BasicNameValuePair("pwd", HODReg.this.pwd1));
            params.add(new BasicNameValuePair("email", HODReg.this.email1));
            params.add(new BasicNameValuePair("mno", HODReg.this.mno1));
            JSONObject json = HODReg.this.jsonParser.makeHttpRequest(HODReg.url_create_product, "POST", params);
            Log.d("Response for Register", json.toString());
            try {
                if (json.getInt(HODReg.TAG_SUCCESS) == 1) {
                    Intent i = new Intent(HODReg.this.getApplicationContext(), MainActivity.class);
                    i.putExtra("status", HODReg.TAG_SUCCESS);
                    HODReg.this.startActivity(i);
                    HODReg.this.sts = 1;
                    HODReg.this.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            Toast.makeText(HODReg.this.getApplicationContext(), "Registered Successfully..!", Toast.LENGTH_LONG).show();
            HODReg.this.pDialog.dismiss();
        }
    }

    public HODReg() {
        this.sts = 0;
        this.jsonParser = new JSONParser();
    }

    static {
        url_create_product = "http://www.ctcorphyd.com/notifications/hodreg.php";
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hod_register);
        this.unm = (EditText) findViewById(R.id.username);
        this.pwd = (EditText) findViewById(R.id.passwd_edtext);
        this.email = (EditText) findViewById(R.id.email);
        this.mno = (EditText) findViewById(R.id.mno);
    }

    public void onNothingSelected(AdapterView arg0) {
    }

    public void register(View v) {
        this.email1 = this.email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String unmpattern = "[A-Za-z]+";
        this.unm1 = this.unm.getText().toString().trim();
        this.pwd1 = this.pwd.getText().toString().trim();
        this.mno1 = this.mno.getText().toString();
        if (this.unm1 == null || this.unm1.trim().length() == 0) {
            this.unm.setError("Enter Your Username");
            this.unm.requestFocus();
        } else if (!this.unm1.matches(unmpattern)) {
            this.unm.setError("Incorect Name Entry ");
            this.unm.requestFocus();
        } else if (this.pwd1 == null || this.pwd1.trim().length() == 0) {
            this.pwd.setError("Enter Your Password");
            this.pwd.requestFocus();
        } else if (this.email1 == null || this.email1.trim().length() == 0) {
            this.email.setError("Enter Your Email ID");
            this.email.requestFocus();
        } else if (!this.email1.matches(emailPattern)) {
            this.email.setError("Incorect Email Id Entry ");
            this.email.requestFocus();
        } else if (this.mno1 == null || this.mno1.trim().length() == 0) {
            this.mno.setError("Enter Your Mobile No.");
            this.mno.requestFocus();
        } else if (this.mno1.length() != 10) {
            this.mno.setError("Invalid Phone Number.");
            this.mno.requestFocus();
        } else {
            new Register().execute(new String[0]);
        }
    }
}
