package org.tyoda.wurmunlimited.mods.CommonLibrary;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Terraforming;
import com.wurmonline.server.villages.Villages;
import com.wurmonline.server.zones.Zones;

import java.util.Random;
import java.util.logging.Logger;

public class WorldPosition2D {
    public static final Random random = CommonLibrary.random;
    public static final Logger logger = Logger.getLogger(WorldPosition2D.class.getName());
    private int tileX = 0;
    private int tileY = 0;
    private float meterX = 0;
    private float meterY = 0;
    public WorldPosition2D(){}
    public WorldPosition2D(float x, float y){
        setMeterPos(x, y);
    }
    public WorldPosition2D(int x, int y){
        setTilePos(x, y);
    }
    public void setTilePos(int x, int y){
        tileX = x;
        tileY = y;
        meterX = x<<2;
        meterY = x<<2;
    }
    public void setMeterPos(float x, float y){
        meterX = x;
        meterY = y;
        tileX = (int)x>>2;
        tileY = (int)y>>2;
    }
    public static WorldPosition2D getRandomPos(){
        return getRandomPos(200, 500);
    }
    public static WorldPosition2D getRandomPos(int padding, int maxTries){
        // This code is mostly copied from
        // https://github.com/dmon82/TreasureHunting/blob/master/src/com/pveplands/treasurehunting/Treasuremap.java
        WorldPosition2D pos = new WorldPosition2D();
        int tries = 0;
        boolean foundPos = false, isWaterOrLava;

        int waterCount = 0, altarCount = 0, villageCount = 0;

        while (!foundPos && tries < maxTries) {
            pos.setMeterPos(
                    random.nextInt((int)Zones.worldMeterSizeX - padding * 2) + padding,
                    random.nextInt((int)Zones.worldMeterSizeY - padding * 2) + padding
            );

            isWaterOrLava = false;

            for (int x = pos.tileX-1; x <= pos.tileX + 1; ++x) {
                for (int y = pos.tileY-1; y <= pos.tileY + 1; ++y) {
                    int tile = Server.surfaceMesh.getTile(x, y);

                    if (Terraforming.isTileUnderWater(tile, x, y, true)
                            || Tiles.decodeType(tile) == Tiles.Tile.TILE_LAVA.id) {
                        isWaterOrLava = true;
                        break;
                    }
                }
                if(isWaterOrLava) break;
            }

            if (isWaterOrLava) { ++waterCount; continue; }
            if (Terraforming.isAltarBlocking(null, pos.tileX, pos.tileY)) { ++altarCount; continue; }
            if (Villages.getVillageWithPerimeterAt(pos.tileX, pos.tileY, true)!=null){++villageCount;continue;}

            foundPos = true;
        }
        if(!foundPos){
            logger.warning(
                    String.format("Could not generate suitable random position. Failures were: Water/Lava-%d  Altar-%d  Village-%d",
                            waterCount, altarCount, villageCount));
            pos = null;
        }
        return pos;
    }
    public int getTileX(){
        return this.tileX;
    }
    public int getTileY(){
        return this.tileY;
    }
    public float getMeterX(){
        return this.meterX;
    }
    public float getMeterY(){
        return this.meterY;
    }
}
