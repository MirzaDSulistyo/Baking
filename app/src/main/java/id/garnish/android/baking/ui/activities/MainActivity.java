package id.garnish.android.baking.ui.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.garnish.android.baking.IdlingResource.SimpleIdlingResource;
import id.garnish.android.baking.R;
import id.garnish.android.baking.ui.fragments.RecipeFragment;

public class MainActivity extends AppCompatActivity {

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle args = new Bundle();

        if (findViewById(R.id.container_recipe) != null) {
            args.putBoolean(getString(R.string.is_tablet), false);
            recipeFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_recipe, recipeFragment)
                    .commit();
        } else {
            args.putBoolean(getString(R.string.is_tablet), true);
            recipeFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_recipe_land, recipeFragment)
                    .commit();
        }

        getIdlingResource();

    }

}