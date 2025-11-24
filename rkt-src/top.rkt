#lang racket

(require "core.rkt")

(define a (file->value "a"))

(define (Class-method-find-by-name clazz name)
  (for/first ([m (Class-methods clazz)] #:when (equal? name (Method-name m))) m)
)
