package com.noticeboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends Fragment {
    private static final String TAG_SUCCESS = "success";
    private static String notificationlist;
    ArrayList al;
    ArrayAdapter<String> itemsAdapter;
    JSONParser jsonParser;
    JSONArray notice;
    private ProgressDialog pDialog;
    ListView listView;

    /* renamed from: com.bharat.institute.Home.1 */
    class click implements OnItemClickListener {
        click() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        }
    }

    class Listview extends AsyncTask<String, String, String> {
        Listview() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Home.this.pDialog = new ProgressDialog(Home.this.getActivity());
            Home.this.pDialog.setMessage("Loading...");
            Home.this.pDialog.setIndeterminate(false);
            Home.this.pDialog.setCancelable(true);
            Home.this.pDialog.show();
        }

        protected String doInBackground(String... args) {
            JSONObject json = Home.this.jsonParser.makeHttpRequest(Home.notificationlist, "GET", new ArrayList());
            Log.d("Response for Notifns", json.toString());
            try {
                if (json.getInt(Home.TAG_SUCCESS) == 1) {
                    Home.this.notice = json.getJSONArray("notify");
                    Home.this.al.clear();
                    for (int i = 0; i < Home.this.notice.length(); i++) {
                        JSONObject c = Home.this.notice.getJSONObject(i);
                        Home.this.al.add("Date : " + c.getString("date") + " " + "\n\n" + "          " + c.getString("message") + " " + "\n\n" + "                   - " + c.getString("uname") + "(" + c.getString("type") + ")");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            Home.this.pDialog.dismiss();
            itemsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,al);
            listView.setAdapter(itemsAdapter);
            Home.this.itemsAdapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new click());
        }
    }

    static {
        notificationlist = "http://www.ctcorphyd.com/notifications/viewnotify.php";
    }

    public Home() {
        this.al = null;
        this.notice = null;
        this.jsonParser = new JSONParser();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.al = new ArrayList();
        new Listview().execute(new String[0]);
        View v = inflater.inflate(R.layout.home, container, false);
        this.itemsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, this.al);
        listView = (ListView) v.findViewById(R.id.lv);
        listView.setAdapter(this.itemsAdapter);
        this.itemsAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new click());
        return v;
    }
}
