package com.nirmalya.enactus.nirmalya.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nirmalya.enactus.nirmalya.fragments.OverviewFragment;
import com.nirmalya.enactus.nirmalya.fragments.ProposalsFragment;
import com.nirmalya.enactus.nirmalya.fragments.QueryFragment;
import com.nirmalya.enactus.nirmalya.fragments.RequestVisitFragment;
import com.nirmalya.enactus.nirmalya.fragments.TasksFragment;

import java.util.ArrayList;

public class OrdersFragmentAdapter extends FragmentPagerAdapter {

    private static ArrayList<Fragment> listOfFragments;
    private static ArrayList<String> titlesOfFragments = new ArrayList<>();

    public OrdersFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        if (listOfFragments == null) {

            listOfFragments = new ArrayList<>();

            addFragment(new RequestVisitFragment(), "Request Visit");
            addFragment(new OverviewFragment(), "Overview");
            addFragment(new ProposalsFragment(), "Proposals");
            addFragment(new TasksFragment(), "Tasks");
            addFragment(new QueryFragment(), "Chat with us");

        }
    }

    @Override
    public Fragment getItem(int position) {
        return listOfFragments.get(position);
    }

    @Override
    public int getCount() {
        return listOfFragments.size();
    }

    public void addFragment(Fragment fragment, String title) {
        titlesOfFragments.add(title);
        listOfFragments.add(fragment);
        this.notifyDataSetChanged();
    }

    public void removeFragment(Fragment fragment, String title) {
        listOfFragments.remove(fragment);
        titlesOfFragments.remove(title);
    }

    public void removeRequestVisitFragment() {
        if (titlesOfFragments.get(0).equals("Request Visit")) {
            listOfFragments.remove(0);
            titlesOfFragments.remove(0);
            notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titlesOfFragments.get(position);
    }

}
