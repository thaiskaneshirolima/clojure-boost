(ns clojure-boost.model
  (:use clojure.pprint)
  (:require [clojure-boost.logic :as cb.logic]
            [schema.core :as s]
            [java-time :as jt]))

;; (def lista-compras [])
(def lista-compras
  [{:data "2022-01-01"
    :valor 100.00
    :estabelecimento "Otback"
    :categoria "Alimentação"
    :cartao "1234 1234 1234 1234"}
   {:data "2022-02-01"
    :valor 200.00
    :estabelecimento "Cinema"
    :categoria "Lazer"
    :cartao "1234 1234 1234 1234"}
   {:data "2022-01-02"
    :valor 300.00
    :estabelecimento "Dentista"
    :categoria "Saúde"
    :cartao "1234 1234 1234 1234"}])

(def ValidDate (s/pred cb.logic/valid-date? 'valid-date))
(def ValidValue (s/pred cb.logic/valid-value? 'valid-value))
(def ValidEstablishment (s/constrained s/Str cb.logic/valid-establishment? 'valid-establishment))
(def ValidCategory (s/constrained s/Str cb.logic/valid-category? 'valid-category))
(def ValidCard (s/pred cb.logic/valid-card? 'valid-card))

(def CompraSchema
  "Schema de uma compra" 
  {(s/optional-key :id) (s/maybe Long)
   :data ValidDate
   :valor ValidValue
   :estabelecimento ValidEstablishment
   :categoria ValidCategory
   :cartao ValidCard})

(pprint (s/validate CompraSchema {:data (jt/local-date)
                                  :valor 10M
                                  :estabelecimento "12"
                                  :categoria "Casa"
                                  :cartao 111111}))

(s/defn nova-compra :- CompraSchema
        [data :- ValidDate
         valor :- ValidValue
         estabelecimento :- ValidEstablishment
         categoria :- ValidCategory
         cartao :- ValidCard]
        {:data data
         :valor valor
         :estabelecimento estabelecimento
         :categoria categoria
         :cartao cartao})

(pprint(nova-compra "01/08/2022" 10M "Lar" "Casa" 123456))


(defn insere-compra [lista-compras CompraSchema]
 (conj lista-compras CompraSchema))

(insere-compra lista-compras (s/validate CompraSchema {:data (jt/local-date)
                                                       :valor 10M
                                                       :estabelecimento "Riachuello"
                                                       :categoria "Casa"
                                                       :cartao 10000000000000000}))
