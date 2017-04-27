package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import static com.tommymathews.slackersguidetohealth.ExploreMain.removeShiftMode;

/**
 * Created by lidya on 4/6/2017.
 */

public class ExploreEatOutRight extends AppCompatActivity {

    int checkAccumulator = 0;
    private TextView mTextMessage;
    public ListView listViewEvents;
    public TextView EatOutBasedonDistance, EatOutBasedonCost;

//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View customView = inflater.inflate(R.layout.row_layout1, parent, false);
//        String stringelement = getItem(position);
//        TextView Text = (TextView) customView.findViewById(R.id.textelement);
//
//        CheckBox checkBox1 = (CheckBox) customView.findViewById(R.id.);
//        CheckBox checkBox2 = (CheckBox) customView.findViewById(R.id.Checkbox2);
//
//        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                countCheck(isChecked);
//                Log.i("MAIN", checkAccumulator + "");
//            }
//        };
//
//        checkBox1.setOnCheckedChangeListener(checkListener);
//        checkBox2.setOnCheckedChangeListener(checkListener);
//
//        Text.setText(stringelement);
//        return customView;
//    }
//
//    private void countCheck(boolean isChecked) {
//
//        checkAccumulator += isChecked ? 1 : -1 ;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_eat_out_right);

        EatOutBasedonDistance = (Button) findViewById(R.id.distance_checkBox);
        EatOutBasedonCost = (Button) findViewById(R.id.cost_checkBox);


        EatOutBasedonDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Distance.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        EatOutBasedonCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Cost.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById
                (R.id.bottomNavigationView);
        removeShiftMode(bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.exerciseItem) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.foodItem) {
                    //start the food activity
                    Intent i = new Intent(getApplicationContext(), FoodManager.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);

                } else if (item.getItemId() == R.id.exploreItem) {
                } else if (item.getItemId() == R.id.funItem) {
                    //Intent intent = new Intent(getApplicationContext(), Events.class);
                    //startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                return false;
            }
        });
    }
}
