package id.garnish.android.baking.ui.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.garnish.android.baking.R;
import id.garnish.android.baking.models.Ingredient;

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Ingredient> mIngredients;

    public void setData(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    public IngredientAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;

        View view = layoutInflater.inflate(R.layout.ingredient_list_item, parent, false);
        viewHolder = new IngredientViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureViewHolder((IngredientViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null) {
            return mIngredients.size();
        }
        return 0;
    }

    private void configureViewHolder(IngredientViewHolder ingredientViewHolder, int position) {
        Ingredient ingredient = mIngredients.get(position);

        ingredientViewHolder.ingredientTextView.setText(ingredient.getIngredient());

        String quantity = String.valueOf(ingredient.getQuantity());
        ingredientViewHolder.quantityTextView.setText(quantity);

        ingredientViewHolder.measureTextView.setText(ingredient.getMeasure());
    }

    private class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientTextView;
        TextView quantityTextView;
        TextView measureTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            ingredientTextView = (TextView) itemView.findViewById(R.id.tv_ingredient);
            quantityTextView = (TextView) itemView.findViewById(R.id.tv_ingredient_quantity);
            measureTextView = (TextView) itemView.findViewById(R.id.tv_ingredient_unit);
        }
    }
}
