package id.garnish.android.baking.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.garnish.android.baking.R;
import id.garnish.android.baking.models.Recipe;
import id.garnish.android.baking.ui.activities.DetailActivity;
import id.garnish.android.baking.ui.adapters.RecipeAdapter;
import id.garnish.android.baking.utils.NetworkUtils;
import id.garnish.android.baking.utils.RecyclerClickListener;

public class RecipeFragment extends Fragment {

    private final static String TAG = RecipeFragment.class.getSimpleName();

    @BindView(R.id.progress_bar) ProgressBar loading;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    Recipe[] mRecipe;

    LinearLayoutManager layoutManager;
    GridLayoutManager gridLayoutManager;

    boolean isTablet;

    public RecipeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.display_recipes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            isTablet = getArguments().getBoolean(getResources().getString(R.string.is_tablet));
        }

        if (!isTablet) {
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        } else {
            gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            recyclerView.setLayoutManager(gridLayoutManager);
        }

        URL url = NetworkUtils.buildUrl();

        getRecipe(url);

        recyclerView.addOnItemTouchListener(new RecyclerClickListener(getContext(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Recipe recipe = mRecipe[position];
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(getResources().getString(R.string.parcel_recipe), recipe);
                startActivity(intent);
            }
        }));
    }

    private void getRecipe(URL url) {
        if (isNetworkAvailable()) {
            new FetchData().execute(url);
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.error_need_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private class FetchData extends AsyncTask<URL, Void, Recipe[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Recipe[] doInBackground(URL... params) {
            URL url = params[0];
            String json = null;
            try {
                json = NetworkUtils.getResponseFromHttp(url);
                return NetworkUtils.getRecipeDataFromJson(json);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            try {
//                return NetworkUtils.getRecipeDataFromJson(json);
//            } catch (JSONException e) {
//                Log.e(TAG, e.getMessage(), e);
//                e.printStackTrace();
//            }

            return null;
        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {
            super.onPostExecute(recipes);
            if (recipes == null) {
                Toast.makeText(getContext(), "error occurred", Toast.LENGTH_SHORT).show();
                return;
            }
            loading.setVisibility(View.INVISIBLE);
            mRecipe = recipes;

            recyclerView.setAdapter(new RecipeAdapter(recipes, getContext()));
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
