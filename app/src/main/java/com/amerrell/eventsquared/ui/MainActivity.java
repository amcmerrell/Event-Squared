package com.amerrell.eventsquared.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amerrell.eventsquared.R;
import com.amerrell.eventsquared.services.TicketmasterService;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

    private void getTMEvents(String city, String state) {

        final TicketmasterService ticketmasterService = new TicketmasterService();
        ticketmasterService.findTMEvents(city, state, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                Log.v("RESPONSE: ", jsonData);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == mMainSearchButton) {
            String searchCity = mCityEditText.getText().toString();
            String searchState = mStateEditText.getText().toString();

            Intent intent = new Intent(MainActivity.this, EventListActivity.class);
            intent.putExtra("city", searchCity);
            intent.putExtra("state", searchState);
            startActivity(intent);
        }
    }
}
