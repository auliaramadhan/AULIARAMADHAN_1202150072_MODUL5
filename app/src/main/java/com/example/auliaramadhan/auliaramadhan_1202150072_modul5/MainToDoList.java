package com.example.auliaramadhan.auliaramadhan_1202150072_modul5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainToDoList extends AppCompatActivity {

    private ToDoListOpenHelper mDB;

    public static final int WORD_EDIT = 1;
    public static final int WORD_ADD = -1;

    private RecyclerView mRecyclerView;
    private ToDoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_to_do_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // BUAT OBJEK DB dari kelas openhelper yang dibuat
        mDB = new ToDoListOpenHelper(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewlisttodo);
        mAdapter = new ToDoListAdapter(this, mDB);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false);
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String colour = sharedPref.getString("sync_frequency", "white");
        changeBackgorundrecyclerview(colour);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start empty edit activity.
                Intent intent = new Intent(getBaseContext(), EditListActivity.class);
                startActivityForResult(intent, WORD_EDIT);
//                mDB.drop();
//                mAdapter.notifyDataSetChanged();
            }
        });

        //Helper class for creating swipe to dismiss and drag and drop functionality
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //Remove the item from the dataset
                mDB.delete(viewHolder.getAdapterPosition());

                //Notify the adapter
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        helper.attachToRecyclerView(mRecyclerView);
    }

    private void changeBackgorundrecyclerview(String color) {

        switch (color){
            case "red" :
                mRecyclerView.setBackgroundColor(Color.RED);break;
            case "yellow" :
                mRecyclerView.setBackgroundColor(Color.YELLOW);break;
            case "green" :
                mRecyclerView.setBackgroundColor(Color.GREEN);break;
            case "brown" :
                mRecyclerView.setBackgroundColor(Color.MAGENTA);break;
            case "cyan" :
                mRecyclerView.setBackgroundColor(Color.CYAN);break;
            case "white" :
                mRecyclerView.setBackgroundColor(Color.WHITE);break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Add code to update the database.
        if (requestCode == WORD_EDIT) {
            if (resultCode == RESULT_OK) {
                String nama = data.getStringExtra("nama");
                String kegiatan = data.getStringExtra("kegiatan");
                int priorotas = data.getIntExtra("prioritas", -99);
                // Update the database
                if (!TextUtils.isEmpty(nama)) {
                    int id = data.getIntExtra(ToDoListAdapter.EXTRA_ID, -99);
                    if (id == WORD_ADD) {
                        mDB.insert(nama,kegiatan,priorotas);
                    }
                    else if (id >= 0) {
                        mDB.update(id, nama);
                    }
                    // Update the UI
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.empty_not_saved,
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("ss", "lagi diresume");
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coba2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
