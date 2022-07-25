(ns clojure-boost.semana1
  (:require [clojure.string :as s])
  (:require [java-time :as java-time])
  (:require [clojure.data.csv :as csv])
  (:require [clojure.java.io :as io]))



;--------------------------------------------------------------------------------------------

;; Tarefa
;; Criar a função nova-compra, que retorne uma estrutura de dados 
;; que represente uma compra realizada em um determinado cartão.

;; Parâmetros:
;; data (String: yyyy-mm-dd)
;; valor (BigDecimal)
;; estabelecimento (String)
;; categoria (String)
;; cartao (Long)
;; Retorno:
;; Map com a seguinte estrutura:
;; {:data ... :valor ... :estabelecimento ... :categoria ... :cartao ...}

(def exemplo-nova-compra {:data (str "2022-07-19")
                       :valor (bigdec 0.06)
                       :estabelecimento (str "starbucks")
                       :categoria (str "café")
                       :cartao (long 32995491)})

(println "Resultado do exercicio: Criar a função nova-compra")

(defn nova-compra [data valor estabelecimento categoria cartao]
  {:data (str data)
   :valor (bigdec valor)
   :estabelecimento (str estabelecimento)
   :categoria (str categoria)
   :cartao (long cartao)})

(def exemplo-compra
  (nova-compra "2022-07-19" 0.06 "starbucks" "café" 3939393939393939))

(println exemplo-compra)


;--------------------------------------------------------------------------------------------

;; Tarefa
;; Crie as funções (lista-compras) que retorna uma coleção 
;; com todas as compras realizadas.

;; Parâmetros
;; A função não recebe parâmetros.

;; Retorno
;; Deve retornar um vetor de maps de compras.

;; Critérios de aceitação
;; O vetor deve ter os 19 maps de compras, 
;; com os dados da planilha Massa de dados. 

(defn lista-compras []
  [{:data  "2022-01-01"
    :valor 129.90
    :estabelecimento "Otback"
    :categoria "Alimentação"
    :cartao "1234 1234 1234 1234"}
   {:data  "2022-01-02"
    :valor 260.00
    :estabelecimento "Dentista"
    :categoria "Saúde"
    :cartao "1234 1234 1234 1234"}
   {:data  "2022-02-01"
    :valor 20.00
    :estabelecimento "Cinema"
    :categoria "Lazer"
    :cartao "1234 1234 1234 1234"}
   {:data  "2022-02-01"
    :valor 150.00
    :estabelecimento "Show"
    :categoria "Lazer"
    :cartao "4321 4321 4321 4321"}
   {:data  "2022-01-10"
    :valor 289.99
    :estabelecimento "Posto de gasolina"
    :categoria "Automóvel"
    :cartao "4321 4321 4321 4321"}
   {:data  "2022-02-20"
    :valor 79.90
    :estabelecimento "iFood"
    :categoria "Alimentação"
    :cartao "4321 4321 4321 4321"}
   {:data  "2022-03-01"
    :valor 85.00
    :estabelecimento "Alura"
    :categoria "Educação"
    :cartao "4321 4321 4321 4321"}
   {:data  "2022-01-30"
    :valor 85.00
    :estabelecimento "Alura"
    :categoria "Educação"
    :cartao "1598 1598 1598 1598"}
   {:data  "2022-01-31"
    :valor 350.00
    :estabelecimento "Tok&Stok"
    :categoria "Casa"
    :cartao "1598 1598 1598 1598"}
   {:data  "2022-02-01"
    :valor 400.00
    :estabelecimento "Leroy Merlin"
    :categoria "Casa"
    :cartao "1598 1598 1598 1598"}
   {:data  "2022-03-01"
    :valor 50.00
    :estabelecimento "Madero"
    :categoria "Alimentação"
    :cartao "6655 6655 6655 6655"}
   {:data  "2022-03-01"
    :valor 70.00
    :estabelecimento "Teatro"
    :categoria "Lazer"
    :cartao "6655 6655 6655 6655"}
   {:data  "2022-03-04"
    :valor 250.00
    :estabelecimento "Hospital"
    :categoria "Saúde"
    :cartao "6655 6655 6655 6655"}
   {:data  "2022-04-10"
    :valor 130.00
    :estabelecimento "Drogaria"
    :categoria "Saúde"
    :cartao "6655 6655 6655 6655"}
   {:data  "2022-03-10"
    :valor 100.00
    :estabelecimento "Show de pagode"
    :categoria "Lazer"
    :cartao "3939 3939 3939 3939"}
   {:data  "2022-03-11"
    :valor 25.90
    :estabelecimento "Dogão"
    :categoria "Alimentação"
    :cartao "3939 3939 3939 3939"}
   {:data  "2022-03-12"
    :valor 215.87
    :estabelecimento "Praia"
    :categoria "Lazer"
    :cartao "3939 3939 3939 3939"}
   {:data  "2022-04-01"
    :valor 976.88
    :estabelecimento "Oficina"
    :categoria "Automóvel"
    :cartao "3939 3939 3939 3939"}
   {:data  "2022-04-10"
    :valor 85.00
    :estabelecimento "Alura"
    :categoria "Educação"
    :cartao "3939 3939 3939 3939"}])

(println "Resultado do exercicio: Crie as funções (lista-compras)")
(println "Tenho" (count (lista-compras)) "mapas no meu vetor (lista-compras)")




;--------------------------------------------------------------------------------------------


;; Tarefa
;; Criar a função total-gasto, que recebe um vetor de compras 
;; e retorna a soma dos valores gastos.

