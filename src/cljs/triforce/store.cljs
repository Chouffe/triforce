(ns triforce.store
  (:require [clojure.string :refer [lower-case]]
            [reagent.core :refer [create-class create-element reactify-component]]
            [re-frame.core :as re-frame]
            [re-frame.db :refer [app-db]]))

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
  (reactify-component
    (create-class
      {:component-did-mount
       (fn [this]
         ;; Monitoring
         ;; TODO: kill or only in debug mode
         (add-watch app-db :store-update
                    (fn [k r o n]
                      (.log js/console "Store Update")
                      (.log js/console o)
                      (.log js/console n)
                      (.log js/console this))))

       ;; Monitoring -> Potential Memory Leak
       :component-will-unmount
       #(remove-watch app-db :store-update)

       :render
       #(->> @app-db
             ;; Might be expensive to do clj->js on each app-change
             ;; What about Mori/Immutable?
             ;; What about a function clj->immutablejs
             ;; its a bit silly since cljs data structures are faster and moree heavily tested...
             clj->js
             (create-element root-component))})))
