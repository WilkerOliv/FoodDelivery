CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    email VARCHAR(254) NOT NULL UNIQUE,
    name VARCHAR(120) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    phone VARCHAR(30),
    address_line VARCHAR(250),
    role VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE stores (
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL REFERENCES accounts(id),
    name VARCHAR(140) NOT NULL,
    description VARCHAR(1000),
    address VARCHAR(250) NOT NULL,
    category VARCHAR(60) NOT NULL,
    active BOOLEAN NOT NULL,
    approved BOOLEAN NOT NULL,
    timezone VARCHAR(80) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE INDEX idx_stores_owner ON stores(owner_id);

CREATE TABLE catalog_items (
    id UUID PRIMARY KEY,
    store_id UUID NOT NULL REFERENCES stores(id),
    name VARCHAR(140) NOT NULL,
    description VARCHAR(1000),
    price_cents BIGINT NOT NULL CHECK (price_cents >= 0),
    stock INTEGER NOT NULL CHECK (stock >= 0),
    active BOOLEAN NOT NULL,
    service_item BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX idx_catalog_items_store ON catalog_items(store_id);

CREATE TABLE customer_orders (
    id UUID PRIMARY KEY,
    customer_id UUID NOT NULL REFERENCES accounts(id),
    store_id UUID NOT NULL REFERENCES stores(id),
    status VARCHAR(24) NOT NULL,
    fulfillment_type VARCHAR(16) NOT NULL,
    delivery_address VARCHAR(400),
    total_cents BIGINT NOT NULL CHECK (total_cents >= 0),
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX idx_order_customer_created ON customer_orders(customer_id, created_at);

CREATE TABLE order_lines (
    order_id UUID NOT NULL REFERENCES customer_orders(id) ON DELETE CASCADE,
    catalog_item_id UUID NOT NULL,
    name VARCHAR(140) NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price_cents BIGINT NOT NULL CHECK (unit_price_cents >= 0)
);

CREATE TABLE payments (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL UNIQUE REFERENCES customer_orders(id),
    amount_cents BIGINT NOT NULL CHECK (amount_cents >= 0),
    status VARCHAR(20) NOT NULL,
    provider_reference VARCHAR(120),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE reservations (
    id UUID PRIMARY KEY,
    store_id UUID NOT NULL REFERENCES stores(id),
    customer_id UUID NOT NULL REFERENCES accounts(id),
    starts_at TIMESTAMP WITH TIME ZONE NOT NULL,
    service_name VARCHAR(140) NOT NULL,
    catalog_item_id UUID NOT NULL REFERENCES catalog_items(id),
    amount_cents BIGINT NOT NULL CHECK (amount_cents > 0),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT uk_reservation_slot UNIQUE(store_id, starts_at)
);
CREATE INDEX idx_reservation_customer_start ON reservations(customer_id, starts_at);

CREATE TABLE delivery_tasks (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL UNIQUE REFERENCES customer_orders(id),
    courier_id UUID REFERENCES accounts(id),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX idx_delivery_courier_created ON delivery_tasks(courier_id, created_at);
