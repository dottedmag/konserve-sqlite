# konserve-sqlite

A SQLite implementation of the [konserve
kv-protocol](https://github.com/replikativ/konserve).

## Usage

The whole purpose of konserve is to have a unified associative key-value
interface for edn datastructures. Just use the standard interface functions of
konserve.

You can also provide a DB connection object to the `new-sqlite-store`
constructor as an argument. We do not require additional settings beyond the
konserve serialization protocol for the store, so you can still access the store
through SQLite directly wherever you need.

~~~clojure
  (require '[konserve-sqlite.core :refer :all]
           '[konserve.core :as k)
  (def sqlite-store (<!! (new-sqlite-store "jdbc:sqlite:database.db")))

  (<!! (k/exists? sqlite-store  "john"))
  (<!! (k/get-in sqlite-store ["john"]))
  (<!! (k/assoc-in sqlite-store ["john"] 42))
  (<!! (k/update-in sqlite-store ["john"] inc))
  (<!! (k/get-in sqlite-store ["john"]))

  (defrecord Test [a])
  (<!! (k/assoc-in sqlite-store ["peter"] (Test. 5)))
  (<!! (k/get-in sqlite-store ["peter"]))
~~~

## Changes

### 0.1.0

- Converted PostgreSQL implementation to SQLite

## License

Copyright © 2019 Mikhail Gusarov
Copyright © 2014-2019 Christian Weilbach and Mihael Konjevic

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
