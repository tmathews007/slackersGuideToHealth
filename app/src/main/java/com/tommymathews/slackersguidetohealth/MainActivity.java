package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] itemname ={
                "Fitness",
                "Food",
                "Events"
        };

        Integer[] imgid={
                R.drawable.run,
                R.drawable.food,
                R.drawable.fun
        };

        CustomListAdapter adapter = new CustomListAdapter(this, itemname, imgid);
        listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent;
                if (position == 0) {
                    intent = new Intent(getApplicationContext(), FitnessActivity.class);
                } else if (position == 1) {
                    intent = new Intent(getApplicationContext(), FoodMain.class);
                } else {
                    intent = new Intent(getApplicationContext(), Events.class);
                }
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuItem) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menuItem);
        return super.onCreateOptionsMenu(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileItem:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
