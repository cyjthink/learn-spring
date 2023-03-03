package cn.cyj.springframework.util;

import com.sun.istack.internal.Nullable;

import java.util.Collection;

public abstract class StringUtils {

    private static final String[] EMPTY_STRING_ARRAY = {};

    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return (!CollectionUtils.isEmpty(collection) ? collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY);
    }
}
