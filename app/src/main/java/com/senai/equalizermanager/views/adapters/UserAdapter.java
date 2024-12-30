package com.senai.equalizermanager.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.senai.equalizermanager.R;
import com.senai.equalizermanager.models.User;
import com.senai.equalizermanager.utils.OnListItemClick;

import java.util.List;

/**
 * Adapter para exibir uma lista de usuários em um RecyclerView.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private LayoutInflater inflater; // Inflador para criar as views
    private final List<User> userList; // Lista de usuários a ser exibida
    private OnListItemClick onListItemClick; // Listener para cliques nos itens

    /**
     * Construtor do adapter.
     *
     * @param context  contexto da aplicação.
     * @param userList lista de usuários a ser exibida.
     */
    public UserAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.inflater = LayoutInflater.from(context);
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
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item do usuário
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // Vincula os dados do usuário atual ao ViewHolder
        User user = userList.get(position);
        holder.tvUsername.setText(user.getUsername()); // Define o nome de usuário
        holder.imgAvatar.setImageResource(R.drawable.ic_launcher_foreground); // Define um avatar padrão
    }

    @Override
    public int getItemCount() {
        // Retorna o número total de usuários na lista
        return userList.size();
    }

    /**
     * ViewHolder para representar os itens de usuário no RecyclerView.
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername; // Exibe o nome de usuário
        ImageView imgAvatar; // Exibe o avatar do usuário

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa as views do layout do item do usuário
            tvUsername = itemView.findViewById(R.id.tvUsername);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);

            // Configura o listener para cliques no item
            itemView.setOnClickListener(view -> {
                if (onListItemClick != null) {
                    onListItemClick.onClick(view, getAdapterPosition());
                }
            });
        }
    }
}