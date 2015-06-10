package com.pradeep.nanodegree.spotifystreamer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;

import kaaes.spotify.webapi.android.SpotifyService;

import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Pradeep on 6/7/15.
 */
public class ArtistTopTen extends Activity {

    String artist, id;
    ListView listTopTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        artist = getIntent().getStringExtra("ArtistName");
        id = getIntent().getStringExtra("SpotifyID");
        getActionBar().setTitle("Top 10 Tracks");
        getActionBar().setSubtitle(artist);


        listTopTen = (ListView) findViewById(R.id.listTopTen);

        GetTopTenFromSpotify obj = new GetTopTenFromSpotify();
        obj.execute(id);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class GetTopTenFromSpotify extends AsyncTask<String, String, List<Track>> {
        @Override
        protected List<Track> doInBackground(String... params) {


            List<Track> tracksList = null;

            try {

                SpotifyApi api = new SpotifyApi();

                SpotifyService spotify = api.getService();

                HashMap<String, Object> map = new HashMap<>();

                map.put("country", "US");

                Tracks tracks = spotify.getArtistTopTrack(params[0], map);

                tracksList = tracks.tracks;

            } catch (RetrofitError e) {
//                Toast.makeText(ArtistTopTen.this, "UnkownHostException! Are You Connected to the Internet?", Toast.LENGTH_SHORT).show();
                Log.e("Exception", e.toString());
            }

            return tracksList;
        }

        @Override
        protected void onPostExecute(List<Track> tracks) {
            super.onPostExecute(tracks);

            if (tracks != null && tracks.size() != 0) {

                TopTenAdapter adapter = new TopTenAdapter(ArtistTopTen.this, tracks);
                listTopTen.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } else {

                Toast.makeText(ArtistTopTen.this, "No Top Tracks", Toast.LENGTH_SHORT).show();
                listTopTen.setAdapter(null);
                listTopTen.refreshDrawableState();
            }


        }
    }
}
