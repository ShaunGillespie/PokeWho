package com.noctislabs.pokewho;

/**
 * The details page, shows details of a single pokemon
 */

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PokemonDetailFragment extends Fragment implements View.OnClickListener {

    /**
     * Variables
     */
    private View view;
    private String pokemonID;
    private PokemonItem pokemonItem;
    private ProgressBar spinner;

    private static APIHandler apiHandler = new APIHandler();
    private getPokemon getPokemonTask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pokemon_details,
                container, false);

        // Instantiate things
        pokemonID = this.getArguments().getString("PokemonID");
        getPokemonTask = new getPokemon();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get the pokemon data
        getPokemonTask.execute(pokemonID);
    }

    /**
     * Displays the data on the view
     */
    private void setPokemonData() {

        // Spinner while loading, we need to hide it once we have data
        spinner = (ProgressBar)view.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        // Get textviews
        TextView name = (TextView)view.findViewById(R.id.nameTextView);
        TextView species = (TextView)view.findViewById(R.id.speciesTextView);
        TextView hp = (TextView)view.findViewById(R.id.HPTextView);
        TextView attack = (TextView)view.findViewById(R.id.attackTextView);
        TextView specialAttack = (TextView)view.findViewById(R.id.spAttackTextView);
        TextView speed = (TextView)view.findViewById(R.id.speedTextView);
        TextView defense = (TextView)view.findViewById(R.id.defenseTextView);
        TextView specialDefense = (TextView)view.findViewById(R.id.spDefenseTextView);
        TextView types = (TextView)view.findViewById(R.id.typesTextView);
        TextView abilities = (TextView)view.findViewById(R.id.abilitiesTextView);
        TextView evolution = (TextView)view.findViewById(R.id.evolutionTextView);

        // Setup all the text
        name.setText(pokemonItem.getName());
        species.setText(pokemonItem.getSpecies());
        hp.setText(pokemonItem.getHp());
        attack.setText(pokemonItem.getAttack());
        specialAttack.setText(pokemonItem.getSpecialAttack());
        speed.setText(pokemonItem.getSpeed());
        defense.setText(pokemonItem.getDefense());
        specialDefense.setText(pokemonItem.getSpecialDefense());
        types.setText(pokemonItem.getTypes());
        abilities.setText(pokemonItem.getAbilities());

        // We don't always have an evolution
        // Set it to black with no click by default
        evolution.setText(pokemonItem.getEvolution());
        evolution.setTextColor(Color.BLACK);
        evolution.setOnClickListener(null);

        // If we do have evolution data the set the clicklistener and change the color
        if (!pokemonItem.getEvolution().equals("No Evolution")) {
            evolution.setTextColor(Color.BLUE);
            evolution.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        // Evolution clicked, swap in a new pokemon and reshow the spinner while it loads
        spinner.setVisibility(View.VISIBLE);

        getPokemonTask = new getPokemon();
        getPokemonTask.execute(pokemonItem.getEvolutionId());
    }

    /**
     * AsyncTask to get the pokemon on a separate thread
     */
    private class getPokemon extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Check for internet, cancel data pull if not available
            if (!apiHandler.checkInternet(getActivity())) {
                getPokemonTask.cancel(true);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            // Get the data with the end-point provided
            return apiHandler.GetData(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // If we have data then show it.
            if (!result.equals("Failed")) {
                pokemonItem = apiHandler.parsePokemon(result);
                setPokemonData();
            }
        }

    }

}
