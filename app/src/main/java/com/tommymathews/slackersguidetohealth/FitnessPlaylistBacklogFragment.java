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
    private FitnessPlaylistBacklogFragment.PlaylistAdapter adapter;

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

        playlistFitnessRecyclerView = (RecyclerView)view.findViewById(R.id.story_recycler_view);
        playlistFitnessRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

//            Fitness fitnessCreated = FitnessQuestionsActivity.getFitnessCreated(data);
            Playlist playlistCreated = FitnessPlaylistActivity.getFitnessCreated( data );
            playlistService.addPlaylist( playlistCreated );
            updateUI();
        }
    }

    private void updateUI() {
        Log.d(TAG, "updating UI all stories");

        List<Playlist> stories = playlistService.getAllPlaylists();

        if (adapter == null) {
            adapter = new FitnessPlaylistBacklogFragment.PlaylistAdapter(stories);
            playlistFitnessRecyclerView.setAdapter( adapter );
        } else {
            adapter.setPlaylist(stories);
            adapter.notifyDataSetChanged();
        }
    }

    private class PlaylistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView playlistImageView;
        private TextView playlistNameTextView;

        private Playlist playlist;

        public PlaylistHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            playlistImageView = ( ImageView ) itemView.findViewById( R.id.playlist_fitness_image);
            playlistNameTextView = (TextView)itemView.findViewById(R.id.playlist_fitness_name);
        }

        public void bindStory( Playlist playlist ) {
            this.playlist = playlist;

//            playlistImageView.setImageBitmap(  );
            playlistNameTextView.setText( playlist.getName() );
        }

        @Override
        public void onClick(View view) {
            Intent intent = FitnessQuestionsActivity.newIntent(getActivity(), playlist.getId() );

            startActivityForResult( intent, REQUEST_CODE_CREATE_FITNESS );
        }
    }

    private class PlaylistAdapter extends RecyclerView.Adapter<FitnessPlaylistBacklogFragment.PlaylistHolder> {
        private List<Playlist> playlists;

        public PlaylistAdapter ( List<Playlist> fitness ) {
            this.playlists = fitness;
        }

        public void setPlaylist( List<Playlist> playlist ) {
            this.playlists = playlist;
        }

        @Override
        public FitnessPlaylistBacklogFragment.PlaylistHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_fitness, parent, false);
            return new FitnessPlaylistBacklogFragment.PlaylistHolder(view);
        }

        @Override
        public void onBindViewHolder(FitnessPlaylistBacklogFragment.PlaylistHolder holder, int position ) {
            Playlist story = playlists.get(position);
            holder.bindStory(story);
        }

        @Override
        public int getItemCount() {
            return playlists.size();
        }
    }
}
