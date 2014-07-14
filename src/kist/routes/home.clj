(ns kist.routes.home
	(:use compojure.core)
	(:require [kist.layout :as layout]
						[kist.util :as util]
						[kist.db.core :as db]))

(defn paging? []
	(> (first (db/get-cnt-messages)) 10))

(defn paging-list []
	(range (quot (first (db/get-cnt-messages)) 10)))


(defn home-page [& [name message error]]
	(layout/render "home.html"
								 {:error    error
									:name     name
									:message  message
									:messages (db/get-messages)
									:paging-list (paging-list)
									:paging (paging?)
									}))

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


(defn about-page []
	(layout/render "about.html"))

(defroutes home-routes
	(GET "/" [] (home-page))
	(POST "/" [name message] (save-message name message))
	(GET "/about" [] (about-page)))








