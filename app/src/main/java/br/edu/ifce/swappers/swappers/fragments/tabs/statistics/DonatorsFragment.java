package br.edu.ifce.swappers.swappers.fragments.tabs.statistics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.util.SwappersToast;
import de.hdodenhof.circleimageview.CircleImageView;


public class DonatorsFragment extends Fragment {

    ImageButton nextMonthDonatorImageButton;
    ImageButton previousMonthDonatorImageButton;

    CircleImageView coverMonthDonatorCircleImageView;

    TextView nameMonthDonatorTextView;
    TextView cityMonthDonatorTextView;
    TextView ageMonthDonatorTextView;
    TextView donationsMonthDonatorTextView;

    public DonatorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_donators, container, false);
        this.initViewComponents(rootView);
        this.initViewListeners();

        return rootView;
    }

    private void initViewComponents(View rootView) {
        this.nextMonthDonatorImageButton      = (ImageButton) rootView.findViewById(R.id.next_donator_image_button);
        this.previousMonthDonatorImageButton  = (ImageButton) rootView.findViewById(R.id.previous_donator_image_button);

        this.coverMonthDonatorCircleImageView = (CircleImageView) rootView.findViewById(R.id.cover_month_donator);

        this.nameMonthDonatorTextView = (TextView) rootView.findViewById(R.id.month_donator_name);
        this.cityMonthDonatorTextView = (TextView) rootView.findViewById(R.id.month_donator_city);
        this.ageMonthDonatorTextView = (TextView) rootView.findViewById(R.id.month_donator_age);
        this.donationsMonthDonatorTextView = (TextView) rootView.findViewById(R.id.month_donator_donations);

    }

    private void initViewListeners() {
        this.nextMonthDonatorImageButton.setOnClickListener(this.nextMonthDonatorClickListener());
        this.previousMonthDonatorImageButton.setOnClickListener(this.previousMonthDonatorClickListener());
    }

    private View.OnClickListener previousMonthDonatorClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwappersToast.makeText(getActivity(), "Loading previous month donator... #SQN", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener nextMonthDonatorClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwappersToast.makeText(getActivity(), "Opening next month donator... #SQN", Toast.LENGTH_SHORT).show();
            }
        };
    }

}
