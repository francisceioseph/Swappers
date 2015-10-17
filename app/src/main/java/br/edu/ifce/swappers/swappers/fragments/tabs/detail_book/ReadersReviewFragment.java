package br.edu.ifce.swappers.swappers.fragments.tabs.detail_book;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.activities.DetailBookActivity;
import br.edu.ifce.swappers.swappers.miscellaneous.adapters.CommentRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.model.Review;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.RecycleViewOnClickListenerHack;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.RetrieveReviewsTaskInterface;
import br.edu.ifce.swappers.swappers.miscellaneous.SwappersToast;
import br.edu.ifce.swappers.swappers.miscellaneous.tasks.RetrieveReviewsTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadersReviewFragment extends Fragment implements RecycleViewOnClickListenerHack,
        RetrieveReviewsTaskInterface,
        DetailBookActivity.RequestRecyclerViewUpdate {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Review> dataSource;
    CommentRecyclerViewAdapter adapter;

    public ReadersReviewFragment() {
        dataSource = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loadReaderComments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_readers_comments, container, false);

        adapter = new CommentRecyclerViewAdapter(dataSource);
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
        //Implementar na versão 2 do Swappers.

    }

    @Override
    public void onReceiveReviews(ArrayList<Review> reviews) {
        if (reviews != null) {
            this.dataSource.clear();
            this.dataSource.addAll(reviews);
            this.adapter.notifyDataSetChanged();
        }
        else {
            SwappersToast.makeText(getActivity(), getString(R.string.no_comments_found_message), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        DetailBookActivity detailBookActivity = (DetailBookActivity) activity;
        detailBookActivity.setRecyclerViewDelegate(this);
    }

    @Override
    public void reloadRecyclerView() {
        this.loadReaderComments();
    }

    private void loadReaderComments(){
        RetrieveReviewsTask task = new RetrieveReviewsTask(getActivity(), this);
        DetailBookActivity detailBookActivity = (DetailBookActivity) getActivity();

        task.execute(detailBookActivity.getBook().getId());
    }
}
