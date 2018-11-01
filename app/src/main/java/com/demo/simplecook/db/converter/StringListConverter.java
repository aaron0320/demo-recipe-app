package com.demo.simplecook.db.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.room.TypeConverter;

public class StringListConverter {
    // XXX - Consider the string split format might not be able to cater all data sources
    public static final String STRING_SPLIT_REGEX = ";;;";

    @TypeConverter
    public static List<String> toStringList(String string) {
        if (string == null) {
            return null;
        } else {
            return Arrays.asList(string.split(STRING_SPLIT_REGEX));
        }
    }

    @TypeConverter
    public static String toString(List<String> stringList) {
        if (stringList == null) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();
            for(String s : stringList) {
                builder.append(s).append(STRING_SPLIT_REGEX);
            }
            builder.setLength(builder.length() - STRING_SPLIT_REGEX.length());
            return builder.toString();
        }
    }
}