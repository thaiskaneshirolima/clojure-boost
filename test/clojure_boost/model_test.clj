(ns clojure-boost.model-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [clojure-boost.model :refer :all]
            [schema.core :as s]
            [java-time :as jt]))

(s/set-fn-validation! true)

(deftest nova-compra-test
  (testing "Context of the test assertions"
    (is (= {:data (jt/local-date),
            :valor 100M,
            :estabelecimento "Amazon",
            :categoria "Casa",
            :cartao 1111222233334444}
           (s/validate CompraSchema {:data (jt/local-date)
                                     :valor 100M
                                     :estabelecimento "Amazon"
                                     :categoria "Casa"
                                     :cartao 1111222233334444}))))
  (testing "Does't accept date greater than current date"
    (is (thrown? clojure.lang.ExceptionInfo
                 (s/validate CompraSchema {:data (jt/plus (jt/local-date) (jt/days 1))
                                           :valor 100M
                                           :estabelecimento "Amazon"
                                           :categoria "Casa"
                                           :cartao 1111222233334444}))))
  (testing "Does't accept negative value"
    (is (thrown? clojure.lang.ExceptionInfo
                 (s/validate CompraSchema {:data (jt/local-date)
                                           :valor -100M
                                           :estabelecimento "Amazon"
                                           :categoria "Casa"
                                           :cartao 1111222233334444}))))
  (testing "Does't accept empty string"
    (is (thrown? clojure.lang.ExceptionInfo
                 (s/validate CompraSchema {:data (jt/local-date)
                                           :valor 100M
                                           :estabelecimento ""
                                           :categoria "Casa"
                                           :cartao 1111222233334444}))))
  (testing "Does't accept unlisted items"
    (is (thrown? clojure.lang.ExceptionInfo
                 (s/validate CompraSchema {:data (jt/local-date)
                                           :valor 100M
                                           :estabelecimento "Amazon"
                                           :categoria "Viagem"
                                           :cartao 1111222233334444}))))
  (testing "Does't accept value 100000000000000000000"
    (is (thrown? clojure.lang.ExceptionInfo
                 (s/validate CompraSchema {:data (jt/local-date)
                                           :valor 100M
                                           :estabelecimento "Amazon"
                                           :categoria "Casa"
                                           :cartao 100000000000000000000})))))

;; (s/validate CompraSchema {:data "09/08/2022"
;;                           :valor 100M
;;                           :estabelecimento "Amazon"
;;                           :categoria "Casa"
;;                           :cartao 1111222233334444})

;; (pprint (validate-schemas (nova-compra "01/08/2022" 10 "Lar" "Casa" 123456)))


