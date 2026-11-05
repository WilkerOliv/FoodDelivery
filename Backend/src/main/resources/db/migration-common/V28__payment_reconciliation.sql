create table payment_reconciliations (
 id uuid primary key,
 payment_id uuid not null references payments(id),
 actor_id uuid not null references accounts(id),
 result varchar(24) not null,
 provider_status varchar(40),
 summary varchar(300) not null,
 created_at timestamp with time zone not null
);
create index idx_payment_reconciliation_payment on payment_reconciliations(payment_id,created_at);
