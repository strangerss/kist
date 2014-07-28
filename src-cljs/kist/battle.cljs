(ns kist.battle
	(:use [domina :only [by-id]]))

(defn ^:export natk[]
	(let [form (by-id "battlefrom")]
		(set! (.-action form) "/battlenatk")))
