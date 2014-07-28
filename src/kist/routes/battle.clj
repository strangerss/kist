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
(defn calc-natack [atk defence]
  (cond
   (< 0 (- atk defence))
   (- atk defence)
   :else 0))

;;
;; ページ データバインディング 関数群
;;
(defn battle-natack [mid eid]
  (do
    ;; 引数チェック
    (cond
     (empty? mid) (battle-page-error "mid error")
     (empty? eid) (battle-page-error "eid error"))



    ;; ダメージからHP更新
    (let [me    (db/get-charactor_status mid)
          enemy (db/get-charactor_status eid)
          damege (calc-natack (:atk me) (:def enemy))]
      (db/update-hp! eid  (- (:hp enemy) damege)) ; dbにデータ有
      (battle-page-error "どちらかのidが存在しません")) ; dbにデータ無


    ;; システムログ的なあれは一旦は保存なしで
    ;; レイアウト関数への受け渡し
    (battle-page)))

;;
;; ルート定義関数
;;
(defroutes battle-routes
  (GET  "/battle" [] (battle-page))
  (POST  "/battle" [] (battle-page))
  (POST  "/battlenatk" [mid eid] (battle-natack mid eid)))
