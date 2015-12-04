package com.noctislabs.pokewho;

/**
 * Creates a list view of all the Pokemon within the Pokedex
 */

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class PokemonListFragment extends ListFragment {

    /**
     * Setup our variables
     *
     * values holds our full list
     * apiHandler gives access to the APIHandler class
     * getListTask gives access to the AsyncTask to get the data
     */
    private ArrayList<PokemonListItem> values;
    private static APIHandler apiHandler = new APIHandler();
    private getListItems getListTask;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText("No Data Available");

        // Instantiate things
        values = new ArrayList<>();
        getListTask = new getListItems();
        getListTask.execute();
    }

    /**
     * List item clicked, swap to the detail fragment with the end-point of the selected pokemon
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ((LearnActivity)getActivity()).ShowDetail(values.get(position).getId());
    }

    /**
     * This sets up the adapter
     * Called after data retrieval finishes
     */
    public void CreateList() {
        PokemonArrayAdapter adapter = new PokemonArrayAdapter(getActivity(), values);
        setListAdapter(adapter);
    }

    /**
     * Create the AsyncTask to get the data on a seperate thread
     */
    private class getListItems extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Check for a data connection and cancel retrieval if not available
            if (!apiHandler.checkInternet(getActivity())) {
                getListTask.cancel(true);
                setListShown(true);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            // Use the apiHander to get the data for the pokedex
            return apiHandler.GetData("/api/v1/pokedex/1/");
        }

        @Override
        protected void onPostExecute(String result) {
            // If we didn't get a failed message then parse the results and display the list
            if (!result.equals("Failed")) {
                values = apiHandler.parseListItems(result);
            }
            CreateList();
        }

    }

}
