#lang racket

(struct Class (name fields) #:prefab)

(struct Field (name tt access static/init-value) #:prefab)

(define a (file->value "a"))
