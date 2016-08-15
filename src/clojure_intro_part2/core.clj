(ns clojure-intro-part2.core
  (:use bakery.core))

(defn error [& rs]
  (apply println rs)
  :error)

(defn add-egg []
  (grab :egg)
  (squeeze)
  (add-to-bowl))

(defn add-sugar []
  (grab :cup)
  (scoop :sugar)
  (add-to-bowl)
  (release))

(defn add-flour []
  (grab :cup)
  (scoop :flour)
  (add-to-bowl)
  (release))

(defn add-milk []
  (grab :cup)
  (scoop :milk)
  (add-to-bowl)
  (release))

(defn add-butter []
  (grab :butter)
  (add-to-bowl))

(def scooped-ingredients #{:milk :sugar :flour})
(defn scooped? [ingredient]
  (contains? scooped-ingredients ingredient))

(def squeezed-ingredients #{:egg})
(defn squeezed? [ingredient]
  (contains? squeezed-ingredients ingredient))

(def simple-ingredients #{:butter})
(defn simple? [ingredient]
  (contains? simple-ingredients ingredient))

(defn add-eggs [n]
  (dotimes [e n]
    (add-egg)))

(defn add-flour-cups [n]
  (dotimes [e n]
    (add-flour)))

(defn add-milk-cups [n]
  (dotimes [e n]
    (add-milk)))

(defn add-sugar-cups [n]
  (dotimes [e n]
    (add-sugar)))

(defn add-butters [n]
  (dotimes [e n]
    (add-butter)))

(defn add-squeezed
  ([ingredient]
     (add-squeezed ingredient 1))
  ([ingredient amount]
     (if (squeezed? ingredient)
       (dotimes [i amount]
         (grab ingredient)
         (squeeze)
         (add-to-bowl))
       (error "This function only works on squeezed ingredients. You asked me to squeeze" ingredient))))

(defn add-scooped
  ([ingredient]
     (add-scooped ingredient 1))
  ([ingredient amount]
     (if (scooped? ingredient)
       (do
         (grab :cup)
         (dotimes [i amount]
          (scoop ingredient)
          (add-to-bowl))
         (release))
       (error "This function only works on scooped ingredients. You asked me to scoop" ingredient))))

(defn add-simple
  ([ingredient]
     (add-simple ingredient 1))
  ([ingredient amount]
     (if (simple? ingredient)
       (dotimes [i amount]
         (grab ingredient)
         (add-to-bowl))
       (error "This function only works on simple ingredients. You asked me to add" ingredient))))

(defn add
  ([ingredient]
     (add ingredient 1))
  ([ingredient amount]
     (cond
      (squeezed? ingredient)
      (add-squeezed ingredient amount)

      (simple? ingredient)
      (add-simple ingredient amount)

      (scooped? ingredient)
      (add-scooped ingredient amount)

      :else
      (error "I do not have the ingredient" ingredient))))

(defn bake-cake []
  (add :egg 2)
  (add :flour 2)
  (add :milk 1)
  (add :sugar 1)

  (mix)

  (pour-into-pan)
  (bake-pan 25)
  (cool-pan))

(defn bake-cookies []
  (add :egg 1)
  (add :flour 1)
  (add :butter 1)
  (add :sugar 1)

  (mix)

  (pour-into-pan)
  (bake-pan 30)
  (cool-pan))

(def pantry-ingredients #{:sugar :flour})
(defn from-pantry? [ingredient]
  (contains? pantry-ingredients ingredient))

(def fridge-ingredients #{:egg :milk :butter})
(defn from-fridge? [ingredient]
  (contains? fridge-ingredients ingredient))

(defn fetch-from-pantry
  ([ingredient]
   (fetch-from-pantry ingredient 1))
  ([ingredient amount]
   (if (from-pantry? ingredient)
     (do
       (go-to :pantry)
       (dotimes [i amount]
         (load-up ingredient))
       (go-to :prep-area)
       (dotimes [i amount]
         (unload ingredient)))
     (error ingredient " not in pantry area"))))

(defn fetch-from-fridge
  ([ingredient]
   (fetch-from-fridge ingredient 1))
  ([ingredient amount]
   (if (from-fridge? ingredient)
     (do
       (go-to :fridge)
       (dotimes [i amount]
         (load-up ingredient))
       (go-to :prep-area)
       (dotimes [i amount]
         (unload ingredient)))
     (error ingredient " not in fridge area"))))

(defn -main
  [& args]
  (bake-cake)
  (bake-cookies)
  (fetch-from-pantry :flour 12)
  (fetch-from-fridge :egg 45))
