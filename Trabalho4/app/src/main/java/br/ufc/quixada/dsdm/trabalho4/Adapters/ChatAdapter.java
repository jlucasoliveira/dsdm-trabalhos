package br.ufc.quixada.dsdm.trabalho4.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.trabalho4.ChatActivity;
import br.ufc.quixada.dsdm.trabalho4.Model.Group;
import br.ufc.quixada.dsdm.trabalho4.Model.Identification;
import br.ufc.quixada.dsdm.trabalho4.Model.User;
import br.ufc.quixada.dsdm.trabalho4.R;
import br.ufc.quixada.dsdm.trabalho4.utils.ChatTypes;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private int mTYPE;
    private Context mContext;
    private List<Identification> mUsers;

    public ChatAdapter(Context context, List<Identification> users, int type) {
        this.mTYPE = type;
        this.mUsers = users;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        String uid = mUsers.get(position).getId();
        String name = mUsers.get(position).getName();

        holder.mTxtChatName.setText(name);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra("uid", uid);
            intent.putExtra("name", name);
            intent.putExtra("CHAT_TYPE", mTYPE);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtChatName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtChatName = itemView.findViewById(R.id.chat_name);
        }
    }
}
