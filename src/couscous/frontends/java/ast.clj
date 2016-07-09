(ns couscous.frontends.java.ast)

(defrecord Literal [value])

(defrecord ClassDeclaration [name])

(defrecord EnumDeclaration [name members])
