#lang racket

(struct Class (name fields methods) #:prefab)
(struct Field (name tt access static/init-value) #:prefab)
(struct Method (name tt access exceptions) #:prefab)

(provide (struct-out Class) (struct-out Field) (struct-out Method))
