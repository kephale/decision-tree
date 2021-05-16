(ns decision-tree.tree-test
  (:require [clojure.test :refer :all]
            [decision-tree.test-util :refer :all]
            [decision-tree.tree :refer :all]
            [clj-random.core :as random]))

(deftest apply-tree-test
  (testing "apply-tree"
    (is (= (apply-tree [:weather
                        {"Sunny" [:late-at-work
                                  {"yes" [:just-ate
                                          {"no" "no"
                                           "yes" "no"}]
                                   "no" [:just-ate {"no" "no"
                                                    "yes" "no"}]}]
                         "Rainy" "no"}]
                       (first dataset))
           "no"))))

(deftest fit-decision-tree-test
  (testing "fit-decision-tree"
    (random/with-rng
      (random/make-java-rng
       (byte-array [88, 8, -59, 121, -35, -28, 70, 31]))
      ;; Test against a hardcoded tree
      (is (compare-trees (fit-decision-tree :will-go-running
                                            (disj (set (keys (first dataset)))
                                                  :will-go-running
                                                  :day)
                                            dataset)
                         [:weather
                          {"Sunny" "yes",
                           "Rainy" [:late-at-work {"yes" "no", "no" [:just-ate {"no" "no"}]}]}]))
      ;; Show that we get different trees on different datasets
      (is (not (compare-trees (fit-decision-tree :will-go-running
                                                 (disj (set (keys (first dataset)))
                                                       :will-go-running
                                                       :day)
                                                 dataset)
                              (fit-decision-tree :will-go-running
                                                 (disj (set (keys (first dataset)))
                                                       :will-go-running
                                                       :day)
                                                 (rest dataset)))))
      ;; Show that trees can have the same output
      (is (compare-tree-output
           (fit-decision-tree :will-go-running
                              (disj (set (keys (first dataset)))
                                    :will-go-running
                                    :day)
                              dataset)
           [:weather
            {"Sunny" "yes",
             "Rainy" [:late-at-work {"yes" "no", "no" [:just-ate {"no" "yes"}]}]}]
           dataset))
      ;; A trivial example to show that it works with non-keys
      (is (compare-tree-output
           (fit-decision-tree :will-go-running                              
                              (set (doall
                                    (map #(comp hash %)
                                         (disj (set (keys (first dataset)))
                                               :will-go-running
                                               :day))))
                              dataset)
           [:weather
            {"Sunny" "yes",
             "Rainy" [:late-at-work {"yes" "no", "no" [:just-ate {"no" "no"}]}]}]
           dataset)))))
