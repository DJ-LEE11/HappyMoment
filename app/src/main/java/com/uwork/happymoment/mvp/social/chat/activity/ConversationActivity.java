package com.uwork.happymoment.mvp.social.chat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uwork.happymoment.R;
import com.uwork.libutil.IntentUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.model.Conversation;

import static com.uwork.happymoment.mvp.social.chat.activity.GroupMemberActivity.GROUP_ID;

//聊天页面
public class ConversationActivity extends AppCompatActivity {


    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.ivGroupMember)
    ImageView mIvGroupMember;
    private String mId;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        mId= getIntent().getData().getQueryParameter("targetId");
        String sName = getIntent().getData().getQueryParameter("title");
        if (!TextUtils.isEmpty(sName)) {
            mTvTitle.setText(sName);
        } else {
            //ToDo 通过Id进行请求获取名字
            mTvTitle.setText(mId);
        }

        mConversationType = Conversation.ConversationType.valueOf(getIntent().getData()
                .getLastPathSegment().toUpperCase(Locale.US));
        if (mConversationType.equals(Conversation.ConversationType.GROUP)) {//群聊
            mIvGroupMember.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ivBack, R.id.ivGroupMember})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivGroupMember:
                new IntentUtils.Builder(this)
                        .to(GroupMemberActivity.class)
                        .putExtra(GROUP_ID,mId)
                        .build()
                        .start();
                break;
        }
    }
}
