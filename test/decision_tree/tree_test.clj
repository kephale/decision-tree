(ns decision-tree.tree-test
  (:require [clojure.test :refer :all]
            [decision-tree.test-util :refer :all]
            [decision-tree.tree :refer :all]
            [clj-random.core :as random]))

(defn compare-trees
  "Compare two trees to test if they are the same."
  [a b]
  (every? identity
          (map = 
               (tree-seq vector? identity a)
               (tree-seq vector? identity b))))

(deftest fit-decision-tree-test
  (testing "fit-decision-tree"
    (random/with-rng
      (random/make-java-rng
       (byte-array [88, 8, -59, 121, -35, -28, 70, 31]))
      (is (compare-trees (fit-decision-tree :will-go-running
                                            (disj (set (keys (first dataset)))
                                                  :will-go-running
                                                  :day)
                                            dataset)
                         [:weather ["yes" [:late-at-work ["no" [:just-ate ["no"]]]]]])))))
