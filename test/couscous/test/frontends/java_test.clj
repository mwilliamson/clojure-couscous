(ns couscous.test.frontends.java-test
  (:require
    [clojure.test :refer [deftest testing]]
    [matcha :as m]
    [couscous.frontends.java :refer [parse]]
    [couscous.frontends.java.ast :as java]))

(defn- parse-string [source] (parse "" source))

(deftest java-parser-tests
  (testing "parse enum"
    (m/is
      (m/= (java/enum-decl {:name "X", :fields ["A", "B", "C"]}))
      (parse-string "enum X { A, B, C }")))

  (testing "class parsing:"
    (testing "parse empty class in default package"
      (m/is
        (m/= (java/class-decl {:name "X"}))
        (parse-string "class X {}")))

    (testing "parse empty class in declared package"
      (m/is
        (m/= (java/class-decl {:name "com.example.X"}))
        (parse-string "package com.example; class X {}")))

    (testing "type parameters:"
      (testing "empty"
        (m/is
          (m/has-entry :type-params [])
          (parse-string "class X {}")))

      (testing "multiple invariant parameters"
        (m/is
          (m/has-entry :type-params ["T" "U"])
          (parse-string "class X<T, U> {}"))))

    (testing "interface parsing:"
      (testing "parse empty class in declared package"
        (m/is
          (m/= (java/interface-decl {:name "com.example.X"}))
          (parse-string "package com.example; interface X {}"))))))
