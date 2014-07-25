(ns kist.routes.battle
	(:use compojure.core)
	(:require [kist.layout :as layout]
						[kist.util :as util]
						[kist.db.core :as db]))

(defn battle-page []
	(layout/render "battle.html"))

;;
;; ルート定義関数
;;
(defroutes battle-routes
	(GET  "/battle" [] (battle-page))
	(POST  "/battle" [] (battle-page)))
