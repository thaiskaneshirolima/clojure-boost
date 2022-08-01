(ns clojure-boost.semana2
  (:use [clojure pprint])
  (:require [java-time :as jt]))

(defn testa-vetor []
  (let [espera [111 222]]
    (println (conj espera 333))
    (println (pop espera))))

(testa-vetor)

(defn testa-fila []
  (let [espera (conj clojure.lang.PersistentQueue/EMPTY "111" "222")]
    (println (seq (conj espera 333)))
    (println (seq (pop espera)))
    (pprint espera)))

(testa-fila)

;;---------------------------------------------------------------------------
;; Tarefa
;; Definir um átomo no símbolo repositorio-de-compras 
;; onde serão salvos os maps de compras. 
;; O átomo deve ser inicializado com um vetor vazio [].

(def repositorio-de-compras (atom []))
repositorio-de-compras

(defn atomo-teste []
  (let [repositorio-de-compras (atom {})]
    (pprint repositorio-de-compras)))
(pprint atomo-teste)


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

;; (defrecord Compra-realizada [ˆLong id, ˆString data, ˆBigDecimal valor, ˆString estabelecimento, ˆString categoria, ˆLong cartao])

;; (defrecord Paciente [^String id, ^String nome, ^String nascimento])
;; (println (Paciente. 15555 222 555))
;; (pprint (->Paciente 15 "Guilherme" "18/9/1981"))
;; (pprint (Paciente. 15 "Guilherme" "18/9/1981"))

(defrecord CompraRealizada [^Long id, ^String data, ^BigDecimal valor, ^String estabelecimento, ^String categoria, ^Long cartao])

(pprint (CompraRealizada. "1" "27/07/2022" 100.90 "Outback" "Alimentação" 123412341234))
(pprint (CompraRealizada. "teste-id" "27/07/2022" 100.90 "Outback" "Alimentação" 123412341234))


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
(def lista-compras
  [{:id 111
    :data  "2022-01-01"
    :valor 129.90
    :estabelecimento "Otback"
    :categoria "Alimentação"
    :cartao "1234 1234 1234 1234"}
   {:id 333
    :data  "2022-02-01"
    :valor 20.00
    :estabelecimento "Cinema"
    :categoria "Lazer"
    :cartao "1234 1234 1234 1234"}
   {:id 222
    :data  "2022-01-02"
    :valor 260.00
    :estabelecimento "Dentista"
    :categoria "Saúde"
    :cartao "1234 1234 1234 1234"}])

;; (def lista-compras
;;   [])

(defn gera-id [lista-compras]
  (let [ultima-compra (last (sort-by :id lista-compras))] 
    (if (= ultima-compra nil) 1
      (inc (:id ultima-compra))))) 

(pprint (gera-id lista-compras))

(def compra (CompraRealizada. nil "27/07/2022" 100.90 "Outback" "Alimentação" 123412341234))


compra

;;compra

(defn insere-compra [lista-compras compra]
  (let [ultimo-id (gera-id lista-compras)]
    (conj lista-compras (assoc compra :id ultimo-id))))

(insere-compra lista-compras compra)

;; (peek [1 2 3])
;; (sort-by :age [{:age 99}, {:age 13}, {:age 7}])
;; (conj [12345] 2)

;; (sort-by :id lista-compras)

;;---------------------------------------------------------------------------
;; Tarefa
;; Criar a função insere-compra! para incluir uma nova compra no átomo de compras usando swap!. 

;; Parâmetros da função:
;; um record de uma compra;
;; átomo repositorio-de-compras criado anteriormente .
;; Retorno da função:
;; "sem retorno"
;; Critérios de aceitação:
;; A função deve substituir o valor interno do átomo por meio de um swap!;
;; O valor do átomo deve ser atualizado com a função insere-compracriada anteriormente.

;; uma funcao invocada junto a um atom recebe o atom como primeiro parametro.
(defn insere-compra! [compraRealizada repositorio-de-compras]
  (swap! repositorio-de-compras insere-compra compraRealizada))

;; (defn insere-compra! [compraRealizada repositorio-de-compras]
;;   (swap! compraRealizada insere-compra repositorio-de-compras))

(insere-compra! (CompraRealizada. nil "27/07/2022" 100.90 "Outback" "Alimentação" 123412341234) repositorio-de-compras)

(insere-compra (deref repositorio-de-compras) compra)

(defn teste-inserir-compra-atomo []
  (let [listas (atom {})]
    (swap! listas assoc CompraRealizada compra)
    (deref listas)))
(teste-inserir-compra-atomo)

;;---------------------------------------------------------------------------
;; Tarefa
;; Crie a função lista-compras!, que lista as compras de um átomo.

;; Parâmetros da função:
;; átomo repositorio-de-compras criado anteriormente.
;; Retorno da função:
;; "sem retorno".
;; Critérios de aceitação:
;; A função deve executar um printlnno derefem cima do átomo;

