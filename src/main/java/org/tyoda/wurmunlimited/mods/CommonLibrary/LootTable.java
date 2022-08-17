package org.tyoda.wurmunlimited.mods.CommonLibrary;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

public class LootTable {
    public static final Random random = CommonLibrary.random;
    public static final Logger logger = CommonLibrary.logger;

    public static final int ironPenny = 1;
    public static final int ironFive = 5;
    public static final int ironTwenty = 20;
    public static final int copperPenny = ironPenny * 100;
    public static final int copperFive = ironFive * 100;
    public static final int copperTwenty = ironTwenty * 100;
    public static final int silverPenny = copperPenny * 100;
    public static final int silverFive = copperFive * 100;
    public static final int silverTwenty = copperTwenty * 100;
    public static final int goldPenny = silverPenny * 100;
    public static final int goldFive = silverFive * 100;
    public static final int goldTwenty = silverTwenty * 100;

    private float rareChance = 0.0001f;
    private float supremeChance = 0.00001f;
    private float fantasticChance = 0.000001f;
    public final HashMap<Integer, ArrayList<Integer>> lootTable = new HashMap<>();

    /**
     * Initializes the lootTable.
     * @param newLootTable A Hashmap that maps the weight of the groups to the Template IDs
     *                     of the items they contain.
     */
    public LootTable(HashMap<Integer, ArrayList<Integer>> newLootTable){
        for (Integer i : newLootTable.keySet()) {
            ArrayList<Integer> arr = new ArrayList<>(newLootTable.get(i));
            this.lootTable.put(i, arr);
        }
    }
    /**
     * Randomly generates a list of items from the loottable.
     * @param maxWeight This is the maximum amount of weight the items will be worth.
     * @return An ArrayList containing the Template IDs of the generated items.
     */
    public ArrayList<Integer> getLoot(int maxWeight){
        ArrayList<Integer> loot = new ArrayList<>();
        int lootValue = 0;
        while(lootValue < maxWeight){
            int diff = maxWeight-lootValue;
            Integer[] weights = new Integer[lootTable.size()];
            weights = lootTable.keySet().toArray(weights);
            int weight;
            int tries = 0;
            while((weight = weights[random.nextInt(weights.length)]) > diff && tries++ < 1000);
            if(tries == 1000) break;

            loot.add(lootTable.get(weight).get(random.nextInt(lootTable.get(weight).size())));
            lootValue += weight;
        }
        return loot;
    }

    /**
     * Returns the appropriate types of coins for a given value. It will be
     * the fewest possible coins for that value.
     * @param moneyPool The value of the coins to be generated.
     * @return An ArrayList containing the templateIds of the generated coins.
     */
    public static ArrayList<Integer> generateCoins(int moneyPool){
        // Probably could use this guy below but idk
        // Economy.getEconomy().getCoinsFor(moneyPool);

        int currentItemTemplate = 61; // gold-twenty coins
        ArrayList<Integer> coins = new ArrayList<>(createCoins(currentItemTemplate, moneyPool, goldTwenty));
        moneyPool %= goldTwenty;
        currentItemTemplate = 57; // gold-five coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, goldFive));
        moneyPool %= goldFive;
        currentItemTemplate = 53; // gold coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, goldPenny));
        moneyPool %= goldPenny;
        currentItemTemplate = 60; // silver-twenty coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, silverTwenty));
        moneyPool %= silverTwenty;
        currentItemTemplate = 56; // silver-five coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, silverFive));
        moneyPool %= silverFive;
        currentItemTemplate = 52; // silver coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, silverPenny));
        moneyPool %= silverPenny;
        currentItemTemplate = 58; // copper-twenty coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, copperTwenty));
        moneyPool %= copperTwenty;
        currentItemTemplate = 54; // copper-five coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, copperFive));
        moneyPool %= copperFive;
        currentItemTemplate = 50; // copper coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, copperPenny));
        moneyPool %= copperPenny;
        currentItemTemplate = 59; // iron-twenty coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, ironTwenty));
        moneyPool %= ironTwenty;
        currentItemTemplate = 55; // iron-five coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, ironFive));
        moneyPool %= ironFive;
        currentItemTemplate = 51; // iron coins
        coins.addAll(createCoins(currentItemTemplate, moneyPool, ironPenny));

        return coins;
    }
    private static ArrayList<Integer> createCoins(int templateId, int moneyPool, int steps){
        ArrayList<Integer> coins = new ArrayList<>();
        while(moneyPool >= steps){
            coins.add(templateId);
            moneyPool -= steps;
        }
        return coins;
    }

    /**
     * Returns a random quality.
     * @return A float between 0 and 100;
     */
    public static float randomQuality(){
        return random.nextInt(100)+random.nextFloat();
    }

