(ns decision-tree.math-test
  (:require [clojure.test :refer :all]
            [decision-tree.test-util :refer :all]
            [decision-tree.math :refer :all]))

(deftest log2-test
  (testing "log2"
    (is (approx= (log2 8) 3 0.0001))))

(deftest entropy-test
  (testing "entropy"
    (is (approx= (entropy second
                          (concat (repeat 5 [:a :a])
                                  (repeat 9 [:b :b])))
                 0.94
                 0.001))
    (is (approx= (entropy :will-go-running dataset)                          
                 0.98522
                 0.001))    
    (is (= (entropy second
                    [[1 :a] [2 :a] [3 :b] [4 :b]])
           1.0))
    (is (= (entropy second
                    [[1 :a] [2 :a] [3 :a] [4 :a]])
           0.0))))

(deftest information-gain-test
  (testing "information-gain"
    (is (approx= (information-gain :will-go-running :weather dataset)
                 0.52164063
                 0.0001))))

(deftest gini-test
  (testing "gini-impurity"
    (is (approx= (gini-impurity :will-go-running dataset)
                 0.489796
                 0.001))))

(deftest gini-gain-test
  (testing "information-gain"
    (is (approx= (gini-gain :will-go-running :weather dataset)
                 (float (/ 27 98))
                 0.0001))))

