package ca.mcgill.ecse321.artgalleryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.sql.Date;
import java.sql.Time;

import cz.msebera.android.httpclient.Header;

import static java.lang.Math.round;

public class Checkout extends AppCompatActivity {
    private String error = null;
    private String username;
    private int artworkId;
    private String artworkTitle;
    private String artworkImageUri;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Get passed vars from ViewArtwork
        username = getIntent().getStringExtra("username");
        artworkId = getIntent().getIntExtra("artworkId", 0);
        artworkImageUri = getIntent().getStringExtra("artworkImageUri");
        artworkTitle = getIntent().getStringExtra("artworkTitle");
        totalAmount = getIntent().getDoubleExtra("price", 0);

        initViews();
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
     * initialize the views with artwork data
     */
    public void initViews(){
        // add image thumbnail
        addImageToView(artworkImageUri, R.id.artwork_thumbnail);

        // get views
        final TextView titleTV = (TextView) findViewById(R.id.artwork_title);
        final TextView subtotalTV = (TextView) findViewById(R.id.subtotal);
        final TextView taxesTV = (TextView) findViewById(R.id.taxes);
        final TextView totalTV = (TextView) findViewById(R.id.total);

        // Calculate price vars
        double subtotal = totalAmount;
        double taxes = totalAmount * 0.1;
        totalAmount = round(subtotal * 110) / 100.0;

        // set text of views
        titleTV.setText(artworkTitle);
        subtotalTV.setText(String.format("$%.2f", subtotal));
        taxesTV.setText(String.format("$%.2f", taxes));
        totalTV.setText(String.format("$%.2f", totalAmount));
    }

    /**
     * placeOrder
     * @param v
     */
    public void placeOrder(View v) {
        error = "";

        // get view strings
        final String address1 = ((TextView) findViewById(R.id.street_address_1)).getText().toString();
        final String address2 = ((TextView) findViewById(R.id.street_address_2)).getText().toString();
        final String postalCode = ((TextView) findViewById(R.id.postal_code)).getText().toString();
        final String city = ((TextView) findViewById(R.id.city)).getText().toString();
        final String province = ((TextView) findViewById(R.id.province)).getText().toString();
        final String country = ((TextView) findViewById(R.id.country)).getText().toString();
        final String cardnumber = ((TextView) findViewById(R.id.card_number)).getText().toString();
        final String cvv = ((TextView) findViewById(R.id.cvv)).getText().toString();

        // if any string is empty return
        if (address1.isEmpty())
            error += "Enter a Street Address!\n";
        if (postalCode.isEmpty())
            error += "Enter a Postal Code!\n";
        if (city.isEmpty())
            error += "Enter a City!\n";
        if (province.isEmpty())
            error += "Enter a Province!\n";
        if (country.isEmpty())
            error += "Enter a Country!\n";
        if (cardnumber.isEmpty())
            error += "Enter a Card Number!\n";
        if (cvv.isEmpty())
            error += "Enter a CVV!\n";

        if (!error.isEmpty()){
            refreshErrorMessage();
            return;
        }

        // get cur date and time
        Date expDate = Date.valueOf("3000-01-01");
        Date date = new java.sql.Date(System.currentTimeMillis());
        Time time = new java.sql.Time(System.currentTimeMillis());

        // Build controller send
        StringBuilder sb = new StringBuilder();
        sb.append("orders/place-order/full/");
        sb.append(username + "/");
        sb.append(artworkId);
        sb.append("/?amount=" + totalAmount);
        sb.append("&paymentForm=CreditCard");
        sb.append("&paymentDate=" + date);
        sb.append("&cardNumber=" + cardnumber);
        sb.append("&expirationDate=" + expDate);
        sb.append("&cvv=" + cvv);
        sb.append("&paymentTime=" + time);

        sb.append("&toGallery=false");
        sb.append("&estimatedArrivalDate=" + expDate);
        sb.append("&estimatedArrivalTime=16:00:00");
        sb.append("&destStreetAddress=" + address1);
        sb.append("&destStreetAddress2=" + address2);
        sb.append("&destPostalCode=" + postalCode);
        sb.append("&destCity=" + city);
        sb.append("&destProvince=" + province);
        sb.append("&destCountry=" + country);

        HttpUtils.post(sb.toString(), new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
                goToPastOrdersActivity();
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
     * go to PastOrders Activity
     */
    public void goToPastOrdersActivity() {
        Intent intent = new Intent(this, PastOrders.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * Helper method for error handling
     * Displays error message on the screen, if there is any
     */
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.checkout_error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * add Image to view
     * @param uri
     * @param imageViewId
     */
    private void addImageToView(String uri, int imageViewId){
        ImageView imageView = (ImageView) findViewById(imageViewId);
        Picasso.get().load(uri).resize(1000, 0).into(imageView);
    }
}
