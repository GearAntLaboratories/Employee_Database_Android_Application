package com.example.grant.employeeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AddEmployee extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etHireDate;
    private EditText etJobTitle;
    private EditText etUsername;
    private EditText etPassword;
    private CheckBox cbAccessRights;

    String fn;
    String ln;
    String em;
    String hd;
    String jt;
    String un;
    String pw;
    String al = "1";

    Boolean validInput = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etHireDate = (EditText) findViewById(R.id.etHireDate);
        etJobTitle = (EditText) findViewById(R.id.etJobTitle);
        etUsername = (EditText) findViewById(R.id.etUsernameAdd);
        etPassword = (EditText) findViewById(R.id.etPasswordAdd);
        cbAccessRights = (CheckBox) findViewById(R.id.cbAdmin);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validInput = true;
                fn = etFirstName.getText().toString();
                ln = etLastName.getText().toString();
                em = etEmail.getText().toString();
                hd = etHireDate.getText().toString();
                jt = etJobTitle.getText().toString();
                un = etUsername.getText().toString();
                pw = etPassword.getText().toString();
                if (cbAccessRights.isChecked()) al = "1";
                else al = "0";


                Log.d("strings:", " " + fn + " " + ln + " " + em + " " + hd + " " + jt + " " + un + " " + pw + " " + al);
                if ((fn.isEmpty()) || (ln.isEmpty()) || (em.isEmpty()) || (hd.isEmpty()) || (jt.isEmpty()) || (un.isEmpty()) || (pw.isEmpty())) {
                    validInput = false;
                }
                if (validInput) {
                    saveEmployee();
                } else Snackbar.make(view, "All Fields Required.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void saveEmployee() {
        AddNewEmp();
    }


    //Connecting to DB and checking UN and PW
    private void AddNewEmp() {

        class ConnectionToDB extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddEmployee.this, "Connecting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                startActivity(new Intent(getApplicationContext(), ViewAllEmployee.class).putExtra("ar", "1"));

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();

                params.put(Config.KEY_EMP_NAME, fn);
                params.put(Config.KEY_EMP_LAST_NAME, ln);
                params.put(Config.KEY_EMP_JOB_TITLE, jt);
                params.put(Config.KEY_EMP_HIRE_DATE, hd);
                params.put(Config.KEY_EMP_EMAIL, em);
                params.put(Config.KEY_EMP_ACCESS_LEVEL, al);
                params.put(Config.KEY_USERNAME, un);
                params.put(Config.KEY_PASSWORD, pw);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_EMP, params);
                return res;
            }
        }

        ConnectionToDB myCon = new ConnectionToDB();
        myCon.execute();
    }


}
