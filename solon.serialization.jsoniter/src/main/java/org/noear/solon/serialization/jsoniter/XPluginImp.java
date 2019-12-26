package org.noear.solon.serialization.jsoniter;

import org.noear.solon.XApp;
import org.noear.solon.core.XRenderManager;
import org.noear.solon.core.XPlugin;

public class XPluginImp implements XPlugin {
    public static boolean output_meta = false;

    @Override
    public void start(XApp app) {
        output_meta = app.prop().getInt("solon.output.meta", 0) > 0;

        JsoniterRender render = new JsoniterRender();

        //XRenderManager.register(render);
        XRenderManager.mapping(XRenderManager.mapping_model,render);
    }
}