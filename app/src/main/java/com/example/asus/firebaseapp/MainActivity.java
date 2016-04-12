package com.example.asus.firebaseapp;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Firebase rootUrl = null;
    String Root = "sorkerf";
    ListView mLv;
    TextView nameServer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        String UrlPath = "https://" + Root + ".firebaseio.com/";
        rootUrl = new Firebase(UrlPath);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_add);
                dialog.setTitle("Add data");
                final EditText text = (EditText) dialog.findViewById(R.id.nameData);

                dialog.show();

                Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> post = new HashMap<String, String>();
                        post.put("name", text.getText().toString());
                        rootUrl.push().setValue(post);
                        dialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        AddDB();




        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void AddDB(){
        rootUrl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    mLv = (ListView) findViewById(R.id.listView);
                    ArrayList<String> names = new ArrayList<String>();
                    ArrayList<String> keys = new ArrayList<String>();
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        names.add(datas.child("name").getValue().toString());
                        keys.add(datas.getRef().getKey().toString());
                    }
                    String[] namesArray = new String[names.size()];
                    namesArray = names.toArray(namesArray);
                    String[] keysArray = new String[keys.size()];
                    keysArray = keys.toArray(keysArray);
                    //Toast.makeText(MainActivity.this, keysArray[0], Toast.LENGTH_LONG).show();
                    if (namesArray.length > 0 && keysArray.length > 0){
                        //Toast.makeText(MainActivity.this, rootUrl.toString(), Toast.LENGTH_LONG).show();
                        CustomAdapter adapter = new CustomAdapter(MainActivity.this, namesArray, keysArray, rootUrl);
                        //Toast.makeText(MainActivity.this, adapter.toString(), Toast.LENGTH_LONG).show();
                        mLv.setAdapter(adapter);
                    }else {
                        Toast.makeText(MainActivity.this, "Vacio", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                    Root = "sorkerf";
                    String UrlPath = "https://" + Root + ".firebaseio.com/";
                    AddDB();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.asus.firebaseapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.asus.firebaseapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void settings(MenuItem item) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_add);
        dialog.setTitle("Change Server");
        final EditText text = (EditText) dialog.findViewById(R.id.nameData);
        dialog.show();

        Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameServer = (TextView) findViewById(R.id.nameServer);
                nameServer.setText(text.getText().toString());
                rootUrl = new Firebase("https://" + text.getText().toString() + ".firebaseio.com/");
                AddDB();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
