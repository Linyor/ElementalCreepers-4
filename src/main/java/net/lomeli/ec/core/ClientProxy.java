package net.lomeli.ec.core;

import net.lomeli.ec.entity.EntityCookieCreeper;
import net.lomeli.ec.entity.EntityDarkCreeper;
import net.lomeli.ec.entity.EntityEarthCreeper;
import net.lomeli.ec.entity.EntityElectricCreeper;
import net.lomeli.ec.entity.EntityFireCreeper;
import net.lomeli.ec.entity.EntityIceCreeper;
import net.lomeli.ec.entity.EntityLightCreeper;
import net.lomeli.ec.entity.EntityMagmaCreeper;
import net.lomeli.ec.entity.EntityReverseCreeper;
import net.lomeli.ec.entity.EntityWaterCreeper;
import net.lomeli.ec.entity.render.RenderBasicCreeper;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {
        super.registerRenders();
        RenderingRegistry.registerEntityRenderingHandler(EntityFireCreeper.class, new RenderBasicCreeper().setTexture("firecreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityWaterCreeper.class, new RenderBasicCreeper().setTexture("watercreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityElectricCreeper.class, new RenderBasicCreeper().setTexture("electriccreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityCookieCreeper.class, new RenderBasicCreeper().setTexture("cookiecreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityDarkCreeper.class, new RenderBasicCreeper().setTexture("darkcreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityLightCreeper.class, new RenderBasicCreeper().setTexture("lightcreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityEarthCreeper.class, new RenderBasicCreeper().setTexture("earthcreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityMagmaCreeper.class, new RenderBasicCreeper().setTexture("magmacreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityReverseCreeper.class, new RenderBasicCreeper().setTexture("reversecreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityIceCreeper.class, new RenderBasicCreeper().setTexture("icecreeper"));
    }
}
