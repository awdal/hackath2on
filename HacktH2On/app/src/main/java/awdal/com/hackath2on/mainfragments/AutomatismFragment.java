package awdal.com.hackath2on.mainfragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;

import awdal.com.hackath2on.R;

public class AutomatismFragment extends Fragment {
    private LinearLayout chartLyt;
    private Button rndButton;
    public static AutomatismFragment newInstance() {
        AutomatismFragment fragment = new AutomatismFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public AutomatismFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_automatism, container, false);
        chartLyt = (LinearLayout) v.findViewById(R.id.chart);
        rndButton = (Button) v.findViewById(R.id.random);
        rndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double elem = Math.random()*Math.random()*10;
                seriesList.remove(0);
                series = new XYSeries("Consum d'aigua");
                for (int i = 0; i < seriesList.size(); i++) {
                    series.add(i, seriesList.get(i));

                }
                series.add(seriesList.size(),elem);
                seriesList.add(elem);




                dataset = new XYMultipleSeriesDataset();
                dataset.addSeries(series);
                GraphicalView chartView = ChartFactory.getLineChartView(getActivity(), dataset, mRenderer);


                chartLyt.addView(chartView,0);
            }
        });
        initChart();
        return v;
    }
    private XYMultipleSeriesDataset dataset;
    private XYMultipleSeriesRenderer mRenderer;
    private ArrayList<Double> seriesList;
    private XYSeries series;
    private void initChart(){
        series = new XYSeries("Consum d'aigua");
        seriesList = new ArrayList();

        for (int i = 0;i < 10; i++) {
            double elem = Math.random()*Math.random()*10;
            series.add(i, elem);//canviar
            seriesList.add(elem);
        }
        dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);
// Now we create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        XYSeriesRenderer.FillOutsideLine a = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BELOW);


        a.setColor(Color.rgb(0,255,0));

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
        mRenderer.setYAxisMax(35);
        mRenderer.setYAxisMin(0);
        mRenderer.setShowGrid(true); // we show the grid


        GraphicalView chartView = ChartFactory.getLineChartView(this.getActivity(), dataset, mRenderer);


        chartLyt.addView(chartView,0);
    }
}
