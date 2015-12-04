package com.noctislabs.pokewho;

/**
 * The main activity
 *
 * Doesn't do anything but handle the swapping of fragments between the list and the detail
 *
 */

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LearnActivity extends AppCompatActivity {

    // Setup the two fragments
    public final PokemonListFragment pokemonListFragment = new PokemonListFragment();
    public final PokemonDetailFragment pokemonDetailFragment = new PokemonDetailFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        // Show the list view on initial creation
        ShowList();
    }

    /**
     * We need to go backwards in the fragment stack instead of trying to go back to another activity
     */
    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStackImmediate();
    }

    /**
     * Show the PokemonListFragment, This is our default view
     */
    public void ShowList() {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentHolder, pokemonListFragment, pokemonListFragment.getTag());
        transaction.addToBackStack(pokemonListFragment.getTag());
        transaction.commit();
    }

    /**
     * Load up the detail view
     * @param _id   This is the end-point for the Pokéapi service
     */
    public void ShowDetail(String _id) {

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Bundle the id and add it
        Bundle bundle = new Bundle();
        bundle.putString("PokemonID", _id);
        pokemonDetailFragment.setArguments(bundle);

        // Swap out the fragment
        transaction.replace(R.id.fragmentHolder, pokemonDetailFragment, pokemonDetailFragment.getTag());
        transaction.addToBackStack(pokemonDetailFragment.getTag());
        transaction.commit();
    }

}
