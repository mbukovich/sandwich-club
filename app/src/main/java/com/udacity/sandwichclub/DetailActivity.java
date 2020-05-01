package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sand) {
        // First we must get the IDs of the necessary text views.
        // I took the liberty of adding a Sandwich object parameter so this method will have data to work with.
        TextView akaTV = (TextView) findViewById(R.id.also_known_tv);
        TextView originTV = (TextView) findViewById(R.id.origin_tv);
        TextView ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        TextView descriptionTV = (TextView) findViewById(R.id.description_tv);

        // The alsoKnownAs property of a Sandwich object is a list, so we must loop through the list items.
        List<String> temp = sand.getAlsoKnownAs();
        for (String s : temp)
            akaTV.append(s + "\n"); // We list the items vertically
        if (temp.size() == 0)
            akaTV.setText("No Data for this item.");

        originTV.setText(sand.getPlaceOfOrigin());

        // The ingredients property of Sandwich is a list, so we must loop again.
        temp = sand.getIngredients();
        for (String s : temp)
            ingredientsTV.append(s + "\n"); // We list the ingredients vertically
        if (temp.size() == 0)
            ingredientsTV.setText("Ingredients not found.");

        descriptionTV.setText(sand.getDescription());
    }
}
