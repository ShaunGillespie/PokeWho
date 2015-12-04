package com.noctislabs.pokewho;

/**
 * Holds the item used to populate the ListFragment
 */

public class PokemonListItem {
    /**
     * Variables
     */
    private String name;
    private String id;

    /**
     * Empty Constructor
     */
    public PokemonListItem(){}

    /**
     * Complete constructor
     *
     * @param name  The pokemons name
     * @param id    The end-point for the Pokéapi service
     */
    public PokemonListItem(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Getters and Setters
     */

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
