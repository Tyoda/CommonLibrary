package org.tyoda.wurmunlimited.mods.CommonLibrary;

import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.util.Random;

public class CommonLibrary implements WurmServerMod {
    public static final Random random = new Random();
    private static final String version = "0.0.1";
    @Override
    public String getVersion() {
        return version;
    }
}