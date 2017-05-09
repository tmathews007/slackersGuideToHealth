package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;
import java.util.List;

public class FitnessBacklogFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private static final int REQUEST_CODE_CREATE_FITNESS = 0;

    private FitnessService fitnessService;

    private RecyclerView fitnessRecyclerView;
    private FitnessAdapter adapter;
    private int bodyPart;

    public static FitnessBacklogFragment newInstance() {
        FitnessBacklogFragment fragment = new FitnessBacklogFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fitnessService = DependencyFactory.getFitnessService(getActivity().getApplicationContext());
        bodyPart = getActivity().getIntent().getIntExtra("BODYPART", -1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness_backlog, container, false);

        fitnessRecyclerView = (RecyclerView)view.findViewById(R.id.story_recycler_view);
        fitnessRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        Log.d(TAG, "updating UI all fitnesses");
        List<Fitness> fitnesses;

        if (bodyPart !=  -1) {
            fitnesses = fitnessService.getFitnessesByBodyPart(bodyPart);
        } else {
            fitnesses = fitnessService.getAllFitness();
        }

        if (adapter == null) {
            adapter = new FitnessAdapter(fitnesses);
            fitnessRecyclerView.setAdapter( adapter );
        } else {
            adapter.setFitnesses(fitnesses);
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

        private Fitness fitness;

        public FitnessHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            fitnessImageView = (ImageView) itemView.findViewById( R.id.list_fitness_image);
            fitnessNameTextView = (TextView) itemView.findViewById(R.id.list_fitness_name);
        }

        public void bindStory(Fitness fitness) {
            this.fitness = fitness;

            Uri uri = Uri.parse( fitness.getImage());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fitnessImageView.setImageBitmap(bitmap);
            fitnessNameTextView.setText( fitness.getFitnessName() );
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), FitnessDescriptionActivity.class);
            intent.putExtra("ID", fitness.getId());
            startActivity(intent);
        }
    }

    private class FitnessAdapter extends RecyclerView.Adapter<FitnessHolder> {
        private List<Fitness> fitnesses;

        public FitnessAdapter ( List<Fitness> fitnesses ) {
            this.fitnesses = fitnesses;
        }

        public void setFitnesses( List<Fitness> fitnesses ) {
            this.fitnesses = fitnesses;
        }

        @Override
        public FitnessHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_fitness, parent, false);
            return new FitnessHolder(view);
        }

        @Override
        public void onBindViewHolder( FitnessHolder holder, int position ) {
            Fitness story = fitnesses.get(position);
            holder.bindStory(story);
        }

        @Override
        public int getItemCount() {
            return fitnesses.size();
        }
    }
}