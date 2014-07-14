(ns kist.db.core
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [kist.db.schema :as schema]))

(defdb db schema/db-spec)

(defentity users)
(defentity guestbook)

(defn create-user [user]
  (insert users
          (values user)))

(defn save-message
  [name message]
  (insert guestbook
          (values {:name name
                   :message message
                   :timestamp (new java.util.Date)})))

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

(defn get-messages []
  (select guestbook))

(defn get-cnt-messages []
	(map :cnt (select guestbook	(aggregate (count :*) :cnt))))






