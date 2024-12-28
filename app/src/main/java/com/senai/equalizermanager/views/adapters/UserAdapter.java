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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

//    private final Context context;
    private LayoutInflater inflater;
    private final List<User> userList;
    private OnListItemClick onListItemClick;

    public UserAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.inflater = LayoutInflater.from(context);
    }

    public void setClickListener(OnListItemClick onListItemClick){
        this.onListItemClick = onListItemClick;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvUsername.setText(user.getUsername());
        holder.imgAvatar.setImageResource(R.drawable.ic_launcher_foreground);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        ImageView imgAvatar;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);

            itemView.setOnClickListener(view -> {
                if(onListItemClick != null){
                    onListItemClick.onClick(view, getAdapterPosition());
                }
            });
        }
    }
}
