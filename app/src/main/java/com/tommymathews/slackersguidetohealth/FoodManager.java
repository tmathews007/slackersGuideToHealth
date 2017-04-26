package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FoodManager extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_manager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation2, menu);
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
                break;
            case R.id.exploreItem:
                intent = new Intent(getApplicationContext(), ExploreMain.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.funItem:
                intent = new Intent(getApplicationContext(), Events.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.mainItem:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.profileItem:
                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.purchasesItem:
                intent = new Intent(getApplicationContext(), PurchasesActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
