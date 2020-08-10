package com.example.nowastenohunger.Adapter;


/*
    PostAdapter Class was used to Make a RecyclerViewAdapter for making User's Post.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nowastenohunger.Class.Post;
import com.example.nowastenohunger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class PostAdapter extends  RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public Context mContext;
    public List<Post>mPost;

    private FirebaseUser firebaseUser;
    private  DatabaseReference databaseReference;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post, parent ,false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(position);


        if(post.getPost().equals(""))
        {
            holder.description.setVisibility(View.GONE);
        }
        else
        {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(post.getPost());
            holder.fullname.setText(post.getfullname());
            holder.time.setText(post.getTime());
        }

        databaseReference =  FirebaseDatabase.getInstance().getReference("Users");



    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView fullname,description,time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname =  itemView.findViewById(R.id.post_user);
            description = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.post_time);
        }
    }


}