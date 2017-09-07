package id.garnish.android.baking.ui.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.garnish.android.baking.R;
import id.garnish.android.baking.models.Step;

public class StepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Step> mSteps;

    private ListItemClickListener lOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Step> stepsOut, int clickedItemIndex);
    }

    public void setData(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    public StepAdapter(List<Step> steps, ListItemClickListener listener) {
        mSteps = steps;
        lOnClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;

        View view = layoutInflater.inflate(R.layout.step_list_item, parent, false);
        viewHolder = new StepViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureViewHolder((StepViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        if (mSteps != null) {
            return mSteps.size();
        }
        return 0;
    }

    private void configureViewHolder(StepViewHolder stepViewHolder, int position) {
        Step step = mSteps.get(position);

        stepViewHolder.shortDescription.setText(step.getShortDescription());
    }

    private class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView shortDescription;

        public StepViewHolder(View itemView) {
            super(itemView);

            shortDescription = (TextView) itemView.findViewById(R.id.tv_step_short_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(mSteps, clickedPosition);
        }
    }
}
