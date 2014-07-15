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
									:elucidate-msg (elucidate-msg?)
									}))

(defn manage [& [id]]
	(layout/render "manage.html"
								 {:id id
									:messages (db/get-messages)
									}))

(defn manage [& [id error]]
	(layout/render "manage.html"
								 {:id id
									:error error
									:messages (db/get-messages)
									}))

(defn about-page []
	(layout/render "about.html"))

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

(defn delete-message [id]
	;; selectして存在チェック
	;; なかったらidとerror_message返して自画面遷移
	;; あったら該当idのguestbook削除して自画面遷移
	(cond
	 (empty? id)
	 (manage id "id error")

	 :else
	 (do
		 (db/delete-message  id)
		 (manage))))

;;
;; ルート定義関数
;;
(defroutes home-routes
	(GET "/" [] (home-page))
	(POST "/" [name message] (save-message name message))
	(GET "/about" [] (about-page))
	(GET "/manage" [] (manage))
	(POST "/manage" [id] (delete-message id)))
