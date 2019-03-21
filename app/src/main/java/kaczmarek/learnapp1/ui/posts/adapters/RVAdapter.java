package kaczmarek.learnapp1.ui.posts.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import kaczmarek.learnapp1.R;
import kaczmarek.learnapp1.ui.posts.models.Posts;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PostViewHolder> {

    List<Posts> posts;
    RecyclerView recyclerView;

    public RVAdapter(List<Posts> posts,RecyclerView recyclerView) {
        this.posts = posts;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_post,viewGroup,false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        postViewHolder.title.setText(posts.get(i).getTitle());
        postViewHolder.body.setText(posts.get(i).getBody());
        postViewHolder.actionButton.setOnClickListener(v -> {
            if(postViewHolder.body.getVisibility() == View.GONE){
                postViewHolder.actionButton.animate().rotation(180f).start();
                TransitionManager.beginDelayedTransition( recyclerView, new AutoTransition());
                postViewHolder.body.setVisibility(View.VISIBLE);
            } else{
                postViewHolder.actionButton.animate().rotation(0f).start();
                TransitionManager.beginDelayedTransition( recyclerView);
                postViewHolder.body.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
    public static class PostViewHolder extends RecyclerView.ViewHolder{
        ImageButton actionButton;
        CardView cardView;
        TextView title;
        TextView body;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            title = itemView.findViewById(R.id.titlePost);
            body = itemView.findViewById(R.id.bodyPost);
            actionButton = itemView.findViewById(R.id.actionButton);

        }
    }
}
