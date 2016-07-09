(ns couscous.frontends.java.ast
  (:require [plumbing.core :refer [defnk]]))

(defrecord Literal [value])

(defrecord ClassDeclaration [name type-params super-types])

(defnk class-decl [name {type-params []} {super-types []}]
  (->ClassDeclaration name type-params super-types))

(defrecord InterfaceDeclaration [name type-params super-types])

(defnk interface-decl [name {type-params []} {super-types []}]
  (->InterfaceDeclaration name type-params super-types))

(defrecord EnumDeclaration [name fields])

(defnk enum-decl [name fields]
  (->EnumDeclaration name fields))
