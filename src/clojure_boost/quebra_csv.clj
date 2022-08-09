(ns clojure-boost.quebra-csv
  (:use clojure.pprint)
  (:require [clojure.string :as str]
             [java-time :as jt]))

;; (slurp "resources/compras.csv")
(def ler-arquivo (slurp "resources/compras.csv"))

ler-arquivo

(defn quebra-de-linha [ler-arquivo] (str/split-lines ler-arquivo))
;; (first (clojure.string/split-lines
        ;; (slurp "resources/compras.csv")))

(defn string-individual [ler-arquivo]
  (map #(str/split % #",") (quebra-de-linha ler-arquivo)))

(string-individual ler-arquivo)

(defn nova-compra
  [[data valor estabelecimento categoria cartao]]
  {:data data
   :valor valor
   :estabelecimento estabelecimento
   :categoria categoria
   :cartao cartao})

;; (nova-compra [1 2 3 44 55])

(println (map nova-compra (rest (string-individual ler-arquivo))))
;; (map nova-compra (rest (string-individual ler-arquivo)))
;; (map (get (nova-compra (rest (string-individual ler-arquivo))) 0))

(map #(println %) ["2022-04-10" "85.00" "Alura" "Educação" "3939 3939 3939 3939"]) 
(get ["2022-04-10" "85.00" "Alura" "Educação" "3939 3939 3939 3939"] 0)

;; (defn exemplo [[primeiro & resto]]
;;   resto)

;; (exemplo [[1 2 3 4 5 6] [1 2 3]])
;; (exemplo '(["DATA" "VALOR" "ESTABELECIMENTO" "CATEGORIA" "CARTÃO"]
;;            ["2022-01-01" "129.90" "Outback" "Alimentação" "1234 1234 1234 1234"]))
;; (exemplo (string-individual ler-arquivo))

;; (println (map nova-compra (exemplo (string-individual ler-arquivo))))
;; (map nova-compra (exemplo (string-individual ler-arquivo)))

;; (println (map [["2022-01-01" "129.90" "Outback" "Alimentação" "1234 1234 1234 1234"]
;;                ["2022-01-02" "260.00" "Dentista" "Saúde" "1234 1234 1234 1234"]]))

;; (str/split "DATA,VALOR,ESTABELECIMENTO,CATEGORIA,CARTÃO" #",")
;; (def ler-arquivo (slurp "resources/compras.csv"))
;; (map #(str/split % #",") (quebra-de-linha ler-arquivo))
;; (map ((quebra-de-linha ler-arquivo) str/split  #','))


;; (map #(identity %) [1 2 3 4])
;; (map inc [1 2 3 4])


;; (def s1 [["2022-01-01" "129.90" "Outback" "Alimentação" "1234 1234 1234 1234"]
;;          ["2022-01-02" "260.00" "Dentista" "Saúde" "1234 1234 1234 1234"]
;;          ["2022-01-02" "260.00" "Dentista" "Saúde" "1234 1234 1234 1234"]])

(defn covert-type [[data valor estabelecimento categoria cartao]]
  (let [data-transformado (jt/local-date "yyyy-MM-dd" data)
        valor-transformado (bigdec valor)
        estabelecimento-transformado (str categoria)
        categoria-transformado (str estabelecimento)
        cartao-transformado (Long/parseLong (str/join "" (clojure.string/split cartao #" ")))]
    nova-compra [data-transformado valor-transformado estabelecimento-transformado categoria-transformado cartao-transformado]))

;; (map covert-type s1)
(println (map covert-type (rest (string-individual ler-arquivo))))

;; (Long/parseLong "1234 1234 1234 1234")
;; (Long/parseLong "12345")
;; (Long/parseLong (str/join "" (clojure.string/split "1234 1234 1234 1234" #" ")))


;;-------------------------------------------------------------------------------------------------

(defn str->long [cartao]
  (-> cartao
      (clojure.string/replace #" " "")
      Long/parseLong))

(def csv->compra [java-time/local-date
                  bigdec
                  identity
                  identity
                  str->long])

(defn converte-valores-em-registro [funcoes-de-conversao registro]
  (map #(%1 %2) funcoes-de-conversao registro))

;; SUA BASE DE CÓDIGO
(def ler-arquivo (slurp "resources/compras.csv"))

(defn quebra-de-linha [ler-arquivo] (str/split-lines ler-arquivo))

(defn string-individual [ler-arquivo]
  (map #(str/split % #",") (quebra-de-linha ler-arquivo))

;; (->> (rest (string-individual ler-arquivo))
;;     (map #(converte-valores-em-registro csv->compra %))
;;     (map nova-compra)
;;     vec)
  
  (->> (rest (string-individual ler-arquivo))
    (map #(converte-valores-em-registro csv->compra %)))