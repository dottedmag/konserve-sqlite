-- :name db-create-table
-- :command :execute
-- :result :raw
CREATE TABLE IF NOT EXISTS __konserve_data (
  id TEXT PRIMARY KEY,
  edn_value,
  attachment
)

-- :name db-drop-table
-- :command :execute
-- :result :raw
DROP TABLE IF EXISTS __konserve_data

-- :name db-record-exists
SELECT COUNT(*) AS count FROM __konserve_data WHERE id = :id

-- :name db-get-record-edn-value
SELECT id, edn_value FROM __konserve_data WHERE id = :id

-- :name db-get-record-attachment
SELECT id, attachment FROM __konserve_data WHERE id = :id

-- :name db-delete-record :! :n
DELETE FROM __konserve_data WHERE id = :id

-- :name db-upsert-record-edn-value :i!
INSERT INTO __konserve_data (id, edn_value) VALUES(:id, :edn-value)
ON CONFLICT (id)
   DO UPDATE SET
      edn_value = :edn-value

-- :name db-upsert-record-attachment :i!
INSERT INTO __konserve_data (id, attachment) VALUES(:id, :attachment)
ON CONFLICT (id)
   DO UPDATE SET
      attachment = :attachment
