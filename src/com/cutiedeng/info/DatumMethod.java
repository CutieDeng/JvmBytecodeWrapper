package com.cutiedeng.info;

import org.objectweb.asm.*;

import java.io.*;
import java.util.*;

import com.cutiedeng.util.*;

public class DatumMethod {
  public String name;
  public String descriptor;
  public ArrayList<String> access = new ArrayList();
  public static DatumMethod create(String name, String descriptor, int access) {
    DatumMethod self = new DatumMethod();
    self.name = name;
    self.descriptor = descriptor;
    AccessUtil.parseAccess(self.access, access);
    return self;
  }
  public void toString(PrintStream out) {
    out.printf("#s(Method ");
    // name
    StringUtil.toQuotedString(out, name);
    // descriptor
    StringUtil.toQuotedString(out, descriptor);
    // access
    out.printf("(");
    StringUtil.toQuotedStrings(out, access);
    out.printf(") ");
    // exceptions
    out.printf("() ");
    out.printf(") ");
  }
}
