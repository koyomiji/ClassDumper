package com.koyomiji.classdumper.coremod;

import cpw.mods.fml.relauncher.IFMLCallHook;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ClassDumperCorePluginSetup implements IFMLCallHook {
  static boolean runtimeDeobfuscationEnabled;
  static File mcLocation;
  static LaunchClassLoader classLoader;
  static File coremodLocation;
  static String deobfuscationFileName;

  @Override
  public void injectData(Map<String, Object> data) {
    if (data.containsKey("runtimeDeobfuscationEnabled")) {
      runtimeDeobfuscationEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    if (data.containsKey("mcLocation")) {
      mcLocation = (File) data.get("mcLocation");
    }

    if (data.containsKey("classLoader")) {
      classLoader = (LaunchClassLoader) data.get("classLoader");
    }

    if (data.containsKey("coremodLocation")) {
      coremodLocation = (File) data.get("coremodLocation");
    }

    if (data.containsKey("deobfuscationFileName")) {
      deobfuscationFileName = (String) data.get("deobfuscationFileName");
    }
  }

  @Override
  public Void call() {
    loadConfig();

    try {
      FileUtils.deleteDirectory(ClassDumperCorePlugin.outDir);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  public static void loadConfig() {
    File configFile = new File(new File(mcLocation, "config"), "classDumper.cfg");
    Configuration config = new Configuration(configFile);
    config.load();
    String outDir = config.get("general", "outDir", "classes", "Dump destination directory").getString();
    ClassDumperCorePlugin.outDir = new File(ClassDumperCorePlugin.mcLocation, outDir);
    String[] formats = config.get("general", "format", new String[] {"class", "jat"}, "Dump formats").setValidValues(new String[] {"class", "jat"}).getStringList();

    for (String format : formats) {
      if (format.equals("class")) {
        ClassDumperCorePlugin.formats |= Formats.FORMAT_CLASS;
      } else if (format.equals("jat")) {
        ClassDumperCorePlugin.formats |= Formats.FORMAT_JAT;
      }
    }

    if (config.hasChanged()) {
      config.save();
    }
  }
}
