package com.example.retrofitdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.retrofitdemo.API.GitAPI;
import com.example.retrofitdemo.model.GitModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //NOTE: For retrofit2 end base Url with /
    private static final String API_ENDPOINT = "https://api.github.com/";

    private TextView userInfoTextView;
    private EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //show userInfo after fetch
        userInfoTextView = (TextView) findViewById(R.id.userInfoTextView);
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);

        Button fetchUserInfoButton = (Button) findViewById(R.id.fetchUserInfo);
        fetchUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1. Create rest adapter with the API class
                Retrofit restAdapter = new Retrofit.Builder()
                        .baseUrl(API_ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                //2. Create service instance (i.e. the interface instance)
                GitAPI gitAPI = restAdapter.create(GitAPI.class);


                //3. call api method from interface
                Call<GitModel> gitAPICall = gitAPI.getFeedFromUser(userNameEditText.getText().toString());
                gitAPICall.enqueue(new Callback<GitModel>() {
                    @Override
                    public void onResponse(Call<GitModel> call, Response<GitModel> response) {
                        GitModel gitModel = response.body();

                        if (gitModel != null) {
                            userInfoTextView.setText("Github Name : " + gitModel.getName() +
                                    "\nRepo Url : " + gitModel.getRepos_url() + "\nLocation : " + gitModel.getLocation());
                        }
                    }

                    @Override
                    public void onFailure(Call<GitModel> call, Throwable throwable) {
                        userInfoTextView.setText("Error: " + throwable.getMessage());
                    }
                });

                //NOTE: Retrofit2 the response is always parsed as String so if you want it to parse response as JSON use retrofit2.GSON converter
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
}
