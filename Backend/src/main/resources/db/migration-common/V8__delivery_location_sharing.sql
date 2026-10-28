ALTER TABLE delivery_tasks ADD COLUMN courier_latitude DOUBLE PRECISION;
ALTER TABLE delivery_tasks ADD COLUMN courier_longitude DOUBLE PRECISION;
ALTER TABLE delivery_tasks ADD COLUMN location_updated_at TIMESTAMP WITH TIME ZONE;
