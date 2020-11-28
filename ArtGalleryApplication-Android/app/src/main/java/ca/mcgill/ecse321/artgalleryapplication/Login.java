package ca.mcgill.ecse321.artgalleryapplication;

import android.content.Intent;
import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {
    private String error = null;
    private String successUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        refreshErrorMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * login method
     * @param v
     */
    public void login(View v) {
        error = "";

        final TextView username = (TextView) findViewById(R.id.user_username);
        final TextView password = (TextView) findViewById(R.id.user_password);

        //http request to the backend to attempt to login the user
        HttpUtils.get("users/" + username.getText().toString() + "?password=" + password.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    if(!response.get("username").equals(username.getText().toString()) || !response.get("password").equals(password.getText().toString())) {
                        error += "Incorrect password";

                        refreshErrorMessage();
                        password.setText("");

                        return;
                    }
                    successUsername = response.get("username").toString();
                    Log.i("Login", response.toString());
                }catch (JSONException e) {
                    error += e.getMessage();
                    refreshErrorMessage();
                    return;
                }

                goToHomeActivity(v);
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
     * Go to sign up activity method
     * @param v
     */
    public void goToSignUpActivity(View v) {
        Intent intent = new Intent(this, SignUp.class);
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
