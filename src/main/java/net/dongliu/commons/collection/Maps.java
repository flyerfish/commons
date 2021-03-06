package net.dongliu.commons.collection;

import net.dongliu.commons.annotation.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static java.util.Collections.*;
import static java.util.Objects.requireNonNull;

/**
 * Utils method for Map
 */
public class Maps {

    /**
     * If map is null, return immutable empty map; else return map self.
     *
     * @param map the map
     * @return non-null map
     */
    public static <K, V> Map<K, V> nullToEmpty(@Nullable Map<K, V> map) {
        if (map == null) {
            return of();
        }
        return map;
    }

    /**
     * Create new immutable empty Map
     */
    public static <K, V> Map<K, V> of() {
        return emptyMap();
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    public static <K, V> Map<K, V> of(K key, V value) {
        return singletonMap(requireNonNull(key), requireNonNull(value));
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2) {
        HashMap<K, V> map = new HashMap<>();
        map.put(requireNonNull(key1), requireNonNull(value1));
        map.put(requireNonNull(key2), requireNonNull(value2));
        return unmodifiableMap(map);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3) {
        HashMap<K, V> map = new HashMap<>();
        map.put(requireNonNull(key1), requireNonNull(value1));
        map.put(requireNonNull(key2), requireNonNull(value2));
        map.put(requireNonNull(key3), requireNonNull(value3));
        return unmodifiableMap(map);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3,
                                      K key4, V value4) {

        HashMap<K, V> map = new HashMap<>();
        map.put(requireNonNull(key1), requireNonNull(value1));
        map.put(requireNonNull(key2), requireNonNull(value2));
        map.put(requireNonNull(key3), requireNonNull(value3));
        map.put(requireNonNull(key4), requireNonNull(value4));
        return unmodifiableMap(map);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3,
                                      K key4, V value4,
                                      K key5, V value5) {
        HashMap<K, V> map = new HashMap<>();
        map.put(requireNonNull(key1), requireNonNull(value1));
        map.put(requireNonNull(key2), requireNonNull(value2));
        map.put(requireNonNull(key3), requireNonNull(value3));
        map.put(requireNonNull(key4), requireNonNull(value4));
        map.put(requireNonNull(key5), requireNonNull(value5));
        return unmodifiableMap(map);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    @SafeVarargs
    public static <K, V> Map<K, V> ofEntries(Map.Entry<K, V>... entries) {
        HashMap<K, V> map = new HashMap<>();
        for (Map.Entry<K, V> entry : entries) {
            map.put(requireNonNull(entry.getKey()), requireNonNull(entry.getValue()));
        }
        return unmodifiableMap(map);
    }

    /**
     * Convert map to new immutable map, with key converted by keyMapper, and value converted by valueMapper.
     *
     * @param map         the original map
     * @param valueMapper the function to convert map value
     * @param <K>         the key type
     * @param <R>         the target key type
     * @param <V>         the original value type
     * @param <U>         the target value type
     * @return new map
     */
    public static <K, R, V, U> Map<R, U> convert(Map<K, V> map, Function<? super K, ? extends R> keyMapper,
                                                 Function<? super V, ? extends U> valueMapper) {
        requireNonNull(map);
        requireNonNull(keyMapper);
        requireNonNull(valueMapper);
        if (map.isEmpty()) {
            return Maps.of();
        }
        Map<R, U> result = new HashMap<>();
        map.forEach((k, v) -> result.put(keyMapper.apply(k), valueMapper.apply(v)));
        return unmodifiableMap(result);
    }

    /**
     * Convert map to new immutable map, with key not modified, and value converted by function.
     *
     * @param map         the original map
     * @param valueMapper the function to convert map value
     * @param <K>         the key type
     * @param <V>         the original value type
     * @param <U>         the target value type
     * @return new map
     */
    public static <K, V, U> Map<K, U> convert(Map<K, V> map, Function<? super V, ? extends U> valueMapper) {
        requireNonNull(map);
        requireNonNull(valueMapper);
        return convert(map, k -> k, valueMapper);
    }

    /**
     * Filter map to new immutable map, contains the entries accepted by predicate.
     *
     * @param map       the origin map
     * @param predicate the predicate to filter map entries
     * @param <K>       the key type
     * @param <V>       the value type
     * @return the new map
     */
    public static <K, V> Map<K, V> filter(Map<K, V> map, BiPredicate<? super K, ? super V> predicate) {
        requireNonNull(map);
        requireNonNull(predicate);
        if (map.isEmpty()) {
            return Maps.of();
        }
        Map<K, V> result = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (predicate.test(entry.getKey(), entry.getValue())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return unmodifiableMap(result);
    }

    /**
     * Merge tow maps, to a new immutable map, contains key-value in both map. If map1 and map2 contains the same key,
     * the value from map2 will be used.
     *
     * @param map1 map1
     * @param map2 map2
     * @param <K>  map key type
     * @param <V>  map value type
     * @return new map
     */
    public static <K, V> Map<K, V> merge(Map<K, V> map1, Map<K, V> map2) {
        requireNonNull(map1);
        requireNonNull(map2);
        Map<K, V> map = new HashMap<>();
        map.putAll(map1);
        map.putAll(map2);
        return unmodifiableMap(map);
    }

    /**
     * Create new immutable map from a collection of keys, the values are constructed by valueMaker.
     *
     * @param keys       the key collections
     * @param valueMaker to make value from key
     * @param <K>        key type
     * @param <V>        value type
     * @return new map
     */
    public static <K, V> Map<K, V> fromKeys(Collection<? extends K> keys, Function<? super K, ? extends V> valueMaker) {
        requireNonNull(keys);
        requireNonNull(valueMaker);
        if (keys.isEmpty()) {
            return Maps.of();
        }
        Map<K, V> map = new HashMap<>();
        for (K key : keys) {
            map.put(key, valueMaker.apply(key));
        }
        return unmodifiableMap(map);
    }

    /**
     * Create new immutable map from a collection of values, the values are constructed by keyMaker.
     *
     * @param values   the value collections
     * @param keyMaker to get key from value
     * @param <K>      key type
     * @param <V>      value type
     * @return new map
     */
    public static <K, V> Map<K, V> fromValues(Collection<? extends V> values,
                                              Function<? super V, ? extends K> keyMaker) {
        requireNonNull(values);
        requireNonNull(keyMaker);
        if (values.isEmpty()) {
            return Maps.of();
        }
        Map<K, V> map = new HashMap<>();
        for (V value : values) {
            map.put(keyMaker.apply(value), value);
        }
        return unmodifiableMap(map);
    }
}
