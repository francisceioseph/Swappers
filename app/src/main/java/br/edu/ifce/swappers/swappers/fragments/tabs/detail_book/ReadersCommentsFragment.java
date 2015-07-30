package br.edu.ifce.swappers.swappers.fragments.tabs.detail_book;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.activities.ProfileActivity;
import br.edu.ifce.swappers.swappers.adapters.CommentRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Comment;
import br.edu.ifce.swappers.swappers.util.RecycleViewOnClickListenerHack;
import br.edu.ifce.swappers.swappers.util.SwappersToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadersCommentsFragment extends Fragment implements RecycleViewOnClickListenerHack {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Comment> dataSource;

    public ReadersCommentsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_readers_comments, container, false);

        this.dataSource = MockSingleton.INSTANCE.createMockedReadersCommentsSource();

        CommentRecyclerViewAdapter adapter = new CommentRecyclerViewAdapter(dataSource);
        adapter.setRecycleViewOnClickListenerHack(this);

        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.readers_comments_list);


        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());



        return rootView;
    }


    @Override
    public void onClickListener(View view, int position) {
        Intent detailBookFragmentIntent;
        detailBookFragmentIntent = new Intent(this.getActivity(), ProfileActivity.class);
        this.startActivity(detailBookFragmentIntent);

    }

}
