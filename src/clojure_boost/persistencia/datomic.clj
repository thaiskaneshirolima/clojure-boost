(ns clojure-boost.persistencia.datomic
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

(def db-uri "datomic:mem://clojure-boost")
(d/create-database db-uri)
;; If you're using local dev storage, use the db uri you created earlier.
;; replace <DB-NAME> with your database name. It will look similar to:
;; (def db-uri "datomic:dev://localhost:4334/hello")

;;conectar com o banco
;;(pprint (d/connect db-uri))
;;(def conn (d/connect db-uri))
 (defn cria-conexao! []
   (d/create-database db-uri)
   (d/connect db-uri))
;;apagar o banco
;;(pprint (d/delete-database db-uri))

(defn exclui-conexao! []
  (d/delete-database db-uri))

(def schema [{:db/ident :compra/data
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc "Data da compra"}
             {:db/ident :compra/valor
              :db/valueType :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc "Valor da compra"}
             {:db/ident :compra/estabelecimento
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc "Estabelecimento onde a compra foi realizada"}
             {:db/ident :compra/categoria
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc "Categoria da compra realizada"}
             {:db/ident :compra/cartao
              :db/valueType :db.type/long
              :db/cardinality :db.cardinality/one
              :db/doc "Numero do cartao usado para compra"}])

(defn cria-schema [conn]
  (d/transact conn schema))

;; (defn lista-compras! [db]
;;   (d/q '[:find (pull ?entidade [:compra/cartao :compra/data :compra/valor :compra/estabelecimento :compra/categoria])
;;          :where [?entidade :compra/cartao ?card]] db))

(defn lista-compras! [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :compra/cartao ?card]] db))

;; (defn lista-compras! [db]
;;   (d/q '[:find (max ?categoria)  (sum ?valor)
;;          :keys categoria valor
;;          :where [?entidade :compra/categoria ?categoria]
;;         [?entidade :compra/valor ?valor] db]))

(defn lista-gastos-por-categoria! [db]
    (d/q '[:find ?categoria  ?entidade ?preco
           :keys categoria minimo 
           :with ?entidade
           :where [?entidade :compra/valor ?preco]
                  [?entidade :compra/categoria ?categoria]]
         db))


(defn lista-compras-por-cartao! [db card]
  (d/q '[:find ?entidade, ?card, ?data, ?valor, ?estabelecimento, ?categoria
         :in $ ?card 
         :where [?entidade :compra/cartao ?card]
         [?entidade :compra/data ?data]
         [?entidade :compra/valor ?valor]
         [?entidade :compra/estabelecimento ?estabelecimento]
         [?entidade :compra/categoria ?categoria]] db card))

;;(defn lista-compras-por-cartao-mes! [db card month])
