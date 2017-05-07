package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.model.Playlist;

import java.io.File;

/**
 * Created by Thomas on 5/7/2017.
 */

public class FitnessPlaylistFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    private static final int REQUEST_CODE_CREATE_PLAYLIST = 0;

    private static final String EXTRA_PLAYLIST_CREATED = "PlaylistCreated";
    private final static String ARG_PLAYLIST_ID = "PlaylistId";
    private final static int REQUEST_PHOTO = 1;

    private ImageView photoView;
    private TextView nameOfPlaylistText;

    private String photoPathName;

    private Playlist playlist;

    private File photoFile;

    public static Playlist getFitnessCreated(Intent data) {
        return ( Playlist ) data.getSerializableExtra( EXTRA_PLAYLIST_CREATED );
    }

    public static FitnessPlaylistFragment newInstance( String fitnessId ) {
        Bundle args = new Bundle();
        args.putString( ARG_PLAYLIST_ID, fitnessId );
        Log.d( "TAG", "ARG_FITNESS_ID: " + args.toString() );

        FitnessPlaylistFragment fragment = new FitnessPlaylistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String playlistId = getArguments().getString( ARG_PLAYLIST_ID );
        playlist = DependencyFactory.getPlaylistService( getActivity().getApplicationContext() ).getPlaylistById( playlistId );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_playlist, container, false );

        photoView = ( ImageView ) view.findViewById( R.id.playlist_fitness_image);
        photoView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), FitnessDescriptionActivity.class );
                intent.putExtra(EXTRA_PLAYLIST_CREATED, playlist.getId());
                startActivity( intent );
            }
        }
        );

        nameOfPlaylistText = ( TextView ) view.findViewById( R.id.playlist_fitness_name );
        nameOfPlaylistText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), FitnessDescriptionActivity.class );
                intent.putExtra( EXTRA_PLAYLIST_CREATED, playlist.getId() );
                startActivity( intent );
            }
        }
        );

        return view;
    }
}
