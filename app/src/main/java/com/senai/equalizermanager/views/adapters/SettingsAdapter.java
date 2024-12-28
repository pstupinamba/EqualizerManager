package com.senai.equalizermanager.views.adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.senai.equalizermanager.R;
import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.utils.OnListItemClick;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private Context context;
    private List<EqualizerSettings> settingsList;
    private OnListItemClick onListItemClick;
    private int userId;
    private int selectedPosition = -1;
    private OnSettingActivatedListener listener;

    public SettingsAdapter(Context context, List<EqualizerSettings> settingsList, int userId, OnSettingActivatedListener listener) {
        this.context = context;
        this.settingsList = settingsList;
        this.userId = userId;
        this.listener = listener;
    }

    public void setClickListener(OnListItemClick onListItemClick) {
        this.onListItemClick = onListItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EqualizerSettings settings = settingsList.get(position);
        Log.i("SETTING NAME: ", settings.getName());
        holder.tvName.setText(settings.getName());
        holder.tvLowFreq.setText("Low: " + settings.getLow_freq());
        holder.tvMidFreq.setText("Mid: " + settings.getMid_freq());
        holder.tvHighFreq.setText("High: " + settings.getHigh_freq());
        holder.rbActive.setChecked(position == selectedPosition);
        holder.rbActive.setChecked(settings.isActive());

        holder.rbActive.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
            if (listener != null) {
                listener.onSettingActivated(settings);
            }
        });

        if (settings.isActive()) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.active_setting));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.default_setting));
        }

        holder.itemView.setOnClickListener(view -> {
            if (onListItemClick != null) {
                onListItemClick.onClick(view, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    public EqualizerSettings getSelectedSetting(){
        if(selectedPosition >= 0 && selectedPosition < settingsList.size()){
            return settingsList.get(selectedPosition);
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLowFreq, tvMidFreq, tvHighFreq, tvName;
        RadioButton rbActive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLowFreq = itemView.findViewById(R.id.tvLowFreq);
            tvMidFreq = itemView.findViewById(R.id.tvMidFreq);
            tvHighFreq = itemView.findViewById(R.id.tvHighFreq);
            tvName = itemView.findViewById(R.id.tvName);
            rbActive = itemView.findViewById(R.id.rbActive);
            itemView.setOnClickListener(view -> {
                if (onListItemClick != null) {
                    onListItemClick.onClick(view, getAdapterPosition());
                }
            });
        }
    }

    public interface OnSettingActivatedListener {
        void onSettingActivated(EqualizerSettings setting);
    }
}
