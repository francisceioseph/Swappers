package br.edu.ifce.swappers.swappers.fragments.principal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.adapters.NotificationRecyclerViewAdapter;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.RecycleViewOnClickListenerHack;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Notification;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment implements RecycleViewOnClickListenerHack{
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Notification> dataSource;

    public NotificationsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.dataSource = new ArrayList<Notification>();
        Notification notification = new Notification();
        notification.setUserName("Francisco José");

        this.dataSource.add(notification);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);


        NotificationRecyclerViewAdapter adapter = new NotificationRecyclerViewAdapter(this.dataSource);
        adapter.setmRecycleViewOnClickListenerHack(this);

        this.layoutManager = new LinearLayoutManager(getActivity());

        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    @Override
    public void onClickListener(View view, int position) {

    }
}
