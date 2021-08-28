package it.units.ceschia.help.utility;

import android.view.View;
import android.widget.TextView;

public class ViewsUtility {
    public static String getTextFromEditTextView(View view, int id){
        TextView t = (TextView) view.findViewById(id);
        return t.getText().toString();
    }
    public static TextView getTextView(View view, int id){
        return (TextView) view.findViewById(id);
    }
}
