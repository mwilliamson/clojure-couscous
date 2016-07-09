(ns couscous.test.frontends.java.ast.builders
  (:require
    [couscous.frontends.java.ast :as java]
    [plumbing.core :refer [defnk]]))

(defnk class [name]
       (java/map->ClassDeclaration {:name name}))

(defnk enum [name fields]
       (java/map->EnumDeclaration {:name name, :fields fields}))
