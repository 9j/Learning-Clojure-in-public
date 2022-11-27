#(map
  (fn fibo [x]
    (if (>= x 2)
      (do (+ (fibo (- x 1)) (fibo (- x 2))))
      x)) (range 1 (inc %)))