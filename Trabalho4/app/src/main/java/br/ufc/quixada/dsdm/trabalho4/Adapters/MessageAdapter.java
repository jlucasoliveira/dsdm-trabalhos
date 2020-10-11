package br.ufc.quixada.dsdm.trabalho4.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.trabalho4.Model.Message;
import br.ufc.quixada.dsdm.trabalho4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Map;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final static int MSG_TYPE_RIGHT = 0;
    private final static int MSG_TYPE_LEFT = 1;

    private Map<String, String> mUsers;
    private List<Message> mMessages;

    public MessageAdapter(List<Message> messages, Map<String, String> users) {
        this.mMessages = messages;
        this.mUsers = users;
    }

    public void setUsers(Map<String, String> users) {
        this.mUsers = users;
    }

    public void setMessages(List<Message> mMessages) {
        this.mMessages = mMessages;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_LEFT)
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_left, parent, false);
        else view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item_right, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        String message = mMessages.get(position).getMessage();
        String sender= mMessages.get(position).getSender();

        if (mUsers != null) {
            String senderName = mUsers.get(sender);
            holder.mTxtSender.setText(senderName);
            holder.mTxtSender.setVisibility(View.VISIBLE);
        }

        holder.mTxtMessage.setText(message);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (mMessages.get(position).getSender().equals(user.getUid()))
            return MSG_TYPE_RIGHT;
        return MSG_TYPE_LEFT;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtMessage;
        TextView mTxtSender;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtMessage = itemView.findViewById(R.id.message_item);
            mTxtSender = itemView.findViewById(R.id.sender);
        }
    }
}