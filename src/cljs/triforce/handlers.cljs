(ns triforce.handlers
    (:require [re-frame.core :as re-frame]
              [triforce.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn [_ _] db/default-db))

(re-frame/register-handler
  :inc
  (fn [db _] (update db :counter inc)))

(re-frame/register-handler
  :add
  (fn [db [_ v]]
     (update db :counter (partial + v))))

(re-frame/register-handler
  :dec
  (fn [db _] (update db :counter dec)))

(re-frame/register-handler
  :reset
  (fn [db _] (assoc db :counter 0)))
