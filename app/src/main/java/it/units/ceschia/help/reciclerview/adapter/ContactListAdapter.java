package it.units.ceschia.help.reciclerview.adapter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.net.URLEncoder;
import java.util.ArrayList;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.Contact;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private ArrayList<Contact> localDataSet;

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

            nick = (TextView) view.findViewById(R.id.text_view_contact_nick);
            number = (TextView) view.findViewById(R.id.text_view_contact_number);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PackageManager packageManager = view.getContext().getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);

                    try {

                        String text= "Ciao, le mie coordinate attuali sono: \n latitudine: ";

                        String url = "https://api.whatsapp.com/send?phone=+39"+ number.getText()+"&text=" + URLEncoder.encode(text, "UTF-8");
                        i.setPackage("com.whatsapp");
                        i.setData(Uri.parse(url));
                        if (i.resolveActivity(packageManager) != null) {
                            view.getContext().startActivity(i);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }

        public TextView getNick() {
            return nick;
        }
        public TextView getNumber() {
            return number;
        }
    }


    public ContactListAdapter(ArrayList<Contact> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_list_contact_row, viewGroup, false);

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

