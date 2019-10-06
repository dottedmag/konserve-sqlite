(ns konserve-sqlite.core-test
  (:require [clojure.test :refer :all]
            [konserve.core :as k]
            [konserve-sqlite.core :refer [new-sqlite-store delete-store]]
            [clojure.core.async :refer [<!!]]))

(deftest sqlite-store-test
  (testing "Test the SQLite store functionality."
    (let [uri "jdbc:sqlite:test.db"
          store (<!! (new-sqlite-store uri))]
      (is (= (<!! (k/exists? store :foo))
             false))
      (<!! (k/assoc-in store [:foo] nil))
      (is (= (<!! (k/get-in store [:foo]))
             nil))

      (<!! (k/assoc-in store [:foo] :bar))

      (is (= (<!! (k/exists? store :foo))
             true))
      (is (= (<!! (k/get-in store [:foo]))
             :bar))
      (<!! (k/dissoc store :foo))
      (is (= (<!! (k/get-in store [:foo]))
             nil))
      (<!! (k/bassoc store :binbar (byte-array (range 10))))
      (<!! (k/bget store :binbar (fn [{:keys [input-stream]}]
                                   (is (= (map byte (slurp input-stream))
                                          (range 10))))))
      (delete-store uri))))


(comment

  (def store (<!! (new-sqlite-store "jdbc:sqlite:test.db")))

  (<!! (k/assoc-in store [:foo] nil))

  (<!! (k/get-in store [:foo]))

  (<!! (k/assoc-in store [:foo] :bar))

  (run-tests))
