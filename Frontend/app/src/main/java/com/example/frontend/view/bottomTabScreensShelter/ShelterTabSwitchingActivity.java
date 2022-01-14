package com.example.frontend.view.bottomTabScreensShelter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.R;
import com.example.frontend.model.MainApplication;
import com.example.frontend.view.SafeCloseActivity;
import com.example.frontend.view.bottomTabScreensShelter.listings.ShelterListingsActivity;
import com.example.frontend.view.bottomTabScreensShelter.listings.ShelterNewListingActivity;
import com.example.frontend.view.bottomTabScreensShelter.shelterProfile.ShelterProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Stack;

public abstract class ShelterTabSwitchingActivity extends SafeCloseActivity {

    protected MenuItem menuItem;
    protected BottomNavigationView bottomNavigationView;
    protected static Stack<Class> tabBackStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed ()
    {
        if(tabBackStack.empty()){
            finish();
            return;
        }
        gotToNextPage(tabBackStack.pop());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((intent.getFlags() | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) > 0) {
            //System.out.println("Top: "+getLocalClassName());
            overridePendingTransition(0,0);
        }
    }

    protected void setNavSwitcher(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == menuItem.getItemId()){

            }
            else if(item.getItemId() == R.id.nav_profile){
                gotToNextPage(ShelterProfileActivity.class);
                tabBackStack.push(getClass());

            }else if(item.getItemId() == R.id.nav_listings){
                gotToNextPage(ShelterListingsActivity.class);
                tabBackStack.push(getClass());

            }else if(item.getItemId() == R.id.nav_add_listing){
                gotToNextPage(ShelterNewListingActivity.class);
                tabBackStack.push(getClass());
            }
            return true;
        });

    }

    private void gotToNextPage(Class goToActivity){
        Intent intent = new Intent(getApplicationContext(), goToActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

}
