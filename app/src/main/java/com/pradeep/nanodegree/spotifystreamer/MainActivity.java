package com.pradeep.nanodegree.spotifystreamer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.UnknownHostException;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;


public class MainActivity extends Activity {

    EditText etArtist;
    ListView listSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState==null){
            Log.d("!!!!!","NULL");
        }else{
            Log.d("!!!!!","NOT NULL");
        }

        setContentView(R.layout.activity_main);

        etArtist = (EditText) findViewById(R.id.etArtist);

        listSearchResult = (ListView) findViewById(R.id.listSearchResult);

        listSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = (Artist) parent.getItemAtPosition(position);

                Intent in = new Intent(MainActivity.this, ArtistTopTen.class);

                in.putExtra("SpotifyID", artist.id);
                in.putExtra("ArtistName", artist.name);

                startActivity(in);


            }
        });


        etArtist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String artist = s.toString();
                if (!artist.equals("")) {
                    new SearchInSpotify().execute(artist);
                } else {
                    listSearchResult.setAdapter(null);
                    listSearchResult.refreshDrawableState();
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outBundle) {
        super.onSaveInstanceState(outState, outBundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class SearchInSpotify extends AsyncTask<String, String, List<Artist>> {
        @Override
        protected List<Artist> doInBackground(String... params) {
            ArtistsPager results = null;
            List<Artist> artistInfo = null;
            try {

                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();

                results = spotify.searchArtists(params[0]);
                artistInfo = results.artists.items;

            } catch (Exception e) {
               // Toast.makeText(MainActivity.this, "UnkownHostException! Are You Connected to the Internet?", Toast.LENGTH_SHORT).show();
                Log.e("Exception", e.toString());
                return null;
            }

            Log.d("!!!",artistInfo.size()+"");

            return artistInfo;
        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            super.onPostExecute(artists);

            if (artists!=null && artists.size() != 0) {

                MyAdapter adapter = new MyAdapter(MainActivity.this, artists);
                listSearchResult.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } else {

                Toast.makeText(MainActivity.this, "No Search Results", Toast.LENGTH_SHORT).show();
                listSearchResult.setAdapter(null);
                listSearchResult.refreshDrawableState();

            }

        }
    }

}
