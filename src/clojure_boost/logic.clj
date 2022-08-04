(ns clojure-boost.logic
  (:use clojure.pprint)
  (:require [schema.core :as s]
            [java-time :as jt]))


(s/set-fn-validation! true)

(defn is-nil? [data]
  (not= data nil))

(defn valid-date? [date]
  {:pre [(is-nil? date)]}
  (let [now (jt/local-date)
        date-test (if (instance? String date)
                    (jt/local-date "dd/MM/yyyy" date)
                    date)]
    (jt/not-before? now date-test)))

(valid-date? nil)

(defn valid-value? [value]
  {:pre [(is-nil? value)]}
  (and (= (bigdec value) value) (pos? value)))

;; (pprint (s/validate ValidValue 10M))

(defn valid-establishment? [establishment]
  {:pre [(is-nil? establishment)]}
  (>= (count establishment) 2))

(defn valid-category? [category]
  {:pre [(is-nil? category)]}
  (if (some #(= category %)
        ["Alimentação"
         "Automóvel"
         "Casa"
         "Educação"
         "Lazer"
         "Saúde"]) true false))

(defn valid-card? [card-numb]
  {:pre [(is-nil? card-numb)]}
  (and (>= card-numb 0) (<= card-numb 10000000000000000)))

;; (valid-card? 2147483647)
;; (= (int card-numb) card-numb)

(defn total-spent [lista-compras]
  {:pre [(is-nil? lista-compras)]}
  (->> lista-compras
       (map :valor)
       (reduce +)))

(defn grouped-by-category [lista-compras]
  (group-by :categoria lista-compras))


(defn total-spent-grouped-by-category [lista-compras]
  (into {}
        (map (fn [[chave valor]]
               [chave (total-spent valor)])
             (grouped-by-category lista-compras))))

