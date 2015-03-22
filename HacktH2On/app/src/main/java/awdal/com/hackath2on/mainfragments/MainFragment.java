package awdal.com.hackath2on.mainfragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import awdal.com.hackath2on.MainActivity;
import awdal.com.hackath2on.R;
import awdal.com.hackath2on.otherfragments.CounterFragment;

public class MainFragment extends Fragment {
    private MainActivity mActivity;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        /*Bundle args = new Bundle();

        args.putSerializable("MainActivity", m);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public void setMainActivity(MainActivity ma){
        mActivity = ma;
    }
    public MainFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        final CounterFragment f = new CounterFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.card_view, f);
        transaction.commit();


        TextView cl = (TextView) v.findViewById(R.id.litres_minut);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f.startOrStopCounter();
            }
        });
        if (mActivity != null)
            cl.setText(mActivity.getConsumActual() + "");
        else
            Log.e("connectat", "interficie nula");

        return v;
    }
}
