package awdal.com.hackath2on.mainfragments;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import awdal.com.hackath2on.Constants;
import awdal.com.hackath2on.MainActivity;
import awdal.com.hackath2on.R;
import awdal.com.hackath2on.otherfragments.CounterFragment;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class MainFragment extends Fragment implements CounterFragment.LiterCounterInteface{
    private MainActivity mActivity;
    private TextView cl;
    private LinearLayout chartLyt;

    private XYMultipleSeriesDataset dataset;
    private XYMultipleSeriesRenderer mRenderer;
    private ArrayList<Double> seriesListAixeta1;
    private ArrayList<Double> seriesListAixeta2;
    private ArrayList<Double> seriesListDutxa;
    private ArrayList<Double> seriesListGeneral;
    private ArrayList<Double> seriesList;
    private XYSeries series;


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
        f.setCounterListener(this);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.card_view, f);
        transaction.commit();


        cl = (TextView) v.findViewById(R.id.litres_minut);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f.startOrStopCounter();
            }
        });
        if (mActivity != null) {
            DecimalFormat df = new DecimalFormat("#0.0");
            DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            df.setDecimalFormatSymbols(dfs);
            cl.setText(df.format(mActivity.getConsumActual()));
        }else
            Log.e("connectat", "interficie nula");


        chartLyt = (LinearLayout) v.findViewById(R.id.chart);
        initChart();
        return v;
    }


    private void initChart(){
        series = new XYSeries("");
        seriesListAixeta1 = new ArrayList();
        seriesListAixeta2 = new ArrayList();
        seriesListDutxa = new ArrayList();
        seriesListGeneral = new ArrayList();

        for (int i = 0;i < 50; i++) {
            series.add(i, 0);//canviar
            seriesListAixeta1.add(0.0);
            seriesListAixeta2.add(0.0);
            seriesListDutxa.add(0.0);
            seriesListGeneral.add(0.0);
        }
        dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);
// Now we create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.rgb(1,87,157));
        XYSeriesRenderer.FillOutsideLine a = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BELOW);


        a.setColor(Color.rgb(66,117,175));

// Include low and max value

        renderer.setDisplayBoundingPoints(true);
// we add point markers
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setLineWidth(10f);
        renderer.addFillOutsideLine(a);
        renderer.setPointStrokeWidth(3);
        mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
// We want to avoid black border
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
// Disable Pan on two axis
        mRenderer.setPanEnabled(false, false);
        mRenderer.setYAxisMax(150);
        mRenderer.setYAxisMin(0);
        mRenderer.setShowGrid(true); // we show the grid


        GraphicalView chartView = ChartFactory.getLineChartView(this.getActivity(), dataset, mRenderer);


        chartLyt.addView(chartView,0);
    }

    public void updateView() {

        Log.i("connectat::general",mActivity.getConsumActual()+"");

        DecimalFormat df = new DecimalFormat("#0.0");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        cl.setText(df.format(mActivity.getConsumActual()));


        double elem = mActivity.getConsumActual();
        switch (mActivity.getSelected()) {
            case Constants.AIXETA1:
                seriesList = seriesListAixeta1;
                break;
            case Constants.AIXETA2:
                seriesList = seriesListAixeta2;
                break;
            case Constants.DUTXA:
                seriesList = seriesListDutxa;
                break;
            case Constants.GENERAL:
                seriesList = seriesListGeneral;
                break;
        }
        seriesList.remove(0);
        series = new XYSeries("");
        for (int i = 0; i < seriesList.size(); i++) {
            series.add(i, seriesList.get(i));

        }
        series.add(seriesList.size(), elem);
            seriesList.add(elem);




        dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);
        GraphicalView chartView = ChartFactory.getLineChartView(getActivity(), dataset, mRenderer);


        chartLyt.addView(chartView,0);
    }

    @Override
    public float getLiterPerMinut() {
        return mActivity.getConsumActual();
    }
}
