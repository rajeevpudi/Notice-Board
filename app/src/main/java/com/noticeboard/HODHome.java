package com.noticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HODHome extends AppCompatActivity {
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product;
    Bundle extras;
    JSONParser jsonParser;
    private ProgressDialog pDialog;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    String uname;
    private ViewPager viewPager;

    class temp extends AsyncTask<String, String, String> {
        temp() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            HODHome.this.pDialog = new ProgressDialog(HODHome.this);
            HODHome.this.pDialog.setMessage("Loading...");
            HODHome.this.pDialog.setIndeterminate(false);
            HODHome.this.pDialog.setCancelable(true);
            HODHome.this.pDialog.show();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected String doInBackground(String... r8) {

            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("name", HODHome.this.uname));
            JSONObject json = HODHome.this.jsonParser.makeHttpRequest(HODHome.url_create_product, "POST", params);
            Log.d("Response for Login", json.toString());
            try {
                if (json.getInt(HODHome.TAG_SUCCESS) == 1) {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
            /*
            r7 = this;
            r2 = new java.util.ArrayList;
            r2.<init>();
            r4 = new org.apache.http.message.BasicNameValuePair;
            r5 = "name";
            r6 = com.bharat.institute.HODHome.this;
            r6 = r6.uname;
            r4.<init>(r5, r6);
            r2.add(r4);
            r4 = com.bharat.institute.HODHome.this;
            r4 = r4.jsonParser;
            r5 = com.bharat.institute.HODHome.url_create_product;
            r6 = "POST";
            r1 = r4.makeHttpRequest(r5, r6, r2);
            r4 = "Temp :";
            r5 = r1.toString();
            android.util.Log.d(r4, r5);
            r4 = "success";
            r3 = r1.getInt(r4);	 Catch:{ JSONException -> 0x0035 }
            r4 = 1;
            if (r3 != r4) goto L_0x0033;
        L_0x0033:
            r4 = 0;
            return r4;
        L_0x0035:
            r0 = move-exception;
            r0.printStackTrace();
            goto L_0x0033;
            */
//            throw new UnsupportedOperationException("Method not decompiled: com.bharat.institute.HODHome.temp.doInBackground(java.lang.String[]):java.lang.String");
        }

        protected void onPostExecute(String file_url) {
            HODHome.this.pDialog.dismiss();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList;
        private final List<String> mFragmentTitleList;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            this.mFragmentList = new ArrayList();
            this.mFragmentTitleList = new ArrayList();
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) this.mFragmentTitleList.get(position);
        }
    }

    public HODHome() {
        this.extras = null;
        this.jsonParser = new JSONParser();
    }

    static {
        url_create_product = "http://www.ctcorphyd.com/notifications/temp.php";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.notify);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(this.viewPager);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            this.uname = b.getString("uname");
            new temp().execute(new String[0]);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HODNotify(), "Notification");
        viewPager.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.logout /*2131493006*/:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
        return true;
    }
}
