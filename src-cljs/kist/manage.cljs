(ns kist.manage
	(:use [domina :only [by-id value set-value!]]))

(defn ^:export del[]
	(let [form (by-id "managefrom")]
		(set! (.-action form) "/managedel")))

(defn ^:export edit[]
	(let [form (by-id "managefrom")]
		(set! (.-action form) "/manageedit")))
