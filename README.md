# typespec

A Clojure library designed to combine useful functionality of spec and
core typed in a more user friendly / less verbose fashion.

## Usage

### Without typespec syntax

See this verbose sample - maybe that's what drives some people away
from using both spec and core typed together?

```
(t/ann add-nums [t/Int t/Int :-> t/Int])

(defn add-nums [a b]
  (+ a b))

(s/fdef add-nums
  :args (s/cat :a int? :b int?)
  :ret int?))

(stest/instrument `add-nums)
```

What if it could be reduced to a simple:

```
(defun add-nums (Int a Int b → Int)
  (+ a b))
```

To enable all the same features/functionality?


### With typespec syntax

This is early/WIP, do not use it yet (although it is a bit usable).

It is going to enable syntax such as:

```
(defun add-nums (Int a Int b → Int)
  (+ a b))

(defn good []
  (add-nums 3 4))

(defn bad []
  (add-nums "dog" "cat"))

;; When running (lint), will produce a type mismatch error thanks to
;; Core Typed
;; When running at runtime, will produce a Clojure Spec based error
```

I also plan for additional shorthand (an undefined return type will be
assumed to mirror the input data for arity/1 functions), as well as an
additional shorthand for calling the definitional macro and anaphoric
variable binding derived from the types:

```
(ƒ factorial Int
  0 → 1
  i → (* i (factorial (- i 1)))) ; You could obviously use recur here
```

Or maybe print a name so many times:

```
(ƒ repeater Str Int Nil s → (dotimes (x i) (println s)))
```

## License

Copyright © 2018 Matthew Carter <m@ahungry.com>

GPLv3 or later.
