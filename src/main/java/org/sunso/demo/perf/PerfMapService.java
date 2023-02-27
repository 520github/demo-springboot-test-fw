package org.sunso.demo.perf;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PerfMapService {
    Map<Integer, Integer> map = new ConcurrentHashMap<>();

    public void putMap(int cycleNum, int randomNun) {
        Random random = new Random(randomNun);
        for (int i = 0; i < cycleNum; i++) {
            int value = random.nextInt();
            map.put(value, value);
        }
    }
}