;; Parametros
;; compras (vetor com maps de compra)
;; Retorno
;; BigDecimal com a soma do valor das compras
;; Exemplo:
;; [{:valor: 100.00 ; demais chaves do mapa {:valor: 250.00 ; demais chaves do mapa {:valor: 400.00 ; demais chaves do mapa}]

;; TOTAL: R$ 750,00

(defn filtro-cartao
  [card-numb lista-compras]
  (filter #(= (get % :cartao) card-numb) lista-compras))

(defn total-gasto
  [compras]
  (->> compras
       (map :valor)
       (reduce +)))

(defn total-gasto-cartao [lista-compras card-numb]
  (total-gasto (filtro-cartao card-numb lista-compras)))

(println "Resultado do exercicio: função total-gasto")
(println "teste da funcao para filtrar por cartao"
         (filtro-cartao "1234 1234 1234 1234" (lista-compras)))
(println "teste da funcao para somar o total" 
         (total-gasto (lista-compras)))
(println "teste da funcao para somar o total gasto por cartao"
         (total-gasto-cartao (lista-compras) "1234 1234 1234 1234"))

;--------------------------------------------------------------------------------------------

;; Tarefa
;; Criar uma função que, dado um mês e uma lista de compras, retorne um vetor das compras feitas somente naquele mês.

;; Parâmetros
;; mes (inteiro)
;; lista de compras (vetor ou list com maps de compras)
;; Retorno
;; vetor com maps de compra

(defn split-mes
  [datas]
  (-> (s/split datas #"-")
      (get 1)
      (Integer/parseInt)))

(defn filtro-mes
  [lista-compras mes]
  (filter #(= (split-mes (:data %)) mes) lista-compras))

(println "Resultado do exercicio: função filtro-mes")
(println (filtro-mes (lista-compras) 3))


;--------------------------------------------------------------------------------------------
;; Tarefa
;; Criar a função total-gasto-no-mes, que calcule a soma dos valores gastos num determinado cartão em um mês.

;; Para facilitar, considere que todas as compras sejam de um mesmo cartão.

(defn total-gasto-mes
  [lista-compras mes card]
  (total-gasto-cartao (filtro-mes lista-compras mes) card))

(println "Resultado do exercicio: Criar a função total-gasto-no-mes")
(println "total:" (total-gasto-mes (lista-compras) 1 "1234 1234 1234 1234"))


;--------------------------------------------------------------------------------------------
;; Tarefa
;; Criar uma função que retorne os total gasto agrupados por categoria.

;; Parâmetros
;; compras (vetor com maps de compras)
;; Retorno
;; Map* com as categorias associadas ao valor gasto
;; Exemplo
;; [{:categoria "Educação" :valor 700.00 ; demais chaves do mapa} {:categoria "Saúde" :valor 1500.00 ; demais chaves do mapa} {:categoria "Educação" :valor 50.00 ; demais chaves do mapa} {:categoria "Alimentação" :valor 100.00 ; demais chaves do mapa} {:categoria "Alimentação" :valor 89.90 ; demais chaves do mapa}]

;; Saída
;; {"Educação" 750.00 "Saúde" 1500.00 "Alimentação" 189.90}

;; OBSERVAÇÃO
;; A saída não precisa ser ordenada

(defn compras-agrupadas [lista-compras]
  (group-by :categoria lista-compras))

(defn total-gasto-categoria [lista-compras]
  (into {}
        (map (fn [[chave valor]]
               [chave (total-gasto valor)])
             (compras-agrupadas (lista-compras)))))

(println "Resultado do exercicio: Criar a função total gasto agrupados por categoria")
(println (total-gasto-categoria lista-compras))


;--------------------------------------------------------------------------------------------
;; Tarefa
;; Criar uma função que retorne as compras 
;; que estão dentro de um intervalo de valor máximo e valor mínimo.

(defn lista-compras-intervalo-valor [val-ini val-fim lista-compras]
  (filter #(and (>= (:valor %) val-ini) (<= (:valor %) val-fim)) lista-compras))

(println "Resultado do exercicio: Criar a função dentro de um intervalo de valores")
(println (lista-compras-intervalo-valor 10.00 50.00 (lista-compras)))

;--------------------------------------------------------------------------------------------
;; Tarefa
;; Utilizar a API de datas do Java (Java Time) para representar 
;; as datas das compras, e da validade do cartão.

;; Adaptar também a função que filtra compras no mês.

;; Resolução aplicada para filtro compras no mes utilizando o java-time:

(defn get-mes
  [datas]
  (java-time/as (java-time/local-date datas) :month-of-year))

;;(println (get-mes "2022-03-01"))

(defn filtro-mes2
  [lista-compras mes]
  (filter #(= (get-mes (:data %)) mes) lista-compras))

(println "Resultado do exercicio: Utilizar a API de datas do Java (Java Time)")
(println (filtro-mes2 (lista-compras) 3))

;--------------------------------------------------------------------------------------------
;; Tarefa
;; Adapte a função lista-compras para carregar os dados do arquivo compras.csv 
;; anexo nesta tarefa.


(def conteudo-csv
  (with-open [reader (io/reader "compras.csv")]
    (doall
     (csv/read-csv reader))))

(println "Resultado do exercicio: carregar os dados do arquivo: TENTATIVA")

(let [header (first conteudo-csv)
      data (rest conteudo-csv)]
  (println "TESTE COM ZIPMAP" (map #(zipmap (map keyword header) %1) data))
  (println (get header 2))
  (println "TESTE HEADER" header)
  (println "TESTE DATA" data))

(println "Resultado do exercicio: carregar os dados do arquivo: OFICIAL")

;; (defn processa-compra-csv []
;;   (->> (slurp "compras.csv")
;;        clojure.string/split-lines
;;        (map #(clojure.string/split % #","))
;;        rest
;;        (map #(nova-compra %))))

