package com.mycompany.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BaseSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphActivity extends AppCompatActivity {
    static final int REQUEST_DATA = 1;
    private LinkedList memories;
    private BaseSeries<DataPoint> series;
    private GraphView graph;
    private MediaPlayer mp;
    private VideoView videoView;
    private double[] check= new double[25];
    private static int index =0;
    private static int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_graph);
        mp = null;

        memories = new LinkedList();
        series = new LineGraphSeries<DataPoint>();
        DataPoint dataPoint = new DataPoint(0,5);
        series.appendData(dataPoint, false, 25);
        graph = (GraphView) findViewById(R.id.graph);
        graph.setTitle("Life Music");
        graph.addSeries(series);
        for(index=0;index<25;index++)
            check[index]=-1;
        index =0;
/*
//sets min/max values for graph
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(25);
        graph.getViewport().setMinX(0);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(1);
        graph.getViewport().setMinY(-1);

        //determines how many digits for fraction/integer
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(0);
        nf.setMaximumIntegerDigits(2);

        //organizes the labeling
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));
        graph.getGridLabelRenderer().setNumHorizontalLabels(6);
        graph.getGridLabelRenderer().setNumVerticalLabels(3);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Age");
        graph.getGridLabelRenderer().setHighlightZeroLines(true);
*/
  //      Log.v("LabelVisible",String.valueOf(graph.getGridLabelRenderer().isHorizontalLabelsVisible()));

        OnDataPointTapListener dat = new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Iterator<DataPoint> it = series.getValues(series.getLowestValueX(), series.getHighestValueX());
                while (it.hasNext())
                {
                    if(it.next() == dataPoint)
                    {

                        try {
                            check[index]=dataPoint.getY();
                            play();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Dialog dialog = new Dialog((Context) dataPoint);
                        dialog.setContentView(R.layout.activity_graph);
                        dialog.setTitle("Memory:");
                        TextView text = (TextView) memories.get((int) dataPoint.getX());
                        dialog.show();
                    }
                }
            }
        };
        mp = new MediaPlayer();
        try {
            mp.setDataSource(this, Uri.parse("android.resource://com.mycompany.myapplication/" + R.raw.pianoe));
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.prepare();
           // mp.start();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
/*
        series.setOnDataPointTapListener(dat);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(GraphActivity.this, "Series1: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */
        for(int i=0;i<24;i++)
        {
            memories.add(i, "");
        }

    }

    public void addMemory(View v)
    {
        Intent intent = new Intent(this, PlotActivity.class);
        startActivityForResult(intent, REQUEST_DATA);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("RequestData", String.valueOf(REQUEST_DATA));
        Log.i("RequestOk", String.valueOf(resultCode));
        if(requestCode == REQUEST_DATA && resultCode == RESULT_OK) {
/*
            Bundle extras = data.getExtras();
            Log.i("Received", String.valueOf(extras.getString(PlotActivity.SCALE)));

            int ageX = Integer.parseInt(extras.getString(PlotActivity.AGE_RANGE));
            int progressY = Integer.parseInt(extras.getString(PlotActivity.SCALE));
            String description = extras.getString(PlotActivity.DESCRIPTION);
            DataPoint dataPoint = new DataPoint(ageX, progressY);

            memories.add(ageX, description);
            //then place on graph via appendDataPoint
            series.appendData(dataPoint,false, memories.size());

*/
            Log.i("AGERANGE", String.valueOf(data.getIntExtra("AGE_RANGE", 3)));
            Log.i("SCALE", String.valueOf(data.getIntExtra("SCALE", 5)));
            Log.i("DESCRIPTION", String.valueOf(data.getStringExtra("DESCRIPTION")));
            Log.i("NAME", String.valueOf(data.getStringExtra("TITLE")));

            int ageX = data.getIntExtra("AGE_RANGE", 3);
            int progressY = data.getIntExtra("SCALE", 5);
            String description = data.getStringExtra("DESCRIPTION");
            DataPoint dataPoint = new DataPoint(ageX, progressY);

            //add to a Datapoint linked list which will use Datapoint as key for a class of info
            memories.add(ageX, description);
            //then place on graph via appendDataPoint
            series.appendData(dataPoint,false , 25);
            graph.addSeries(series);
        }
    }

    public void playMusic(View v) throws IOException {
        double x = series.getLowestValueX();

        if(x < 0.0)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("You need notes to play music!");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Got it!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        mp.start();
        Iterator<DataPoint> note = series.getValues(series.getLowestValueX(), series.getHighestValueX());
        while (note.hasNext())
        {
            double y = note.next().getY();
            Log.v("NoteLX", String.valueOf(series.getLowestValueX()));
            Log.v("NoteHX", String.valueOf(series.getHighestValueX()));
            Log.v("NoteY", String.valueOf(y));
            check[index] = y;
            index++;
        }
        play();
    }
    public void play() throws IOException {
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    try {
                            if(check[count]<0) {
                                mp.stop();
                                mp.reset();
                                mp.release();
                            }
                            else {
                                if (check[count] > 5)
                                    mp.setDataSource(GraphActivity.this, Uri.parse("android.resource://com.mycompany.myapplication/" + R.raw.pianob));
                                else if (check[count] < 5)
                                    mp.setDataSource(GraphActivity.this, Uri.parse("android.resource://com.mycompany.myapplication/" + R.raw.pianof));
                                else
                                    mp.setDataSource(GraphActivity.this, Uri.parse("android.resource://com.mycompany.myapplication/" + R.raw.pianoe));
                                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                mp.prepare();
                                mp.start();
                                count++;
                            }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
}
