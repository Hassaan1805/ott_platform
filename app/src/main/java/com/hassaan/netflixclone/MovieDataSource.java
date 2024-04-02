package com.hassaan.netflixclone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MovieDataSource extends SQLiteOpenHelper {

    public static String db_path;
    public static  String DATABASE_NAME = "netflix_db";
    public static final int DATABASE_VERSION = 1;
    private static final String TABLE_TOP_RATED = "top_rated";
    private static final String TABLE_UPCOMING = "upcoming";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_VOTE_AVERAGE = "vote_average";
    private static final String COLUMN_POSTER_PATH = "poster_path";
    Context context;
    public MovieDataSource(Context mcontext) {
        super(mcontext, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTopRatedTable = "CREATE TABLE " + TABLE_TOP_RATED + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_VOTE_AVERAGE + " REAL,"
                + COLUMN_POSTER_PATH + " TEXT" + ")";
        db.execSQL(createTopRatedTable);

        String createUpcomingTable = "CREATE TABLE " + TABLE_UPCOMING + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_VOTE_AVERAGE + " REAL,"
                + COLUMN_POSTER_PATH + " TEXT" + ")";
        db.execSQL(createUpcomingTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOP_RATED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPCOMING);
        onCreate(db);
    }

    public List<MovieModel> getAllTopRatedMovies() {
        List<MovieModel> movieList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM top_rated", null);
        if (cursor != null) {
            try {
                int titleIndex = cursor.getColumnIndex("title");
                int voteAverageIndex = cursor.getColumnIndex("vote_average");
                int posterPathIndex = cursor.getColumnIndex("poster_path");

                while (cursor.moveToNext()) {
                    MovieModel movie = new MovieModel();
                    movie.setTitle(cursor.getString(titleIndex));
                    movie.setVoteAverage(cursor.getDouble(voteAverageIndex));
                    movie.setPosterPath(cursor.getString(posterPathIndex));

                    movieList.add(movie);
                }
            } catch (Exception e) {
                Log.e("MovieDataSource", "Error retrieving top rated movies: " + e.getMessage());
            } finally {
                cursor.close();
                db.close();
            }
        }
        return movieList;
    }

    public List<MovieModel> getAllUpcomingMovies() {
        List<MovieModel> movieList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM upcoming", null);
        if (cursor != null) {
            try {
                int titleIndex = cursor.getColumnIndex("title");
                int voteAverageIndex = cursor.getColumnIndex("vote_average");
                int posterPathIndex = cursor.getColumnIndex("poster_path");

                while (cursor.moveToNext()) {
                    MovieModel movie = new MovieModel();
                    movie.setTitle(cursor.getString(titleIndex));
                    movie.setVoteAverage(cursor.getDouble(voteAverageIndex));
                    movie.setPosterPath(cursor.getString(posterPathIndex));

                    movieList.add(movie);
                }
            } catch (Exception e) {
                Log.e("MovieDataSource", "Error retrieving upcoming movies: " + e.getMessage());
            } finally {
                cursor.close();
                db.close();
            }
        }
        return movieList;
    }

public void insertMovies1(List<String> movieDataList) {
    SQLiteDatabase db = this.getWritableDatabase();
    try {
        for (String movieData : movieDataList) {
            String[] movies = movieData.split("-\n"); // Split each movie data
            for (String movie : movies) {
                String[] data = movie.split("\n"); // Split key-value pairs
                ContentValues values = new ContentValues();
                for (String line : data) {
                    String[] keyValue = line.split(" : ");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        switch (key) {
                            case "name":
                                values.put("title", value);
                                break;
                            case "vote_average":
                                values.put("vote_average", Double.parseDouble(value));
                                break;
                            case "poster_path":
                                values.put("poster_path", value);
                                break;
                        }
                    }
                }
                boolean exists = checkMovieExists(db, TABLE_UPCOMING, values);
                if (!exists) {
                    // Insert the movie data into the appropriate table
                    db.insert(TABLE_UPCOMING, null, values); // Change "top_rated" to your table name
                } else {
                    // Show a toast message indicating that the movie is already inserted
                    showToast("fetching..");
                }
            }
        }
    } catch (Exception e) {
        Log.e("DatabaseHelper", "Error inserting movies: " + e.getMessage());
    } finally {
        db.close();
    }
}
    public void insertMovies2(List<String> movieDataList) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            for (String movieData : movieDataList) {
                String[] movies = movieData.split("-\n"); // Split each movie data
                for (String movie : movies) {
                    String[] data = movie.split("\n"); // Split key-value pairs
                    ContentValues values = new ContentValues();
                    for (String line : data) {
                        String[] keyValue = line.split(" : ");
                        if (keyValue.length == 2) {
                            String key = keyValue[0].trim();
                            String value = keyValue[1].trim();

                            switch (key) {
                                case "name":
                                    values.put("title", value); // Assuming column name in your table is "title"
                                    break;
                                case "vote_average":
                                    values.put("vote_average", Double.parseDouble(value));
                                    break;
                                case "poster_path":
                                    values.put("poster_path", value);
                                    break;
                                // Add cases for other keys as needed
                            }
                        }
                    }
                    // Insert the movie data into the appropriate table
                    boolean exists = checkMovieExists(db, TABLE_UPCOMING, values);
                    if (!exists) {
                        // Insert the movie data into the appropriate table
                        db.insert(TABLE_TOP_RATED, null, values); // Change "top_rated" to your table name
                    } else {
                        // Show a toast message indicating that the movie is already inserted
                        showToast("fetching..");
                    }
                }
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting movies: " + e.getMessage());
        } finally {
            db.close();
        }
    }
    private boolean checkMovieExists(SQLiteDatabase db, String tableName, ContentValues values) {
        String title = values.getAsString("title"); // Assuming title is the unique identifier for movies
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE title=?", new String[]{title});
        boolean exists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
