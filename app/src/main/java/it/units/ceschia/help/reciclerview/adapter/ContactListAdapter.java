package it.units.ceschia.help.reciclerview.adapter;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URLEncoder;
import java.util.ArrayList;

import it.units.ceschia.help.R;
import it.units.ceschia.help.entity.Application;
import it.units.ceschia.help.entity.Contact;
import it.units.ceschia.help.entity.Position;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private Position currentPos;
    private ArrayList<Contact> localDataSet;
    private Context mContext;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mContext = recyclerView.getContext();
    }

    public void setLocalDataSet(ArrayList<Contact> contacts) {
        this.localDataSet = contacts;
    }

    public void setCurrentPos(Position currentPos) {
        this.currentPos = currentPos;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nick;
        private final TextView number;
        private final ImageButton button1;
        private final ImageButton button2;
        private final ImageButton button3;
        private final ImageButton button4;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            //TODO: move this out of constructor and put it in onCreateViewHolder

            nick = view.findViewById(R.id.text_view_contact_nick);
            number = view.findViewById(R.id.text_view_contact_number);
            button1 = view.findViewById(R.id.image_button_contact1);
            button2 = view.findViewById(R.id.image_button_contact2);
            button3 = view.findViewById(R.id.image_button_contact3);
            button4 = view.findViewById(R.id.image_button_contact4);


            LinearLayout buttonLayout = view.findViewById(R.id.linear_layout_contact_row_buttons);
            CardView cardView = view.findViewById(R.id.card_view_contact_row);
            ConstraintSet constraintSet = new ConstraintSet();
            ConstraintLayout constraintLayout = view.findViewById(R.id.constraint_layout_contact_row);
            constraintSet.clone(constraintLayout);


            //get density to multiply pixel width and get dp measure
            float factor = view.getContext().getResources().getDisplayMetrics().density;
            view.setOnClickListener(view1 -> {
                int visibility = buttonLayout.getVisibility();
                if (visibility == View.GONE) {
                    //set card view height to wrap content
                    cardView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    //remove text constraint to correctly display buttons
                    constraintSet.clear(R.id.text_view_contact_nick, ConstraintSet.BOTTOM);
                    constraintSet.applyTo(constraintLayout);
                    //set button layout to visible
                    buttonLayout.setVisibility(View.VISIBLE);
                } else {
                    //reset height of card view to 50dp
                    cardView.setLayoutParams(new CardView.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, (int) (50 * factor)));
                    //reset constraint of text views to center text)
                    constraintSet.connect(R.id.text_view_contact_nick, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                    constraintSet.applyTo(constraintLayout);
                    //hide button layout
                    buttonLayout.setVisibility(View.GONE);
                }
            });

        }

        public ImageButton getButton1() {
            return button1;
        }

        public ImageButton getButton2() {
            return button2;
        }

        public ImageButton getButton3() {
            return button3;
        }

        public ImageButton getButton4() {
            return button4;
        }

        public TextView getNick() {
            return nick;
        }

        public TextView getNumber() {
            return number;
        }


    }

    public ContactListAdapter() {
    }

    // Create new views (invoked by the layout manager)
    @NonNull
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

        viewHolder.getNick().setText(localDataSet.get(position).getNick());
        viewHolder.getNumber().setText(localDataSet.get(position).getPhone());

        viewHolder.getButton1().setImageDrawable(findImage(viewHolder.getButton1(),position,"1"));
        viewHolder.getButton1().setOnClickListener(view -> sendMessageToSelectedMedium(view, viewHolder.getAdapterPosition(), "1"));
        viewHolder.getButton2().setImageDrawable(findImage(viewHolder.getButton2(),position,"2"));
        viewHolder.getButton2().setOnClickListener(view -> sendMessageToSelectedMedium(view, viewHolder.getAdapterPosition(), "2"));
        viewHolder.getButton3().setImageDrawable(findImage(viewHolder.getButton3(),position,"3"));
        viewHolder.getButton3().setOnClickListener(view -> sendMessageToSelectedMedium(view, viewHolder.getAdapterPosition(), "3"));
        viewHolder.getButton4().setImageDrawable(findImage(viewHolder.getButton4(),position,"4"));
        viewHolder.getButton4().setOnClickListener(view -> sendMessageToSelectedMedium(view, viewHolder.getAdapterPosition(), "4"));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    private void sendMessageToSelectedMedium(View view, int pos, String priority) {
        Contact c = localDataSet.get(pos);
        Application app = Application.valueOf(c.getPriorities().get(priority).toString());
        PackageManager packageManager = view.getContext().getPackageManager();

        String message = c.getMessage();
        String text = "\n \n" + view.getContext().getString(R.string.predefined_message) +
                "\n -" + view.getContext().getString(R.string.latitude) + currentPos.getLatitude() +
                "\n -" + view.getContext().getString(R.string.longitude) + currentPos.getLongitude() +
                "\n -" + view.getContext().getString(R.string.altitude) + currentPos.getAltitude();
        String finalMessage = message + text;
        switch (app) {
            case WHATSAPP:
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String number = c.getPhone();
                    String url = "https://api.whatsapp.com/send?phone=+39" + number + "&text=" + URLEncoder.encode(finalMessage, "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        view.getContext().startActivity(i);
                    }
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Whatsapp Error", Toast.LENGTH_SHORT).show();
                }
                break;
            case SMS:
                sendSmsMsgFnc(view, "+39" + c.getPhone(), finalMessage);
                break;
            case CALL:
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:+39"+c.getPhone()));
                view.getContext().startActivity(i);
                break;
            case TELEGRAM:
                    Toast.makeText(view.getContext(), "Telegram not yet supported", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    void sendSmsMsgFnc(View view, String mblNumVar, String smsMsgVar) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try {
                SmsManager smsMgrVar = SmsManager.getDefault();
                smsMgrVar.sendTextMessage(mblNumVar, null, smsMsgVar, null, null);
                Toast.makeText(mContext, "Message Sent",
                        Toast.LENGTH_LONG).show();
            } catch (Exception ErrVar) {
                Toast.makeText(mContext, ErrVar.getMessage(),
                        Toast.LENGTH_LONG).show();
                ErrVar.printStackTrace();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions((AppCompatActivity) (view.getContext()), new String[]{Manifest.permission.SEND_SMS}, 10);
            }
        }

    }

    private Drawable findImage(View view, int pos,String priority){

        Contact c = localDataSet.get(pos);
        Application app = Application.valueOf(c.getPriorities().get(priority).toString());
        Drawable drawable = AppCompatResources.getDrawable(view.getContext(), R.drawable.outline_account_circle_24);
        switch (app){
            case WHATSAPP:
                drawable = AppCompatResources.getDrawable(view.getContext(), R.drawable.whats);
                break;
            case TELEGRAM:
                drawable = AppCompatResources.getDrawable(view.getContext(), R.drawable.telegram);
                break;
            case CALL:
                drawable = AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_baseline_call_24);
                break;
            case SMS:
                drawable = AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_baseline_message_24);
                break;
        }

        return drawable;

    }
}

