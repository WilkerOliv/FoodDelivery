ALTER TABLE stores ADD COLUMN operating_hours_enabled BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE stores ADD COLUMN weekly_hours_json TEXT NOT NULL DEFAULT '{}';
ALTER TABLE stores ADD COLUMN hours_exceptions_json TEXT NOT NULL DEFAULT '[]';
