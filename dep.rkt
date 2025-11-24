#lang racket

(define deps `(
  ("com/cutiedeng/ClassTransform.java" "com/cutiedeng/info/DatumClass.java")
))

(define compile-files `(
  ; "com/cutiedeng/util/AccessUtil.java"
  ; "com/cutiedeng/info/DatumClass.java"
  "com/cutiedeng/ClassTransform.java"
))

(define entry "com/cutiedeng/ClassTransform")

(provide deps entry compile-files)
