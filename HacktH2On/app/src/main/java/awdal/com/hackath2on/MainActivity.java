package awdal.com.hackath2on;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.Locale;

import awdal.com.hackath2on.mainfragments.AutomatismFragment;
import awdal.com.hackath2on.mainfragments.MainFragment;



public class MainActivity extends Activity implements MyInterface,Serializable{
    private final static int REQUEST_ENABLE_BT = 1;
    private float aixeta1 = 34.6f;
    private float aixeta2 = 0;
    private float dutxa = 0;
    private float general = 0;
    private int selected = 3;
    private BluetoothAdapter mBluetoothAdapter;
    private MainFragment m;
    private FloatingActionButton aixeta1F;
    private FloatingActionButton aixeta2F;
    private FloatingActionButton dutxaF;
    private FloatingActionButton generalF;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getActionBar() != null) {
            getActionBar().hide();
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);

        aixeta1F = (FloatingActionButton) findViewById(R.id.aixeta1);
        aixeta2F = (FloatingActionButton) findViewById(R.id.aixeta2);
        dutxaF = (FloatingActionButton) findViewById(R.id.dutxa);
        generalF = (FloatingActionButton) findViewById(R.id.general);
        final int selectedColor = getResources().getColor(R.color.selected);
        final int defaultColor = getResources().getColor(R.color.verd);
        generalF.setColorNormal(selectedColor);
        aixeta2F.setColorNormal(defaultColor);
        dutxaF.setColorNormal(defaultColor);
        aixeta1F.setColorNormal(defaultColor);
        aixeta1F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = Constants.AIXETA1;
                aixeta1F.setColorNormal(selectedColor);
                aixeta2F.setColorNormal(defaultColor);
                dutxaF.setColorNormal(defaultColor);
                generalF.setColorNormal(defaultColor);
            }
        });
        aixeta2F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = Constants.AIXETA2;
                aixeta2F.setColorNormal(selectedColor);
                aixeta1F.setColorNormal(defaultColor);
                dutxaF.setColorNormal(defaultColor);
                generalF.setColorNormal(defaultColor);
            }
        });
        dutxaF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = Constants.DUTXA;
                //v.setBackgroundColor(selectedColor);
                dutxaF.setColorNormal(selectedColor);
                aixeta1F.setColorNormal(defaultColor);
                aixeta2F.setColorNormal(defaultColor);
                generalF.setColorNormal(defaultColor);
            }
        });
        generalF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = Constants.GENERAL;
                generalF.setColorNormal(selectedColor);
                aixeta2F.setColorNormal(defaultColor);
                dutxaF.setColorNormal(defaultColor);
                aixeta1F.setColorNormal(defaultColor);
            }
        });

        initBluetooth();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public float getConsumActual() {

        switch (selected){
            case Constants.AIXETA1:
                Log.e("connectat","aixeta1="+aixeta1);
                return aixeta1;
            case Constants.AIXETA2:
                Log.e("connectat","aixeta2="+aixeta2);
                return aixeta2;
            case Constants.DUTXA:
                Log.e("connectat","dutxa="+dutxa);
                return dutxa;
            case Constants.GENERAL:
                Log.e("connectat","general="+general);
                return general;
        }
        return -1;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {




        Fragment[] fragments = new Fragment[2];

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

            m = MainFragment.newInstance();
            m.setMainActivity(MainActivity.this);

            fragments[0] = m;
            fragments[1] = AutomatismFragment.newInstance();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments[position];
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    //BLUETOOTH STUFF

    private void initBluetooth(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        switch (this.enable()){
            case Constants.ENABLED:
                Toast.makeText(getApplicationContext(), "bluetooth is enabled", Toast.LENGTH_LONG).show();
                //show("bluetooth is enabled");

                AcceptThread th =  new AcceptThread(mBluetoothAdapter,this);
                th.start();

                break;

            case Constants.DISABLED:
                show("bluetooth is disabled");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                onDestroy();
                break;
            case Constants.NOT_SUPORTED:
                Toast.makeText(getApplicationContext(),"bluetooth not supported", Toast.LENGTH_LONG).show();
                onDestroy();
                break;
        }

    }




    public int enable(){

        if (mBluetoothAdapter != null) {
            // Device does support Bluetooth
            if (mBluetoothAdapter.isEnabled()) {
                // Enabled. Work with Bluetooth.
                return Constants.ENABLED;
            }
            else
            {
                // Disabled.

                return Constants.DISABLED;

            }
        }else{
            return Constants.NOT_SUPORTED;
        }
    }



    private void show(String text){
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }
    public void showMessageObtained(String text){
        Log.e("connectat","he rebut:"+text);
        if(text.charAt(0) == '/'){//valor actual consum
            //2 aixetes,1 dutxa i 1general en aquest ordre, per exemple: /10.3,99.0,55.2,45.4
            String []split = text.substring(1).split(",");
            aixeta1 = Float.parseFloat(split[0]);
            aixeta2 = Float.parseFloat(split[1]);
            dutxa  = Float.parseFloat(split[2]);
            general = Float.parseFloat(split[3]);

            /*Log.i("connectat::aixeta1",aixeta1+"");
            Log.i("connectat::aixeta2",aixeta2+"");
            Log.i("connectat::dutxa",dutxa+"");
            Log.i("connectat::general",general+"");*/

            if(m != null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        m.updateView();
                    }
                });
            }

        }else{
            notification(text,"Notification");//temporal
        }


        //show("text rebut en el seguent toast");

        //show(text);
    }
    private void notification(String text,String title){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.rsz_generic_water)
                        .setContentTitle(title)
                        .setContentText(text);
        // Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
    public int getSelected(){
        return selected;
    }



}
