package com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.FrameFragments.FrameRecyclerAdapter.FrameViewHolder.CommandListViewHolder;

import java.util.ArrayList;

import static com.example.reddragon.remote_connection_master_app.MainActivity.commandArrList;

/**
 * Created by RedDragon on 2/14/17.
 */

public class ProfileListAdapter extends RecyclerView.Adapter{
    public static  ArrayList<String> dataArrList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CommandListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.connect_list_viewholder, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CommandListViewHolder) holder).onBind(position);

    }

    @Override
    public int getItemCount() {
        if(commandArrList.size() + 1 > 2){ return commandArrList.size() + 1; }
        return 3;
    }

    public void swap(){

//        if(dataArrList.size() < 3) {
//            while (dataArrList.size() < 3) {
//                dataArrList.add("+New Command");
//            }
//        }

        commandArrList = new ArrayList<>();
        commandArrList.addAll(dataArrList);


        notifyDataSetChanged();

    }

}
