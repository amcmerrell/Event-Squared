package com.amerrell.eventsquared.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amerrell.eventsquared.Constants;
import com.amerrell.eventsquared.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.cityEditText) EditText mCityEditText;
    @Bind(R.id.stateEditText) EditText mStateEditText;
    @Bind(R.id.mainSearchButton) Button mMainSearchButton;

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
    }

    private void addToSharedPreferences(String city, String state) {
        mEditor.putString(Constants.SHARED_PREFERENCES_CITY, city).apply();
        mEditor.putString(Constants.SHARED_PREFERENCES_STATE, state).apply();
    }

    @Override
    public void onClick(View view) {
        if (view == mMainSearchButton) {
            String searchCity = mCityEditText.getText().toString();
            String searchState = mStateEditText.getText().toString();
            addToSharedPreferences(searchCity, searchState);

            Intent intent = new Intent(MainActivity.this, EventListActivity.class);

            mCityEditText.setText("");
            mStateEditText.setText("");
            startActivity(intent);
        }
    }
}
