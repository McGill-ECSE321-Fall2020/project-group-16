package ca.mcgill.ecse321.artgalleryapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ViewArtwork extends AppCompatActivity {
    private String error = null;
    private String username;
    private int artworkId;
    private String artworkImageUri;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_artwork);

        //@TODO - get passed artworkId
        this.username = "aomidi1";
        this.artworkId = 661;

        getArtworkData();

        refreshErrorMessage();

        // Initialize navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.pastorders);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.pastorders:

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



    /**
     * getArtwork
     * @param v
     */
    public void getArtworkData() {
        error = "";

        HttpUtils.get("artworks/" + this.artworkId, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    updateViews(serverResp, serverResp.getJSONArray("artists").getJSONObject(0));
                } catch (JSONException e) {
                    error += e.getMessage();
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

    private void updateViews(JSONObject artworkJSON, JSONObject artistJSON) throws JSONException {
        // format price
        DecimalFormat df2 = new DecimalFormat("#.##");
        double price = artworkJSON.getDouble("price");

        // Set Image to image url
        addImageToView(artworkJSON.getString("imageUrl"), R.id.artwork_image);

        // Get Views
        final TextView titleTV = (TextView) findViewById(R.id.artwork_title);
        final TextView artistNameTV = (TextView) findViewById(R.id.artist_name);
        final TextView creationDateTV = (TextView) findViewById(R.id.creation_date);
        final TextView mediumTV = (TextView) findViewById(R.id.medium);
        final TextView dimensionsTV = (TextView) findViewById(R.id.dimensions);
        final TextView collectionTV = (TextView) findViewById(R.id.collection);
        final TextView descriptionTV = (TextView) findViewById(R.id.description);
        final Button purchaseButton = (Button) findViewById(R.id.purchase_button);


        // Get other fields
        titleTV.setText(artworkJSON.getString("title"));
        artistNameTV.setText(artistJSON.getString("firstName") + " " + artistJSON.getString("lastName"));
        creationDateTV.setText("Created On: " + artworkJSON.getString("creationDate"));
        mediumTV.setText("Medium: " + artworkJSON.getString("medium"));
        dimensionsTV.setText("Dimensions: " + artworkJSON.getString("dimensions"));
        collectionTV.setText("Collection: " + artworkJSON.getString("collection"));
        descriptionTV.setText("Description: " + artworkJSON.getString("description"));

        // if artist is not user and artwork not sold allow purchase
        String artworkStatus = artworkJSON.getString("artworkStatus");
        String artistUsername = artistJSON.getString("username");

        if (!artworkStatus.equals("ForSale")){
            purchaseButton.setText("Sold");
            purchaseButton.setTextSize(17);
            purchaseButton.setClickable(false);
            purchaseButton.setBackgroundColor(Color.parseColor("#3ACBF1"));
        } else if (artistUsername.equals(username)){
            purchaseButton.setVisibility(View.GONE);
        } else {
            purchaseButton.setText(String.format("Purchase - $%.2f", artworkJSON.getDouble("price")));
        }
    }

    public void goToCheckoutActivity(View v) {
        Intent intent = new Intent(this, Checkout.class);
        intent.putExtra("username", username);
        intent.putExtra("artworkId", artworkId);
        startActivity(intent);
    }

    /**
     * Helper method for error handling
     * Displays error message on the screen, if there is any
     */
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.view_artwork_error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    private void addImageToView(String uri, int imageViewId){
        ImageView imageView = (ImageView) findViewById(imageViewId);
        Picasso.get().load(uri).resize(1000, 0).into(imageView);
    }
}