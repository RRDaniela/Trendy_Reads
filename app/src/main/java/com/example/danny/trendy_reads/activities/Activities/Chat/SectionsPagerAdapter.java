package com.example.danny.trendy_reads.activities.Activities.Chat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.danny.trendy_reads.activities.ChatFragments.ChatFragment;
import com.example.danny.trendy_reads.activities.ChatFragments.FriendsFragment;
import com.example.danny.trendy_reads.activities.ChatFragments.RequestsFragment;

class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
          case 0:
              RequestsFragment requestsFragment = new RequestsFragment();
              return requestsFragment;
          case 1:
              ChatFragment chatFragment = new ChatFragment();
              return chatFragment;
          case 2:
              FriendsFragment friendsFragment = new FriendsFragment();
              return friendsFragment;

          default:
              return null;
      }}
/*
       if (position == 0) {
            return new RequestsFragment();
        } else if (position == 1){
            return new ChatFragment();
        } else {
            return new FriendsFragment();
        }
    }
*/
    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return "SOLICITUDES";
            case 1:
                return "CHATS";
            case 2:
                return "AMIGOS";
            default:
                return null;
        }
    }
}
