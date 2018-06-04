package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

     String port;
     String memoryLimit;
     String cfInstanceIndex;
     String cfInstanceAddr;

    public EnvController(@Value("${PORT:NOT SET}") String port, @Value("${MEMORY_LIMIT:NOT SET}") String memoryLimit
            , @Value("${CF_INSTANCE_INDEX:NOT SET}") String cfInstanceIndex, @Value("${CF_INSTANCE_ADDR:NOT SET}") String cfInstanceAddr){
            this.port = port;
            this.memoryLimit = memoryLimit;
            this.cfInstanceIndex = cfInstanceIndex;
            this.cfInstanceAddr = cfInstanceAddr;

    }


    @GetMapping("/env")
    public Map<String, String> getEnv() throws Exception{
        Map<String, String > envMaps = new HashMap<String, String>() ;
        envMaps.put("PORT", port);
        envMaps.put("MEMORY_LIMIT", memoryLimit);
        envMaps.put("CF_INSTANCE_INDEX", cfInstanceIndex);
        envMaps.put("CF_INSTANCE_ADDR", cfInstanceAddr);

        return envMaps;
    }
}
