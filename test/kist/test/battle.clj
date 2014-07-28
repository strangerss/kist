(ns kist.test.battle
  (:use clojure.test
        kist.routes.battle))

(deftest test-calc-natack
  (testing "nomal"
    (is (= (calc-natack 1 1) 0))
    (is (= (calc-natack 2 1) 1))
    (is (= (calc-natack 1 2) 0))
    (is (= (calc-natack 0 0) 0))))
