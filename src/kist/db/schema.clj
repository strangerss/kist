(ns kist.db.schema
  (:use korma.core [korma.db :only (defdb)])
  (:require [clojure.java.jdbc :as sql]
            [korma.sql.utils :as utils]
            [noir.io :as io]))

(def db-store "site.db")

;;
;; db情報
;;
(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2"
              :subname (str (io/resource-path) db-store)
              :user "sa"
              :password ""
              :make-pool? true
              :naming {:keys clojure.string/lower-case
                       :fields clojure.string/upper-case}})

(defn initialized?
  "checks to see if the database schema is present"
  []
  (.exists (new java.io.File (str (io/resource-path) db-store ".mv.db"))))

;;
;; スキーマ情報
;;
(defn create-guestbook-table []
  (sql/db-do-commands
   db-spec
   (sql/create-table-ddl
    :guestbook
    [:id "INTEGER PRIMARY KEY AUTO_INCREMENT"]
    [:timestamp :timestamp]
    [:name "varchar(30)"]
    [:message "varchar(200)"]))
  (sql/db-do-prepared db-spec
                      "CREATE INDEX timestamp_index ON guestbook (timestamp)"))

(defn create-users-table
  []
  (sql/db-do-commands
   db-spec
   (sql/create-table-ddl
    :users
    [:id "varchar(20) PRIMARY KEY"]
    [:first_name "varchar(30)"]
    [:last_name "varchar(30)"]
    [:email "varchar(30)"]
    [:admin :boolean]
    [:last_login :time]
    [:is_active :boolean]
    [:pass "varchar(100)"])))

(defn create-charactor_status-table
  []
  (sql/db-do-commands
   db-spec
   (sql/create-table-ddl
    :charactor_status
    [:id "INTEGER PRIMARY KEY"]
    [:lv "INTEGER"]
    [:hp "INTEGER"]
    [:tp "INTEGER"]
    [:atk "INTEGER"]
    [:def "INTEGER"]
    [:int "INTEGER"]
    [:res "INTEGER"]
    [:dex "INTEGER"]
    [:agi "INTEGER"])))

(defn create-charactor_info-table
  []
  (sql/db-do-commands
   db-spec
   (sql/create-table-ddl
    :charactor_info
    [:id "INTEGER PRIMARY KEY"]
    [:name "varchar(30)"]
    [:job "INTEGER"]
    [:exp "INTEGER"])))

(defn create-party-table
  []
  (sql/db-do-commands
   db-spec
   (sql/create-table-ddl
    :party
    [:id "INTEGER"]
    [:charactor_id "INTEGER"]
    [:position "INTEGER"]
    [:order_num "INTEGER"])))

(defn drop-party-table
  []
  (sql/db-do-commands
   db-spec
   (sql/drop-table-ddl :party)))

;;
;; テーブル作成
;;
(defn create-tables
  "creates the database tables used by the application"
  []
  (create-guestbook-table)
  (create-charactor_status-table)
  (create-charactor_info-table)
  (create-party-table)
  (create-users-table))
