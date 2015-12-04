package com.noctislabs.pokewho;

/**
 * Handles all of the API functionality
 *
 * checkInternet will return a true for active internet, false for inactive internet.
 * getData will pull the data from the Pokéapi service
 * parseListItems returns an ArrayList with all the pokemons from the JSON string returned by getData
 * parsePokemon returns a PokemonItem for a single pokemon from the JSON string returned by getData
 *
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class APIHandler {

    /**
     * Used to check for a data connection. Will alert the user if connection is unavailable.
     *
     * @param _context  Context
     * @return boolean to show if a data connection is available.
     */
    public boolean checkInternet(final Context _context) {

        // Create the ConnectivityManager and check for active network
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            // Connection is good
            return true;
        } else {
            // No connection, alert the user and return a false.
            new AlertDialog.Builder(_context)
                    .setTitle("No Internet")
                    .setMessage("Internet connectivity is unavailable, please enable your internet connection and try again.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return false;
        }
    }

    /**
     * Gets the JSON string from the Pokéapi service
     *
     * @param location This is the end-point on the service
     * @return  String JSON data
     */
    public String GetData(String location) {
        try {
            URL url = new URL("http://pokeapi.co/" + location);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return parseStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Failed";
    }

    /**
     * Parses the InputStream from GetData into a JSON string.
     *
     * @param inputStream   this is the GetData Stream
     * @return  String      The JSON string that is needed by GetData
     */
    private static String parseStream(InputStream inputStream) {
        // Create a StringBuilder object
        StringBuilder sb = new StringBuilder();
        try {
            // Buffer and read line by line
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                // Append to the StringBuilder
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // All done, return it
        return sb.toString();
    }

    /**
     * Parses the string returned by GetData into an ArrayList
     *
     * @param itemList  JSON String from GetData
     * @return  ArrayList<PokemonListItem> used to populate the PokemonListFragment
     */
    public ArrayList<PokemonListItem> parseListItems(String itemList) {
        // Instantiate the ArrayList
        ArrayList<PokemonListItem> pokemonListItems = new ArrayList<>();

        try {
            // Convert the String to a JSONObject
            JSONObject items = new JSONObject(itemList);

            // Get the full list of pokemon
            JSONArray pokemonItems = items.getJSONArray("pokemon");

            // Cycle through the JSONArray and add them to the ArrayList
            for (int i=0; i < pokemonItems.length(); i++)
            {
                try {
                    // Pull each one out, add it and move on
                    JSONObject pokemon = pokemonItems.getJSONObject(i);
                    PokemonListItem pokemonListItem = new PokemonListItem(pokemon.getString("name"), pokemon.getString("resource_uri"));
                    pokemonListItems.add(pokemonListItem);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // All done, return the ArrayList
        return pokemonListItems;

    }

    /**
     * Parses the string returned by GetData into a PokemonItem
     *
     * @param pokemonJSON   Retuned by GetData
     * @return              PokemonItem containing a single pokemon
     */
    public PokemonItem parsePokemon(String pokemonJSON) {

        // Instantiate the PokemonItem
        PokemonItem pokemonItem = new PokemonItem();

        try {
            JSONObject pokemon = new JSONObject(pokemonJSON);

            // Set the easy text fields from strings within the JSONObject
            pokemonItem.setName(pokemon.getString("name"));
            pokemonItem.setSpecies(pokemon.getString("species"));
            pokemonItem.setHp(pokemon.getString("hp"));
            pokemonItem.setAttack(pokemon.getString("attack"));
            pokemonItem.setSpecialAttack(pokemon.getString("sp_atk"));
            pokemonItem.setSpeed(pokemon.getString("speed"));
            pokemonItem.setDefense(pokemon.getString("defense"));
            pokemonItem.setSpecialDefense(pokemon.getString("sp_def"));
            pokemonItem.setSpecialDefense(pokemon.getString("sp_def"));

            // Types are an array, get the array and parse it into a single string with the name of the type
            JSONArray typesArray = pokemon.getJSONArray("types");

            for (int i=0; i < typesArray.length(); i++)
            {
                try {
                    JSONObject type = typesArray.getJSONObject(i);
                    if (i == 0) {
                        pokemonItem.setTypes(type.getString("name"));
                    } else {
                        pokemonItem.setTypes(pokemonItem.getTypes() + ", " + type.getString("name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Abilities are an array, get the array and parse it into a single string with the name of the ability
            JSONArray abilitiesArray = pokemon.getJSONArray("abilities");
            for (int i=0; i < abilitiesArray.length(); i++)
            {
                try {
                    JSONObject ability = abilitiesArray.getJSONObject(i);
                    if (i == 0) {
                        pokemonItem.setAbilities(ability.getString("name"));
                    } else {
                        pokemonItem.setAbilities(pokemonItem.getAbilities() + ", " + ability.getString("name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Get the evolutionary pokemon name and end_point
            if (pokemon.getJSONArray("evolutions").length() > 0) {
                JSONObject evolution = pokemon.getJSONArray("evolutions").getJSONObject(0);
                pokemonItem.setEvolution(evolution.getString("to"));
                pokemonItem.setEvolutionId(evolution.getString("resource_uri"));
            } else {
                // No evolution, set a default string
                pokemonItem.setEvolution("No Evolution");
                pokemonItem.setEvolutionId("-1");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // All done, send it back.
        return pokemonItem;
    }

}
