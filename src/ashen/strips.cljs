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
     :precondition (and (block ?b) (table ?t1) (table ?t2) (on ?b ?t1) (not (on ?b ?t2)))
     :effect (and (on ?b ?t2) (not (on ?b ?t1)))))")

(def example-problem "(define (problem move-blocks-from-a-to-b)
      (:domain blocksworld)
    (:init (and (block a) (block b) (table x) (table y)
           (on a x) (on b x)))
    (:goal (and (on a y) (on b y)))
  )")

(def example-domain2 "(define (domain starcraft)
  (:requirements :strips :typing)
  (:types builder building area)
  (:action move
     :parameters (?b - builder ?t1 - area ?t2 - area)
     :precondition (and (scv ?b) (location ?t1) (location ?t2) (at ?b ?t1) (not (at ?b ?t2))
     :effect (and (at ?b ?t2)) (not (at ?b ?t1))))
  (:action collect-minerals
     :parameters (?b - builder ?t1 - area)
     :precondition (and (scv ?b) (location ?t1) (minerals ?t1) (not (empty ?t1)) (at ?b ?t1))
     :effect (and (empty ?t1) (collected-minerals ?b)))
  (:action build-supply-depot
     :parameters (?b - builder ?t1 - area)
     :precondition (and (scv ?b) (location ?t1) (collected-minerals ?b) (at ?b ?t1) not (building ?t1) not (minerals ?t1))
     :effect (and (building ?t1) (depot ?t1) not (collected-minerals ?b)))
  (:action build-barracks
     :parameters (?b - builder ?t1 - area ?t2 - area)
     :precondition (and (scv ?b) (location ?t1) (location ?t2) (depot ?t2) (collected-minerals ?b) (at ?b ?t1) not (building ?t1) not (minerals ?t1))
     :effect (and (building ?t1) (barracks ?t1) not (collected-minerals ?b)))
  (:action build-factory
     :parameters (?b - builder ?t1 - area  ?t2 - area)
     :precondition (and (scv ?b) (location ?t1) (location ?t2) (barracks ?t2) (collected-minerals ?b) (at ?b ?t1) not (building ?t1) not (minerals ?t1))
     :effect (and (building ?t1) (factory ?t1) not (collected-minerals ?b)))
  (:action build-starport
     :parameters (?b - builder ?t1 - area  ?t2 - area)
     :precondition (and (scv ?b) (location ?t1) (location ?t2) (factory ?t2) (collected-minerals ?b) (at ?b ?t1) not (building ?t1) not (minerals ?t1))
     :effect (and (building ?t1) (starport ?t1) not (collected-minerals ?b)))
  (:action build-fusion-core
     :parameters (?b - builder ?t1 - area  ?t2 - area)
     :precondition (and (scv ?b) (location ?t1) (location ?t2) (starport ?t2) (collected-minerals ?b) (at ?b ?t1) not (building ?t1) not (minerals ?t1))
     :effect (and (building ?t1) (fusion-core ?t1) not (collected-minerals ?b)))
  (:action train-marine
     :parameters (?b - builder ?t1 - area)
     :precondition (and (collected-minerals ?b) (barracks ?t1))
     :effect (and (marine ?t1) not (collected-minerals ?b)))
  (:action train-tank
     :parameters (?b - builder ?t1 - area)
     :precondition (and (collected-minerals ?b) (factory ?t1))
     :effect (and (tank ?t1) not (collected-minerals ?b)))
  (:action train-wraith
     :parameters (?b - builder ?t1 - area)
     :precondition (and (collected-minerals ?b) (starport ?t1))
     :effect (and (wraith ?t1) not (collected-minerals ?b)))
  (:action train-battlecruiser
     :parameters (?b - builder ?t1 - area ?t2 - area)
     :precondition (and (collected-minerals ?b) (starport ?t1) (fusion-core ?t2))
     :effect (and (battlecruiser ?t1) not (collected-minerals ?b)))
)")

(def example-problem2 "(define (problem build-barracks)
  (:domain starcraft)
  (:objects
    scv - builder
    sectorA sectorB mineralFieldA mineralFieldB - area)
  (:init (and (scv scv) (location sectorA) (location sectorB)
         (location mineralFieldA) (location mineralFieldB)
         (minerals mineralFieldA) (minerals mineralFieldB)
         (at scv sectorA)))
  (:goal (and (barracks sectorA)))
  )")

(def ashen-example-domain "(define (domain ashen)
  (:requirements :strips)
  (:action moveself
     :parameters (?s ?l1 ?l2)
     :precondition (and (self ?s) (location ?l1) (location ?l2) (at ?s ?l1) (not (at ?s ?l2)))
     :effect (and (at ?s ?l2) (not (at ?s ?l1)))))")


(def ashen-example-problem "(define (problem move-self)
     (:domain ashen)
   (:init (and (self s) (location x) (location y)
          (at s x) ))
   (:goal (and (at s y) ))
 )")

(defn to-words [str]
  (string/split str #"\s+"))

;;(defn solution-to-sentence [solution])


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

(defn solve-simple []
  (println (.parseAndSolve strips example-domain2 example-problem2 cost)))

(defn solve-simple-ashen []
  (.parseAndSolve strips ashen-example-domain ashen-example-problem cost))
