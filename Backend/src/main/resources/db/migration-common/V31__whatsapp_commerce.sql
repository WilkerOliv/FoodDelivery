create table whatsapp_bindings (
 phone_hash varchar(64) primary key,
 account_id uuid not null unique references accounts(id),
 credential_version integer not null,
 created_at timestamp with time zone not null
);
create table whatsapp_sessions (
 phone_hash varchar(64) primary key,
 encrypted_state text not null,
 updated_at timestamp with time zone not null
);
create table whatsapp_events (
 message_id varchar(200) primary key,
 phone_hash varchar(64) not null,
 encrypted_phone text not null,
 encrypted_text text not null,
 created_at timestamp with time zone not null,
 status varchar(20) not null,
 attempts integer not null default 0
);
create index idx_whatsapp_event_work on whatsapp_events(status,created_at);
create table whatsapp_replies (
 id uuid primary key,
 message_id varchar(200) not null unique references whatsapp_events(message_id),
 encrypted_phone text not null,
 encrypted_text text not null,
 payment_id uuid references payments(id),
 customer_id uuid references accounts(id),
 status varchar(20) not null,
 attempts integer not null default 0,
 next_attempt_at timestamp with time zone not null,
 lease_id uuid,
 created_at timestamp with time zone not null
);
create index idx_whatsapp_reply_work on whatsapp_replies(status,next_attempt_at);
create index idx_whatsapp_event_rate on whatsapp_events(phone_hash,created_at);
