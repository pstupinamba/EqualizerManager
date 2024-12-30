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

/**
 * Adapter para gerenciar a exibição de configurações do equalizador em um RecyclerView.
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private Context context;
    private List<EqualizerSettings> settingsList;
    private OnListItemClick onListItemClick;
    private int userId;
    private int selectedPosition = -1; // Posição do item atualmente selecionado
    private OnSettingActivatedListener listener;

    /**
     * Construtor do adapter.
     *
     * @param context       contexto da aplicação.
     * @param settingsList  lista de configurações do equalizador.
     * @param userId        ID do usuário associado.
     * @param listener      listener para ativação de configurações.
     */
    public SettingsAdapter(Context context, List<EqualizerSettings> settingsList, int userId, OnSettingActivatedListener listener) {
        this.context = context;
        this.settingsList = settingsList;
        this.userId = userId;
        this.listener = listener;
    }

    /**
     * Define o listener para cliques nos itens da lista.
     *
     * @param onListItemClick listener de clique.
     */
    public void setClickListener(OnListItemClick onListItemClick) {
        this.onListItemClick = onListItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item para o RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Vincula os dados da configuração atual ao ViewHolder
        EqualizerSettings settings = settingsList.get(position);

        // Configuração dos valores nas views
        holder.tvName.setText(settings.getName());
        holder.tvLowFreq.setText("Low: " + settings.getLow_freq());
        holder.tvMidFreq.setText("Mid: " + settings.getMid_freq());
        holder.tvHighFreq.setText("High: " + settings.getHigh_freq());
        holder.rbActive.setChecked(position == selectedPosition);
        holder.rbActive.setChecked(settings.isActive());

        // Listener para ativação de configuração
        holder.rbActive.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged(); // Atualiza a lista para refletir a seleção
            if (listener != null) {
                listener.onSettingActivated(settings);
            }
        });

        // Ajusta a cor de fundo dependendo do estado ativo
        if (settings.isActive()) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.active_setting));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.default_setting));
        }

        // Listener para cliques no item
        holder.itemView.setOnClickListener(view -> {
            if (onListItemClick != null) {
                onListItemClick.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Retorna o número total de itens na lista
        return settingsList.size();
    }

    /**
     * Retorna a configuração atualmente selecionada.
     *
     * @return configuração selecionada ou null se nenhuma estiver selecionada.
     */
    public EqualizerSettings getSelectedSetting() {
        if (selectedPosition >= 0 && selectedPosition < settingsList.size()) {
            return settingsList.get(selectedPosition);
        }
        return null;
    }

    /**
     * ViewHolder para representar os itens do RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLowFreq, tvMidFreq, tvHighFreq, tvName;
        RadioButton rbActive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa as views do layout do item
            tvLowFreq = itemView.findViewById(R.id.tvLowFreq);
            tvMidFreq = itemView.findViewById(R.id.tvMidFreq);
            tvHighFreq = itemView.findViewById(R.id.tvHighFreq);
            tvName = itemView.findViewById(R.id.tvName);
            rbActive = itemView.findViewById(R.id.rbActive);

            // Listener de clique no item
            itemView.setOnClickListener(view -> {
                if (onListItemClick != null) {
                    onListItemClick.onClick(view, getAdapterPosition());
                }
            });
        }
    }

    /**
     * Interface para ativação de configurações do equalizador.
     */
    public interface OnSettingActivatedListener {
        void onSettingActivated(EqualizerSettings setting);
    }
}