    /**
     * Returns a random rarity.
     * @return A random byte between 0 and 3 (inclusive)
     */
    public byte randomRarity(){
        return randomRarity(rareChance, supremeChance, fantasticChance);
    }

    /**
     * Returns a random rarity. If two rarities are rolled
     * to be set, the better one is returned.
     * @param rare The chance of any one item being Rare [0..1]
     * @param supreme The chance of any one item being Supreme [0..1]
     * @param fantastic The chance of any one item being Fantastic [0..1]
     * @return A random byte between 0 and 3 (inclusive)
     */
    public static byte randomRarity(float rare, float supreme, float fantastic){
        byte rarity = 0;
        if(random.nextFloat() <= rare) rarity = 1;
        if(random.nextFloat() <= supreme) rarity = 2;
        if(random.nextFloat() <= fantastic) rarity = 3;
        return rarity;
    }

    /**
     * Generates items of random quality and rarity from the supplied Template IDs
     * Chances for items being rare+ are that returned by get
     * @param templates Array of Template IDs to generate items from
     * @return ArrayList<Item> containing a list of the items generated
     */
    public ArrayList<Item> createItems(ArrayList<Integer> templates)
            throws FailedException {
        return createItems(templates, rareChance, supremeChance, fantasticChance);
    }

    /**
     * Generates items of random quality and rarity from the supplied Template IDs
     * @param templates Array of Template IDs to generate items from
     * @param rare The chance of any one item being Rare
     * @param supreme The chance of any one item being Supreme
     * @param fantastic The chance of any one item being Fantastic
     * @return ArrayList<Item> containing a list of the items generated
     */
    public static ArrayList<Item> createItems(ArrayList<Integer> templates, float rare, float supreme, float fantastic)
            throws FailedException {
        ArrayList<Item> items = new ArrayList<>();

        for(int currentTemplateId : templates) {
            try {
                items.add(ItemFactory.createItem(currentTemplateId,
                        randomQuality(), randomRarity(rare, supreme, fantastic), null));
            }catch(NoSuchTemplateException e){
                logger.severe("Template ID not found for: "+currentTemplateId);
                logger.severe(e.getMessage());
            }
        }

        Collections.shuffle(items);
        return items;
    }

    public float getRareChance() {
        return rareChance;
    }

    /**
     * Sets the chance for an item generated by this LootTable
     * to be rare
     * @param _rareChance Rare Chance. Must be between zero and one inclusive.
     */
    public void setRareChance(float _rareChance){
        if(_rareChance < 0 || _rareChance > 1) {
            throw new IllegalArgumentException("chance must be a float between zero and one, inclusive.");
        }
        rareChance = _rareChance;
    }

    public float getSupremeChance() {
        return supremeChance;
    }

    /**
     * Sets the chance for an item generated by this LootTable
     * to be supreme
     * @param _supremeChance Supreme Chance. Must be between zero and one inclusive.
     */
    public void setSupremeChance(float _supremeChance){
        if(_supremeChance < 0 || _supremeChance > 1) {
            throw new IllegalArgumentException("chance must be a float between zero and one, inclusive.");
        }
        supremeChance = _supremeChance;
    }

    public float getFantasticChance() {
        return fantasticChance;
    }

    /**
     * Sets the chance for an item generated by this LootTable
     * to be fantastic
     * @param _fantasticChance Fantastic Chance. Must be between zero and one inclusive.
     */
    public void setFantasticChance(float _fantasticChance){
        if(_fantasticChance < 0 || _fantasticChance > 1) {
            throw new IllegalArgumentException("chance must be a float between zero and one, inclusive.");
        }
        fantasticChance = _fantasticChance;
    }

    /**
     * Sets the chance for an item generated by this LootTable
     * to be fantastic
     * @param _rareChance Rare Chance. Must be between zero and one inclusive.
     * @param _supremeChance Supreme Chance. Must be between zero and one inclusive.
     * @param _fantasticChance Fantastic Chance. Must be between zero and one inclusive.
     */
    public void setRarityChances(float _rareChance, float _supremeChance, float _fantasticChance){
        if((_rareChance < 0 || _rareChance > 1)
            || (_supremeChance < 0 || _supremeChance > 1)
            || (_fantasticChance < 0 || _fantasticChance > 1)) {
            logger.severe(String.format("Chances supplied were rare: %f, supreme: %f, fantastic: %f",
                                            _rareChance, _supremeChance, _fantasticChance));
            throw new IllegalArgumentException("Each chance must be a float between zero and one, inclusive.");
        }
        rareChance = _rareChance;
        supremeChance = _supremeChance;
        fantasticChance = _fantasticChance;
    }
}