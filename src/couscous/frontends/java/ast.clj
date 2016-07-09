(ns couscous.frontends.java.ast
  (:require [plumbing.core :refer [defnk]]))

(defrecord Literal [value])

(defrecord ClassDeclaration [name, type-params])

(defnk class-decl [name {type-params []}]
  (->ClassDeclaration name type-params))

(defrecord InterfaceDeclaration [name type-params])

(defnk interface-decl [name {type-params []}]
  (->InterfaceDeclaration name type-params))

(defrecord EnumDeclaration [name fields])

(defnk enum-decl [name fields]
  (->EnumDeclaration name fields))
