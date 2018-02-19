package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        /* Find the Views */
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView placeOfOrgin = findViewById(R.id.origin_tv);
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        TextView alsoKnownTitle = findViewById(R.id.also_known);
        TextView origin = findViewById(R.id.origin);

        setTitle(sandwich.getMainName());
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        if (sandwich.getIngredients() != null) {
            String ingredientsString = sandwich.getIngredients().toString();
            ingredients.setText(ingredientsString.substring(1, ingredientsString.length() - 1));
        }

        description.setText(sandwich.getDescription());

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
             /* Hide the empty views */
            origin.setVisibility(View.GONE);
            placeOfOrgin.setVisibility(View.GONE);
        } else {
            placeOfOrgin.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getAlsoKnownAs() != null) {
            String alsoKnownAsString = sandwich.getAlsoKnownAs().toString();
            alsoKnownAs.setText(alsoKnownAsString.substring(1, alsoKnownAsString.length() - 1));
        } else {
            /* Hide the empty views */
            alsoKnownAs.setVisibility(View.GONE);
            alsoKnownTitle.setVisibility(View.GONE);
        }
    }
}
