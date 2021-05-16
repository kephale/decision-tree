(ns decision-tree.test-util
  (:require [clojure.test :refer :all]))

(defn approx=
  "Is approximately equal to within precision"
  [a b precision]
  (< (Math/abs (- a b))
     precision))
;; Example dataset from: https://bambielli.com/til/2017-10-29-gini-impurity/

;;"Will I Go Running" Data Set
(def dataset [{:day 1 :weather "Sunny" :just-ate "yes" :late-at-work "no" :will-go-running "yes"}
              {:day 2 :weather "Rainy" :just-ate "yes" :late-at-work "yes" :will-go-running "no"}
              {:day 3 :weather "Sunny" :just-ate "no" :late-at-work "yes" :will-go-running "yes"}
              {:day 4 :weather "Rainy" :just-ate "no" :late-at-work "no" :will-go-running "no"}
              {:day 5 :weather "Rainy" :just-ate "no" :late-at-work "no" :will-go-running "yes"}
              {:day 6 :weather "Sunny" :just-ate "yes" :late-at-work "no" :will-go-running "yes"}
              {:day 7 :weather "Rainy" :just-ate "no" :late-at-work "yes":will-go-running "no"}])
