package com.orishkevich.accelerometerapp;

import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import com.bumptech.glide.Glide;
import com.orishkevich.accelerometerapp.model.ChatMessage;

public class Main extends AppCompatActivity {

    private DatabaseReference mSimpleFirechatDatabaseReference;
    private FirebaseRecyclerAdapter<ChatMessage, FirechatMsgViewHolder>
            mFirebaseAdapter;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;

    private Button mSendButton;
    private EditText mMsgEditText;

    private SharedPreferences mSharedPreferences;
    public static final String DEFAULT_NAME = "aleksander";
    private String mUsername;
    private String mPhotoUrl;



    public static class FirechatMsgViewHolder extends RecyclerView.ViewHolder {
        public TextView msgTextView;
        public TextView userTextView;
        public CircleImageView userImageView;

        public FirechatMsgViewHolder(View v) {
            super(v);
            msgTextView = (TextView) itemView.findViewById(R.id.msgTextView);
            userTextView = (TextView) itemView.findViewById(R.id.userTextView);
            userImageView = (CircleImageView) itemView.findViewById(R.id.userImageView);


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);




        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSimpleFirechatDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatMessage,
                FirechatMsgViewHolder>(
                ChatMessage.class,
                R.layout.chat_message,
                FirechatMsgViewHolder.class,
                mSimpleFirechatDatabaseReference.child("messages")) {

            @Override
            protected void populateViewHolder(FirechatMsgViewHolder viewHolder,
                                              ChatMessage friendlyMessage, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.msgTextView.setText(friendlyMessage.getText());
                viewHolder.userTextView.setText(friendlyMessage.getName());
                if (friendlyMessage.getPhotoUrl() == null) {
                    viewHolder.userImageView
                            .setImageDrawable(ContextCompat
                                    .getDrawable(Main.this,
                                            R.drawable.ic_account_circle_black_36dp));
                } else {
                    Glide.with(Main.this)
                            .load(friendlyMessage.getPhotoUrl())
                            .into(viewHolder.userImageView);
                }
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int chatMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (chatMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);


        //mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mMsgEditText = (EditText) findViewById(R.id.msgEditText);
        //mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mSharedPreferences
        //        .getInt(CodelabPreferences.FRIENDLY_MSG_LENGTH, DEFAULT_MSG_LENGTH_LIMIT))});

        mMsgEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSendButton = (Button) findViewById(R.id.sendButtonmain);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessage friendlyMessage = new
                        ChatMessage(mMsgEditText.getText().toString(),
                        mUsername,
                        mPhotoUrl);
                mSimpleFirechatDatabaseReference.child("messages")
                        .push().setValue(friendlyMessage);
                mMsgEditText.setText("");
            }
        });
    }


}