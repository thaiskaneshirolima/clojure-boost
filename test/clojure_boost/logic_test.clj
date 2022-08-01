(ns clojure-boost.logic-test
  (:require [clojure.test :refer :all]
            [clojure-boost.logic :refer :all]
            [java-time :as jt]))

(deftest valid-date-test
  (testing "Accept current data"
    (is (valid-date? (jt/local-date))))
  (testing "Accepts data less than the current date"
    (is (valid-date? (jt/minus (jt/local-date) (jt/days 30)))))
  (testing "Does not accept data greater than the current date"
    (is (thrown? Exception (valid-date? (jt/plus (jt/local-date) (jt/days 30))))))
  (testing "Does not accept nill"
    (is (thrown? NullPointerException (valid-date? nil)))))

(deftest valid-value-test
  (testing "Accept bugdec value"
    (is (valid-value? (bigdec 10))))
  (testing "Does not accept bigint value"
    (is (thrown? Exception (valid-value? (bigint 10)))))
  (testing "Does not accept biginteger value"
    (is (thrown? Exception (valid-value? (biginteger 10)))))
  (testing "Does not accept int value"
    (is (thrown? Exception (valid-value? (int 10)))))
  (testing "Does not accept nill"
    (is (thrown? Exception (valid-value? nil)))))

(deftest valid-establishment-test
  (testing "Accept more than 2 caracteres"
    (is (valid-establishment? "Lazer")))
  (testing "Does not accept less than 2 characters"
    (is (thrown? Exception (valid-establishment? "L"))))
  (testing "Does not accept nill"
    (is (thrown? Exception (valid-establishment? nil)))))

(deftest valid-category-test
  (testing "Only accepts listed categories: Alimentação"
    (is (valid-category? "Alimentação")))
  (testing "Only accepts listed categories: Automóvel"
    (is (valid-category? "Automóvel")))
  (testing "Only accepts listed categories: Casa"
    (is (valid-category? "Casa")))
  (testing "Only accepts listed categories: Educação"
    (is (valid-category? "Educação")))
  (testing "Only accepts listed categories: Lazer"
    (is (valid-category? "Lazer")))
  (testing "Only accepts listed categories: Saúde"
    (is (valid-category? "Saúde")))
  (testing "Does not accept unlisted categories: teste"
    (is  (thrown? Exception (valid-category? "teste"))))
  (testing "Does not accept nill"
    (is (thrown? Exception (valid-category? nil)))))

(deftest gera-id-test
  (testing "If the shopping list is empty, the ID must be the value 1"
    (is (= 1 (gera-id []))))
  (testing "The new purchase ID must be the maximum value of the shopping list ID plus 1"
    (is (= 334 (gera-id [{:id 111} {:id 333} {:id 222}]))))) 

;; (deftest insere-compra-test
;;   (testing "If the shopping list is empty, the ID must be the value 1"
;;     (is (= [{:id 1
;;               :data "27/07/2022"
;;               :valor 100.90M
;;               :estabelecimento "Outback"
;;               :categoria "Alimentação"
;;               :cartao 123412341234}]
;;            (insere-compra [] 
;;                           (->CompraRealizada 10 "27/07/2022" 100.90M "Outback" "Alimentação" 123412341234))))))

