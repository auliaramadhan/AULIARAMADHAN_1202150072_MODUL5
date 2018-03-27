package com.example.auliaramadhan.auliaramadhan_1202150072_modul5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Aulia Ramadhan on 24/03/2018.
 */

public class ToDoListOpenHelper extends SQLiteOpenHelper {

    // It's a good idea to always define a log tag like this.
    private static final String TAG = ToDoList.class.getSimpleName();

    // versi, nama table dan database table
    private static final int DATABASE_VERSION = 1;
    private static final String WORD_LIST_TABLE = "table_kegiatan";
    private static final String DATABASE_NAME = "todolist";

    // Nama Column
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "nama";
    public static final String COL_TODO = "list";
    public static final String COL_PRIOR = "priority";

    // Build the SQL query that creates the table.
    private static final String TODO_LIST_TABLE_CREATE =
            "CREATE TABLE " + WORD_LIST_TABLE + " (" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    // id will auto-increment if no value passed
                    COL_NAME + " TEXT, " +
                    COL_TODO + " TEXT, " +
                    COL_PRIOR + " INTEGER );";

    // ... and a string array of columns.
    private static final String[] COLUMNS = {COL_ID, COL_NAME, COL_TODO, COL_PRIOR};

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;


    public ToDoListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//      membuat table pada database jika baru pertma kali
        sqLiteDatabase.execSQL(TODO_LIST_TABLE_CREATE);
        //fillDatabaseWithData(sqLiteDatabase);

    }

    // GAK HARUS ADA SOALNYA AWALNYA EMANG BLM ADA DARTA
//    private void fillDatabaseWithData(SQLiteDatabase sqLiteDatabase) {
//
//        // Create a container for the data.
//        ContentValues values = new ContentValues();
//        values.put(COL_NAME, ) ;
//        values.put(COL_TODO, );
//        values.put(COL_PRIOR, );
//    }

    public ToDoList query(int position) {
//        memebuat kode untuk query table
        String query = "SELECT  * FROM " + WORD_LIST_TABLE +
                " ORDER BY " + COL_ID + " ASC " +
                "LIMIT " + position + ", 1" ;
        Cursor cursor = null;
        ToDoList entry = new ToDoList();

        try {
            if (mReadableDB == null) {
                // mengambil akses sqlite database yang dapat di read
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query, null);
//            cursor mengambil data paling atas
            cursor.moveToFirst();
//            inserrt data ke objek kelas Todolist
            entry.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            entry.setNamaKegiatan(cursor.getString(cursor.getColumnIndex(COL_NAME)));
            entry.setKegiatan(cursor.getString(cursor.getColumnIndex(COL_TODO)));
            entry.setPrioritas(cursor.getInt(cursor.getColumnIndex(COL_PRIOR)));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            cursor.close();
            return entry;
        }

    }

    //method insert
    public long insert(String nama, String kegiatan, int prioritas) {
        long newId = 0;
//        membuat container yang akan duginakan dalam method insert Sqlite
        ContentValues values = new ContentValues();

        values.put(COL_NAME, nama);
        values.put(COL_TODO, kegiatan);
        values.put(COL_PRIOR, prioritas);
        try {
            if (mWritableDB == null) {
                // mengambil akses sqlite database yang dapat di write
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(WORD_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    // menghapus row pada table sesuai id
    public int delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            deleted = mWritableDB.delete(WORD_LIST_TABLE, //table name
                    COL_ID + " =? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d(TAG, "DELETE EXCEPTION! " + e.getMessage());
        }
        return deleted;
    }

    // menghitung jumlah row pada table
    public long count() {
        if (mReadableDB == null) {
            mReadableDB = getReadableDatabase();
        }
        long a =  DatabaseUtils.queryNumEntries(mReadableDB, WORD_LIST_TABLE);
        return a;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(ToDoListOpenHelper.class.getName(),
                "Upgrading database from version " + i + " to "
                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WORD_LIST_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void drop() {
        SQLiteDatabase database = getReadableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + WORD_LIST_TABLE);
        onCreate(database);
    }

    public void update(int id, String word) {
    }
}
