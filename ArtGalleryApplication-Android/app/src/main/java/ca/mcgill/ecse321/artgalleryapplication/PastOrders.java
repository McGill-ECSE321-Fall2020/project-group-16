package ca.mcgill.ecse321.artgalleryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PastOrders extends AppCompatActivity {
    private String error = null;
    //Context context;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //context = this;
        setContentView(R.layout.activity_past_orders);

        username = getIntent().getStringExtra("username");


        refreshErrorMessage();
        getPastOrders();

        // Initialize navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        Intent orderIntent = new Intent(getApplicationContext(), Profile.class);
                        orderIntent.putExtra("username", username);
                        startActivity(orderIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        Intent orderIntent1 = new Intent(getApplicationContext(), Home.class);
                        orderIntent1.putExtra("username", username);
                        startActivity(orderIntent1);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.pastorders:
                        return true;
                }
                return false;
            }
        });
    }

    public void getPastOrders() {
        error = "";

        System.out.println("reached this point: username: " + username);

        HttpUtils.get("orders/get-by-user/" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<String> list = new ArrayList<String>();
                JSONArray jsonArray = (JSONArray) response;
                if (jsonArray != null) {
                    int len = jsonArray.length();
                    for (int i=0;i<len;i++){
                        try {
                            list.add(jsonArray.get(i).toString());

                        } catch (JSONException e) {
                            error += e.getMessage();
                        }
                    }
                }
                System.out.println("all the previous orders " + list);
                refreshErrorMessage();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();

                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }




    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
}