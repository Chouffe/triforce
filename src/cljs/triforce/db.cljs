(ns triforce.db)

(defn counter [id]
  {:id id :value 0})

(def default-db
  {:counters [(counter 0)] :uid 1})
