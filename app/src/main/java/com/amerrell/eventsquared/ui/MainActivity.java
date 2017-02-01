package com.amerrell.eventsquared.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amerrell.eventsquared.Constants;
import com.amerrell.eventsquared.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.cityEditText) EditText mCityEditText;
    @Bind(R.id.stateEditText) EditText mStateEditText;
    @Bind(R.id.mainSearchButton) Button mMainSearchButton;
    @Bind(R.id.onSaleSearchButton) Button mOnSaleSearchButton;

    private SharedPreferences mSharedPrefences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPrefences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPrefences.edit();

        mMainSearchButton.setOnClickListener(this);
        mOnSaleSearchButton.setOnClickListener(this);
    }

    private void addToSharedPreferences(String city, String state, boolean onSale) {
        mEditor.putString(Constants.SHARED_PREFERENCES_CITY, city).apply();
        mEditor.putString(Constants.SHARED_PREFERENCES_STATE, state).apply();
        mEditor.putBoolean(Constants.SHARED_PREFERENCES_ONSALE, onSale).apply();
    }

    @Override
    public void onClick(View view) {
        String searchCity = mCityEditText.getText().toString();
        String searchState = mStateEditText.getText().toString();
        mCityEditText.setText("");
        mStateEditText.setText("");

        if (!(searchCity.length() > 0) || !(searchState.length() > 0)) {
            Toast.makeText(MainActivity.this, "Please fill out all fields before submitting.", Toast.LENGTH_SHORT).show();
        }

        if (view == mMainSearchButton) {
            boolean onSale = true;
            addToSharedPreferences(searchCity, searchState, onSale);
            Intent intent = new Intent(MainActivity.this, EventListActivity.class);
            startActivity(intent);
        }

        if (view == mOnSaleSearchButton) {
            boolean onSale = false;
            addToSharedPreferences(searchCity, searchState, onSale);
            Intent intent = new Intent(MainActivity.this, EventListActivity.class);
            startActivity(intent);
        }
    }
}
