CREATE TABLE store_cover_submissions (
    id UUID PRIMARY KEY,
    store_id UUID NOT NULL REFERENCES stores(id),
    file_name VARCHAR(80) NOT NULL UNIQUE,
    status VARCHAR(16) NOT NULL,
    review_note VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    reviewed_at TIMESTAMP WITH TIME ZONE,
    reviewed_by UUID,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT ck_store_cover_status CHECK (status IN ('PENDING','APPROVED','REJECTED','SUPERSEDED'))
);
CREATE INDEX idx_cover_submission_store ON store_cover_submissions(store_id, created_at);
CREATE INDEX idx_cover_submission_status ON store_cover_submissions(status, created_at);
