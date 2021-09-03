package it.units.ceschia.help.reciclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.Application;
import it.units.ceschia.help.utility.ItemTouchHelperAdapter;

public class PrioritiesAdapter extends RecyclerView.Adapter<PrioritiesAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    ArrayList<Application> priorities;

    public PrioritiesAdapter() {

    }

    public ArrayList<Application> getPriorities() {
        return priorities;
    }

    public void setPriorities(HashMap<String, Application> priorities) {
        this.priorities = new ArrayList<>();
        for(int i= 1 ; i<5;i++)
            this.priorities.add(priorities.get(Integer.toString(i)));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.priority_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.getPriority().setText(priorities.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return priorities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView priority;
        public ViewHolder(@NonNull View view) {
            super(view);
            priority = view.findViewById(R.id.text_view_priority_name);
        }

        public TextView getPriority() {
            return priority;
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(priorities, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(priorities, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }


}
