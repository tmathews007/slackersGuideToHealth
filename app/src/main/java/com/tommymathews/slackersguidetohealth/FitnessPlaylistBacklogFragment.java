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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.model.Fitness;
import com.tommymathews.slackersguidetohealth.model.Playlist;
import com.tommymathews.slackersguidetohealth.service.PlaylistService;

import java.util.List;

/**
 * Created by Thomas on 5/7/2017.
 */

public class FitnessPlaylistBacklogFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private final static String EXTRA_FITNESS_CREATED = "FitnessID";
    private static final int REQUEST_CODE_CREATE_FITNESS = 0;

    private PlaylistService playlistService;

    private RecyclerView playlistFitnessRecyclerView;
    private FitnessAdapter adapter;

    public static FitnessPlaylistBacklogFragment newInstance() {
        FitnessPlaylistBacklogFragment fragment = new FitnessPlaylistBacklogFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        playlistService = DependencyFactory.getPlaylistService( getActivity().getApplicationContext() );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_backlog, container, false);

        playlistFitnessRecyclerView = (RecyclerView)view.findViewById(R.id.playlist_recycler_view);
        playlistFitnessRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        Log.d(TAG, "updating UI all fitnesses in playlist");

        String id = getActivity().getIntent().getStringExtra("ID");
        Playlist p = playlistService.getPlaylistById(id);
        List<Fitness> playlist = p.getList();

        if (adapter == null) {
            adapter = new FitnessAdapter(playlist);
            playlistFitnessRecyclerView.setAdapter(adapter);
        } else {
            adapter.setPlaylist(playlist);
            adapter.notifyDataSetChanged();
        }
    }

    private class FitnessHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView fitnessName;
        private ImageView fitnessImage;

        private Fitness fitness;

        public FitnessHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            fitnessName = (TextView)itemView.findViewById(R.id.playlist_fitness_name);
            fitnessImage = ( ImageView ) itemView.findViewById( R.id.playlist_fitness_image);

        }

        public void bindFitness( Fitness fitness) {
            this.fitness = fitness;
            fitnessName.setText(fitness.getFitnessName());
            fitnessImage.setImageBitmap(fitness.getImage());
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), FitnessDescriptionActivity.class );
            intent.putExtra("ID", fitness.getId());
            startActivity(intent);
        }
    }

    private class FitnessAdapter extends RecyclerView.Adapter<FitnessHolder> {
        private List<Fitness> playlist;

        public FitnessAdapter ( List<Fitness> playlist) {
            this.playlist = playlist;
        }

        public void setPlaylist( List<Fitness> playlist ) {
            this.playlist = playlist;
        }

        @Override
        public FitnessHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_fitness, parent, false);
            FitnessHolder temp = new FitnessHolder(view);
            return temp;
        }

        @Override
        public void onBindViewHolder(FitnessHolder holder, int position) {
            Fitness fitness = playlist.get(position);
            holder.bindFitness(fitness);
        }

        @Override
        public int getItemCount() {
            return playlist.size();
        }
    }
}
