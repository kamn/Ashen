(ns ashen.strips
  (:require [cljs.nodejs :as nodejs]
            [clojure.string :as string]))

(def fs (nodejs/require "fs"))
(def strips (nodejs/require "strips-sync"))
(def PEG (nodejs/require "pegjs"))

(def example-domain "(define (domain blocksworld)
  (:requirements :strips)
  (:action move
     :parameters (?b ?t1 ?t2)
     :precondition (and (block ?b) (table ?t1) (table ?t2) (on ?b ?t1) (not (on ?b ?t2))
     :effect (and (on ?b ?t2)) (not (on ?b ?t1))))
     )")


(def example-problem "(define (problem move-blocks-from-a-to-b)
      (:domain blocksworld)
    (:init (and (block a) (block b) (table x) (table y)
           (on a x) (on b x)))
    (:goal (and (on a y) (on b y)))
  )")

(defn cost [state] 10)

(defn solve-simple []
  (println (.parseAndSolve strips example-domain example-problem cost)))
