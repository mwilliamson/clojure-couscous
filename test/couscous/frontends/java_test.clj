(ns couscous.frontends.java-test
  (:require
    [clojure.test :refer [deftest testing is]]
    [couscous.frontends.java :refer [parse]]
    [couscous.frontends.java.ast :as java]))

(deftest java-parser-tests
  (testing "parse empty class in default package"
    (is (=
          (java/class {:name "X"})
          (parse "" "class X {}"))))

  (testing "parse empty class in declared package"
    (is (=
          (java/class {:name "com.example.X"})
          (parse "" "package com.example; class X {}"))))

  (testing "parse enum"
    (is (=
          (java/enum {:name "X", :fields ["A", "B", "C"]})
          (parse "" "enum X { A, B, C }")))))
