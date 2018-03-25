package com.example.auliaramadhan.auliaramadhan_1202150072_modul5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aulia Ramadhan on 23/03/2018.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private ArrayList<ToDoList> mListData;
    private Context mContext;
    private ToDoListOpenHelper mDB;

    public static final String EXTRA_ID = "ID";

    public ToDoListAdapter(Context mContext, ToDoListOpenHelper DB) {
        this.mListData = mListData;
        this.mContext = mContext;
        this.mDB = DB;
    }

    @Override
    public ToDoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_list_todo, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDoList current = mDB.query(position);
        holder.bindTo(current);

       // final WordViewHolder h = holder;
    }

    @Override
    public int getItemCount() {
        return (int) mDB.count();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView namakegiatan;
        private TextView kegiatan;
        private TextView prioritas;

        public ViewHolder(View itemView) {
            super(itemView);
            namakegiatan = (TextView)itemView.findViewById(R.id.namakegiatan)  ;
            kegiatan = (TextView)itemView.findViewById(R.id.kegiatan) ;
            prioritas = (TextView)itemView.findViewById(R.id.priority) ;
        }

        void bindTo(ToDoList list){

            //Populate the textviews with data
            namakegiatan.setText(list.getNamaKegiatan());
            kegiatan.setText(list.getKegiatan());
            prioritas.setText(String.valueOf(list.getPrioritas()));
        }
    }
}
