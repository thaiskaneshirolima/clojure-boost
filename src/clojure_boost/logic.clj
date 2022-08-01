(ns clojure-boost.logic
  (:use [clojure pprint])
  (:require [java-time :as jt]))


;; Data da compra:
;; Se você estiver usando a data como String, então ela deve ter o formato yyyy-mm-dd;
;; Se você estiver usando API do Java Time, então ela deve ser uma data menor ou igual à data atual.

(defn valid-date? [date]
  (let [now (jt/local-date)
        date-test (if (instance? String date)
                     (jt/local-date "dd/MM/yyyy" date)
                     date)]
    (if (and (not= date-test nil)
             (jt/not-before? now date-test))
      true 
        (throw (ex-info "Oops! Something went wrong with date!" {:date date-test})))))

;; Valor:
;; deve ser um BigDecimal positivo.

(defn valid-value? [value]
  (if (and 
       (not= value nil)
       (= (bigdec value) value) (>= (bigdec value) 0))
    true
    (throw (ex-info "Oops! Something went wrong with value!" {:valor value}))))

;; Estabelecimento:
;; Deve ter pelo menos 2 caracteres.

(defn valid-establishment? [estabelecimento]
  (if (>= (count estabelecimento) 2) 
    true
      (throw (ex-info "Oops! Something went wrong with establishment!"  {:estabelecimento estabelecimento}))))


;; Categoria:
;; Deve ser uma das opções: Alimentação, Automóvel, Casa, Educação, Lazer ou Saúde.

(defn valid-category? [category]
  (if (some #(= category %)
            ["Alimentação"
             "Automóvel"
             "Casa"
             "Educação"
             "Lazer"
             "Saúde"])
    true
    (throw (ex-info "Oops! Something went wrong with category!" {:categoria category}))))

;;---------------------------------------------------------------------------
;; Tarefa
;; Crie um record para representar uma Compra realizada em um determinado Cartão.

;; Atributos do record devem ser:
;; ID (Long ou nil)
;; Data (String: yyyy-mm-dd ou LocalDate)
;; Valor (BigDecimal)
;; Estabelecimento (String)
;; Categoria (String)
;; Cartão (Long)
;; A função nova-compra ainda faz sentido?

(defrecord CompraRealizada [^Long id, ^String data, ^BigDecimal valor, ^String estabelecimento, ^String categoria, ^Long cartao])

;;Fução com as validações

(defn valida-compra [CompraRealizada]
  (valid-date? (:data CompraRealizada))
  (valid-value? (:valor CompraRealizada))
  (valid-establishment? (:estabelecimento CompraRealizada))
  (valid-category? (:categoria CompraRealizada)))

;;---------------------------------------------------------------------------
;; Tarefa
;; Criar a função insere-compra. 
;; Ela vai atribuir um id a uma compra e armazená-la num vetor.

;; Parâmetros da função:
;; um record de uma compra sem id;
;; um vetor com as compras já cadastradas..
;; Retorno da função:
;; um vetor com a nova compra inserida nele.
;; Critérios de aceitação:
;; O ID da nova compra deve ser o valor máximo de ID da lista de compras mais 1;
;; Se a lista de compras estiver vazia, o ID deve ser o valor 1.

(defn gera-id [lista-compras]
  (let [ultima-compra (last (sort-by :id lista-compras))]
    (if (= ultima-compra nil) 1
        (inc (:id ultima-compra)))))

(defn insere-compra [lista-compras compra]
  (let [ultimo-id (gera-id lista-compras)]
    (conj lista-compras (assoc compra :id ultimo-id))))

;;---------------------------------------------------------------------------
;; Tarefa
;; Crie a função exclui-compra, que exclui uma compra de determinado id de um vetor.

;; Parâmetros da função:
;; id da compra a ser excluída;
;; vetor de compras.
;; Retorno da função:
;; Novo vetor sem a compra excluída.
;; Critérios de aceitação:
;; Caso a compra não exista, retornar o vetor original recebido por parâmetro.

(defn exclui-compra [lista-compras id]
  (remove #(= (:id %) id) lista-compras))
