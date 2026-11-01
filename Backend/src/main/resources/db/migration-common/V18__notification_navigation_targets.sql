ALTER TABLE account_notifications ADD COLUMN target_type VARCHAR(30);
ALTER TABLE account_notifications ADD COLUMN target_id UUID;
CREATE INDEX idx_notifications_target ON account_notifications(target_type, target_id);
