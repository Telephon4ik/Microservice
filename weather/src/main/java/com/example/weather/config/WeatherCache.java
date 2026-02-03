package com.example.weather.config;

import com.example.weather.model.Main;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherCache {

    private static class CacheEntry {
        Main data;
        long timestamp;
    }

    private final Map<String, CacheEntry> cache = new HashMap<>();
    private static final long CACHE_TTL = 60_000; // 1 минута

    public Main get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null) return null;

        long now = System.currentTimeMillis();
        if (now - entry.timestamp > CACHE_TTL) {
            cache.remove(key);
            return null;
        }

        return entry.data;
    }

    public void put(String key, Main data) {
        CacheEntry entry = new CacheEntry();
        entry.data = data;
        entry.timestamp = System.currentTimeMillis();
        cache.put(key, entry);
    }
}
