#lang racket

(require "core.rkt")
(require "jvm-block.rkt")
(require "jvm-method.rkt")
(require "jvm-method-impl.rkt")

(define a (file->value "a"))

(define (Class-method-find-by-name clazz name)
  (for/first ([m (Class-methods clazz)] #:when (equal? name (Method-name m))) m)
)
