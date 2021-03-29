package com.mostafabor3e.eat_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mostafabor3e.eat_app.Model.Comment;
import com.mostafabor3e.eat_app.R;

import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.CommentHolder> {
    public List<Comment>comments;
    Context  context;

    public AdapterComment(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,null,false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Comment comment=comments.get(position);
        holder.ratingBar.setRating(comment.getRating());
        holder.comment.setText(comment.getComment());
        holder.phone.setText(comment.getUserPhone());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        TextView phone ,comment;
        RatingBar ratingBar;
        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            phone=itemView.findViewById(R.id.comment_tv_phone);
            comment=itemView.findViewById(R.id.comment_tv_comment);
            ratingBar=itemView.findViewById(R.id.comment_rate);



        }
    }
}
