CREATE TABLE clients (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    address VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE restaurants (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    address VARCHAR(200),
    phone_number VARCHAR(20),
    delivery_fee DECIMAL(10,2),
    rating DECIMAL(2,1),
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE products (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    price DECIMAL(10,2),
    category VARCHAR(50),
    available BOOLEAN DEFAULT TRUE,
    restaurant_id CHAR(36),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE orders (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    order_number VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_price DECIMAL(10,2),
    observation VARCHAR(200),
    client_id CHAR(36),
    restaurant_id CHAR(36),
    items VARCHAR(200),
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
