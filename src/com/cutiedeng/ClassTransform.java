package com.cutiedeng;

import org.objectweb.asm.*;

import java.io.*;
import java.util.*;

import com.cutiedeng.info.*;

public class ClassTransform {
  private int uselessId = 0;
  public String jarPath = "lib/asm-9.9.jar";
  public String clazzName = "com.cutiedeng.ClassTransform";
  public static void main(String[] args) throws Throwable {
    new ClassTransform().executeClazz();
  }
  public void executeClazz() throws IOException {
    ClassReader r = new ClassReader(clazzName);
    ClassBuilder b = new ClassBuilder();
    r.accept(b, 0);
    b.clazz.toString(System.out);
  }
  public DatumClass buildDatumClass() throws IOException {
    return null;
  }
  public class ClassBuilder extends ClassVisitor {
    public DatumClass clazz;
    @Override 
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      clazz = DatumClass.create(name, superName, access, interfaces);
    }
    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
      System.err.printf("=== field ===%n");
      System.err.printf("field: %s (%s)%n", name, descriptor);
      // type analyzer
      var tt = Type.getType(descriptor);
      System.err.printf("clazz name: %s%n", tt.getClassName());
      System.err.printf("internal name: %s%n", tt.getInternalName());
      if (signature != null) {
        System.err.printf("sig: %s%n", signature);
      } else {
        System.err.printf("!sig%n");
      }
      System.err.printf("access: 0x%x%n", access);
      System.err.printf("value: %s%n", value);
      System.err.printf("=========%n%n");
      DatumField f = DatumField.create(name, descriptor, access);
      clazz.fields.add(f);
      return null;
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      System.err.printf("=== method ===%n");
      System.err.printf("method: %s (%s)%n", name, descriptor);
      if (signature != null) {
        System.err.printf("sig: %s%n", signature);
      } else {
        System.err.printf("!sig%n");
      }
      System.err.printf("access: 0x%x%n", access);
      if (exceptions == null) { exceptions = new String[] {}; };
      System.err.printf("exceptions: %s%n", Arrays.asList(exceptions));
      System.err.printf("=========%n%n");
      DatumMethod m = DatumMethod.create(name, descriptor, access);
      clazz.methods.add(m);
      return null;
    }
    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
      System.err.printf("=== inner class ===%n");
      System.err.printf("name: %s(out: %s, inner: %s)%n", name, outerName, innerName);
      System.err.printf("access: 0x%x%n", access);
      System.err.printf("============%n%n");
      DatumInnerClass cl = DatumInnerClass.create(name, outerName, innerName, access);
      clazz.innerClasses.add(cl);
    }
    public ClassBuilder() {
      super(Opcodes.ASM9);
    }
  }
}
