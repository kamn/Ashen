(ns ashen.strips
  (:require [cljs.nodejs :as nodejs]
            [clojure.string :as string]))

(def fs (nodejs/require "fs"))
(def strips (nodejs/require "strips"))
(def PEG (nodejs/require "pegjs"))

(def domain-parser (nodejs/require "./grammar/domain-parser.js"))

;;(def problem-grammar "program   = result:problem { return result; }\r\n \r\nproblem = space* delimiter* \"define\" space* delimiter* \"problem\" space* name:word delimiter* space* domain:domain delimiter* req:req? delimiter* objects:objects? delimiter* states:state*\r\n{ return { name: name.join('').replace(/[,:? ]/g, ''), domain: domain, objects: objects, states: states }; }\r\n \r\ndomain = \":domain\" space* name:word delimiter*\r\n{ return name.join('').replace(/[,:? ]/g, ''); }\r\n\r\nreq = \":requirements\" req:reqType* delimiter*\r\n{\r\n  var result = [];\r\n  for (var i in req) {\r\n     result.push(req[i]);\r\n  }\r\n \r\n  return result;\r\n}\r\n \r\nreqType = space* \":\" req:word\r\n{ return req.join('').replace(/[,:?]/g, ''); }\r\n\r\nobjects = \":objects\" space* objects:object*\r\n{ return objects; }\r\n\r\nobject = parameters:objectName+ \"-\"? space* type:alphanum? space*\r\n{ return { parameters: parameters, type: type }; }\r\n\r\nobjectName = name:alphanum space*\r\n{ return name; }\r\n\r\nstate = space* delimiter* \":\" name:word space* delimiter* actions:logic*\r\n{ return { name: name.join(\'\').replace(\/[,:? ]\/g, \'\'), actions: actions }; }\r\n \r\nlogic = operation:logicOp* delimiter* action:word space* params:parameter* delimiter*\r\n{ return { operation: operation.join(\'\').replace(\/[,:? ]\/g, \'\'), action: action.join(\'\').replace(\/[,:? ]\/g, \'\'), parameters: params }; }\r\n \r\nboolean   = \"#t\" \/ \"#f\"\r\ninteger   = [1-9] [0-9]*\r\nstring    = \"\\\"\" (\"\\\\\" . \/ [^\"])* \"\\\"\"\r\nword      = word:([a-zA-Z0-9\\-]+) { return word; }\r\nalphanum  = word:([a-zA-Z0-9]+) { return word.join(\'\').replace(\/[,:? ]\/g, \'\'); }\r\nsymbol    = (!delimiter .)+\r\nspace     = [\\n\\r\\t ] \/ comment\r\nparen     = \"(\" \/ \")\"\r\nlogicOp   = \"and\" \/ \"not\"\r\ndelimiter = paren \/ space\r\ncomment   = \";\" [a-zA-Z0-9\\_\\-\\;\\?\\.\\:\\!\\@\\#\\$\\%\\^\\&\\*\\~\\(\\)\\[\\]\\{\\}\\\'\\\"\\<\\>\\,\\\/\\\\ ]* [\\n\\r]\r\n \r\nparameter = param:word space*\r\n{ return param.join(\'\').replace(\/[,:? ]\/g, \'\'); }")
(println "before parse")
(println "after parser")

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

(def temp-domain-file "strips/example-domain.txt")
(def temp-problem-file "strips/example-problem.txt")

(defn cost [state] 10)

(println (.parse domain-parser example-domain))

;;Load pegjs
;;Load grammer for domain
;;Load gramer for problem

;;Parse domain and parse problem
;;StripsManager.initializeProblem for the problem
;;domain.values = problem.values
;;for (var i in domain.actions) {
;; domain.actions[i].parameterCombinations = StripsManager.parameterCombinations(domain, domain.actions[i]));
;; }
;; aset arg1 arg2 value
;;
;;
(defn write-to-file [filename string callback]
  (.writeFile fs filename string
    (fn [err]
      (if err (println err)
        (callback)))))

(defn write-all-to-file [domain problem callback]
  (def final-func (fn [] (callback temp-domain-file temp-problem-file)))
  (def problem-func (fn [] (write-to-file temp-problem-file problem final-func)))
  (write-to-file temp-domain-file domain problem-func))


(defn solve-example-problem [domain-file problem-file]
  (strips.load domain-file problem-file
    (fn [domain problem]
       (let [solutions (strips.solve domain problem cost)]
         (println (first solutions))
         (println (.-path (first solutions)))
         (println (first (.-path (first solutions))))))))

(defn solve-simple []
 (->> solve-example-problem
   (write-all-to-file example-domain example-problem)))
