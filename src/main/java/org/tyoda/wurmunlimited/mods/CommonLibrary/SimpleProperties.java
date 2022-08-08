package org.tyoda.wurmunlimited.mods.CommonLibrary;

import java.util.Properties;

public class SimpleProperties extends Properties {
    /**
     * The delimiter used by array functions if one
     * is not set manually
     */
    private String defaultDelimiter = ",";

    /**
     * Initializes a SimpleProperties with the values in the
     * supplied properties.
     * @param p The properties file with the values.
     */
    public SimpleProperties(Properties p){
        super(p);
    }

    /**
     * Initializes an empty SimpleProperties
     */
    public SimpleProperties(){
       super();
    }

    /**
     * Sets the default delimiter used by this SimpleProperties object
     * @param _defaultDelimiter The new default delimiter
     */
    public void setDefaultDelimiter(String _defaultDelimiter){
        defaultDelimiter = _defaultDelimiter;
    }

    /**
     * Returns the default delimiter this SimpleProperties object will use
     */
    public String getDefaultDelimiter() {
        return defaultDelimiter;
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list.
     * @param key The property key
     * @param def The default value
     * @return The value in properties if it exists or the default value
     */
    public boolean getBoolean(String key, boolean def){
        String value = getProperty(key);
        return value == null ? def : Boolean.parseBoolean(value);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list.
     * @param key The property key
     * @param def The default value
     * @return The value in properties if it exists or the default value
     */
    public byte getByte(String key, byte def){
        String value = getProperty(key);
        return value == null ? def : Byte.parseByte(value);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list.
     * @param key The property key
     * @param def The default value
     * @return The value in properties if it exists or the default value
     */
    public short getShort(String key, short def){
        String value = getProperty(key);
        return value == null ? def : Short.parseShort(value);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list.
     * @param key The property key
     * @param def The default value
     * @return The value in properties if it exists or the default value
     */
    public int getInt(String key, int def){
        String value = getProperty(key);
        return value == null ? def : Integer.parseInt(value);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list.
     * @param key The property key
     * @param def The default value
     * @return The value in properties if it exists or the default value
     */
    public float getFloat(String key, float def){
        String value = getProperty(key);
        return value == null ? def : Float.parseFloat(value);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list.
     * @param key The property key
     * @param def The default value
     * @return The value in properties if it exists or the default value
     */
    public long getLong(String key, long def){
        String value = getProperty(key);
        return value == null ? def : Long.parseLong(value);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list.
     * @param key The property key
     * @param def The default value
     * @return The value in properties if it exists or the default value
     */
    public String getString(String key, String def){
        String value = getProperty(key);
        return value == null ? def : value;
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing byte values delimited by the string returned by
     * getDefaultDelimiter()
     * @param key The property key
     * @param def The default value
     * @return An array created from the value in properties if it exists or the default value
     */
    public byte[] getByteArray(String key, byte[] def){
        return getByteArray(key, def, defaultDelimiter);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing byte values delimited by the supplied delimiter
     * @param key The property key
     * @param def The default value
     * @param delimiter The string the values are separated by
     * @return An array created from the value in properties if it exists or the default value
     */
    public byte[] getByteArray(String key, byte[] def, String delimiter){
        String value = getProperty(key);
        if(value == null) return def;

        String[] strings = value.split(delimiter);
        byte[] arr = new byte[strings.length];
        int i = 0;
        for(String s : strings)
            arr[i++] = Byte.parseByte(s);

        return arr;
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing short values delimited by the string returned by
     * getDefaultDelimiter()
     * @param key The property key
     * @param def The default value
     * @return An array created from the value in properties if it exists or the default value
     */
    public short[] getShortArray(String key, short[] def){
        return getShortArray(key, def, defaultDelimiter);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing short values delimited by the supplied delimiter
     * @param key The property key
     * @param def The default value
     * @param delimiter The string the values are separated by
     * @return An array created from the value in properties if it exists or the default value
     */
    public short[] getShortArray(String key, short[] def, String delimiter){
        String value = getProperty(key);
        if(value == null) return def;

        String[] strings = value.split(delimiter);
        short[] arr = new short[strings.length];
        int i = 0;
        for(String s : strings)
            arr[i++] = Short.parseShort(s);

        return arr;
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing integers delimited by the string returned by
     * getDefaultDelimiter()
     * @param key The property key
     * @param def The default value
     * @return An array created from the value in properties if it exists or the default value
     */
    public int[] getIntArray(String key, int[] def){
        return getIntArray(key, def, defaultDelimiter);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing integers delimited by the supplied delimiter
     * @param key The property key
     * @param def The default value
     * @param delimiter The string the values are separated by
     * @return An array created from the value in properties if it exists or the default value
     */
    public int[] getIntArray(String key, int[] def, String delimiter){
        String value = getProperty(key);
        if(value == null) return def;

        String[] strings = value.split(delimiter);
        int[] arr = new int[strings.length];
        int i = 0;
        for(String s : strings)
            arr[i++] = Integer.parseInt(s);

        return arr;
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing long values delimited
     * by the character the string returned by getDefaultDelimiter()
     * @param key The property key
     * @param def The default value
     * @return An array created from the value in properties if it exists or the default value
     */
    public long[] getLongArray(String key, long[] def){
        return getLongArray(key, def, defaultDelimiter);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing long values delimited by the supplied delimiter
     * @param key The property key
     * @param def The default value
     * @param delimiter The string the values are separated by
     * @return An array created from the value in properties if it exists or the default value
     */
    public long[] getLongArray(String key, long[] def, String delimiter){
        String value = getProperty(key);
        if(value == null) return def;

        String[] strings = value.split(delimiter);
        long[] arr = new long[strings.length];
        int i = 0;
        for(String s : strings)
            arr[i++] = Long.parseLong(s);

        return arr;
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing float values delimited by the string returned by
     * getDefaultDelimiter()
     * @param key The property key
     * @param def The default value
     * @return An array created from the value in properties if it exists or the default value
     */
    public float[] getFloatArray(String key, float[] def){
        return getFloatArray(key, def, defaultDelimiter);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing float values delimited by the supplied delimiter
     * @param key The property key
     * @param def The default value
     * @param delimiter The string the values are separated by
     * @return An array created from the value in properties if it exists or the default value
     */
    public float[] getFloatArray(String key, float[] def, String delimiter){
        String value = getProperty(key);
        if(value == null) return def;

        String[] strings = value.split(delimiter);
        float[] arr = new float[strings.length];
        int i = 0;
        for(String s : strings)
            arr[i++] = Float.parseFloat(s);

        return arr;
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing boolean values delimited
     * by the string returned by getDefaultDelimiter()
     * @param key The property key
     * @param def The default value
     * @return An array created from the value in properties if it exists or the default value
     */
    public boolean[] getBooleanArray(String key, boolean[] def){
        return getBooleanArray(key, def, defaultDelimiter);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing integers delimited by the supplied delimiter
     * @param key The property key
     * @param def The default value
     * @param delimiter The string the values are separated by
     * @return An array created from the value in properties if it exists or the default value
     */
    public boolean[] getBooleanArray(String key, boolean[] def, String delimiter){
        String value = getProperty(key);
        if(value == null) return def;

        String[] strings = value.split(delimiter);
        boolean[] arr = new boolean[strings.length];
        int i = 0;
        for(String s : strings)
            arr[i++] = Boolean.parseBoolean(s);

        return arr;
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing String values delimited
     * by the string returned by getDefaultDelimiter()
     * @param key The property key
     * @param def The default value
     * @return An array created from the value in properties if it exists or the default value
     */
    public String[] getStringArray(String key, String[] def){
        return getStringArray(key, def, defaultDelimiter);
    }

    /**
     * Gets the value associated with the key, or the default value
     * if key is not present in the properties list. The value must be
     * a String containing Strings delimited by the supplied delimiter
     * @param key The property key
     * @param def The default value
     * @param delimiter The string the values are separated by
     * @return An array created from the value in properties if it exists or the default value
     */
    public String[] getStringArray(String key, String[] def, String delimiter){
        String value = getProperty(key);
        if(value == null) return def;

        return value.split(delimiter);
    }
}



























