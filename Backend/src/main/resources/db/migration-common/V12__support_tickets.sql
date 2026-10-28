CREATE TABLE support_tickets (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts(id),
    subject VARCHAR(140) NOT NULL,
    category VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE INDEX idx_support_tickets_account_updated ON support_tickets(account_id, updated_at);
CREATE INDEX idx_support_tickets_status_updated ON support_tickets(status, updated_at);

CREATE TABLE support_ticket_messages (
    id UUID PRIMARY KEY,
    ticket_id UUID NOT NULL REFERENCES support_tickets(id) ON DELETE CASCADE,
    sender_id UUID NOT NULL REFERENCES accounts(id),
    body VARCHAR(2000) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE INDEX idx_support_ticket_messages_ticket_created ON support_ticket_messages(ticket_id, created_at);
