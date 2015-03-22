package awdal.com.hackath2on.otherfragments;

import android.app.Activity;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import awdal.com.hackath2on.R;

public class CounterFragment extends Fragment {


    private RelativeLayout counterLayout;

    public static CounterFragment newInstance(String param1, String param2) {
        CounterFragment fragment = new CounterFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_counter, container, false);

        counterLayout = (RelativeLayout) v.findViewById(R.id.counterlayout);
        return v;
    }

    public void startOrStopCounter() {
        if(counterLayout.getVisibility() == LinearLayout.GONE) {
            counterLayout.setVisibility(LinearLayout.VISIBLE);
        } else {
           //TODO: stop counter
        }
    }
}
