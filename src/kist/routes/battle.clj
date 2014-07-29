(ns kist.routes.battle
  (:use compojure.core)
  (:require [kist.layout :as layout]
            [kist.util :as util]
            [kist.db.core :as db]))
;;
;; ページ レイアウトバインディング 関数群
;;
(defn battle-page []
  (layout/render "battle.html"
                 {:charactor_info_1 (db/get-charactor_info 1)
                  :charactor_status_1 (db/get-charactor_status 1)
                  :charactor_info_2 (db/get-charactor_info 2)
                  :charactor_status_2 (db/get-charactor_status 2)}))

(defn battle-page-error [error]
  (layout/render "battle.html"
                 {:charactor_info_1 (db/get-charactor_info 1)
                  :charactor_status_1 (db/get-charactor_status 1)
                  :charactor_info_2 (db/get-charactor_info 2)
                  :charactor_status_2 (db/get-charactor_status 2)
                  :error error}))
;;
;; util的な関数
;;
(defn rest-hp [hp damege]
  ;; 残りhpの計算
  (- hp damege))

(defn calc-natack [atk defence]
  ;; 通常攻撃の計算
  (cond
   (< 0 (- atk defence))
   (- atk defence)
   :else 0))

(defn get-charactor_status-data-check [id error-msg]
  ;; charactor_status-dataに存在したらレコード返却､なかったらエラー文言を返却
  (if-let [row (db/get-charactor_status id)]
    row
    error-msg))
;;
;; ページ データバインディング 関数群
;;
(defn battle-natack-db [mid eid]
  ;; 通常攻撃時の処理
  (do
    ;; ダメージからHP更新
    (let [me (get-charactor_status-data-check mid "mid dbに存在しない")
          enemy (get-charactor_status-data-check eid "eid dbに存在しない")]
      (cond

       (string? me)
       (battle-page-error me)

       (string? enemy)
       (battle-page-error enemy)

       :else ;ダメージ計算とdb更新
       (do
         (db/update-hp! eid  (rest-hp (:hp enemy) (calc-natack (:atk me) (:def enemy))))
         ;; システムログ的なあれは一旦は保存なしで
         ;; レイアウト関数への受け渡し
         (battle-page))))))

(defn battle-natack [mid eid]
  ;; リクエストパラメータチェック､問題なければ通常処理へ
  (cond

   (empty? mid)
   (battle-page-error "mid error")

   (empty? eid)
   (battle-page-error "eid error")

   :else
   (battle-natack-db mid eid)))

;;
;; ルート定義関数
;;
(defroutes battle-routes
  (GET  "/battle" [] (battle-page))
  (POST  "/battle" [] (battle-page))
  (POST  "/battlenatk" [mid eid] (battle-natack mid eid)))
