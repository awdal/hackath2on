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
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import awdal.com.hackath2on.R;

public class CounterFragment extends Fragment {


    public interface LiterCounterInteface {
        public float getLiterPerMinut();
    }

    private RelativeLayout counterLayout;
    private int count;
    private Timer T;
    private TextView counterView1, counterView2;
    private boolean isRunning;

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
        counterView1 = (TextView) v.findViewById(R.id.counterview1);
        counterView2 = (TextView) v.findViewById(R.id.counterview2);

        counterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(T != null)
                    T.cancel();

                //TODO: Register
                count = 0;
                counterLayout.setVisibility(View.GONE);
            }
        });
        return v;
    }


    private String[] secondsToString(int pTime) {
        final int hour = pTime/3600;
        final int min = (pTime%3600)/60;
        final int sec = pTime%60;

        final String strHour = placeZeroIfNeeded(hour);
        final String strMin = placeZeroIfNeeded(min);
        final String strSec = placeZeroIfNeeded(sec);

        return new String[]{strHour, strMin, strSec};
    }

    private String placeZeroIfNeeded(int number) {
        return (number >=10)? Integer.toString(number):String.format("0%s",Integer.toString(number));
    }



    public void startOrStopCounter() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] time = secondsToString(count);
                        counterView1.setText(String.format("%s:%s",time[0], time[1]));
                        counterView2.setText(String.format("%s",time[2]));
                        count++;
                    }
                });
            }
        };

        if(counterLayout.getVisibility() == LinearLayout.GONE) {
            counterLayout.setVisibility(LinearLayout.VISIBLE);
            if(T!=null)
                T.cancel();
            T=new Timer();
            count = 0;
            String[] time = secondsToString(count);
            counterView1.setText(String.format("%s:%s",time[0], time[1]));
            counterView2.setText(String.format("%s",time[2]));
            isRunning = true;
            T.scheduleAtFixedRate(task, 0, 1000);
        } else {
            if(isRunning) {
                T.cancel();
            }else {
                T = new Timer();
                T.scheduleAtFixedRate(task, 0, 1000);
            }
            isRunning = !isRunning;
        }
    }
}
