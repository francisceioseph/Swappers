package br.edu.ifce.swappers.swappers.fragments.tabs.statistics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.util.AndroidUtils;
import br.edu.ifce.swappers.swappers.util.DonatorsInterface;
import br.edu.ifce.swappers.swappers.util.ImageUtil;
import br.edu.ifce.swappers.swappers.util.StatisticDonatorsTask;
import de.hdodenhof.circleimageview.CircleImageView;


public class DonatorsFragment extends Fragment implements DonatorsInterface{

    ImageButton nextMonthDonatorImageButton;
    ImageButton previousMonthDonatorImageButton;

    CircleImageView coverMonthDonatorCircleImageView;

    TextView nameMonthDonatorTextView;
    TextView cityMonthDonatorTextView;
    TextView ageMonthDonatorTextView;
    TextView donationsMonthDonatorTextView;
    int index = 0;

    ArrayList<User> usersDonators = new ArrayList<>();

    public DonatorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_donators, container, false);
        this.initViewComponents(rootView);
        this.initViewListeners();

//        nameMonthDonatorTextView.setText("Jon");
//        cityMonthDonatorTextView.setText("Castelo Negro, ");
//        ageMonthDonatorTextView.setText("17 anos");
//        donationsMonthDonatorTextView.setText("50 doações");

        if(MockSingleton.INSTANCE.getDonators().isEmpty()) {
            StatisticDonatorsTask task = new StatisticDonatorsTask(getActivity(), this);
            task.execute();
        }else{
            usersDonators = MockSingleton.INSTANCE.getDonators();
            updateCardView(index);
        }

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
               // SwappersToast.makeText(getActivity(), "Loading previous month donator... #SQN", Toast.LENGTH_SHORT).show();
                index--;
                if (index<0) index = usersDonators.size()-1;
                updateCardView(index);
            }
        };
    }

    private View.OnClickListener nextMonthDonatorClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SwappersToast.makeText(getActivity(), "Opening next month donator... #SQN", Toast.LENGTH_SHORT).show();
                index++;
                if(index>=usersDonators.size()) index = 0;
                updateCardView(index);
            }
        };
    }


    @Override
    public void updateStatisticDonators(ArrayList<User> userList) {
        usersDonators = userList;
        MockSingleton.INSTANCE.donators = usersDonators;
        if(usersDonators.isEmpty()){
            nameMonthDonatorTextView.setText("Nenhuma doação este mês");
            cityMonthDonatorTextView.setText("Doe um livro, ");
            ageMonthDonatorTextView.setText("17 anos");
            donationsMonthDonatorTextView.setText("0 doações");
        }else{
            nameMonthDonatorTextView.setText(usersDonators.get(index).getUsername());
            cityMonthDonatorTextView.setText(usersDonators.get(index).getCity()+", ");
            donationsMonthDonatorTextView.setText(String.valueOf(usersDonators.get(index).getDonationNum()) +" doações");
            coverMonthDonatorCircleImageView.setImageBitmap(ImageUtil.StringToBitMap(usersDonators.get(index).getPhoto2()));
        }
    }

    private void updateCardView(int index){
        nameMonthDonatorTextView.setText(usersDonators.get(index).getUsername());
        cityMonthDonatorTextView.setText(usersDonators.get(index).getCity() + ", ");
        donationsMonthDonatorTextView.setText(String.valueOf(usersDonators.get(index).getDonationNum()) + " doações");
        coverMonthDonatorCircleImageView.setImageBitmap(ImageUtil.StringToBitMap(usersDonators.get(index).getPhoto2()));
    }
}
