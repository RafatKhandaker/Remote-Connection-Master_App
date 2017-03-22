package com.example.reddragon.remote_connection_master_app.View.FrameFragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.reddragon.remote_connection_master_app.R;
import com.example.reddragon.remote_connection_master_app.View.MainContainerAdapter;
import com.example.reddragon.remote_connection_master_app.View.ViewHolder.CardViewHolder;

import java.util.ArrayList;

import static com.example.reddragon.remote_connection_master_app.MainActivity.Connect_Count;
import static com.example.reddragon.remote_connection_master_app.MainActivity.idArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.ipAddArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.ipArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.portAddArray;
import static com.example.reddragon.remote_connection_master_app.MainActivity.preConDatabase;
import static com.example.reddragon.remote_connection_master_app.MainActivity.storeRemovedIDData;

/**
 * Created by RedDragon on 2/11/17.
 */

public class RecyclerClass extends Fragment {

    private  RecyclerView preConnectRecycler;
    public static RecyclerView.Adapter recyclerClassAdapter;
    private  RecyclerView.LayoutManager recyclerLayoutManager;
    private int prevCount = Connect_Count;

    ImageButton addNewCard;
    ImageButton saveDataBtn;
    ImageButton addUserBtn;

    private CardViewHolder cardViewHolder;

    public RecyclerClass(){}

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_connect, container, false);
        cardViewHolder = new CardViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.main_card_holder, null));

        addNewCard = (ImageButton) view.findViewById(R.id.add_hostname_btn);
        saveDataBtn = (ImageButton) view.findViewById(R.id.save_host_icon);
        addUserBtn = (ImageButton) view.findViewById(R.id.add_prof_btn);

        // initiate recycler
        initiateRecycler(view);

        // Swipe to remove Data
        initiateRemoveOnSwipe();

        //initiate Button Click
        initiateButtonClickListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void initiateRecycler(View view){
        preConnectRecycler = (RecyclerView) view.findViewById(R.id.main_recycler);
        recyclerLayoutManager = new LinearLayoutManager(getContext());
        recyclerClassAdapter = new MainContainerAdapter();
        preConnectRecycler.setLayoutManager(recyclerLayoutManager);
        preConnectRecycler.setAdapter(recyclerClassAdapter);
    }

    public void initiateRemoveOnSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback
                (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                //Remove swiped item from list and notify the RecyclerView
                if(Connect_Count > 0) {

                    storeRemovedIDData.add(cardViewHolder.getAdapterPosition());

                    ipAddArray.remove(cardViewHolder.getAdapterPosition()+1);
                    portAddArray.remove(cardViewHolder.getAdapterPosition()+1);
                    Connect_Count--;

                    recyclerClassAdapter.notifyDataSetChanged();
                }

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(preConnectRecycler);
    }

    public void initiateButtonClickListener(){
        addNewCard.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                ipAddArray.add("");
                portAddArray.add("");
                Connect_Count++;
                recyclerClassAdapter.notifyDataSetChanged();
            }
        });
/**  redo the save data to create host name per host user **/
        saveDataBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RC class ipArray: ", ""+ipArray.size() + " " +ipAddArray.size());
                int x = ipArray.size();

                if(x == (ipAddArray.size())){
                    for(int i = 0; i < storeRemovedIDData.size(); i++){
                        int y = storeRemovedIDData.get(i);
                        Log.d("removed id data: ", "" +storeRemovedIDData.get(i));

                        preConDatabase.deleteData(String.valueOf(y));

                        while(y < prevCount) {
                            Log.d("update id data: ", "" +y);

                            preConDatabase.updateData(
                                    String.valueOf(y+1), String.valueOf(y));
                                    y++;
                        }
                    }
                    storeRemovedIDData = new ArrayList<Integer>();
                }


                while(x < (ipAddArray.size()) ){
                    preConDatabase.insertData(String.valueOf(x),
                            ipAddArray.get(x), portAddArray.get(x));
                    x++;
                }
                for(int i = 0; i < idArray.size() ; i++){
                    Log.d("RC class id array: ", "" +idArray.get(i));

                }

                Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT)
                        .show();

            }
        });


        addUserBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}

