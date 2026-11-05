alter table store_professionals add column operating_hours_enabled boolean not null default false;
alter table store_professionals add column weekly_hours_json text not null default '{}';
alter table store_professionals add column hours_exceptions_json text not null default '[]';
alter table stores add column booking_notice_minutes integer not null default 30;
alter table stores add column booking_step_minutes integer not null default 15;
alter table stores add column automatic_cancellation_notice_minutes integer not null default -1;
