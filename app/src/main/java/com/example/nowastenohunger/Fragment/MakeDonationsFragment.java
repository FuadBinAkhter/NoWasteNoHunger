package com.example.nowastenohunger.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nowastenohunger.Class.UserPost;
import com.example.nowastenohunger.Activity.OptionsActivity;
import com.example.nowastenohunger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MakeDonationsFragment extends Fragment
{
    private TextView makeDonationsFragmentTextView;
    private ImageView options;
    private EditText amount,item;
    private Button button;
    private FirebaseAuth mAuth;
    String currentUserID;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_make_donations, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        currentUserID = user.getUid();


        makeDonationsFragmentTextView = view.findViewById(R.id.makeDonationsFragmentTextView);
        options = view.findViewById(R.id.options);
        amount = (EditText) view.findViewById(R.id.amount);
        item = (EditText) view.findViewById(R.id.item);
        button = (Button) view.findViewById(R.id.button);

        databaseReference =  FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });


        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OptionsActivity.class));
            }
        });

        return view;
    }


    /*
        post method was used to read data from MakeDonationsFragment's TextViews & send these data to
        database.
     */

    public void post()
    {
         String Amount = amount.getText().toString().trim();
         String Item = item.getText().toString().trim();

        if(TextUtils.isEmpty(Amount) || TextUtils.isEmpty(Item))
        {
            Toast.makeText(getContext(), "Please fill up all the fields"+currentUserID, Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            UserPost post = new UserPost(Item,Amount);
            databaseReference = databaseReference.child(currentUserID);
            String currentTime = java.text.DateFormat.getDateTimeInstance().format(new Date());

            String Post = "Has "+ Amount + " " + Item + " left .";

            Map<String, Object> updates = new HashMap<String,Object>();
            updates.put("post",Post);
            updates.put("time",currentTime);

            databaseReference.updateChildren(updates);

            Toast.makeText(getContext(), "Post Successful.\nClick on the search icon to see your post.", Toast.LENGTH_SHORT).show();
        }
    }

}