package com.example.grant.employeeapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewEmployee extends AppCompatActivity implements View.OnClickListener {

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvID;
    private TextView tvPosition;
    private ListView listView;
    private Boolean accessRights = true;
    private String id;
    private String JSON_STRING;
    private String fn = "", ln = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        Intent intent = getIntent();

        id = intent.getStringExtra(Config.EMP_ID);
        accessRights = intent.getBooleanExtra("ar", false);

        listView = (ListView) findViewById(R.id.listView2);


        tvName = (TextView) findViewById(R.id.tvName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvID = (TextView) findViewById(R.id.tvID);
        tvPosition = (TextView) findViewById(R.id.tvPosition);

        tvID.setText(id);

        getEmployee();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accessRights) {
                    Intent i = new Intent(view.getContext(), EditEmployee.class);
                    i.putExtra("fn", fn);
                    i.putExtra("ln", ln);
                    i.putExtra("em", tvEmail.getText().toString());
                    i.putExtra("id", id);
                    startActivity(i);
                } else
                    Snackbar.make(view, "You do not have access to edit employee details.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
    }

    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewEmployee.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
                JSON_STRING = s;
                showHistory(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_EMP, id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name = c.getString(Config.TAG_NAME);
            String lastName = c.getString(Config.TAG_LAST_NAME);
            String email = c.getString(Config.TAG_EMAIL);

            //for purpose of sending to edit employee
            fn = name;
            ln = lastName;
            String fullName = name + " " + lastName;
            tvName.setText(fullName);
            tvEmail.setText(email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showHistory(String json) {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Log.d("IN HISTORY", "");
        try {
            Log.d("TRY", "CATCH");
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                Log.d("IN LOOP For History:", "i:" + i);
                JSONObject jo = result.getJSONObject(i);


                String title = jo.getString(Config.TAG_TITLE);
                String start = jo.getString(Config.TAG_START_DATE);
                String end = jo.getString(Config.TAG_END_DATE);
                if (end.equalsIgnoreCase("current")) {
                    tvPosition.setText(title);
                }

                HashMap<String, String> history = new HashMap<>();
                history.put(Config.TAG_TITLE, title);
                history.put(Config.TAG_START_DATE, start + "  -->  " + end);
                history.put(Config.TAG_END_DATE, end);
                Log.d("history:", "" + title + start + end);
                list.add(history);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewEmployee.this, list, R.layout.list_item,
                new String[]{Config.TAG_TITLE, Config.TAG_START_DATE},
                new int[]{R.id.name, R.id.id});

        listView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {

    }
}
