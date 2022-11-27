(fn [x] (loop [xs (seq x)
               result []]
          (if (not (empty? xs))
            (let [x (last xs)]
              (recur (drop-last xs) (conj result x)))
            result)))