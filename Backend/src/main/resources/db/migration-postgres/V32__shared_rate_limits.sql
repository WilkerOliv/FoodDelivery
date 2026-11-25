CREATE TABLE authentication_rate_windows (
    bucket_key CHAR(64) PRIMARY KEY,
    window_id UUID NOT NULL DEFAULT gen_random_uuid(),
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    attempts INTEGER NOT NULL CHECK (attempts >= 0)
);
CREATE INDEX idx_authentication_rate_expiry ON authentication_rate_windows (expires_at);
