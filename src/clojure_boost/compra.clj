(ns clojure-boost.compra
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [clojure-boost.persistencia.datomic :as bd]
            [clojure.string :as str]))

(def conn (bd/cria-conexao!))
(bd/cria-schema conn)
;;(def db-leitura (d/db conn))
(pprint conn)


;; record de compra
;; (defrecord RealizaCompra [^String data, ^BigDecimal valor, ^String estabelecimento, ^String categoria, ^Long cartao])
;; (pprint (->RealizaCompra "27/07/2022" 100.90 "Outback" "Alimentação" 123412341234))

;; lista de compras
(def lista-compras
  [{:compra/data "2022-01-01"
    :compra/valor 129.90M
    :compra/estabelecimento "Otback"
    :compra/categoria "Alimentação"
    :compra/cartao 5656565656565656}
   {:compra/data "2022-02-01"
    :compra/valor 20.00M
    :compra/estabelecimento "Cinema"
    :compra/categoria "Lazer"
    :compra/cartao 5656565656565656}
   {:compra/data "2022-01-02"
    :compra/valor 260.00M
    :compra/estabelecimento "Dentista"
    :compra/categoria "Saúde"
    :compra/cartao 1234123412341234}])

;;lista-compras

(defn nova-compra [data valor estabelecimento categoria cartao]
  {:compra/data data
   :compra/valor valor
   :compra/estabelecimento estabelecimento
   :compra/categoria categoria
   :compra/cartao cartao})

(defn insere-compra! [lista-compras nova-compra]
  (let [compras (conj lista-compras nova-compra)]
    (d/transact conn compras)))


(insere-compra! lista-compras (nova-compra "27/07/2022" 100.90M "Outback" "Alimentação" 1234123412341234))
;; #datom [id-da-entidade atributo valor id-da-tx added?]

;; (let [compra(novo-produto "27/07/2022" 100.90M "Outback" "Alimentação" 123412341234)]
;;   (d/transact conn [compra]))

(bd/lista-compras! (d/db conn))

(bd/lista-gastos-por-categoria! (d/db conn))

(bd/lista-compras-por-cartao! (d/db conn) 1234123412341234)

;;(bd/exclui-conexao!)
