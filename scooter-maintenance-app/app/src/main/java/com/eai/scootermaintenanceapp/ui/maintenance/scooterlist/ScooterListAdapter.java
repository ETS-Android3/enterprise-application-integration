package com.eai.scootermaintenanceapp.ui.maintenance.scooterlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eai.scootermaintenanceapp.R;
import com.eai.scootermaintenanceapp.data.model.Scooter;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScooterListAdapter extends RecyclerView.Adapter<ScooterListAdapter.ViewHolder> {

    private static final String LOG_TAG = ScooterListAdapter.class.getSimpleName();

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NOT_EMPTY = 1;

    private List<Scooter> mScooterList = new ArrayList<>();
    private Scooter mSelectedScooter;
    private int lastPosition = -1;

    private final ScooterItemClickListener mOnClickListener;

    public ScooterListAdapter(ScooterItemClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView scooterIdTextView;
        private final TextView scooterErrorTimeTextView;
        private final TextView scooterErrorCodeTextView;
        private final TextView scooterInfoTextView;
        private final View scooterInProgressMarker;

        public ViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            scooterIdTextView = (TextView) view.findViewById(R.id.scooter_id);
            scooterErrorTimeTextView = (TextView) view.findViewById(R.id.scooter_error_time);
            scooterErrorCodeTextView = (TextView) view.findViewById(R.id.scooter_error_code);
            scooterInfoTextView = (TextView) view.findViewById(R.id.scooter_info);
            scooterInProgressMarker = view.findViewById(R.id.in_progress_marker);
        }

        public TextView getScooterIdTextView() {
            return scooterIdTextView;
        }

        public TextView getScooterErrorTimeTextView() {
            return scooterErrorTimeTextView;
        }

        public TextView getScooterErrorCodeTextView() {
            return scooterErrorCodeTextView;
        }

        public TextView getScooterInfoTextView() {
            return scooterInfoTextView;
        }

        public void markAsInProgress() {
            scooterInProgressMarker.setBackgroundColor(ContextCompat.getColor(
                    scooterInProgressMarker.getContext(), R.color.in_progress));
        }

        public void markAsTodo() {
            scooterInProgressMarker.setBackgroundColor(ContextCompat.getColor(
                    scooterInProgressMarker.getContext(), R.color.todo));
        }

        @Override
        public void onClick(View view) {
            Scooter scooter = mScooterList.get(getAdapterPosition());
            mOnClickListener.onScooterItemClick(scooter);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mScooterList.isEmpty()) {
            return VIEW_TYPE_EMPTY;
        }

        return VIEW_TYPE_NOT_EMPTY;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View emptyView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.empty_list_view, viewGroup, false);
            return new ViewHolder(emptyView);
        }

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.scooter_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) {
            return;
        }

        Scooter scooter = mScooterList.get(position);

        Context context = viewHolder.itemView.getContext();
        viewHolder.getScooterIdTextView().setText(context.getString(
                R.string.scooter_id, scooter.getId()));

        DateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String dateString = dateFormat.format(scooter.getErrorDate());
        viewHolder.getScooterErrorTimeTextView().setText(dateString);

        viewHolder.getScooterErrorCodeTextView().setText(context.getString(
                R.string.scooter_error_code, scooter.getErrorCode()));
        viewHolder.getScooterInfoTextView().setText(scooter.getFailureReason());

        if (scooter == mSelectedScooter) {
            viewHolder.markAsInProgress();
        } else {
            viewHolder.markAsTodo();
        }

        setAnimation(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        if (mScooterList.isEmpty()) {
            return 1;
        }

        return mScooterList.size();
    }

    public void updateScooterList(List<Scooter> scooterList) {
        mScooterList = scooterList;
        notifyDataSetChanged();
    }

    public void setSelectedScooter(Scooter scooter) {
        mSelectedScooter = scooter;
        notifyDataSetChanged();
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
