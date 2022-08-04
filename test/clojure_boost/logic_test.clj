(ns clojure-boost.logic-test
  (:require [clojure.test :refer :all]
            [clojure-boost.logic :refer :all]
            [java-time :as jt]))

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

;; Data da compra:
;; Se você estiver usando a data como String, então ela deve ter o formato yyyy-mm-dd;
;; Se você estiver usando API do Java Time, então ela deve ser uma data menor ou igual à data atual.

(deftest valid-date-test
  (testing "Accepts only a date less than or equal to the current date"
    (are [result date] (= result (valid-date? date))
      true    (jt/local-date)
      true    (jt/minus (jt/local-date) (jt/days 1))
      false   (jt/plus (jt/local-date) (jt/days 1))))
  (testing "Does't accept nil date"
    (is (thrown? AssertionError
                 (valid-date? nil)))))

;; Valor:
;; deve ser um BigDecimal positivo.

(deftest valid-value-test
  (testing "Accepts positive bigdec values"
    (are [result value] (= result (valid-value? value))
      true    (bigdec 10)
      false   (bigdec 0)
      false   (bigdec -10)
      false   (bigint 10)
      false   (biginteger 10)))
  (testing "Does't accept nil values"
    (is (thrown? AssertionError
                 (valid-date? nil)))))

;; Estabelecimento:
;; Deve ter pelo menos 2 caracteres.

(deftest valid-establishment-test
  (testing "Accepts 2 or more characters"
    (are [result establishment] (= result (valid-establishment? establishment))
      true    "ab"
      true    "abcd"
      false   "a"))
  (testing "Does't accept nil values"
    (is (thrown? AssertionError
                 (valid-establishment? nil)))))


;; Categoria:
;; Deve ser uma das opções: Alimentação, Automóvel, Casa, 
;; Educação, Lazer ou Saúde.

(deftest valid-category-test
  (testing "Only accepts one of the items listed."
    (are [result category] (= result (valid-category? category))
      true    "Alimentação"
      true    "Automóvel"
      true    "Casa"
      true    "Educação"
      true    "Lazer"
      true    "Saúde"
      false   "Viagem"))
  (testing "Does't accept nil values"
    (is (thrown? AssertionError
                 (valid-category? nil)))))

;; Cartão:
;; inteiro entre 0 e 1 0000 0000 0000 0000.

(deftest valid-card-test
  (testing "Checking varied inputs in valid-card"
    (are [result number] (= result (valid-card? number))
      true    0 
      true    12341234
      true    10000000000000000
      false  -1
      false   20000000000000000))
  (testing "Does't accept nil values"
    (is (thrown? AssertionError
                 (valid-card? nil))))
  (testing "Does't accept string"
    (is (thrown? ClassCastException
                 (valid-card? "teste")))))

(deftest total-spent-test
  (testing "Adds the total inside the key :valor"
    (are [result value] (= result (total-spent value))
      600.0   lista-compras
      0       []))
  (testing "Does't accept nil values"
    (is (thrown? AssertionError
                 (total-spent nil)))))

(deftest grouped-by-category-test
  (testing "Function should grouped by category"
    (is (= {"Alimentação"
            [{:data "2022-01-01" :valor 100.0 :estabelecimento "Otback" :categoria "Alimentação" :cartao "1234 1234 1234 1234"}],
            "Lazer"
            [{:data "2022-02-01", :valor 200.0, :estabelecimento "Cinema", :categoria "Lazer", :cartao "1234 1234 1234 1234"}],
            "Saúde"
            [{:data "2022-01-02", :valor 300.0, :estabelecimento "Dentista", :categoria "Saúde", :cartao "1234 1234 1234 1234"}]} 
           (grouped-by-category lista-compras))))
  (testing "Accept nil values"
    (is (= {} (grouped-by-category nil)))))

(deftest total-spent-grouped-by-category-test
  (testing "Function should grouped by category"
    (is (= {"Alimentação" 300.5}
           (total-spent-grouped-by-category [{:valor 100.0
                                              :categoria "Alimentação"}
                                             {:valor 200.5
                                              :categoria "Alimentação"}]))))
  (testing "Accept nil values"
    (is (= {} (total-spent-grouped-by-category nil)))))

