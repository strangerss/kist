(ns kist.routes.home
	(:use compojure.core)
	(:require [kist.layout :as layout]
						[kist.util :as util]
						[kist.db.core :as db]))

;;
;; util的な関数
;;
(defn elucidate-msg? []
	(> (first (db/get-cnt-messages)) 10))

;;
;; ページ レイアウトバインディング 関数群
;;
(defn home-page [& [name message error]]
	(layout/render "home.html"
								 {:error    error
									:name     name
									:message  message
									:messages (db/get-messages-10)
									:elucidate-msg (elucidate-msg?)}))

;;
;; ページ データバインディング 関数群
;;
(defn save-message [name message]
	(cond

	 (empty? name)
	 (home-page name message "Somebody forgot to leave a name")

	 (empty? message)
	 (home-page name message "Don't you have something to say?")

	 :else
	 (do
		 (db/save-message name message)
		 (home-page))))

;;
;; ルート定義関数
;;
(defroutes home-routes
	(GET  "/" [] (home-page))
	(POST "/" [name message] (save-message name message)))
