package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.model.Fitness;
import com.tommymathews.slackersguidetohealth.service.FitnessService;

import org.w3c.dom.Text;

import java.util.List;

public class FitnessBacklogFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private final static String EXTRA_FITNESS_CREATED = "FitnessID";
    private static final int REQUEST_CODE_CREATE_FITNESS = 0;

    private FitnessService fitnessService;

    private RecyclerView storyRecyclerView;
    private FitnessAdapter adapter;

    public static FitnessBacklogFragment newInstance() {
        FitnessBacklogFragment fragment = new FitnessBacklogFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fitnessService = DependencyFactory.getFitnessService( getActivity().getApplicationContext() );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness_backlog, container, false);

        storyRecyclerView = (RecyclerView)view.findViewById(R.id.story_recycler_view);
        storyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CREATE_FITNESS ) {
            if (data == null) {
                return;
            }

            Fitness fitnessCreated = FitnessQuestionsActivity.getFitnessCreated(data);
            fitnessService.addFitness( fitnessCreated );
            updateUI();
        }
    }

    private void updateUI() {
        Log.d(TAG, "updating UI all stories");

        List<Fitness> stories = fitnessService.getAllFitness();

        if (adapter == null) {
            adapter = new FitnessAdapter(stories);
            storyRecyclerView.setAdapter( adapter );
        } else {
            adapter.setStories(stories);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_fitness_backlog, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_create_fitness:
                Intent createFitnessIntent = new Intent(getActivity(), FitnessQuestionsActivity.class);
                startActivityForResult(createFitnessIntent, REQUEST_CODE_CREATE_FITNESS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class FitnessHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView fitnessImageView;
        private TextView fitnessNameTextView;
        private TextView numRepsTextView;
        private TextView bodyPartTextView;

        private Fitness fitness;

        public FitnessHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            fitnessImageView = ( ImageView ) itemView.findViewById( R.id.list_item_fitness_image );
            fitnessNameTextView = (TextView)itemView.findViewById(R.id.list_item_fitness_name);
            numRepsTextView = (TextView)itemView.findViewById(R.id.list_item_num_reps);
            bodyPartTextView = ( TextView ) itemView.findViewById( R.id.list_item_body_part );
        }

        public void bindStory(Fitness fitness) {
            this.fitness = fitness;

            fitnessImageView.setImageBitmap( fitness.getImage() );
            fitnessNameTextView.setText( fitness.getFitnessName() );
            numRepsTextView.setText( "" + fitness.getNumReps() );
            bodyPartTextView.setText( fitness.getBodyPartPosition() );
        }

        @Override
        public void onClick(View view) {
            Intent intent = FitnessQuestionsActivity.newIntent(getActivity(), fitness.getFitnessName() );

            startActivityForResult( intent, REQUEST_CODE_CREATE_FITNESS );
        }
    }

    private class FitnessAdapter extends RecyclerView.Adapter<FitnessHolder> {
        private List<Fitness> fitness;

        public FitnessAdapter ( List<Fitness> fitness ) {
            this.fitness = fitness;
        }

        public void setStories( List<Fitness> fitness ) {
            this.fitness = fitness;
        }

        @Override
        public FitnessHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_fitness, parent, false);
            return new FitnessHolder(view);
        }

        @Override
        public void onBindViewHolder( FitnessHolder holder, int position ) {
            Fitness story = fitness.get(position);
            holder.bindStory(story);
        }

        @Override
        public int getItemCount() {
            return fitness.size();
        }
    }
}