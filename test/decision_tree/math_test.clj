(ns decision-tree.math-test
  (:require [clojure.test :refer :all]
            [decision-tree.math :refer :all]))

(defn approx=
  "Is approximately equal to within precision"
  [a b precision]
  (< (Math/abs (- a b))
     precision))

(deftest log2-test
  (testing "log2"
    (is (approx= (log2 8) 3 0.0001))))

;; Example dataset from: https://bambielli.com/til/2017-10-29-gini-impurity/

;;"Will I Go Running" Data Set
(def dataset [{:day 1 :weather "Sunny" :just-ate "yes" :late-at-work "no" :will-go-running "yes"}
              {:day 2 :weather "Rainy" :just-ate "yes" :late-at-work "yes" :will-go-running "no"}
              {:day 3 :weather "Sunny" :just-ate "no" :late-at-work "yes" :will-go-running "yes"}
              {:day 4 :weather "Rainy" :just-ate "no" :late-at-work "no" :will-go-running "no"}
              {:day 5 :weather "Rainy" :just-ate "no" :late-at-work "no" :will-go-running "yes"}
              {:day 6 :weather "Sunny" :just-ate "yes" :late-at-work "no" :will-go-running "yes"}
              {:day 7 :weather "Rainy" :just-ate "no" :late-at-work "yes":will-go-running "no"}])

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

