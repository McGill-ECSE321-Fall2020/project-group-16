package ca.mcgill.ecse321.artgalleryapplication;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUp extends AppCompatActivity {
    private String error = null;
    private String successUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        refreshErrorMessage();
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

    /**
     * SignUp method
     * @param v
     */
    public void signUp(View v) {
        error = "";

        final TextView firstName = (TextView) findViewById(R.id.user_firstName);
        final TextView lastName = (TextView) findViewById(R.id.user_lastName);
        final TextView email = (TextView) findViewById(R.id.user_email);
        final TextView username = (TextView) findViewById(R.id.user_username);
        final TextView password = (TextView) findViewById(R.id.user_password);

        RequestParams params = new RequestParams();
        params.put("email", email.getText());
        params.put("firstName", firstName.getText());
        params.put("lastName", lastName.getText());
        params.put("password", password.getText());

        HttpUtils.post("users/" + username.getText().toString(), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    successUsername = response.get("username").toString();
                    goToHomeActivity(v);
                } catch ( Exception e) {
                    Log.d("Sign Up", e.getMessage());
                }

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
     * Go to login activity method
     * @param v
     */
    public void goToLoginActivity(View v) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    /**
     * Go to home activity method
     * @param v
     */
    public void goToHomeActivity(View v) {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("username", successUsername);
        startActivity(intent);
    }

    public void goToCheckoutActivity(View v) {
        Intent intent = new Intent(this, Checkout.class);
        startActivity(intent);
    }


    // todo - delete after Home page is made
    public void goToViewArtworkActivity(View v) {
        Intent intent = new Intent(this, ViewArtwork.class);
        intent.putExtra("username", "test1");
        intent.putExtra("artworkId", 881);
        startActivity(intent);
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
}