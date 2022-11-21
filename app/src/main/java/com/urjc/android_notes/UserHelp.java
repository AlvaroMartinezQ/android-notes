package com.urjc.android_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import com.urjc.android_notes.generic.GenericValues;

public class UserHelp extends GenericValues {

    TextView userHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_help);
        userHelp = findViewById(R.id.userHelp);
        Resources resources = getResources();
        String[] textString = resources.getStringArray(R.array.userHelp);
        for (String s: textString) {
            userHelp.append(s + "\n\n");
        }
    }
}