package com.koyomiji.classdumper.coremod;

import com.koyomiji.asmine.sexpr.SExprClassPrinter;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.*;

public class ClassDumperClassTransformer implements IClassTransformer {
  private void mkdirs(File file) {
    File parent = file.getParentFile();
    parent.mkdirs();
  }

  @Override
  public byte[] transform(String name, String transformedName, byte[] basicClass) {
    if ((ClassDumperCorePlugin.formats & Formats.FORMAT_CLASS) != 0) {
      File classDest = new File(ClassDumperCorePlugin.outDir, transformedName.replace(".", File.separator) + ".class");
      mkdirs(classDest);

      try {
        FileOutputStream fos = new FileOutputStream(classDest);
        fos.write(basicClass);
        fos.close();
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    if ((ClassDumperCorePlugin.formats & Formats.FORMAT_JAT) != 0) {
      File jatDest = new File(ClassDumperCorePlugin.outDir, transformedName.replace(".", File.separator) + ".jat");
      mkdirs(jatDest);

      try {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(jatDest)));
        ClassReader reader = new ClassReader(basicClass);
        TraceClassVisitor visitor = new TraceClassVisitor(null, new SExprClassPrinter(), writer);
        reader.accept(visitor, 0);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

    return basicClass;
  }
}
