;; (require 'typespec.core :reload)
(ns typespec.core
  {:lang :core.typed}
  (:require
   [clojure.repl :refer :all]
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [clojure.spec.test.alpha :as stest]
   [clojure.core.typed :as t]
   ))

(t/ann add-one [t/Int :-> t/Int])
(defn add-one [x]
  (+ 1 x))

(t/ann add-two [t/Int :-> t/Int])
(defn add-two [n]
  (add-one (add-one n)))

(defn one-call []
  (add-one 2))

;; (defn two-call []
;;   (add-two "oops"))

(defmacro defun [name sig & args]
  `(do
     (t/ann ~name [~@(take-nth 2 (butlast sig)) :-> ~(last sig)])
     (defn ~name [~@(take-nth 2 (rest sig))]
       ~@args)))

(s/fdef add-nums
  :args (s/cat :a int? :b int?)
  :ret int?)
(stest/instrument `add-nums)
;; (s/exercise-fn `add-nums)

(defun add-nums (t/Int a t/Int b t/Int)
  (+ a b))

(defn woops []
  (add-nums 3 4))

;; (defun add-three (Int n → Int)
;;   (add-two (add-one n)))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
