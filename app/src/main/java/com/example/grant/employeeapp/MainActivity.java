package com.example.grant.employeeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonConnect;
    private EditText etUsername;
    private EditText etPassword;
    private String JSON_STRING;
    private String accessRights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonConnect = (Button) findViewById(R.id.bConnect);
        buttonConnect.setOnClickListener(this);
        etUsername = (EditText) findViewById(R.id.etUsernameAdd);
        etPassword = (EditText) findViewById(R.id.etPassword);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Connecting to DB and checking UN and PW
    private void connectToDB() {

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        class ConnectionToDB extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Connecting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                signIn();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();

                params.put(Config.KEY_USERNAME, username);
                params.put(Config.KEY_PASSWORD, password);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_CONNECT, params);
                return res;
            }
        }

        ConnectionToDB myCon = new ConnectionToDB();
        myCon.execute();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonConnect) {
            connectToDB();
        }
    }

    private void signIn() {
        String un;
        String success = "";
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {

                JSONObject jo = result.getJSONObject(i);
                un = jo.getString(Config.TAG_USERNAME);
                success = jo.getString(Config.TAG_SUCCESS);
                accessRights = jo.getString("rights");

            }
            if (success.equalsIgnoreCase("true")) {
                startActivity(new Intent(this, ViewAllEmployee.class).putExtra("ar", accessRights));
            } else {
                Toast.makeText(getApplicationContext(), "INVALID USERNAME OR PASSWORD!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

