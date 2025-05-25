package com.koyomiji.classdumper.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({"com.koyomiji.classdumper","com.koyomiji.asmine"})
public class ClassDumperCorePlugin implements IFMLLoadingPlugin {
    static File coremodLocation;
    static File mcLocation;
    static ArrayList<String> coremodList;
    static Boolean runtimeDeobfuscationEnabled;

    static Logger logger = LogManager.getLogger("ClassDumper");
    static File outDir;
    static int formats;

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"com.koyomiji.classdumper.coremod.ClassDumperClassTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return "com.koyomiji.classdumper.coremod.ClassDumperModContainer";
    }

    @Override
    public String getSetupClass() {
        return "com.koyomiji.classdumper.coremod.ClassDumperCorePluginSetup";
    }

    @Override
    public void injectData(Map<String, Object> data) {
        if (data.containsKey("coremodLocation")) {
            coremodLocation = (File) data.get("coremodLocation");
        }

        if (data.containsKey("mcLocation")) {
            mcLocation = (File) data.get("mcLocation");
        }

        if (data.containsKey("coremodList")) {
            coremodList = (ArrayList<String>) data.get("coremodList");
        }

        if (data.containsKey("runtimeDeobfuscationEnabled")) {
            runtimeDeobfuscationEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
