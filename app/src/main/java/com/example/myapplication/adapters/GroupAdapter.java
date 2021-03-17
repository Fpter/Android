package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.models.Group;
import com.cometchat.pro.uikit.Avatar;
import com.example.myapplication.ChatActivity;
import com.example.myapplication.R;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    List<Group> groups;
    Context context;
    public GroupAdapter(List<Group> groups, Context context) {
        this.context = context;
        this.groups = groups;
    }
    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupViewHolder(LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.bind(groups.get(position));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName;
        private LinearLayout containerLayout;
        private Avatar avatar;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.call_sender_name);
            containerLayout = itemView.findViewById(R.id.mtrl_view);
            avatar = itemView.findViewById(R.id.call_sender_avatar);
        }
        public void bind(Group group) {
            groupName.setText(group.getName());
            avatar.setAvatar(group.getIcon());
            containerLayout.setOnClickListener(view -> ChatActivity.start(context, group.getGuid(),group.getName(), group.getIcon()));

        }
    }
}
