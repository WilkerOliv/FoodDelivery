create table courier_compensation (
 id integer primary key check (id = 1),
 configured boolean not null default false,
 amount_cents bigint not null default 0 check (amount_cents >= 0),
 updated_at timestamp with time zone not null
);
insert into courier_compensation(id,configured,amount_cents,updated_at) values(1,false,0,current_timestamp);
alter table delivery_tasks add column courier_fee_cents bigint check (courier_fee_cents >= 0);
create table courier_earnings (
 id uuid primary key,
 delivery_id uuid not null unique references delivery_tasks(id),
 courier_id uuid not null references accounts(id),
 amount_cents bigint not null check (amount_cents > 0),
 created_at timestamp with time zone not null
);
create index idx_courier_earnings_account on courier_earnings(courier_id,created_at);
create table courier_payouts (
 id uuid primary key,
 courier_id uuid not null references accounts(id),
 amount_cents bigint not null check (amount_cents > 0),
 destination_cpf varchar(11) not null,
 status varchar(20) not null,
 request_key uuid not null,
 created_at timestamp with time zone not null,
 decided_at timestamp with time zone,
 paid_at timestamp with time zone,
 decided_by uuid references accounts(id),
 external_reference varchar(120) unique,
 decision_note varchar(300),
 version bigint not null default 0,
 constraint uk_courier_payout_key unique(courier_id,request_key),
 constraint ck_courier_payout_status check(status in ('REQUESTED','APPROVED','PAID','REJECTED','CANCELLED'))
);
create index idx_courier_payout_account on courier_payouts(courier_id,created_at);