(defn lista-compras! [repositorio-de-compras]
  (println (deref repositorio-de-compras)))

(pprint (lista-compras! repositorio-de-compras))

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

(exclui-compra lista-compras 111)

lista-compras

;;---------------------------------------------------------------------------
;; Tarefa
;; Criar a função exclui-compra! para uma compra de um átomo por meio de swap!.

;; Parâmetros da função:
;; id da compra a ser excluída;
;; átomo repositorio-de-compras criado anteriormente .
;; Retorno da função:
;; "sem retorno"
;; Critérios de aceitação:
;; A função deve substituir o valor interno do átomo por meio de um swap!;
;; O valor do átomo deve ser atualizado com a função exclui-compracriada anteriormente.

(defn exclui-compra! [repositorio-de-compras id]
  (swap! repositorio-de-compras exclui-compra id))


(exclui-compra! repositorio-de-compras 99)



;;---------------------------------------------------------------------------
;; Tarefa
;; Criar a função valida-compra para validar uma compra. Depois, altere a função insere-compra! 
;; definida anteriormente para validar a compra antes de salvá-la no átomo.

;; Parâmetros da função:
;; um record da nova compra.
;; Regras de aceitação:

;; Data da compra:
;; Se você estiver usando a data como String, então ela deve ter o formato yyyy-mm-dd;
;; Se você estiver usando API do Java Time, então ela deve ser uma data menor ou igual à data atual.

(defn valid-date? [date]
  (let [now (jt/local-date)
        data-teste (jt/local-date "dd/MM/yyyy" date)]
   (if (jt/not-before? now data-teste) 
     true
       (throw (ex-info "Oops! Something went wrong with date!" {})))))

(valid-date? "20/07/2022")


;; (jt/local-date)
;; ;=> #object[java.time.LocalDate "2015-09-27"]
;; (jt/local-date "dd/MM/yyyy" "27/07/2022")
;=> #object[java.time.LocalDate "2015-09-28"]

;; Valor:
;; deve ser um BigDecimal positivo.

;; (def valor (bigdec 600))
;; (= (bigdec valor) valor)

;; (def valor (bigdec -90))
;; (and (= (bigdec valor) valor) (>= (bigdec valor) 0))

(defn valid-value? [value]
  (if (and (= (bigdec value) value) (>= (bigdec value) 0)) 
    true
      (throw (ex-info "Oops! Something went wrong with value!" {}))))

(valid-value? 100M)
;; Estabelecimento:
;; Deve ter pelo menos 2 caracteres.

(defn valid-establishment? [estabelecimento]
  (if (>= (count estabelecimento) 2) true
      (throw (ex-info "Oops! Something went wrong with establishment!" {}))))

(valid-establishment? "tss")

;; (count "teste")
;; (>= (count "te") 2)
;; Categoria:
;; Deve ser uma das opções: Alimentação, Automóvel, Casa, Educação, Lazer ou Saúde.

;;(some #(= "ww" %) ["ww" "aa" "tt" "qq"])

(defn valid-category? [category]
  (if (some #(= category %)
            ["Alimentação"
             "Automóvel"
             "Casa"
             "Educação"
             "Lazer"
             "Saúde"])
    true
    (throw (ex-info "Oops! Something went wrong with category!" {}))))

(valid-category? "Alimentação")

;; (defrecord Paciente [^String nascimento, ^String valor, ^String categoria])
;; (println (Paciente. 15555 222 555))

;; (let [paciente (Paciente. "27/07/2022" 100M "Lazer")]
;;   (pprint (valid-date? (:nascimento paciente)))
;;   (pprint (valid-value? (:valor paciente)))
;;   (pprint (valid-category? (:categoria paciente))))

(defn valida-compra [CompraRealizada]
  (valid-date? (:data CompraRealizada))
  (valid-value? (:valor CompraRealizada))
  (valid-establishment?(:estabelecimento CompraRealizada))
  (valid-category? (:categoria CompraRealizada)))


;;false
(valida-compra (CompraRealizada. 1 "27/07/2022" 100.90 "Outback" "Alimentação" 123412341234))
;;true
(valida-compra (CompraRealizada. 1 "27/07/2022" 100.90M "Outback" "Alimentação" 123412341234))


;; uma funcao invocada junto a um atom recebe o atom como primeiro parametro.
(def compra2 (CompraRealizada. 1 "27/07/2022" 100.90M "Outback" "teste" 123412341234))

compra2
(pprint (lista-compras! repositorio-de-compras))

(defn insere-compra2! [compraRealizada repositorio-de-compras] 
  (if (= (valida-compra compraRealizada) true)
    (swap! repositorio-de-compras insere-compra compraRealizada)
    (throw (ex-info "Oops! Something went wrong!!" {}))))

(insere-compra2! compra2 repositorio-de-compras)

(pprint (lista-compras! repositorio-de-compras))

