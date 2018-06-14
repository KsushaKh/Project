create table buyer (
id SERIAL PRIMARY KEY,
name VARCHAR(50),
surname VARCHAR(50),
login_buyer VARCHAR(30),
password_buyer VARCHAR(15),
address_buyer VARCHAR(200),
mail VARCHAR(80),
mobile INTEGER
);

create table category (
id SERIAL PRIMARY KEY,
name_category VARCHAR(70)
);

create table product (
id SERIAL PRIMARY KEY,
category_id INTEGER REFERENCES category(id),
name_product VARCHAR(30),
article INTEGER,
material VARCHAR(300),
color VARCHAR(30),
size VARCHAR(30),
availability_product VARCHAR(20),
price NUMERIC
);

create table payment (
id SERIAL PRIMARY KEY,
name_payment VARCHAR(200),
status_payment VARCHAR(200)
);

create table delivery (
id SERIAL PRIMARY KEY,
name_delivery VARCHAR(200),
price_delivery NUMERIC
);

create table user_order (
id SERIAL PRIMARY KEY,
product_id INTEGER REFERENCES product(id),
buyer_id INTEGER REFERENCES buyer(id),
amount INTEGER,
price_product NUMERIC,
order_price NUMERIC,
delivery_id INTEGER REFERENCES delivery(id),
payment_id INTEGER REFERENCES payment(id),
status_order VARCHAR(200)
);

