(ns ashen.strips
  (:require [cljs.nodejs :as nodejs]
            [clojure.string :as string]
            [ashen.tracery :refer [base-grammar walk-base-grammar gen-on-grammar]]))

(def fs (nodejs/require "fs"))
(def strips (nodejs/require "strips-sync"))
(def PEG (nodejs/require "pegjs"))

(def ashen-example-domain "(define (domain ashen)
  (:requirements :strips)
  (:action moveself
     :parameters (?s ?l1 ?l2)
     :precondition (and (self ?s) (location ?l1) (location ?l2) (at ?s ?l1) (not (at ?s ?l2)))
     :effect (and (at ?s ?l2) (not (at ?s ?l1))))
  (:action investigate
     :parameters (?s ?l1)
     :precondition (and (self ?s) (location ?l1) (at ?s ?l1))
     :effect (and (at ?s ?l1) (knows ?s ?l1))))")


(def ashen-example-problem "(define (problem move-self)
     (:domain ashen)
   (:init (and (self Ash) (location forest) (location cave)
          (at Ash forest) ))
   (:goal (and (at Ash cave) (not (knows Ash cave))))
 )")

(def ashen-example-problem2 "(define (problem investigate)
      (:domain ashen)
    (:init (and (self Ash) (location forest) (location cave)
           (at Ash forest) ))
    (:goal (and (knows Ash cave) ))
  )")

(defn to-words [str]
  (string/split str #"\s+"))


(defn walk-step-to-sentence [step]
  (def words (to-words step))
  (def correct-grammar (merge walk-base-grammar {:agent (get words 1) :location (get words 3)}))
  (gen-on-grammar correct-grammar))

(defn step-to-sentence [step]
  (let [words (to-words step)]
    (condp = (get words 0)
      "moveself" (walk-step-to-sentence step))))



(def global-domain
  {:domain "ashen"
   :requirements :strips
   :actions
      [
        {:name "move"
         :parameters [:?b :?t1 :?t2]
         :precondition [:and [:block :?b]
                             [:table :?t1]
                             [:table :?t2]
                             [:on :?b :?t1]
                             [:not [:on :?b :?t2]]]
         :effect [:and [:on :?b :?t2] [:not [:on :?b :?t1]]]}]})

(defn cost [state] 10)

(defn solve-simple-ashen []
  (.parseAndSolve strips ashen-example-domain ashen-example-problem cost))

(defn solve-simple-ashen2 []
  (.parseAndSolve strips ashen-example-domain ashen-example-problem2 cost))
