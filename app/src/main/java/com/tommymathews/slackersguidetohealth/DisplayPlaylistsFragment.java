package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * Created by gregs on 5/6/2017.
 */

public class DisplayPlaylistsFragment extends Fragment {
    PlaylistService playlistService;
    RecyclerView playlistRecyclerView;
    private PlaylistAdapter adapter;

    public static DisplayPlaylistsFragment newInstance() {
        DisplayPlaylistsFragment fragment = new DisplayPlaylistsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistService = DependencyFactory.getPlaylistService(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_playlists, container, false);

        playlistRecyclerView = (RecyclerView)view.findViewById(R.id.story_recycler_view);
        playlistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        List<Playlist> p = playlistService.getAllPlaylists();

        if (adapter == null) {
            adapter = new PlaylistAdapter(p);
            playlistRecyclerView.setAdapter(adapter);
        } else {
            adapter.setPlaylists(p);
            adapter.notifyDataSetChanged();
        }
    }

    private class PlaylistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private ImageView image;


        private Playlist playlist;

        public PlaylistHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            name = (TextView)itemView.findViewById(R.id.list_item_name);
            image = (ImageView)itemView.findViewById(R.id.list_item_image);

        }

        public void bindPlaylist(Playlist p) {
            this.playlist = p;
            name.setText(playlist.getName());
            image.setImageBitmap(playlist.getThumbNailBitmap());
        }

        @Override
        public void onClick(View view) {
            String inst = getActivity().getIntent().getStringExtra("INSTRUCTION");
            if (inst.equals("VIEW")) {
                Intent intent = new Intent(getActivity(), FitnessPlaylistBacklogActivity.class);
                intent.putExtra("ID", playlist.getId());
                startActivity(intent);

            } else {
                String fitnessId =  getActivity().getIntent().getStringExtra("ID");
                Fitness temp = DependencyFactory.getFitnessService(getActivity()).getFitnessById(fitnessId);
                playlist.addFitnessToPlayList(temp);
                DependencyFactory.getPlaylistService(getActivity()).addPlaylist(playlist);

                Intent intent = new Intent(getActivity(), FitnessActivity.class);
                startActivity(intent);
            }
        }
    }

    private class PlaylistAdapter extends RecyclerView.Adapter<PlaylistHolder> {
        private List<Playlist> playlists;

        public PlaylistAdapter(List<Playlist> p) {
            this.playlists = p;
        }

        public void setPlaylists(List<Playlist> p) {
            this.playlists = p;
        }

        @Override
        public PlaylistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_playlist, parent, false);
            return new PlaylistHolder(view);
        }

        @Override
        public void onBindViewHolder(PlaylistHolder holder, int position) {
            Playlist p = playlists.get(position);
            holder.bindPlaylist(p);
        }

        @Override
        public int getItemCount() {
            return playlists.size();
        }
    }
}
