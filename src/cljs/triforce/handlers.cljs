(ns triforce.handlers
    (:require [re-frame.core :refer [register-handler debug trim-v]]
              [triforce.db :as db]
              [triforce.reducers :as reducers]))

(def mw [debug trim-v])

(register-handler :initialize-db mw (constantly db/default-db))
(register-handler :new mw reducers/create-counter)
(register-handler :inc mw reducers/inc-counter)
(register-handler :dec mw reducers/dec-counter)
(register-handler :reset mw reducers/reset-counter)
(register-handler :remove mw reducers/remove-counter)
