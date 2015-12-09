package com.mycompany.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlotActivity extends AppCompatActivity {
    private SeekBar emotionSeekBar;
    private TextView seekTextView;
    private int progress;

    private EditText titleEditTextView;
    private EditText descriptionEditTextView;
    private String titleText;
    private String descriptionText;

    private Spinner ageRangeSpinner;
    private int defaultSpinnerPosition =3;

    private Button saveButton;

    public static String AGE_RANGE;
    public static String SCALE;
    public static String TITLE;
    public static String DESCRIPTION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent intent = getIntent();
        // int command_value = intent.getIntExtra(GraphActivity.AGE_RANGE, 3);
        // Log.i("FromMainActivity: ", String.valueOf(command_value));

        setContentView(R.layout.activity_plot);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViewVariables();

        //if(command_value> 0) {


        //}

        progress = 0;
        emotionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTextView.setText(" " + progress + "/" + seekBar.getMax());
                //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });

        ageRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                defaultSpinnerPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                defaultSpinnerPosition = 3;
            }
        });
        /*
        titleEditTextView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    titleEditTextView.setFocusable(false);
                    titleEditTextView.setFocusableInTouchMode(true);
                    return true;
                } else {
                    return false;
                }
            }
        });

        descriptionEditTextView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    descriptionEditTextView.setFocusable(false);
                    descriptionEditTextView.setFocusableInTouchMode(true);
                    return true;
                } else {
                    return false;
                }
            }
        });
        */
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                titleText = titleEditTextView.getText().toString();
                descriptionText = descriptionEditTextView.getText().toString();

                if (checkNullOrEmptyString(titleText) || checkNullOrEmptyString(descriptionText)) {
                    //Toast.makeText(AnotherActivity.this, "Fill up description and title", Toast.LENGTH_SHORT);
                } else {
                    Log.d("TITLETEXT", String.valueOf(titleText));
                    Log.d("DESCTEXT", String.valueOf(descriptionText));
                    Log.d("SCALE", String.valueOf(progress));
                    Log.d("spinner", String.valueOf(defaultSpinnerPosition));
                    // give data back to Katie intent
                    Intent graphIntent = new Intent();
                    graphIntent.putExtra("AGE_RANGE", defaultSpinnerPosition);
                    graphIntent.putExtra("SCALE", progress);
                    graphIntent.putExtra("TITLE", titleText);
                    graphIntent.putExtra("DESCRIPTION", descriptionText);
                    setResult(Activity.RESULT_OK, graphIntent);
                    finish();
                }

            }
        });

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean checkNullOrEmptyString(String str) {
        if (str != null && !str.isEmpty()) {
            return false;
        }
        return true;
    }

    private void initializeViewVariables() {
        emotionSeekBar = (SeekBar) findViewById(R.id.emotionSeekBar);
        seekTextView = (TextView) findViewById(R.id.seekTextView);
        seekTextView.setText(" " + emotionSeekBar.getProgress() + "/" + emotionSeekBar.getMax());

        titleEditTextView = (EditText) findViewById(R.id.titleEditText);
        descriptionEditTextView = (EditText) findViewById(R.id.descriptionEditText);

        ageRangeSpinner = (Spinner) findViewById(R.id.ageRangeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.agerange_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageRangeSpinner.setAdapter(adapter);
        ageRangeSpinner.setSelection(defaultSpinnerPosition);

        saveButton = (Button) findViewById(R.id.saveButton);
    }



/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_plot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
*/
}
