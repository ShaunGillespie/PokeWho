package com.noctislabs.pokewho;

/**
 * Holds all the data for a single Pokemon
 */

public class PokemonItem {

    /**
     * Variables
     */
    private String name;
    private String species;
    private String hp;
    private String attack;
    private String specialAttack;
    private String speed;
    private String defense;
    private String specialDefense;
    private String types;
    private String abilities;
    private String evolution;
    private String evolutionId;

    /**
     * Empty Constructor
     */
    public PokemonItem() {}

    /**
     * Complete Constructor
     * @param name              Pokemon Name
     * @param species           Pokemon Species
     * @param hp                Pokemon HP
     * @param attack            Pokemon Attack Power
     * @param specialAttack     Pokemon Special Attack Power
     * @param speed             Pokemon Speed
     * @param defense           Pokemon Defense
     * @param specialDefense    Pokemon Special Defense
     * @param types             Pokemon Types List
     * @param abilities         Pokemon Abilities List
     * @param evolution         Evolution Form
     * @param evolutionId       ID Of Next Evolution
     */
    public PokemonItem(String name, String species, String hp, String attack, String specialAttack, String speed, String defense, String specialDefense, String types, String abilities, String evolution, String evolutionId) {
        this.name = name;
        this.species = species;
        this.hp = hp;
        this.attack = attack;
        this.specialAttack = specialAttack;
        this.speed = speed;
        this.defense = defense;
        this.specialDefense = specialDefense;
        this.types = types;
        this.abilities = abilities;
        this.evolution = evolution;
        this.evolutionId = evolutionId;
    }

    /**
     * Getters & Setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(String specialAttack) {
        this.specialAttack = specialAttack;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(String specialDefense) {
        this.specialDefense = specialDefense;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public String getEvolutionId() {
        return evolutionId;
    }

    public void setEvolutionId(String evolutionId) {
        this.evolutionId = evolutionId;
    }
}
