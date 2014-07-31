(ns kist.db.battle
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [kist.db.schema :as schema]))

(defdb db schema/db-spec)

(defentity charactor_status)
(defentity charactor_info)
(defentity party)

;;
;; charactor_status
;;
(defn insert-charactor_status!
  [id hp tp atk defence integer res dex agi]
  (let [lv 1]
    (insert charactor_status
            (values {:id id
                     :lv lv
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

;;(get-charactor_status 2)
;;(update-hp! 2 200)

(select charactor_status)

;;(insert-charactor_status! 1 200 100 10 30 10 10 10 10)
;;(insert-charactor_status! 2 200 100 10 10 10 10 10 10)
;;(insert-charactor_status! 3 200 100 10 10 10 10 10 10)

;;(update-charactor_status! 1 2 200 200 30 20 20 20 20 20)


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
;;(get-charactor_info 2)
;;(insert-charactor_info! 1 "こっちな" 2 10)
;;(insert-charactor_info! 2 "敵的な" 2 10)
;;(insert-charactor_info! 3 "敵的な2" 2 10)


;;
;; party
;;
(defn insert-party!
  [id charactor_id position order_num]
  (insert party
          (values {:id id
                   :charactor_id charactor_id
                   :position position
                   :order_num order_num
                   })))

(defn get-party [id]
  (select party (where (= :id id))
          (order :order_num :ASC)))

(defn delete-party! [id]
  (delete party (where (= :id id))))

;; (insert-party! 1 1 1 1)
;; (insert-party! 2 2 1 1)
;; (insert-party! 2 3 1 1)

;

(defn gp [party-id]
  (for
    [cid (map :charactor_id (get-party party-id))
     csrow [(get-charactor_status cid)]]
    csrow))

;; (first (gp 2))
;; (first (rest (gp 2)))


(conj [1 2 3] [4 5 6])

(defn get-party-users [party-id]
   (for [party (get-party party-id)
         info  (vector (:name (get-charactor_info (:charactor_id party))))
         status (vector (get-charactor_status (:charactor_id party)))]
      (vector info status)))

;; (first (get-party-users 2))
;; (rest (get-party-users 2))
