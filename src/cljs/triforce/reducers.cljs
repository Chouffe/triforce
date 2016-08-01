(ns triforce.reducers
  (:require [triforce.db :as db]))

(defn ^:private counter-transformer [counter-id f]
  (fn [counter]
    (if-not (= (:id counter) counter-id)
      counter
      (f counter))))

(defn ^:private f-counter [db counter-id f]
  (->> db
       :counters
       (mapv (counter-transformer counter-id f))
       (assoc db :counters)))

(defn inc-counter [db [counter-id]]
  (f-counter db counter-id #(update % :value inc)))

(defn dec-counter [db [counter-id]]
  (f-counter db counter-id #(update % :value dec)))

(defn reset-counter [db [counter-id]]
  (f-counter db counter-id #(assoc % :value 0)))

(defn create-counter [db _]
  (-> db
      (update :counters conj (db/counter (:uid db)))
      (update :uid inc)))

(defn remove-counter [db [counter-id]]
  (update db :counters
          (fn [counters] (vec (remove #(= counter-id (:id %)) counters)))))
