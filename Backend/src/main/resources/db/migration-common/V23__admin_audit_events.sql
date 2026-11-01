CREATE TABLE admin_audit_events (
    id UUID PRIMARY KEY,
    actor_id UUID REFERENCES accounts(id) ON DELETE SET NULL,
    action VARCHAR(80) NOT NULL,
    target_type VARCHAR(40) NOT NULL,
    target_id VARCHAR(80),
    summary VARCHAR(300) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_admin_audit_created ON admin_audit_events(created_at DESC);
CREATE INDEX idx_admin_audit_target ON admin_audit_events(target_type, target_id, created_at DESC);
