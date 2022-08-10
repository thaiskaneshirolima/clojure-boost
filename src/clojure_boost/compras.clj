;; (ns clojure-boost.compras
;;   (:use clojure.pprint)
;;   (:require [schema.core :as s]
;;             [java-time :as jt]))


;; ;; Tarefa
;; ;; Definir o símbolo CompraSchema que estabelece o schema do que é considerada uma compra válida.

;; ;; Regras de aceitação:
;; ;; Data da compra:
;; ;; Se você estiver usando a data como String, então ela deve ter o formato yyyy-mm-dd;
;; ;; Se você estiver usando API do Java Time, então ela deve ser uma data menor ou igual à data atual.

;; ;; Valor:
;; ;; deve ser um BigDecimal positivo.

;; ;; Estabelecimento:
;; ;; Deve ter pelo menos 2 caracteres.

;; ;; Categoria:
;; ;; Deve ser uma das opções: Alimentação, Automóvel, Casa, Educação, Lazer ou Saúde.

;; ;; Cartão:
;; ;; inteiro entre 0 e 1 0000 0000 0000 0000.
;; (s/set-fn-validation! true)

;; (s/defn valid-date? [date]
;;   {:pre [(not= date nil)]}
;;   (let [now (jt/local-date)
;;         date-test (if (instance? String date)
;;                     (jt/local-date "dd/MM/yyyy" date)
;;                     date)]
;;     (jt/not-before? now date-test)))

;; (valid-date? nil)

;; (def ValidDate (s/pred valid-date? 'valid-date))
;; ;; (valid-date? "02/09/2022")
;; ;; (pprint (s/validate ValidDate (jt/plus (jt/local-date) (jt/days 15))))

;; (defn valid-value? [value]
;;   (and (= (bigdec value) value) (pos? value)))

;; (def ValidValue (s/pred valid-value? 'valid-value))
;; ;; (valid-value? -5M)
;; ;; (pprint (s/validate ValidValue 10M))

;; (defn valid-establishment? [establishment]
;;   (>= (count establishment) 2))

;; (def ValidEstablishment (s/constrained s/Str valid-establishment? 'valid-establishment))
;; ;; (pprint (s/validate ValidEstablishment "ls"))
;; ;; (valid-establishment? "teste")

;; (defn valid-category? [category]
;;   (some #(= category %)
;;         ["Alimentação"
;;          "Automóvel"
;;          "Casa"
;;          "Educação"
;;          "Lazer"
;;          "Saúde"]))

;; (def ValidCategory (s/constrained s/Str valid-category? 'valid-category))
;; ;; ;; (valid-category? "Saúde")
;; ;; ;; (pprint(s/validate ValidCategory "teste"))

;; (defn valid-card? [card-numb]
;;   (and (>= card-numb 1) (<= card-numb 10000000000000000)))
;; ;;(= (int card-numb) card-numb)
;; (def ValidCard (s/constrained s/Int valid-card? 'valid-card))
;; ;; ;; (valid-card? 5)
;; ;; (pprint (s/validate ValidCard 0))
;; (valid-card? 12312341234)

;; (def CompraSchema
;;   "Schema de uma compra"
;;   {:data ValidDate
;;    :valor ValidValue
;;    :estabelecimento ValidEstablishment
;;    :categoria ValidCategory
;;    :cartao ValidCard})

;; (pprint (s/validate CompraSchema {:data (jt/local-date)
;;                        :valor 10
;;                        :estabelecimento "11"
;;                        :categoria "Casa"
;;                        :cartao 10000000000000000}))

;; ;; (s/defn nova-compra :- CompraSchema
;; ;;         [data :- ValidDate
;; ;;          valor :- ValidValue
;; ;;          estabelecimento :- ValidEstablishment
;; ;;          categoria :- ValidCategory
;; ;;          cartao :- ValidCard]
;; ;;         {:data data
;; ;;          :valor valor
;; ;;          :estabelecimento estabelecimento
;; ;;          :categoria categoria
;; ;;          :cartao cartao})

;; ;; (pprint(nova-compra "01/08/2022" 10 "Lar" "Casa" 123456))


;; ;; (pprint(s/validate (nova-compra "01/08/2022" 10M "Lar" "Casa" 123456)))