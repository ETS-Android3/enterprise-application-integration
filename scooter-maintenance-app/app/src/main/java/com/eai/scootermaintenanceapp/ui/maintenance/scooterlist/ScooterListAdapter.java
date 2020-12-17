package com.eai.scootermaintenanceapp.ui.maintenance.scooterlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eai.scootermaintenanceapp.R;
import com.eai.scootermaintenanceapp.data.model.Scooter;
import com.eai.scootermaintenanceapp.ui.maintenance.ScooterItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScooterListAdapter extends RecyclerView.Adapter<ScooterListAdapter.ViewHolder> {

    private static final String LOG_TAG = ScooterListAdapter.class.getSimpleName();

    private List<Scooter> mScooterList = new ArrayList<>();
    private Scooter mSelectedScooter;

    private final ScooterItemClickListener mOnClickListener;

    public ScooterListAdapter(ScooterItemClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView scooterNameTextView;
        private final TextView scooterInfoTextView;
        private final View scooterInProgressMarker;

        public ViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            scooterNameTextView = (TextView) view.findViewById(R.id.scooter_name);
            scooterInfoTextView = (TextView) view.findViewById(R.id.scooter_info);
            scooterInProgressMarker = view.findViewById(R.id.in_progress_marker);
        }

        public TextView getScooterNameTextView() {
            return scooterNameTextView;
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

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.scooter_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Scooter scooter = mScooterList.get(position);

        viewHolder.getScooterNameTextView().setText(scooter.getName());
        viewHolder.getScooterInfoTextView().setText(scooter.getFailureReason());

        if (scooter == mSelectedScooter) {
            viewHolder.markAsInProgress();
        } else {
            viewHolder.markAsTodo();
        }
    }

    @Override
    public int getItemCount() {
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
}
