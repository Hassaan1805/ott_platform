package com.hassaan.netflixclone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class historyContent extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "history.db";
    public static final String TABLE_NAME = "user_history";
    public static final String COL_1 = "id";
    public static final String COL_2 = "username";
    public static final String COL_3 = "video_name";
    public static final String COL_4 = "timestamp";
    public static final String COL_5 = "duration";

    public historyContent(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, video_name TEXT, timestamp TEXT, duration TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username, String video_name,String timestamp, String duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, video_name);
        contentValues.put(COL_4, timestamp);
        contentValues.put(COL_5, duration);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    public Cursor getData1(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username='" + username + "'", null);

    }
    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public boolean updateData(String inputVideoname, String inputTimestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timestamp", inputTimestamp);
        int rowsAffected = db.update(TABLE_NAME, contentValues, "video_name=?", new String[]{inputVideoname});
        return rowsAffected > 0;
    }

    public boolean updateData2(String id, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "=?", new String[]{id});
        if (cursor != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_3, password);
            long result = db.update(TABLE_NAME, contentValues, COL_1 + "=?", new String[]{id});
            cursor.close(); // Close cursor when done with it
            return result != -1;
        }
        return false;
    }
    public boolean videoExists(String username, String videoName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_2 + "=? AND " + COL_3 + "=?", new String[]{username, videoName});
        boolean exists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }

}
