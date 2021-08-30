package it.units.ceschia.help.reciclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.Contact;
import it.units.ceschia.help.fragment.AddContactDialog;
import it.units.ceschia.help.fragment.EditContactDialog;

public class EditContactListAdapter extends RecyclerView.Adapter<EditContactListAdapter.ViewHolder> {

    private ArrayList<Contact> localDataSet;
    private RecyclerView mRecyclerView;
    private ArrayList<Contact> mList;
    private Context mContext;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
        this.mContext = recyclerView.getContext();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nick;
        private final TextView number;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            nick = (TextView) view.findViewById(R.id.text_view_edit_contact_nick);
            number = (TextView) view.findViewById(R.id.text_view_edit_contact_number);

        }

        public TextView getNick() {
            return nick;
        }
        public TextView getNumber() {
            return number;
        }
    }


    public EditContactListAdapter(ArrayList<Contact> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.edit_contact_list_contact_row, viewGroup, false);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                Contact item = localDataSet.get(itemPosition);

                FragmentManager fm = ((AppCompatActivity)mContext).getSupportFragmentManager();
                EditContactDialog.display(fm,item);

            }
        });
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getNick().setText(localDataSet.get(position).getNick());
        viewHolder.getNumber().setText(localDataSet.get(position).getPhone());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

