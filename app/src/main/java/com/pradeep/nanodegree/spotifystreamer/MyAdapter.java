package com.pradeep.nanodegree.spotifystreamer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by Pradeep on 6/7/15.
 */

public class MyAdapter extends ArrayAdapter<Artist> {

    Context context;
    List<Artist> artists;
    static LayoutInflater inflater = null;

    public MyAdapter(Activity context, List<Artist> artists) {
        super(context, R.layout.list_item_artist_result);
        this.artists = artists;
        this.context = (Activity) context;
        inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return artists.size();
    }

    public String getSpotifyID(int position){
        return artists.get(position).id;
    }

    @Override
    public Artist getItem(int position) {
        return artists.get(position);
    }



    private class ViewHolder {
        ImageView iv;
        TextView tv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        View row = convertView;

        String artist = artists.get(position).name;

        int size = artists.get(position).images.size();


        if (row == null) {

            row = inflater.inflate(R.layout.list_item_artist_result, null);

            holder = new ViewHolder();

            holder.tv = (TextView) row.findViewById(R.id.tvArtist);
            holder.iv = (ImageView) row.findViewById(R.id.ivThumbnail);

            row.setTag(holder);

        } else {

            holder = (ViewHolder) row.getTag();

            holder.tv.setText(artist);

            if (size != 0) {
                String url = artists.get(position).images.get(0).url;
                Picasso.with(context).load(url).into(holder.iv);
            } else {
                holder.iv.setImageResource(R.drawable.ic_default_music);
            }

        }

        return row;

    }
}
