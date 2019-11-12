package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class EnvController {
    public Map<String, String> getEnv() {
        Map mp = new Map();
        mp.put("PORT",@Value("${port:NOT SET}") );
       return mp;
    }


}
