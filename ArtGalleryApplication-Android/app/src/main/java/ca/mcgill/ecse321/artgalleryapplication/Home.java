package ca.mcgill.ecse321.artgalleryapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class Home extends AppCompatActivity {
    private String error = null;
    private String username;
    ListView artworks;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = getIntent().getStringExtra("username");

        getArtworks();


        refreshErrorMessage();

        // Initialize navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        Intent profileIntent = new Intent(getApplicationContext(), Profile.class);
                        profileIntent.putExtra("username", username);
                        startActivity(profileIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.pastorders:
                        Intent orderIntent = new Intent(getApplicationContext(), PastOrders.class);
                        orderIntent.putExtra("username", username);
                        startActivity(orderIntent);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
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


    public void getArtworks() {
        error = "";

        HttpUtils.get("artworks/", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                JSONArray jsonArray = response;
                if (jsonArray != null) {

                    ArrayList<String> list = new ArrayList<>();
                    int len = jsonArray.length();
                    if(len == 0) {
                        error += "There are no artworks";
                    }

                    //treat every JSON object of the JSON array response separately
                    for (int i=0;i<len;i++){
                        try {
                            JSONObject artworkJSON = (JSONObject) jsonArray.get(i);
                            String artworkId = artworkJSON.get("artworkId").toString();
                            String artist = artworkJSON.getJSONArray("artists").getJSONObject(0).get("username").toString();
                            String artworkTitle = artworkJSON.get("title").toString();

                            String artwork =
                                    "\nArtwork ID: " + artworkId +
                                            "\nArtwork Title: " + artworkTitle +
                                            "\nArtist: " + artist;

                            list.add(artwork);

                        } catch (JSONException e) {
                            error += e.getMessage();
                        }
                    }

                    //create the list representation of the orders using an Array Adapter
                    artworks = (ListView) findViewById(R.id.listview);
                    ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, list);
                    artworks.setAdapter(adapter);

                    final TextView textView = (TextView) findViewById(R.id.textView);

                    artworks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = (String) parent.getItemAtPosition(position);

                            Pattern p = Pattern.compile("\\d+");
                            Matcher m = p.matcher(selectedItem);
                            m.find();

                            int artwork_id = Integer.parseInt(m.group());
                            goToViewArtworkActivity(artwork_id);
                        }
                    });
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

    public void goToViewArtworkActivity(int artworkID) {
        Intent intent = new Intent(this, ViewArtwork.class);
        intent.putExtra("username", username);
        intent.putExtra("artworkId", artworkID);
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
