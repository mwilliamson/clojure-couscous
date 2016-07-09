(ns couscous.test.frontends.java-test
  (:require
    [clojure.test :refer [deftest testing is]]
    [matcha :as m]
    [couscous.frontends.java :refer [parse]]
    [couscous.frontends.java.ast :as java]))

(defn- parse-string [source] (parse "" source))

(deftest java-parser-tests
  (testing "parse enum"
    (is (=
          (java/enum-decl {:name "X", :fields ["A", "B", "C"]})
          (parse-string "enum X { A, B, C }"))))

  (testing "class parsing:"
    (testing "parse empty class in default package"
      (is (=
            (java/class-decl {:name "X"})
            (parse-string "class X {}"))))

    (testing "parse empty class in declared package"
      (is (=
            (java/class-decl {:name "com.example.X"})
            (parse-string "package com.example; class X {}"))))

    (testing "type parameters:"
      (testing "empty"
        (m/is
          (m/has-entry :type-params [])
          (parse-string "class X {}")))

      (testing "multiple invariant parameters"
        (m/is
          (m/has-entry :type-params ["T" "U"])
          (parse-string "class X<T, U> {}"))))))
