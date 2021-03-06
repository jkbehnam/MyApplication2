package com.example.behnam.myapplication.activities.Fragment_home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.behnam.myapplication.R;
import com.example.behnam.myapplication.database_pack.DataBase_read;

public class main_home extends Fragment {

    public main_home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.main_home_frag, container, false);

        FragmentManager childFragmentManager = getChildFragmentManager();
        int backStackCounter = childFragmentManager.getBackStackEntryCount();

        /**
         * get child fragment manager back stack counter first
         */
        if (backStackCounter == 0) {
            /**
             * if it is zero, find if stack 1 fragment has already existed or not
             */
            Fragment_home stack1Fragment = (Fragment_home) childFragmentManager.findFragmentByTag("stack1");
            /**
             * if stack 1 fragment does not exist, create a new one
             * we don't need to do anything if it has already existed because Android OS will handle it
             */
            if (stack1Fragment == null) {
                stack1Fragment = new Fragment_home();
                /**
                 * not adding this transaction, so back button will NOT go back to the container
                 * in tab 1 fragment
                 */
                childFragmentManager.beginTransaction()
                        .replace(R.id.container, stack1Fragment, "stack1")
                        .commit();
            }
        }

        return v;
    }

    void goToStack2Fragment() {
      /*  getChildFragmentManager().beginTransaction()
                .replace(R.id.container, new Stack2Fragment(), "stack2")
                .addToBackStack("stack2")
                .commit();
                */
    }

}