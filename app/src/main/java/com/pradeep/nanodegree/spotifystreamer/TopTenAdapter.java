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

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by Pradeep on 6/7/15.
 */
public class TopTenAdapter extends ArrayAdapter<Track> {
    Context context;
    List<Track> trackList;
    static LayoutInflater inflater = null;


    public TopTenAdapter(Context context, List<Track> trackList) {
        super(context, R.layout.list_item_top_ten);
        this.context = context;
        this.trackList = trackList;
        inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return trackList.size();
    }

    @Override
    public Track getItem(int position) {
        return trackList.get(position);
    }

    private class ViewHolder {
        ImageView ivThumbnail;
        TextView tvTrack, tvAlbum;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        View row = convertView;

        String track = trackList.get(position).name;

        String album = trackList.get(position).album.name;



        int size = trackList.get(position).album.images.size();

        if (row == null) {

            row = inflater.inflate(R.layout.list_item_top_ten, null);
            holder = new ViewHolder();
            holder.ivThumbnail = (ImageView) row.findViewById(R.id.ivAlbum);
            holder.tvAlbum = (TextView) row.findViewById(R.id.tvAlbum);
            holder.tvTrack = (TextView) row.findViewById(R.id.tvTrack);

            row.setTag(holder);
        } else {

            holder = (ViewHolder) row.getTag();

            holder.tvTrack.setText(track);
            holder.tvAlbum.setText(album);

            if(size!=0) {
                String url = trackList.get(position).album.images.get(0).url;
                Picasso.with(context).load(url).into(holder.ivThumbnail);
            }else{
               holder.ivThumbnail.setImageResource(R.drawable.ic_default_music);
            }


        }

        return row;

    }
}
