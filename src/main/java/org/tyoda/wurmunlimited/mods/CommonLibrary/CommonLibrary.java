package org.tyoda.wurmunlimited.mods.CommonLibrary;

import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.util.Random;
import java.util.logging.Logger;

public class CommonLibrary implements WurmServerMod {
    public static final Random random = new Random();
    public static final Logger logger = Logger.getLogger(CommonLibrary.class.getName());
    private static final String version = "0.0.5";
    @Override
    public String getVersion() {
        return version;
    }
}