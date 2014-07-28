(ns kist.db.core
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [kist.db.schema :as schema]))

(defdb db schema/db-spec)

(defentity users)
(defentity guestbook)
(defentity charactor_status)
(defentity charactor_info)

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
;; charactor_status
;;
(defn insert-charactor_status!
  [hp tp atk defence integer res dex agi]
  (let [lv 1]
    (insert charactor_status
            (values {:lv lv
                     :hp hp
                     :tp tp
                     :atk atk
                     :def defence
                     :int integer
                     :res res
                     :dex dex
                     :agi agi}))))

(defn update-charactor_status!
  [id lv hp tp atk defence integer res dex agi]
  (update charactor_status
          (set-fields {:lv lv
                       :hp hp
                       :tp tp
                       :atk atk
                       :def defence
                       :int integer
                       :res res
                       :dex dex
                       :agi agi})
          (where {:id id})))



(defn get-charactor_status [id]
  (first (select charactor_status (where (= :id id)))))

(defn update-hp! [id hp]
  (update  charactor_status
           (set-fields {:hp hp})
           (where {:id id})))

;;(get-charactor_status 2)

(get-charactor_status 2)
(update-hp! 2 195)

;; (select charactor_status)
;; (insert-charactor_status! 200 100 10 10 10 10 10 10)
;;(update-charactor_status! 2 2 200 200 20 20 20 20 20 20)


;;
;; charactor_info
;;
(defn insert-charactor_info!
  [id name job exp]
    (insert charactor_info
            (values {:id id
                     :name name
                     :job job
                     :exp exp})))

(defn get-charactor_info [id]
  (first (select charactor_info (where (= :id id)))))

;;(select charactor_info)
;;(get-charactor_info 1)
;;(insert-charactor_info! 2 "敵的な" 2 10)
