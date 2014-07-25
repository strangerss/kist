(ns kist.routes.battle
  (:use compojure.core)
  (:require [kist.layout :as layout]
            [kist.util :as util]
            [kist.db.core :as db]))

(defn battle-page []
  (layout/render "battle.html"
                 {:charactor_info_1 (db/get-charactor_info-by-id 1)
                  :charactor_status_1 (db/get-charactor_status-by-id 1)
                  :charactor_info_2 (db/get-charactor_info-by-id 2)
                  :charactor_status_2 (db/get-charactor_status-by-id 2)}))

;;
;; ルート定義関数
;;
(defroutes battle-routes
  (GET  "/battle" [] (battle-page))
  (POST  "/battle" [] (battle-page)))
