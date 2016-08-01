(ns triforce.views
  (:require [re-frame.core :as re-frame]
            [cljsjs.react]
            ; [cljsjs.react-dom :as react-dom]
            ))

(defn counter-control []
  [:div
   [:span {:on-click #(re-frame/dispatch [:inc])} "+"]
   [:span {:on-click #(re-frame/dispatch [:dec])} "-"]
   [:span {:on-click #(re-frame/dispatch [:reset])} "reset"]])

(defn main-panel []
  (let [app-state (re-frame/subscribe [:app-state])]
    (fn []
      [:div
       [:h1 "Counter"]
       [:div (str "Value: " (get @app-state :counter))]
       [counter-control]])))
