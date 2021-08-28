package it.units.ceschia.help.utility;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewsUtility {
    public static String getTextFromTextView(View view, int id){
        TextView t = (TextView) view.findViewById(id);
        return t.getText()!=null?t.getText().toString():null;
    }
    public static String getTextFromEditText(View view, int id){
        EditText t = (EditText) view.findViewById(id);

        return t.getText()!=null?t.getText().toString():null;
    }
    public static TextView getTextView(View view, int id){
        return (TextView) view.findViewById(id);
    }
    public static EditText getEditText(View view, int id){
        return (EditText) view.findViewById(id);
    }
    public static void setEditTextWithNullCheck(EditText text, String textToSet){
            if(textToSet!=null)
                text.setText(textToSet);
    }
    public static void setTextViewWithNullCheck(TextView text, String textToSet){
        if(textToSet!=null)
            text.setText(textToSet);
    }
}
