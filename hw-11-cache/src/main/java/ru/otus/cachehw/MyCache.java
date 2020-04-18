package ru.otus.cachehw;

import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

  private Map<K, V> cache = new WeakHashMap<>();
  private ArrayList<HwListener> listeners = new ArrayList<>();


  @Override
  public void put(K key, V value) {
    cache.put(key, value);
    publishEvent(key, value, CacheAction.PUT);
  }

  @Override
  public void remove(K key) {
    if (cache.containsKey(key)) {
      cache.remove(key);
      publishEvent(key, null, CacheAction.REMOVE);
    }
  }

  @Override
  public V get(K key) {
    return cache.get(key);
  }

  @Override
  public void addListener(HwListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeListener(HwListener listener) {
    listeners.remove(listener);
  }

  private void publishEvent(K key, V value, CacheAction action) {
    listeners.forEach(hwListener -> {
      hwListener.notify(key, value, action.name());
    });
  }

}
