(ns triforce.core
  (:require [cljsjs.react]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [devtools.core :as devtools]
            [triforce.handlers]
            [triforce.store]
            [triforce.config :as config]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")
    (devtools/install!)))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup))
