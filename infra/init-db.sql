-- Initialisation de la base de données TravelSignals
-- Création des tables et insertion de données de test

-- Table des destinations
CREATE TABLE IF NOT EXISTS destinations (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    country VARCHAR(100) NOT NULL,
    timezone VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des fournisseurs
CREATE TABLE IF NOT EXISTS providers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL, -- AIRLINE, HOTEL, ACTIVITY
    website VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des événements de prix
CREATE TABLE IF NOT EXISTS price_events (
    id SERIAL PRIMARY KEY,
    destination_id INTEGER REFERENCES destinations(id),
    provider_id INTEGER REFERENCES providers(id),
    old_price DECIMAL(10,2) NOT NULL,
    new_price DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'EUR',
    event_timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des alertes météo
CREATE TABLE IF NOT EXISTS weather_events (
    id SERIAL PRIMARY KEY,
    destination_id INTEGER REFERENCES destinations(id),
    level VARCHAR(10) NOT NULL CHECK (level IN ('GREEN', 'YELLOW', 'RED')),
    message TEXT NOT NULL,
    event_timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des statuts de vol
CREATE TABLE IF NOT EXISTS flight_events (
    id SERIAL PRIMARY KEY,
    flight_number VARCHAR(20) NOT NULL,
    departure_airport VARCHAR(10) NOT NULL,
    arrival_airport VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('ON_TIME', 'DELAYED', 'CANCELLED')),
    event_timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des rappels visa
CREATE TABLE IF NOT EXISTS visa_events (
    id SERIAL PRIMARY KEY,
    country VARCHAR(100) NOT NULL,
    days_before INTEGER NOT NULL,
    message TEXT NOT NULL,
    event_timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des alertes agrégées
CREATE TABLE IF NOT EXISTS alert_aggregates (
    id SERIAL PRIMARY KEY,
    type VARCHAR(20) NOT NULL CHECK (type IN ('PRICE', 'WEATHER', 'FLIGHT', 'VISA')),
    destination VARCHAR(100) NOT NULL,
    priority VARCHAR(5) NOT NULL CHECK (priority IN ('P1', 'P2', 'P3')),
    title VARCHAR(255) NOT NULL,
    details JSONB,
    event_timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertion des données de test

-- Destinations
INSERT INTO destinations (name, country, timezone) VALUES
('Paris', 'France', 'Europe/Paris'),
('Londres', 'Royaume-Uni', 'Europe/London'),
('New York', 'États-Unis', 'America/New_York'),
('Tokyo', 'Japon', 'Asia/Tokyo'),
('Dubai', 'Émirats arabes unis', 'Asia/Dubai'),
('Bangkok', 'Thaïlande', 'Asia/Bangkok'),
('Sydney', 'Australie', 'Australia/Sydney'),
('Rome', 'Italie', 'Europe/Rome'),
('Barcelone', 'Espagne', 'Europe/Madrid'),
('Amsterdam', 'Pays-Bas', 'Europe/Amsterdam')
ON CONFLICT (name) DO NOTHING;

-- Fournisseurs
INSERT INTO providers (name, type, website) VALUES
('Air France', 'AIRLINE', 'https://www.airfrance.fr'),
('Lufthansa', 'AIRLINE', 'https://www.lufthansa.com'),
('Emirates', 'AIRLINE', 'https://www.emirates.com'),
('Singapore Airlines', 'AIRLINE', 'https://www.singaporeair.com'),
('British Airways', 'AIRLINE', 'https://www.britishairways.com'),
('Booking.com', 'HOTEL', 'https://www.booking.com'),
('Expedia', 'HOTEL', 'https://www.expedia.com'),
('Airbnb', 'HOTEL', 'https://www.airbnb.com'),
('GetYourGuide', 'ACTIVITY', 'https://www.getyourguide.com'),
('Viator', 'ACTIVITY', 'https://www.viator.com')
ON CONFLICT (name) DO NOTHING;

-- Événements de prix de test
INSERT INTO price_events (destination_id, provider_id, old_price, new_price, currency, event_timestamp) VALUES
(1, 1, 800.00, 650.00, 'EUR', NOW() - INTERVAL '1 hour'),
(2, 2, 1200.00, 900.00, 'EUR', NOW() - INTERVAL '2 hours'),
(3, 3, 1500.00, 1800.00, 'EUR', NOW() - INTERVAL '30 minutes'),
(4, 4, 2000.00, 1600.00, 'EUR', NOW() - INTERVAL '45 minutes'),
(5, 5, 900.00, 750.00, 'EUR', NOW() - INTERVAL '1 hour 30 minutes')
ON CONFLICT DO NOTHING;

-- Alertes météo de test
INSERT INTO weather_events (destination_id, level, message, event_timestamp) VALUES
(1, 'RED', 'Tempête violente avec vents de 120 km/h', NOW() - INTERVAL '1 hour'),
(2, 'YELLOW', 'Pluie modérée prévue', NOW() - INTERVAL '2 hours'),
(3, 'GREEN', 'Temps ensoleillé', NOW() - INTERVAL '30 minutes'),
(4, 'YELLOW', 'Alerte typhon niveau 2', NOW() - INTERVAL '45 minutes'),
(5, 'GREEN', 'Temps clair et chaud', NOW() - INTERVAL '1 hour 30 minutes')
ON CONFLICT DO NOTHING;

-- Statuts de vol de test
INSERT INTO flight_events (flight_number, departure_airport, arrival_airport, status, event_timestamp) VALUES
('AF123', 'CDG', 'JFK', 'DELAYED', NOW() - INTERVAL '1 hour'),
('LH456', 'FRA', 'LAX', 'CANCELLED', NOW() - INTERVAL '2 hours'),
('EK789', 'DXB', 'SIN', 'ON_TIME', NOW() - INTERVAL '30 minutes'),
('SQ321', 'SIN', 'NRT', 'DELAYED', NOW() - INTERVAL '45 minutes'),
('BA654', 'LHR', 'SYD', 'ON_TIME', NOW() - INTERVAL '1 hour 30 minutes')
ON CONFLICT DO NOTHING;

-- Rappels visa de test
INSERT INTO visa_events (country, days_before, message, event_timestamp) VALUES
('États-Unis', 7, 'N''oubliez pas de faire votre ESTA', NOW() - INTERVAL '1 hour'),
('Japon', 14, 'Vérifiez les exigences de visa', NOW() - INTERVAL '2 hours'),
('Australie', 30, 'Rappel visa eVisitor', NOW() - INTERVAL '30 minutes'),
('Chine', 21, 'Demande de visa touristique requise', NOW() - INTERVAL '45 minutes'),
('Inde', 10, 'Visa électronique e-Visa', NOW() - INTERVAL '1 hour 30 minutes')
ON CONFLICT DO NOTHING;

-- Alertes agrégées de test
INSERT INTO alert_aggregates (type, destination, priority, title, details, event_timestamp) VALUES
('PRICE', 'Paris', 'P2', 'Baisse de prix significative à Paris', '{"provider": "Air France", "oldPrice": 800.0, "newPrice": 650.0, "currency": "EUR", "changePercentage": -18.75}', NOW() - INTERVAL '1 hour'),
('WEATHER', 'Paris', 'P1', 'Alerte météo rouge à Paris', '{"level": "RED", "message": "Tempête violente avec vents de 120 km/h"}', NOW() - INTERVAL '1 hour'),
('FLIGHT', 'JFK', 'P3', 'Changement de statut vol AF123', '{"flightNo": "AF123", "status": "DELAYED", "departure": "CDG", "arrival": "JFK"}', NOW() - INTERVAL '1 hour'),
('VISA', 'États-Unis', 'P3', 'Rappel visa États-Unis dans 7 jours', '{"country": "États-Unis", "daysBefore": 7, "message": "N''oubliez pas de faire votre ESTA"}', NOW() - INTERVAL '1 hour'),
('PRICE', 'Londres', 'P2', 'Baisse de prix significative à Londres', '{"provider": "Lufthansa", "oldPrice": 1200.0, "newPrice": 900.0, "currency": "EUR", "changePercentage": -25.0}', NOW() - INTERVAL '2 hours')
ON CONFLICT DO NOTHING;
