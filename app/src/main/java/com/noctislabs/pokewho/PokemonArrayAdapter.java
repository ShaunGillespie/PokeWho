package com.noctislabs.pokewho;

/**
 * Basic Array Adapter for the PokemonListFragment
 * Nothing special here
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PokemonArrayAdapter extends ArrayAdapter<PokemonListItem> {

    private final Context context;
    private final ArrayList<PokemonListItem> values;

    public PokemonArrayAdapter(Context context, ArrayList<PokemonListItem> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    /**
     * Set up the row, contains just one TextView showing the title field from the PokemonListItem
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.pokemonName);
        textView.setText(values.get(position).getName());

        return rowView;
    }

}
