package com.koyomiji.classdumper.coremod;

import cpw.mods.fml.common.*;

import java.util.Arrays;

public class ClassDumperModContainer extends DummyModContainer {
  public ClassDumperModContainer() {
    super(new ModMetadata());
    ModMetadata meta = getMetadata();
    meta.modId = "classdumper";
    meta.name = "ClassDumper";
    meta.version = "0.1.0";
    meta.authorList = Arrays.asList("k0michi");
  }
}
