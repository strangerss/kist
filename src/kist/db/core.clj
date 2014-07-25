(ns kist.db.core
	(:use korma.core
				[korma.db :only (defdb)])
	(:require [kist.db.schema :as schema]))

(defdb db schema/db-spec)

(defentity users)
(defentity guestbook)
(defentity charactor)

;;
;; クエリ定義
;;

;;
;; userテーブル
;;
(defn create-user [user]
	(insert users
					(values user)))

(defn update-user [id first-name last-name email]
	(update users
					(set-fields {:first_name first-name
											 :last_name last-name
											 :email email})
					(where {:id id})))

(defn get-user [id]
	(first (select users
								 (where {:id id})
								 (limit 1))))

;;
;; guestbookテーブル
;;
(defn save-message
	[name message]
	(insert guestbook
					(values {:name name
									 :message message
									 :timestamp (new java.util.Date)})))

(defn get-messages []
	(select guestbook))

(defn get-cnt-messages []
	(map :cnt (select guestbook	(aggregate (count :*) :cnt))))

(defn get-messages-10 []
	(select guestbook (order :id :desc) (limit 10)))

(defn update-message! [id message]
	(update guestbook
					(set-fields {:message message})
					(where {:id id})))

(defn delete-message! [id]
	(delete guestbook (where (= :id id))))

(defn get-messages-by-id [id]
	(:id (first (select guestbook (where (= :id id))))))

;;
;; charactor
;;

