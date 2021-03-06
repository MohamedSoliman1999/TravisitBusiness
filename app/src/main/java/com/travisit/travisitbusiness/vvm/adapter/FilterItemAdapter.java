package com.travisit.travisitbusiness.vvm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travisit.travisitbusiness.R;
import com.travisit.travisitbusiness.model.Category;

import java.util.List;

public class FilterItemAdapter extends RecyclerView.Adapter<FilterItemAdapter.CategoryFilterViewHolder> {

    private final Context context;
    private List<Category> items;
    private FilterSelectionPropagator observer;
    public FilterItemAdapter(List<Category> items, Context context, FilterSelectionPropagator observer) {
        this.items = items;
        this.context = context;
        this.observer = observer;
    }

    @Override
    public CategoryFilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_green_chip, parent, false);
        return new CategoryFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryFilterViewHolder holder, int position) {
        Category item = items.get(position);
        holder.set(item, context, observer);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class CategoryFilterViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        public CategoryFilterViewHolder(@NonNull View itemView) {
            super(itemView);
                category = itemView.findViewById(R.id.item_green_chip_tv_text);
        }
        public void set(Category item, Context context, FilterSelectionPropagator observer) {
            category.setText(item.getName());
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("businessXX","clicked: "+item.getName() );
                    if(item.isSelected()){
                        category.setBackground(context.getDrawable(R.drawable.summer_sky_green_chip));
                        category.setTextColor(context.getColor(R.color.colorGreenMessage));
                        item.setSelected(false);
                    } else {
                        category.setBackground(context.getDrawable(R.drawable.summer_sky_green_chip_selected));
                        category.setTextColor(context.getColor(R.color.colorWhite));
                        item.setSelected(true);
                    }
                    observer.chipSelected(item);

                }
            });
        }
    }

    public interface FilterSelectionPropagator{
        void chipSelected(Category category);
    }

}