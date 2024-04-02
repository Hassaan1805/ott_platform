package com.hassaan.netflixclone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context mContext;
    private List<MovieModel> movieList;

    public Adaptery(Context mContext, List<MovieModel> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    public void setData(List<MovieModel> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    // Method to get the current list of movies in the adapter
    public List<MovieModel> getMovieList() {
        return movieList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.viewholder_film, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieModel movie = movieList.get(position);
        holder.txt1.setText(movie.getTitle());
        holder.txt2.setText(String.valueOf(movie.getVoteAverage()));

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = movie.getTitle();
                if (title.equals("The Shawshank Redemption")) {
                    Intent intent = new Intent(mContext, shawFragment.class);
                    intent.putExtra("title",title);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }  if (title.equals("The Godfather")) {
                    Intent intent = new Intent(mContext, godfatherFragment.class);
                    intent.putExtra("title",title);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }  if (title.equals("The Godfather Part II")) {
                    Intent intent = new Intent(mContext, godfather2Fragment.class);
                    intent.putExtra("title",title);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }  if (title.equals("Schindler's List")) {
                    Intent intent = new Intent(mContext, schinderFragment.class);
                    intent.putExtra("title",title);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }  if (title.equals("12 Angry Men")) {
                    Intent intent = new Intent(mContext, angrymanFragment.class);
                    intent.putExtra("title",title);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }  if (title.equals("Kung Fu Panda 4")) {
                    Intent intent = new Intent(mContext, pandaFragment.class);
                    intent.putExtra("title",title);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }  if (title.equals("Madame Web")) {
                    Intent intent = new Intent(mContext, webFragment.class);
                    intent.putExtra("title",title);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }  if (title.equals("Godzilla x Kong: The New Empire")) {
                    Intent intent = new Intent(mContext, zillaFragment.class);
                    intent.putExtra("title",title);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }  if (title.equals("Migration")) {
                    Intent intent = new Intent(mContext, migrationFragment.class);
                    intent.putExtra("title",title);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }  if (title.equals("Dune: Part Two")) {
                    Intent intent = new Intent(mContext, duneFragment.class);
                    intent.putExtra("title",title);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView txt1, txt2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv1);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
        }
    }
}
