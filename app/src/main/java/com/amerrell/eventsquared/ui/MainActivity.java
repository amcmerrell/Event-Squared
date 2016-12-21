package com.amerrell.eventsquared.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.amerrell.eventsquared.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.cityEditText) EditText mCityEditText;
    @Bind(R.id.stateEditText) EditText mStateEditText;
    @Bind(R.id.mainSearchButton) Button mMainSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainSearchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mMainSearchButton) {
            String searchCity = mCityEditText.getText().toString();
            String searchState = mStateEditText.getText().toString();

            Intent intent = new Intent(MainActivity.this, EventListActivity.class);
            intent.putExtra("city", searchCity);
            intent.putExtra("state", searchState);

            mCityEditText.setText("");
            mStateEditText.setText("");
            startActivity(intent);
        }
    }
}
