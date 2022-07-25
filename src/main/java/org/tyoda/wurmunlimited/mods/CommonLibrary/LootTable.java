package org.tyoda.wurmunlimited.mods.CommonLibrary;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class LootTable {
    public static final Random random = CommonLibrary.random;
    public static final int rareChance = 10000;
    public static final int supremeChance = 100000;
    public static final int fantasticChance = 1000000;
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
     * Returns the appropriate types of coins for a given value.
     * @param moneyPool The value of the coins to be generated.
     * @return An ArrayList containing the templateIds of the generated coins.
     */
    public static ArrayList<Integer> generateCoins(int moneyPool){
        int currentItemTemplate = 61; // gold-twenty coins
        int currentSteps = 20000000;
        ArrayList<Integer> coins = new ArrayList<>(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 57; // gold-five coins
        currentSteps = 5000000;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 53; // gold coins
        currentSteps = 1000000;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 60; // silver-twenty coins
        currentSteps = 200000;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 56; // silver-five coins
        currentSteps = 50000;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 52; // silver coins
        currentSteps = 10000;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 58; // copper-twenty coins
        currentSteps = 2000;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 54; // copper-five coins
        currentSteps = 500;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 50; // copper coins
        currentSteps = 100;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 59; // iron-twenty coins
        currentSteps = 20;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 55; // iron-five coins
        currentSteps = 5;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
        moneyPool %= currentSteps;
        currentItemTemplate = 51; // iron coins
        currentSteps = 1;
        coins.addAll(createCoins(currentItemTemplate, moneyPool, currentSteps));
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
    private static float randomQuality(){
        return random.nextInt(100)+random.nextFloat();
    }

    /**
     * Returns a random rarity.
     * @return A random byte between 0 and 3 (inclusive)
     */
    private static byte randomRarity(){
        return randomRarity(rareChance, supremeChance, fantasticChance);
    }
    /**
     * Returns a random rarity.
     * @param rare The chance of 1 in rare of any of the items being Rare
     * @param supreme The chance of 1 in supreme of any of the items being Supreme
     * @param fantastic The chance of 1 in fantastic of any of the items being Fantastic
     * @return A random byte between 0 and 3 (inclusive)
     */
    private static byte randomRarity(int rare, int supreme, int fantastic){
        byte rarity = 0;
        if(random.nextInt(rare) == 0) rarity = 1;
        if(random.nextInt(supreme) == 0) rarity = 2;
        if(random.nextInt(fantastic) == 0) rarity = 3;
        return rarity;
    }
    /**
     * Generates items of random quality and rarity from the supplied Template IDs
     * Rare chance is 1 in 10.000, Supreme chance is 1 in 100.000
     * Fantastic chance is 1 in 1.000.000
     * @param templates Array of Template IDs to generate items from
     * @return ArrayList<Item> containing a list of the items generated
     */
    public static ArrayList<Item> createItems(ArrayList<Integer> templates)
            throws NoSuchTemplateException, FailedException {
        return createItems(templates, rareChance, supremeChance, fantasticChance);
    }
    /**
     * Generates items of random quality and rarity from the supplied Template IDs
     * @param templates Array of Template IDs to generate items from
     * @param rare The chance of 1 in rare of any of the items being Rare
     * @param supreme The chance of 1 in supreme of any of the items being Supreme
     * @param fantastic The chance of 1 in fantastic of any of the items being Fantastic
     * @return ArrayList<Item> containing a list of the items generated
     */
    public static ArrayList<Item> createItems(ArrayList<Integer> templates, int rare, int supreme, int fantastic)
            throws NoSuchTemplateException, FailedException {
        ArrayList<Item> items = new ArrayList<>();

        for(int currentTemplateId : templates) {
            items.add(ItemFactory.createItem(currentTemplateId,
                    randomQuality(), randomRarity(rare, supreme, fantastic), null));
        }

        Collections.shuffle(items);
        return items;
    }
}