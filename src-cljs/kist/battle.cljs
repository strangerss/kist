(ns kist.battle
	(:use [domina :only [by-id]]))

(defn ^:export natk[id]
  (let [form (by-id "battlefrom")
        eid (by-id "eid")]
    (do
      (set! (.-value eid) id)
      (set! (.-action form) "/battlenatk"))))
