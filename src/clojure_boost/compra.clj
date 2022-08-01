(ns clojure-boost.compra
  (:use [clojure pprint])
  (:require [clojure-boost.logic :as cb.logic]))

;;---------------------------------------------------------------------------
;; Tarefa
;; Definir um átomo no símbolo repositorio-de-compras 
;; onde serão salvos os maps de compras. 
;; O átomo deve ser inicializado com um vetor vazio [].

(def repositorio-de-compras (atom []))

(println "Exercicio semana2: Definir um átomo no símbolo repositorio-de-compras."
        repositorio-de-compras)

;;---------------------------------------------------------------------------
;; Tarefa
;; Crie um record para representar uma Compra realizada em um determinado Cartão.
(println "Exercicio semana2: Crie um record para representar uma Compra realizada em um determinado Cartão."
         (CompraRealizada. "1" "27/07/2022" 100.90 "Outback" "Alimentação" 123412341234))

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
  (swap! repositorio-de-compras cb.logic/insere-compra compraRealizada))

;;false
;; (println "Exercicio semana2: com validação dos valores de compra invalidos"
        ;;  (cb.logic/valida-compra (CompraRealizada. 1 "27/07/2022" 100.90 "Outback" "Alimentação" 123412341234)))
;;true
(println "Exercicio semana2: com validação dos valores de compra validos"
(cb.logic/valida-compra (CompraRealizada. 1 "27/07/2022" 100.90M "Outback" "Alimentação" 123412341234)))


;;Com as validações dos valores recebidos no record.

(defn insere-compra2! [compraRealizada repositorio-de-compras]
  (if (= (cb.logic/valida-compra compraRealizada) true)
    (swap! repositorio-de-compras cb.logic/insere-compra compraRealizada)
    (throw (ex-info "Oops! Something went wrong!!" {}))))

(println "Exercicio semana2: Criar a função insere-compra! (atomo)"
        (insere-compra!
         (cb.logic/->CompraRealizada nil "27/07/2022" 100.90 "Outback" "Alimentação" 123412341234)
         repositorio-de-compras))

(println "Exercicio semana2: Criar a função insere-compra! com validação dos valores de compra."
         (insere-compra2!
          (cb.logic/->CompraRealizada 10 "27/08/2022" 100.90M "Outback" "Alimentação" 123412341234)
          repositorio-de-compras))

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

(println "Exercicio semana2: Crie a função lista-compras! (atomo)"
         (lista-compras! repositorio-de-compras))

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
  (swap! repositorio-de-compras cb.logic/exclui-compra id))

(println "Exercicio semana2: Criar a função exclui-compra! (atomo)"
         (exclui-compra! repositorio-de-compras 4))