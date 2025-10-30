CREATE TABLE IF NOT EXISTS clients (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    address VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS restaurants (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    address VARCHAR(200),
    phone_number VARCHAR(20),
    delivery_fee DECIMAL(10,2),
    rating DECIMAL(2,1),
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS products (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    price DECIMAL(10,2),
    category VARCHAR(50),
    available BOOLEAN DEFAULT TRUE,
    restaurant_id UUID,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS orders (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    order_number VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_price DECIMAL(10,2),
    observation VARCHAR(200),
    client_id UUID,
    restaurant_id UUID,
    items VARCHAR(200),
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);