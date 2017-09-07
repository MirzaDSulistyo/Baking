package id.garnish.android.baking.ui.adapters;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.garnish.android.baking.R;
import id.garnish.android.baking.models.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = RecipeAdapter.class.getSimpleName();

    private Recipe[] mRecipes;
    private Context mContext;

    public RecipeAdapter(Recipe[] recipes, Context context) {
        mRecipes = recipes;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;

        View view = layoutInflater.inflate(R.layout.recipe_list_item, parent, false);
        viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureViewHolder((RecipeViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        if (mRecipes != null) {
            return  mRecipes.length;
        }
        return 0;
    }

    private void configureViewHolder(RecipeViewHolder recipeViewHolder, int position) {
        Recipe recipe = mRecipes[position];

        String imageUrl = recipe.getImage();

        if (!imageUrl.equals("")) {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Glide.with((mContext))
                    .load(builtUri)
                    .into(recipeViewHolder.imageView);
        }

        recipeViewHolder.nameTextView.setText(recipe.getName());
    }

    private class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageView;

        private RecipeViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            imageView = (ImageView) itemView.findViewById(R.id.iv_recipe);
        }

    }
}
