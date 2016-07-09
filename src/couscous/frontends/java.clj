(ns couscous.frontends.java
  (:require [couscous.frontends.java.ast :as ast])
  (:import (org.eclipse.jdt.core.dom ASTParser AST TypeDeclaration EnumDeclaration))
  (:import (org.eclipse.jdt.core JavaCore)))

(defn- eclipse-parse [name source]
  (let [parser (ASTParser/newParser AST/JLS8)
        options (JavaCore/getOptions)]
    (.setBindingsRecovery parser false)
    (.setKind parser ASTParser/K_COMPILATION_UNIT)
    (.put options JavaCore/COMPILER_COMPLIANCE JavaCore/VERSION_1_8)
    (.put options JavaCore/COMPILER_CODEGEN_TARGET_PLATFORM JavaCore/VERSION_1_8)
    (.put options JavaCore/COMPILER_SOURCE JavaCore/VERSION_1_8)
    (.setCompilerOptions parser options)
    (.setResolveBindings parser true)
    (.setUnitName parser name)
    (let [sourcePaths ["/usr/lib/jvm/java-8-openjdk-amd64/jre/src.zip"]]
      (.setEnvironment
        parser
        (into-array String ["/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar"])
        (into-array String sourcePaths)
        (into-array (map (constantly "UTF-8") sourcePaths))
        false))
    (.setSource parser (.toCharArray source))
    (.createAST parser nil)))

(defn- qualified-name [declaration]
  (.getQualifiedName (.resolveBinding declaration)))

(defn- read-enum-field [field]
  (.getIdentifier (.getName field)))

(defn- read-enum-declaration [declaration]
  (ast/enum-decl
    {
     :name (qualified-name declaration),
     :fields (mapv read-enum-field (.enumConstants declaration))}))

(defn- read-type-parameters [type-parameters]
  (mapv (fn [param] (.getName param)) type-parameters))

(defn- read-type-declaration [declaration]
  (let [name (qualified-name declaration)
        type-params (read-type-parameters (.getTypeParameters (.resolveBinding declaration)))
        properties {
                    :name name
                    :type-params type-params}]
    (if (.isInterface declaration)
      (ast/interface-decl properties)
      (ast/class-decl properties))))


(defn- read-compilation-unit [compilation-unit]
  (let [declaration (.get (.types compilation-unit) 0)]
    (condp = (type declaration)
      TypeDeclaration (read-type-declaration declaration)
      EnumDeclaration (read-enum-declaration declaration)
      (throw (UnsupportedOperationException. (str "Unsupported type declaration: " (type declaration)))))))


(defn parse [name source]
  (let [compilation-unit (eclipse-parse name source)]
    (read-compilation-unit compilation-unit)))
