;; (require 'typespec.core :reload)
;; https://github.com/clojure/core.typed/wiki/Types
(ns typespec.core
  {:lang :core.typed}
  (:require
   [clojure.repl :refer :all]
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [clojure.spec.test.alpha :as stest]
   [clojure.core.typed :as t]
   [typespec.macro :refer [defun ƒ]]
   ))

(defun add-nums (Int a Int b Int)
  (+ a b))

(defn good []
  (add-nums 3 4))

;; (defn bad []
;;   (add-nums "dog" "cat"))

(defun hello-name [Str s Str]
  (println "Hello " s))

(defn hn-call []
  (hello-name 3))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn lint []
  ;; (require *ns* :reload)
  (require 'typespec.core :reload)
  )
