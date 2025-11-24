package com.cutiedeng.util;

import org.objectweb.asm.*;

import java.util.*;
import java.io.*;

public class StringUtil {
  public static void toQuotedString(PrintStream out, String content) {
    if (content.contains("\"")) {
      throw new IllegalArgumentException(String.format("invalid string for quoted: '%s'", content));
    }
    out.printf("\"");
    out.printf("%s", content);
    out.printf("\"");
    out.printf(" ");
  }
  public static void toQuotedStrings(PrintStream out, ArrayList<String> cs) {
    for (String c: cs) {
      toQuotedString(out, c);
      out.printf(" ");
    }
  }
}
