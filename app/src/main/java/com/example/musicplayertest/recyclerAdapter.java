package com.example.musicplayertest;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<ListItem> listItemList;
    private OnNoteListener mOnNoteListener;

    public recyclerAdapter(ArrayList<ListItem> listItemList, OnNoteListener onNoteListener){
        this.mOnNoteListener = onNoteListener;
        this.listItemList = listItemList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView nameTxt;
        //private ImageView imageView;
        OnNoteListener onNoteListener;

        public MyViewHolder(final View view, OnNoteListener onNoteListener){
            super(view);
            nameTxt = view.findViewById(R.id.stopName);
            //imageView = view.findViewById(R.id.stopImg);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);

    }


    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        holder.nameTxt.setImageResource(listItemList.get(position).getStopName());
        //holder.imageView.setImageResource(listItemList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return listItemList.size();
    }
}
