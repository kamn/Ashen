(ns ashen.agent
  (:require [cljs.nodejs :as nodejs]
            [clojure.string :as string]
            [clojure.data :as data]))


;; Emotions
;;"Classical"
;; happy
;; surprised
;; afraid
;; disgusted
;; angry
;; sad

;; happy
;; sad
;; afriad/surprised
;; angry/disgusted

(def base-emtions
  {:hapiness 0
   :sadness 0
   :fear 0
   :angry 0})

(def base-status
  {:location ""
   :items []
   :emotions {}
   :asleep true})

(def base-state
  {:name "Agent" ;; Simple enough to explain
   :status {}
   :prev-status {}}) ;;More complex


;;
(defn create-agent [name location]
  (merge base-state {:name name :status (merge base-status {:location location})}));})}))

;;
(defn update-agent [agent path value]
  (merge agent
         {:status (assoc-in (:status agent) path value)}))

;;
(defn tick-agent [agent]
  (merge agent
         {:prev-status (:status agent)}))

;;
(defn asleep-change [agent-state status-diff]
  (if-not (:asleep status-diff)
    (str (:name agent-state) " woke.")
    (str (:name agent-state) " fell asleep.")))

;;
(defn location-change [agent-state status-diff]
  (str (:name agent-state) " was in " (:location status-diff) "."))

;;
(defn explain-state-change [agent]
  (let [diffd (data/diff (:status agent) (:prev-status agent))
        diff-first (first diffd)]
      (println diff-first)
      (filterv some?
        [(when (contains? diff-first :asleep) (asleep-change agent diff-first))
         (when (contains? diff-first :location) (location-change agent diff-first))])))
