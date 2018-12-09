
package com.example.behnam.myapplication.activities.messages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.Fragment_mental.Fragment_mental_afsordegi;
import com.example.behnam.myapplication.activities.Fragment_mental.Fragment_mental_ezterab;
import com.example.behnam.myapplication.activities.Fragment_mental.Fragment_mental_stress;
import com.example.behnam.myapplication.activities.MainActivity;
import com.example.behnam.myapplication.adapters.adapter_mental_ViewPager;
import com.example.behnam.myapplication.connect_to_server.update_server_data;
import com.example.behnam.myapplication.database_pack.DataBase_issend;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.database_pack.DataBase_write;
import com.example.behnam.myapplication.objects.myChatMessage;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;


public class Fragment_doctor_message extends Fragment {
    ArrayList<ChatMessage> chat_list;
    FragmentActivity c;
    static Fragment_doctor_message fh;
    ChatView chatView;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exercise_fragment_doctor_message, container, false);
        setRetainInstance(true);
        c = getActivity();
        fh = this;

        chatView = (ChatView) rootView.findViewById(R.id.chat_view);


        show_messages();

      //  update_server_data.GetChatMessages(this,MainActivity.mainContext);
        update_server_data.setread_message(MainActivity.mainContext);

        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {

                DataBase_write.write_chat_sender(chatMessage.getMessage());
                update_server_data.send_chat_array(MainActivity.mainContext);
                return true;
            }
        });


        return rootView;
    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    public void show_messages() {
        chat_list = new ArrayList<>();
        ArrayList<myChatMessage> list = DataBase_read.get_chats(MainActivity.mainContext);
        for (myChatMessage chat : list
                ) {
            if (chat.getWho_send().equals("patient")) {
                Long d = Long.parseLong(chat.getTimestamp());
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(d * 1000);
                String s = cal.getTime().toString();
                chat_list.add(new ChatMessage(chat.getMessage(), Long.parseLong(chat.getTimestamp()) * 1000, ChatMessage.Type.SENT));

            } else {
                chat_list.add(new ChatMessage(chat.getMessage(), Long.parseLong(chat.getTimestamp()) * 1000, ChatMessage.Type.RECEIVED));
            }
        }

        chatView.clearMessages();
        chatView.addMessages(chat_list);
    }
}