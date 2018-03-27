package com.example.auliaramadhan.auliaramadhan_1202150072_modul5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditListActivity extends AppCompatActivity {

    private EditText nama, kegiatan, prioritas;
    int mId = MainToDoList.WORD_ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
//        mengmabil id widget texview yang dibuat
        nama = (EditText) findViewById(R.id.textnama);
        kegiatan = (EditText) findViewById(R.id.textkegiatan);
        prioritas = (EditText) findViewById(R.id.textprioritas);

    }

    public void insertKegiatan(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(ToDoListAdapter.EXTRA_ID, mId);
//        set extra intent yang akan dikembalikan ke main activity dan di insert ke database
        replyIntent.putExtra("nama", nama.getText().toString());
        replyIntent.putExtra("kegiatan", kegiatan.getText().toString());
        replyIntent.putExtra("prioritas", Integer.parseInt(prioritas.getText().toString()));
//        set reesult dan menyelesaikan aktivitas lalu kembalu ke aktivitas sebelumnya
        setResult(RESULT_OK, replyIntent);
        finish();
//
//        mDb.insert(nama.getText().toString(),
//                kegiatan.getText().toString(),
//                Integer.parseInt(prioritas.getText().toString()));
//        finish();
    }
}
