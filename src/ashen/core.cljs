(ns ashen.core
  (:require [cljs.nodejs :as nodejs]
            [clojure.string :as string]))

(nodejs/enable-util-print!)

(def fs (nodejs/require "fs"))
(def strips (nodejs/require "strips"))


(def example-domain "(define (domain blocksworld)
  (:requirements :strips)
  (:action move
     :parameters (?b ?t1 ?t2)
     :precondition (and (block ?b) (table ?t1) (table ?t2) (on ?b ?t1) (not (on ?b ?t2))
     :effect (and (on ?b ?t2)) (not (on ?b ?t1))))
     )")


(def example-problem "
  (define (problem move-blocks-from-a-to-b)
      (:domain blocksworld)
    (:init (and (block a) (block b) (table x) (table y)
           (on a x) (on b x)))
    (:goal (and (on a y) (on b y)))
  )")

;; A simple cost function for now
(defn cost [state]
  10)

(defn solve-example-problem []
  (strips.load "strips/example-domain.txt" "strips/example-problem.txt" (fn [domain problem]
    (let [solutions (strips.solve domain problem cost)]
      (println (first solutions))))))

(defn write-to-file [novel]
  (fs.writeFile "novel/ash.txt" novel, (fn [err]
    (if err
      (println err)
      (println "Novel saved!")))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [count 50000
        novel (string/join " " (repeat count "ash"))]
          (write-to-file novel)
          (solve-example-problem)
          (println "Hello, World!")))

(set! *main-cli-fn* -main)
