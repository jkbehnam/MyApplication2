
package com.example.behnam.myapplication.activities.Fragment_mental;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.activities.Fragment_home.Fragment_home;
import com.example.behnam.myapplication.adapters.adapter_stress_recycle;
import com.example.behnam.myapplication.adapters.adapter_training_recycle;
import com.example.behnam.myapplication.adapters.stress_list;
import com.example.behnam.myapplication.database_pack.DataBase_read;
import com.example.behnam.myapplication.druges;
import com.example.behnam.myapplication.my_custom_component.MyWebViewClient;
import com.example.behnam.myapplication.objects.Stress_list;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.zagum.expandicon.ExpandIconView;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;



public class Fragment_mental_stress extends Fragment {
String table_name="stress_training_tb";
    LinearLayoutManager layoutManager;
    RecyclerView list;
    ArrayList<String> s;
    ArrayList<String> p_level;
    adapter_stress_recycle adapter;
    public WebView webView1;
    TextView tv;
    String title;
    TableRow tr;
    FragmentActivity c;
    int[] level;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.exercise_fragment_mental_stress, container, false);
        p_level = new ArrayList<>();
        c = getActivity();
        tv = (TextView) rootView.findViewById(R.id.drug_name);
        tr = (TableRow) rootView.findViewById(R.id.row_training);
        level = new int[]{1};
        s = DataBase_read.get_traningS_items1(c,table_name);
        webView1 = (WebView) rootView.findViewById(R.id.webView_training);
        adapter = new adapter_stress_recycle(s);
        list = (RecyclerView) rootView.findViewById(R.id.list_drug);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        list.setLayoutManager(layoutManager);


        settt();

        //((AppCompatActivity)getActivity()). getSupportActionBar().setDisplayShowHomeEnabled(true);
        //  ((AppCompatActivity)getActivity()). getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //-----------------------------------------------------------------------
        druges d = new druges();
        return rootView;
    }


    public void settt() {

        adapter = new adapter_stress_recycle(s);
        list.setAdapter(adapter);
        adapter.setOnCardClickListner(new adapter_stress_recycle.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                if (level[0] == 1) {
                    p_level.add(0, s.get(position));
                    title = s.get(position);
                    s = DataBase_read.get_traningS_items2(c, s.get(position),table_name);
                    ++level[0];
                } else if (level[0] == 2) {
                    p_level.add(1, s.get(position));
                    title = s.get(position);
                    s = DataBase_read.get_traningS_items3(c, s.get(position),table_name);
                    ++level[0];
                } else if (level[0] == 3) {
                    p_level.add(2, s.get(position));
                    title = s.get(position);
                    s = DataBase_read.get_traningS_items4(c, s.get(position),table_name);
                    ++level[0];
                }
                if (s.size() != 1) {
                    settt();
                } else {
                    list.setVisibility(View.GONE);
                    webView1.setVisibility(View.VISIBLE);
                    tr.setVisibility(View.VISIBLE);
                    tv.setText(title);
                    String summary = "<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\" />\n" + "<html>\n" +
                            "<head><title>Title of the document</title></head>\n" +
                            "<body>\n" +
                            "<div class=\"c2\">\n" + s.get(0) +
                            "</div>\n" +
                            "</body>\n" +
                            "</html>";
                    webView1.loadDataWithBaseURL("file:///android_asset/", summary, "text/html; charset=utf-8", null, null);
                    webView1.setWebViewClient(new MyWebViewClient(c));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int i = event.getAction();
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (s.size() == 1) {
                        list.setVisibility(View.VISIBLE);
                        webView1.setVisibility(View.GONE);
                        tr.setVisibility(View.GONE);
                    }
                    switch (level[0]){
                        case 1:
                            Fragment fragment = new Fragment_home();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.add(R.id.container, fragment);
                            transaction.commit();
                            break;
                        case 2:
                            --level[0];
                            s = DataBase_read.get_traningS_items1(c,table_name);
                            settt();
                            break;
                        case 3:
                            --level[0];
                            s = DataBase_read.get_traningS_items2(c, p_level.get(0),table_name);
                            settt();
                            break;
                        case 4:
                            --level[0];
                            s = DataBase_read.get_traningS_items3(c, p_level.get(1),table_name);
                            settt();
                            break;
                    }



                    return true;
                }
                return false;
            }
        });
    }

}