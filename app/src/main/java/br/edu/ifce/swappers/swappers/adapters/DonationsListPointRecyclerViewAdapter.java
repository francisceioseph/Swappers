package br.edu.ifce.swappers.swappers.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.util.RecycleViewOnClickListenerHack;

/**
 * Created by francisco on 13/08/15.
 */


public class DonationsListPointRecyclerViewAdapter extends RecyclerView.Adapter<DonationsListPointRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Place> donationsListPointDataSource;
    private RecycleViewOnClickListenerHack mRecycleViewOnClickListenerHack;



    public DonationsListPointRecyclerViewAdapter(ArrayList<Place> donationsListPointDataSource) {
        this.donationsListPointDataSource = donationsListPointDataSource;

    }

    public void setRecycleViewOnClickListenerHack(RecycleViewOnClickListenerHack recycleViewOnClickListenerHack) {
        this.mRecycleViewOnClickListenerHack = recycleViewOnClickListenerHack;
    }

    @Override
    public DonationsListPointRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_donate_list_books, null);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DonationsListPointRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        Place place = this.donationsListPointDataSource.get(position);
        String address = place.getStreet() + ", " + place.getNumber() + " - "+ place.getDistrict();
        viewHolder.placeNameTextView.setText(place.getName());
        viewHolder.placeAddrTextView.setText(address);
    }

    @Override
    public int getItemCount() {
        return donationsListPointDataSource.size();
    }

    public int getItemID(int position){
        return donationsListPointDataSource.get(position).getId();
    }




    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView placeNameTextView;
        private TextView placeAddrTextView;



        public ViewHolder(View itemView) {
            super(itemView);

            this.placeNameTextView = (TextView) itemView.findViewById(R.id.adapter_text_donate_list_points);
            this.placeAddrTextView = (TextView) itemView.findViewById(R.id.adapter_addr_donate_list_points);



            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(mRecycleViewOnClickListenerHack != null){
                mRecycleViewOnClickListenerHack.onClickListener(view, getAdapterPosition());
            }
        }
    }
}
