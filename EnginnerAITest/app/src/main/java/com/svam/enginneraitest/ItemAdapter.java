package com.svam.enginneraitest;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends
        RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private ArrayList<HitEntry> itemList;
private Context mContext;
private Callback mCallback;
private ArrayList<HitEntry> itemSelectedList;
    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView createdAt;
        public Switch switchState;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txtTitle);
            createdAt = (TextView) view.findViewById(R.id.txtCreatedAt);
            switchState=(Switch) view.findViewById(R.id.switchState);
        }
    }

    public ItemAdapter(ArrayList<HitEntry> itemList, Context ctx,Callback callback) {
        this.itemList = itemList;
        this.mContext=ctx;
        mCallback=callback;
        itemSelectedList= new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final HitEntry entry = itemList.get(position);
        holder.title.setText(entry.postTitle);
        holder.createdAt.setText(entry.createdAt);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable viewColor = (ColorDrawable) v.getBackground();
                int colorId = viewColor.getColor();
                if(colorId == mContext.getResources().getColor(R.color.white))
                {
                    v.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                    holder.switchState.setChecked(true);
                    itemSelectedList.add(entry);
                    //Return the array containing selected items
                    mCallback.onCallback(itemSelectedList);
                }
                else
                {
                    v.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    holder.switchState.setChecked(false);
                    itemSelectedList.remove(entry);
                    //Return the array containing selected items
                    mCallback.onCallback(itemSelectedList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout,parent, false);


        return new MyViewHolder(v);
    }
}