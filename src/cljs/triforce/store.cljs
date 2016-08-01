(ns triforce.store
  (:require [clojure.string :refer [lower-case]]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [re-frame.db :as rdb]))

(defn action-string->kw
  [action-string]
  (keyword (lower-case action-string)))

(defn ^:export dispatch [action-string & args]
  (-> action-string
      action-string->kw
      vector
      (#(apply conj % args))
      re-frame/dispatch))

(defn ^:export provider [root-component]
  (reagent/reactify-component
    (reagent/create-class
      {:component-did-mount
       (fn [this]
         ;; Monitoring
         ;; TODO: kill or only in debug mode
         (add-watch rdb/app-db :store-update
                    (fn [k r o n]
                      (.log js/console "Store Update")
                      (.log js/console o)
                      (.log js/console n)
                      (.log js/console this))))

       ;; Monitoring -> Potential Memory Leak
       :component-will-unmount
       #(remove-watch rdb/app-db :store-update)

       :render
       #(->> @rdb/app-db
             ;; Might be expensive to do clj->js on each app-change
             ;; What about Mori/Immutable?
             ;; clj->immutablejs
             clj->js
             (reagent/create-element root-component))})))
