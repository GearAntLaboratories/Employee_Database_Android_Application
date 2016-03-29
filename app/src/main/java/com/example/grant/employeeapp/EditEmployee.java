package com.example.grant.employeeapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class EditEmployee extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSave;
    private Button buttonDelete;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private CheckBox cbAdminRights;

    String fn;
    String ln;
    String em;
    String id;
    String al = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        Intent i = getIntent();
        fn = i.getStringExtra("fn");
        ln = i.getStringExtra("ln");
        em = i.getStringExtra("em");
        id = i.getStringExtra("id");

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        cbAdminRights = (CheckBox) findViewById(R.id.cbAdmin);

        etFirstName.setText(fn);
        etLastName.setText(ln);
        etEmail.setText(em);

        buttonSave = (Button) findViewById(R.id.bSave);
        buttonSave.setOnClickListener(this);

        buttonDelete = (Button) findViewById(R.id.bDelete);
        buttonDelete.setOnClickListener(this);

    }

    public void saveChanges() {

        class ConnectionToDB extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditEmployee.this, "Connecting...", "Wait...", false, false);

                if (!cbAdminRights.isChecked()) {
                    al = "0";
                }
                fn = etFirstName.getText().toString();
                ln = etLastName.getText().toString();
                em = etEmail.getText().toString();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Intent i = new Intent(getApplicationContext(), ViewEmployee.class);
                i.putExtra("ar", true);
                i.putExtra(Config.EMP_ID, id);
                startActivity(i);

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();


                params.put(Config.KEY_EMP_ID, id);
                params.put(Config.KEY_EMP_NAME, fn);
                params.put(Config.KEY_EMP_LAST_NAME, ln);
                params.put(Config.KEY_EMP_EMAIL, em);
                params.put(Config.KEY_EMP_ACCESS_LEVEL, al);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_UPDATE_EMP, params);
                return res;
            }
        }

        ConnectionToDB myCon = new ConnectionToDB();
        myCon.execute();

    }

    public void deleteEmployee() {
        class ConnectionToDB extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditEmployee.this, "Connecting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Intent intent = new Intent(getApplicationContext(), ViewAllEmployee.class);
                intent.putExtra("ar", "1");
                startActivity(intent);

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMP_ID, id);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_DELETE_EMP, params);
                return res;
            }
        }

        ConnectionToDB myCon = new ConnectionToDB();
        myCon.execute();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSave) {
            saveChanges();
        }

        if (v == buttonDelete) {

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    EditEmployee.this);
            builder.setTitle("Delete Employee");
            builder.setMessage("Are you sure you want to permanently delete this record? ");
            builder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Toast.makeText(getApplicationContext(), "Record not deleted.", Toast.LENGTH_LONG).show();
                        }
                    });
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Toast.makeText(getApplicationContext(), "Deleting Employee Record", Toast.LENGTH_LONG).show();
                            deleteEmployee();
                        }
                    });
            builder.show();
        }

    }


}
