(ns couscous.frontends.java.ast
  (:require [plumbing.core :refer [defnk]]))

(defrecord Literal [value])

(defrecord JavaClass [name])

(defrecord JavaEnum [name members])

(defnk class [name]
  (map->JavaClass {:name name}))

(defnk enum [name fields]
  (map->JavaEnum {:name name, :fields fields}))
