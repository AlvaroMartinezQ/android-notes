package com.urjc.android_notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.urjc.android_notes.dao.NoteDAO;
import com.urjc.android_notes.database.NotesRDatabase;
import com.urjc.android_notes.generic.GenericValues;

public class MainActivity extends GenericValues {

    Button loginBtn;
    EditText username, password;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("loggedIn", "FALSE");
        editor.commit();

        load();
    }

    public void load() {
        loginBtn = findViewById(R.id.login);

        SharedPreferences sp = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        if (sp.getString("username", "").equals("")) {
            // If the user account has not been initialized request
            // the user to create a new account
            loginBtn.setText(R.string.txt_create_account);
        } else {
            if (sp.getString("loggedIn", "").equals("TRUE")) {
                loadWelcome(sp);
            }
        }
    }

    public void login(View view) {
        SharedPreferences sp = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        if (sp.getString("username", "").equals("")) {
            // Save the username and password
            if (!username.getText().toString().equals("") && !password.getText().toString().equals("")) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username", username.getText().toString());
                // This password should be encrypted - will leave it as is for testing purposes
                editor.putString("password", password.getText().toString());
                editor.putString("loggedIn", "TRUE");
                editor.commit();
                username.setText("");
                password.setText("");
                loginBtn.setText(R.string.txt_login);
                toastIt("User created, now login");
            } else {
                toastIt("Please input your username and password");
            }
        } else {
            // Verify username and password
            if (
                    sp.getString("username", "").equals(username.getText().toString()) &&
                            sp.getString("password", "").equals(password.getText().toString())
            ) {
                loadWelcome(sp);
            } else {
                password.setText("");
                toastIt("Invalid credentials");
            }
        }
    }

    public void startApp(View view) {
        Intent startApp = new Intent(this, NotesHome.class);
        startActivity(startApp);
    }

    public void loadWelcome (SharedPreferences sp) {
        setContentView(R.layout.activity_main);
        welcome = findViewById(R.id.welcome_msg);
        welcome.setText("Welcome, " + sp.getString("username", ""));
    }

    public void deleteAccount(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.txt_delete_acc)
                .setMessage(R.string.txt_delete_acc_desc)
                .setPositiveButton(R.string.btn_continue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Delete the account and notes
                        NotesRDatabase db = NotesRDatabase.getDatabase(view.getContext());
                        NoteDAO nd = db.noteDao();
                        nd.deleteAllNotes();
                        SharedPreferences sp = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove("username");
                        editor.remove("password");
                        editor.commit();
                        setContentView(R.layout.activity_login);
                        load();
                    }
                }).setNegativeButton(R.string.btn_back, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Skip the step and don't do anything
                    }
                }).show();
    }

    public void toHelp(View view) {
        Intent help = new Intent(this, UserHelp.class);
        startActivity(help);
    }
}