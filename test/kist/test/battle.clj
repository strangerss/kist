(ns kist.test.battle
  (:use clojure.test
        kist.routes.battle))

(deftest calc-natack-test
  (testing "nomal"
    (is (= (calc-natack 1 1) 0))
    (is (= (calc-natack 2 1) 1))
    (is (= (calc-natack 1 2) 0))
    (is (= (calc-natack 0 0) 0))))

(deftest rest-hp-test
  (testing "nomal")
  (is (= (rest-hp 1 1) 0))
  (is (= (rest-hp 2 1) 1))
  (is (= (rest-hp 1 2) 0))
  (is (= (rest-hp 0 0) 0)))
