package com.urjc.android_notes;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TagListAdapter extends ArrayAdapter<String> {

    ArrayList<String> tags;
    Context context;
    Boolean hideDelete;

    public TagListAdapter(Context context, ArrayList<String> items, boolean hideDelete) {
        super(context, R.layout.tag_list, items);
        this.tags = items;
        this.context = context;
        this.hideDelete = hideDelete;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // position equals the number of the current row (position in the list)
        // Convert view equals to each row in the view
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.tag_list, null);

            // Dynamically set the number for the row
            TextView number = convertView.findViewById(R.id.number);
            number.setText(position + 1 + ".");

            // Set the tag name (text set by the user)
            TextView name = convertView.findViewById(R.id.name);
            name.setText(tags.get(position));

            // Delete button and its onClick method
            ImageView deleteBtn = convertView.findViewById(R.id.remove);
            if (!hideDelete) {
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Play a custom sound
                        MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.delete);
                        mp.start();

                        // Remove the item if clicked
                        NoteNewEdit.removeTag(view, position);
                    }
                });
            } else {
                deleteBtn.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
}
