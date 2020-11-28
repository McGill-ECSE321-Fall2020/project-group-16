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

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Profile extends AppCompatActivity {

    private String error = null;
    private String user;
    Context context;
    TextView firstName;
    TextView lastName;
    TextView username;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_profile);

        // Get text views on create
        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        username = (TextView) findViewById(R.id.username);
        description = (TextView) findViewById(R.id.description);
        user = getIntent().getStringExtra("username");
        Log.i("Profile", user);

        // Initialize navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        return true;
                    case R.id.home:
                        Intent orderIntent = new Intent(getApplicationContext(), Home.class);
                        orderIntent.putExtra("username", user);
                        startActivity(orderIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.pastorders:
                        Intent orderIntent1 = new Intent(getApplicationContext(), PastOrders.class);
                        orderIntent1.putExtra("username", user);
                        startActivity(orderIntent1);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        refreshErrorMessage();
        getUser();


    }

    /**
     * Helper method for error handling
     * Displays error message on the screen, if there is any
     */
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

    /**
     * method gets the user from the backend, using the username passed in by previous activity
     */
    public void getUser() {
        error = "";
        HttpUtils.get("users/" + user, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // Set
                    Log.i("Profile", response.toString());
                    firstName.setText(response.get("firstName").toString());
                    lastName.setText(response.get("lastName").toString());
                    username.setText(response.get("username").toString());
                    String descriptionText = response.get("description").toString();
                    Log.i("Description", descriptionText);
                    if (descriptionText.equals("")) {
                        description.setText("You don't have a description yet");
                    } else {
                        description.setText(descriptionText);
                    }
                } catch (Exception e) {
                    Log.d("Profile", e.getMessage());
                }

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

    /**
     * logout method, sends user to the login page
     * @param v
     */
    public void logout(View v) {
        startActivity(new Intent(this, Login.class));
    }

}