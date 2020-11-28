package ca.mcgill.ecse321.artgalleryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

    //declaration of the fields
    private String error = null;
    private String username;
    private Context context;
    ListView pastOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);

        // retrieve the username passed by the navbar
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

    /**
     * get the past orders for account with username = this.username
     */
    public void getPastOrders() {
        error = "";

        //http request to backend to retrieve the order by username of the current user
        HttpUtils.get("orders/get-by-user/" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                JSONArray jsonArray = response;
                if (jsonArray != null) {

                    ArrayList<String> list = new ArrayList<>();
                    int len = jsonArray.length();
                    if(len == 0) {
                        error += "You do not have previous orders";
                    }

                    //treat every JSON object of the JSON array response separately
                    for (int i=0;i<len;i++){
                        try {
                            JSONObject orderJSON = (JSONObject) jsonArray.get(i);
                            String orderId = orderJSON.get("orderId").toString();
                            String orderDate = orderJSON.get("orderDate").toString();
                            String totalAmount = orderJSON.get("totalAmount").toString();
                            String artworkTitle = orderJSON.getJSONObject("artwork").get("title").toString();
                            String imageUrl = orderJSON.getJSONObject("artwork").get("imageUrl").toString();
                            String artistName = ((JSONObject) orderJSON.getJSONObject("artwork").getJSONArray("artists").get(0)).get("username").toString();

                            String pastOrder =
                                            "\nOrder ID: " + orderId +
                                            "\nArtwork Title: " + artworkTitle +
                                            "\nArtist: " + artistName +
                                            "\nOrder Date: "  + orderDate +
                                            "\nTotal Amount: " + totalAmount +
                                            "\n";

                            list.add(pastOrder);

                        } catch (JSONException e) {
                            error += e.getMessage();
                        }
                    }

                    //create the list representation of the orders using an Array Adapter
                    pastOrders = (ListView) findViewById(R.id.listview);
                    ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, list);
                    pastOrders.setAdapter(adapter);
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