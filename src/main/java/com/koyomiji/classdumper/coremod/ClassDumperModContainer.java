package com.koyomiji.classdumper.coremod;

import com.koyomiji.classdumper.Tags;
import cpw.mods.fml.common.*;

import java.util.Arrays;

public class ClassDumperModContainer extends DummyModContainer {
  public ClassDumperModContainer() {
    super(new ModMetadata());
    ModMetadata meta = getMetadata();
    meta.modId = Tags.MODID;
    meta.name = "ClassDumper";
    meta.version = Tags.VERSION;
    meta.authorList = Arrays.asList("k0michi");
  }
}
