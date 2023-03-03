package cn.cyj.springframework.util;

import com.sun.istack.internal.Nullable;

import java.util.Collection;

public abstract class CollectionUtils {

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }
}
