package com.amerrell.eventsquared.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amerrell.eventsquared.R;
import com.amerrell.eventsquared.models.Event;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    private ArrayList<Event> mEvents = new ArrayList<>();
    private Context mContext;

    public EventListAdapter(Context context, ArrayList<Event> events) {
        mContext = context;
        mEvents = events;
    }

    @Override
    public EventListAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        EventViewHolder viewHolder = new EventViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventListAdapter.EventViewHolder holder, int position) {
        Event event = mEvents.get(position);
        if (!event.getImageURL().equals("")) {
            Picasso.with(mContext).load(event.getImageURL()).into(holder.mEventListImageView);
        }
        holder.mEventNameTextView.setText(event.getName());
        holder.mDateTextView.setText(event.getDateTime());
        holder.mMaxPriceTextView.setText(event.toStringMaxPrice());
        if (!event.priceIsEqual(event.getMinPrice(), event.getMaxPrice())) {
            holder.mMinPriceTextView.setText(event.toStringMinPrice());
        } else {
            holder.mMinPriceTextView.setText("");
        }
        holder.mVenueTextView.setText(event.getVenue());
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mEventCardView;
        ImageView mEventListImageView;
        TextView mEventNameTextView;
        TextView mDateTextView;
        Button mTicketsButton;
        TextView mMinPriceTextView;
        TextView mMaxPriceTextView;
        TextView mVenueTextView;
        private Context mContext;

        public EventViewHolder(View itemView) {
            super(itemView);
            mEventCardView = (CardView) itemView.findViewById(R.id.eventCardView);
            mEventListImageView = (ImageView) itemView.findViewById(R.id.eventListImageView);
            mEventNameTextView = (TextView) itemView.findViewById(R.id.eventNameTextView);
            mDateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            mTicketsButton = (Button) itemView.findViewById(R.id.ticketsButton);
            mMinPriceTextView = (TextView) itemView.findViewById(R.id.minPriceTextView);
            mMaxPriceTextView = (TextView) itemView.findViewById(R.id.maxPriceTextView);
            mVenueTextView = (TextView) itemView.findViewById(R.id.venueTextView);
            mContext = itemView.getContext();
        }

        @Override
        public void onClick(View view) {

        }
    }
}
