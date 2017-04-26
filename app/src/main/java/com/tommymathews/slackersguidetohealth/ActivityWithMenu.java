package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by cj on 4/24/17.
 */

public class ActivityWithMenu extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.exerciseItem:
                intent = new Intent(getApplicationContext(), FitnessActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.foodItem:
                intent = new Intent(getApplicationContext(), FoodActivity.class);
                startActivity(intent);
                break;
            case R.id.exploreItem:
                intent = new Intent(getApplicationContext(), ExploreMain.class);
                startActivity(intent);
                break;
            case R.id.funItem:
                intent = new Intent(getApplicationContext(), Events.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
