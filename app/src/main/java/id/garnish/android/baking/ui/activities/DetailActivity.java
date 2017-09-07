package id.garnish.android.baking.ui.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import id.garnish.android.baking.R;
import id.garnish.android.baking.models.Ingredient;
import id.garnish.android.baking.models.Recipe;
import id.garnish.android.baking.models.Step;
import id.garnish.android.baking.ui.adapters.StepAdapter;
import id.garnish.android.baking.ui.fragments.DetailStepFragment;
import id.garnish.android.baking.ui.fragments.StepFragment;

public class DetailActivity extends AppCompatActivity implements StepAdapter.ListItemClickListener {

    private final static String TAG = DetailActivity.class.getSimpleName();

    Recipe recipe;
    List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(getString(R.string.parcel_recipe));

        steps = recipe.getSteps();

        if (savedInstanceState == null) {
            StepFragment stepFragment = new StepFragment();

            Bundle args = new Bundle();
            args.putParcelable(getResources().getString(R.string.parcel_step), recipe);
            stepFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_step, stepFragment)
                    .commit();

            if (findViewById(R.id.linear_layout_tablet) != null) {

                Step getStep = steps.get(0);

                DetailStepFragment detailStepFragment = new DetailStepFragment();

                Bundle stepArgs = new Bundle();
                stepArgs.putParcelable(getResources().getString(R.string.parcel_step), getStep);
                detailStepFragment.setArguments(stepArgs);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_step_detail, detailStepFragment)
                        .commit();
            }
        }

    }

    @Override
    public void onListItemClick(List<Step> stepsOut, int clickedItemIndex) {
        Step listStep = steps.get(clickedItemIndex);

        DetailStepFragment fragment = new DetailStepFragment();
        Bundle args = new Bundle();
        args.putParcelable(getResources().getString(R.string.parcel_step), listStep);
        fragment.setArguments(args);

        if (findViewById(R.id.linear_layout) != null) {
            // phone layout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_step, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            // tablet layout
//            Log.e(TAG, "list of step : " + listStep.getDescription());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_step_detail, fragment)
                    .addToBackStack(null)
                    .commit();
        }


    }
}
