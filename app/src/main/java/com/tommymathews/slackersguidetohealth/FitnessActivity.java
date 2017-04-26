package com.tommymathews.slackersguidetohealth;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Thomas on 4/12/2017.
 */

public class FitnessActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return FitnessFragment.newInstance();
    }

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
                intent = new Intent(getApplicationContext(), ExploreMain.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
