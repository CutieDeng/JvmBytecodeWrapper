#lang racket

(struct Class (name fields methods annotations) #:prefab)
(struct Field (name tt access static/init-value) #:prefab)
(struct Method (name tt access exceptions) #:prefab)
(struct Annotation (name tt) #:prefab)

(provide (struct-out Class) (struct-out Field) (struct-out Method) (struct-out Annotation))
