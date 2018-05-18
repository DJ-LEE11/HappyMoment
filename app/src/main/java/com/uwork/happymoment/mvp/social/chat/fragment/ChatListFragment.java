package com.uwork.happymoment.mvp.social.chat.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.chat.activity.AddressListActivity;
import com.uwork.happymoment.mvp.social.chat.activity.GroupChatActivity;
import com.uwork.happymoment.mvp.social.chat.activity.LeadingPhoneActivity;
import com.uwork.happymoment.mvp.social.chat.activity.NewFriendActivity;
import com.uwork.librx.mvp.BaseFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by jie on 2018/5/9.
 */

public class ChatListFragment extends BaseFragment {

    public static final String TAG = ChatListFragment.class.getSimpleName();

    private static ChatListFragment fragment;
    Unbinder unbinder;


    public static ChatListFragment newInstance() {
        if (null == fragment) {
            fragment = new ChatListFragment();
        }
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initChatList();
        return view;
    }

    private void initChatList() {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout, initConversationList());
        transaction.commit();
    }

    //会话列表
    private ConversationListFragment mConversationListFragment = null;

    //会话列表
    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.CUSTOMER_SERVICE.getName(), "false")//客服
                    .build();

            listFragment.setUri(uri);
            return listFragment;
        } else {
            return mConversationListFragment;
        }
    }

    @OnClick({R.id.llLeadingPhone, R.id.llNewFriend, R.id.llGroupChat, R.id.llAddressList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLeadingPhone://导入手机
                goTo(LeadingPhoneActivity.class);
                break;
            case R.id.llNewFriend://新朋友
                goTo(NewFriendActivity.class);
                break;
            case R.id.llGroupChat://群聊
                goTo(GroupChatActivity.class);
                break;
            case R.id.llAddressList://通讯录
                goTo(AddressListActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
