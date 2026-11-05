create table private_settings (
 setting_key varchar(100) primary key,
 encrypted_value text not null,
 updated_at timestamp with time zone not null
);
create table administrator_mfa (
 account_id uuid primary key references accounts(id),
 encrypted_secret text not null,
 enabled boolean not null default false,
 last_counter bigint not null default -1,
 recovery_hashes text not null default '[]',
 failed_attempts integer not null default 0,
 locked_until timestamp with time zone,
 updated_at timestamp with time zone not null
);
