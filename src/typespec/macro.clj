;; (require 'typespec.core :reload)
(ns typespec.macro
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

(defn typespec-symbol-to-spec [symbol]
  (cond
    (= "Int" (name symbol)) 'int?
    (= "Str" (name symbol)) 'string?
    :else symbol))

(def tsts typespec-symbol-to-spec)

(defn typespec-symbol-to-typed [symbol]
  (cond
    (= "Int" (name symbol)) 't/Int
    (= "Str" (name symbol)) 't/Str
    :else 't/Nothing))

(def tstt typespec-symbol-to-typed)

(defmacro defun
  "Usage:  (defun doubler [Int x Int] (+ x x))."
  [name sig & args]
  `(do
     (defn ~name [~@(take-nth 2 (rest sig))]
       ~@args)
     (t/tc-ignore
      (s/fdef ~name
        :args (s/cat ~@(flatten
                        (into []
                              (zipmap
                               (map keyword (take-nth 2 (rest sig)))
                               (map tsts (take-nth 2 (butlast sig)))))))
        :ret ~(tsts (last sig)))
      (stest/instrument))
     (t/ann ~name [~@(map tstt (take-nth 2 (butlast sig))) :-> ~(tstt (last sig))])
     ))

(defmacro ƒ [name sig & args]
  `(defun ~name ~sig ~@args))

(s/fdef add-nums
  :args (s/cat :a int? :b int?)
  :ret int?)
(stest/instrument `add-nums)
;; (s/exercise-fn `add-nums)

(defun add-nums (Int a Int b Int)
  (+ a b))

(defn woops []
  (add-nums 3 4))

;; (defun add-three (Int n → Int)
;;   (add-two (add-one n)))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn lint []
  ;; (require *ns* :reload)
  (require 'typespec.core :reload)
  )
