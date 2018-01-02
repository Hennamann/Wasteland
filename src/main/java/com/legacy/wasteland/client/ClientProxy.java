package com.legacy.wasteland.client;

import com.legacy.wasteland.CommonProxy;
import com.legacy.wasteland.client.renders.EntityRenderRegistry;

public class ClientProxy extends CommonProxy {
   public void preInit() {
      EntityRenderRegistry.initialize();
   }
}
