package com.passin.pmvp.integration.cache;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/20 21:32
 * </pre>
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;

public interface Cache<K, V> {

interface Factory {

    /**
     * 根据 {@link CacheType} 返回一种缓存方式。
     *
     * @param type 框架中需要缓存的模块类型
     * @return
     */
    @NonNull
    Cache build(CacheType type);

}

    /**
     * 返回当前缓存已占用的总 size
     *
     * @return
     */
    int size();

    /**
     * 返回当前缓存所能允许的最大 size
     *
     * @return
     */
    int getMaxSize();

    /**
     * 返回这个 {@code key} 在缓存中对应的 {@code value}, 如果返回 {@code null} 说明这个 {@code key} 没有对应的 {@code value}
     *
     * @param key
     * @return
     */
    @Nullable
    V get(K key);

    /**
     * 将 {@code key} 和 {@code value} 以条目的形式加入缓存,如果这个 {@code key} 在缓存中已经有对应的 {@code value}
     * 则此 {@code value} 被新的 {@code value} 替换并返回,如果为 {@code null} 说明是一个新条目
     *
     * @param key
     * @param value
     * @return
     */
    @Nullable
    V put(K key, V value);

    /**
     * 移除缓存中这个 {@code key} 所对应的条目,并返回所移除条目的 value
     * 如果返回为 {@code null} 则有可能时因为这个 {@code key} 对应的 value 为 {@code null} 或条目不存在
     *
     * @param key
     * @return
     */
    @Nullable
    V remove(K key);

    /**
     * 如果这个 {@code key} 在缓存中有对应的 value 并且不为 {@code null}, 则返回 {@code true}
     *
     * @param key
     * @return
     */
    boolean containsKey(K key);

    /**
     * 返回当前缓存中含有的所有 {@code key}
     *
     * @return
     */
    Set<K> keySet();

    /**
     * 清除缓存中所有的内容
     */
    void clear();
}
