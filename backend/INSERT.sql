-- kategorie
INSERT INTO categories (name, icon_url, parent_id) VALUES ('Laptopy', NULL, NULL)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (name, icon_url, parent_id) VALUES ('Procesory', NULL, NULL)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (name, icon_url, parent_id) VALUES ('Karty graficzne', NULL, NULL)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (name, icon_url, parent_id) VALUES ('RAM', NULL, NULL)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (name, icon_url, parent_id) VALUES ('Płyty główne', NULL, NULL)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (name, icon_url, parent_id) VALUES ('Zasilacze', NULL, NULL)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (name, icon_url, parent_id) VALUES ('Chłodzenia', NULL, NULL)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (name, icon_url, parent_id) VALUES ('Gotowe stacje', NULL, NULL)
    ON CONFLICT (name) DO NOTHING;


-- produkty


INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Laptop gamingowy 15,6 cala z mocną grafiką i szybkim SSD.', NULL, 'ASUS ROG Strix G16', 649999, 5, id
FROM categories WHERE name = 'Laptopy';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Lekki laptop do pracy, nauki i codziennego użytku.', NULL, 'Lenovo IdeaPad Slim 5', 389999, 8, id
FROM categories WHERE name = 'Laptopy';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Ultrabook z ekranem OLED i długim czasem pracy na baterii.', NULL, 'ASUS Zenbook 14 OLED', 529999, 4, id
FROM categories WHERE name = 'Laptopy';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Procesor do gier i pracy wielowątkowej, bardzo opłacalny.', NULL, 'AMD Ryzen 7 7800X3D', 179999, 10, id
FROM categories WHERE name = 'Procesory';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Nowoczesny procesor Intel do komputera domowego i gamingowego.', NULL, 'Intel Core i7-14700K', 199999, 7, id
FROM categories WHERE name = 'Procesory';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Budżetowy procesor do codziennego komputera i pracy biurowej.', NULL, 'AMD Ryzen 5 5600', 51999, 12, id
FROM categories WHERE name = 'Procesory';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Karta graficzna do grania w 1440p z DLSS i Ray Tracingiem.', NULL, 'NVIDIA GeForce RTX 4070 Super', 289999, 6, id
FROM categories WHERE name = 'Karty graficzne';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Wydajna karta AMD do gier w wysokiej rozdzielczości.', NULL, 'AMD Radeon RX 7800 XT', 239999, 5, id
FROM categories WHERE name = 'Karty graficzne';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Karta graficzna do Full HD i e-sportu.', NULL, 'NVIDIA GeForce RTX 4060', 149999, 9, id
FROM categories WHERE name = 'Karty graficzne';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Pamięć DDR5 32 GB o wysokim taktowaniu do nowych platform.', NULL, 'Kingston Fury Beast 32GB DDR5 6000', 48999, 15, id
FROM categories WHERE name = 'RAM';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Zestaw pamięci DDR4 do komputerów gamingowych i biurowych.', NULL, 'Corsair Vengeance LPX 16GB DDR4 3200', 17999, 18, id
FROM categories WHERE name = 'RAM';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Pamięć DDR5 64 GB do pracy wielozadaniowej i stacji roboczej.', NULL, 'G.Skill Trident Z5 64GB DDR5 6400', 89999, 6, id
FROM categories WHERE name = 'RAM';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Płyta główna ATX pod procesory AMD AM5 z Wi-Fi.', NULL, 'MSI B650 Tomahawk WiFi', 89999, 7, id
FROM categories WHERE name = 'Płyty główne';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Płyta główna pod Intel z mocną sekcją zasilania i DDR5.', NULL, 'ASUS TUF Gaming Z790-Plus WiFi', 119999, 4, id
FROM categories WHERE name = 'Płyty główne';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Tańsza płyta micro-ATX do komputera domowego.', NULL, 'Gigabyte B550M DS3H', 42999, 11, id
FROM categories WHERE name = 'Płyty główne';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Modularny zasilacz 850W z certyfikatem 80 Plus Gold.', NULL, 'be quiet! Pure Power 12 M 850W', 56999, 9, id
FROM categories WHERE name = 'Zasilacze';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Zasilacz 750W do nowoczesnych zestawów gamingowych.', NULL, 'Corsair RM750e', 44999, 10, id
FROM categories WHERE name = 'Zasilacze';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Budżetowy zasilacz 600W do komputera domowego.', NULL, 'SilentiumPC Vero L3 600W', 22999, 14, id
FROM categories WHERE name = 'Zasilacze';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Wydajne chłodzenie powietrzne do mocnych procesorów.', NULL, 'Noctua NH-D15', 49999, 5, id
FROM categories WHERE name = 'Chłodzenia';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Chłodzenie wodne AIO 240 mm z podświetleniem RGB.', NULL, 'NZXT Kraken 240 RGB', 67999, 6, id
FROM categories WHERE name = 'Chłodzenia';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Popularne chłodzenie wieżowe do procesorów AMD i Intel.', NULL, 'Endorfy Fortis 5', 17999, 13, id
FROM categories WHERE name = 'Chłodzenia';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Gotowy zestaw gamingowy do grania w Full HD i 1440p.', NULL, 'Komputornix G1', 459999, 3, id
FROM categories WHERE name = 'Gotowe stacje';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Gotowa stacja robocza do projektów, montażu i programowania.', NULL, 'Komputornix Work Pro', 699999, 2, id
FROM categories WHERE name = 'Gotowe stacje';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Kompaktowy komputer do biura i nauki.', NULL, 'Komputornix Office Mini', 269999, 4, id
FROM categories WHERE name = 'Gotowe stacje';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Laptop do codziennej pracy, nauki i internetu.', NULL, 'HP Pavilion 15', 319999, 7, id
FROM categories WHERE name = 'Laptopy';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Laptop premium z bardzo dobrym ekranem i wydajnością.', NULL, 'Dell XPS 15', 849999, 3, id
FROM categories WHERE name = 'Laptopy';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Laptop dla graczy z mocnym chłodzeniem i szybkim ekranem.', NULL, 'Acer Predator Helios Neo', 579999, 4, id
FROM categories WHERE name = 'Laptopy';


INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Nowoczesny procesor Intel do wymagających zastosowań.', NULL, 'Intel Core i5-14600K', 129999, 9, id
FROM categories WHERE name = 'Procesory';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Wydajny procesor AMD do gamingu i pracy.', NULL, 'AMD Ryzen 9 7900X', 189999, 5, id
FROM categories WHERE name = 'Procesory';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Budżetowy procesor Intel do prostszych zestawów.', NULL, 'Intel Core i3-14100F', 48999, 13, id
FROM categories WHERE name = 'Procesory';


INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Bardzo mocna karta do grania w 4K.', NULL, 'NVIDIA GeForce RTX 4080 Super', 489999, 2, id
FROM categories WHERE name = 'Karty graficzne';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Karta AMD do wydajnego grania w 1440p.', NULL, 'AMD Radeon RX 7700 XT', 199999, 6, id
FROM categories WHERE name = 'Karty graficzne';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Budżetowa karta do e-sportu i Full HD.', NULL, 'NVIDIA GeForce RTX 3050', 99999, 8, id
FROM categories WHERE name = 'Karty graficzne';


INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Pamięć DDR4 do komputerów gamingowych.', NULL, 'Patriot Viper Steel 16GB DDR4 3600', 19999, 16, id
FROM categories WHERE name = 'RAM';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Szybka pamięć DDR5 do nowych platform.', NULL, 'Corsair Dominator 32GB DDR5 6200', 62999, 7, id
FROM categories WHERE name = 'RAM';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Duży zestaw RAM do pracy i renderingu.', NULL, 'Kingston Fury Renegade 64GB DDR5 6000', 94999, 4, id
FROM categories WHERE name = 'RAM';


INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Płyta główna pod Intel z chipsetem B760.', NULL, 'MSI PRO B760-P WiFi', 69999, 8, id
FROM categories WHERE name = 'Płyty główne';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Nowoczesna płyta AM5 do Ryzenów.', NULL, 'Gigabyte B650 Aorus Elite AX', 92999, 5, id
FROM categories WHERE name = 'Płyty główne';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Tania płyta główna micro-ATX do domowego PC.', NULL, 'ASRock H610M-HDV', 33999, 10, id
FROM categories WHERE name = 'Płyty główne';


INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Zasilacz 1000W do bardzo mocnych zestawów.', NULL, 'Corsair RM1000x', 74999, 4, id
FROM categories WHERE name = 'Zasilacze';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Zasilacz 650W do średniej klasy komputera.', NULL, 'Seasonic G12 GC 650W', 29999, 9, id
FROM categories WHERE name = 'Zasilacze';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Nowoczesny modularny zasilacz do gamingu.', NULL, 'MSI MPG A850G PCIe5', 61999, 6, id
FROM categories WHERE name = 'Zasilacze';


INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Chłodzenie AIO 360 mm do topowych procesorów.', NULL, 'Corsair iCUE H150i Elite', 89999, 3, id
FROM categories WHERE name = 'Chłodzenia';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Ciche chłodzenie powietrzne do komputera domowego.', NULL, 'be quiet! Pure Rock 2', 14999, 11, id
FROM categories WHERE name = 'Chłodzenia';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Wydajne chłodzenie dla procesorów gamingowych.', NULL, 'Deepcool AK620', 24999, 8, id
FROM categories WHERE name = 'Chłodzenia';


INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Gotowy komputer gamingowy do Full HD.', NULL, 'Komputornix Starter G2', 329999, 5, id
FROM categories WHERE name = 'Gotowe stacje';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Wydajny zestaw do pracy kreatywnej i montażu.', NULL, 'Komputornix Creator X', 589999, 2, id
FROM categories WHERE name = 'Gotowe stacje';

INSERT INTO products (active, description, image_url, name, price_in_grosze, stock_quantity, category_id)
SELECT true, 'Kompaktowy komputer do biura i domu.', NULL, 'Komputornix Office Plus', 239999, 6, id
FROM categories WHERE name = 'Gotowe stacje';

-- dodatkowe parametry produktów

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Procesor', 'Intel Core i7-13650HX', id FROM products WHERE name = 'ASUS ROG Strix G16';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'RAM', '16 GB DDR5', id FROM products WHERE name = 'ASUS ROG Strix G16';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Dysk', '1 TB SSD', id FROM products WHERE name = 'ASUS ROG Strix G16';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Ekran', '15.6 165 Hz', id FROM products WHERE name = 'ASUS ROG Strix G16';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Rdzenie / wątki', '8 / 16', id FROM products WHERE name = 'AMD Ryzen 7 7800X3D';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Taktowanie', '4.2 GHz', id FROM products WHERE name = 'AMD Ryzen 7 7800X3D';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Socket', 'AM5', id FROM products WHERE name = 'AMD Ryzen 7 7800X3D';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Pamięć', '12 GB GDDR6X', id FROM products WHERE name = 'NVIDIA GeForce RTX 4070 Super';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Interfejs', 'PCIe 4.0', id FROM products WHERE name = 'NVIDIA GeForce RTX 4070 Super';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Ray Tracing', 'Tak', id FROM products WHERE name = 'NVIDIA GeForce RTX 4070 Super';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Pojemność', '32 GB', id FROM products WHERE name = 'Kingston Fury Beast 32GB DDR5 6000';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Taktowanie', '6000 MHz', id FROM products WHERE name = 'Kingston Fury Beast 32GB DDR5 6000';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Typ', 'DDR5', id FROM products WHERE name = 'Kingston Fury Beast 32GB DDR5 6000';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Chipset', 'B650', id FROM products WHERE name = 'MSI B650 Tomahawk WiFi';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Format', 'ATX', id FROM products WHERE name = 'MSI B650 Tomahawk WiFi';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Wi-Fi', 'Tak', id FROM products WHERE name = 'MSI B650 Tomahawk WiFi';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Moc', '850W', id FROM products WHERE name = 'be quiet! Pure Power 12 M 850W';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Certyfikat', '80 Plus Gold', id FROM products WHERE name = 'be quiet! Pure Power 12 M 850W';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Modularny', 'Tak', id FROM products WHERE name = 'be quiet! Pure Power 12 M 850W';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Typ', 'Powietrzne', id FROM products WHERE name = 'Noctua NH-D15';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Wentylatory', '2 x 140 mm', id FROM products WHERE name = 'Noctua NH-D15';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Kompatybilność', 'AMD / Intel', id FROM products WHERE name = 'Noctua NH-D15';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Procesor', 'Ryzen 7', id FROM products WHERE name = 'Komputornix G1';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Grafika', 'RTX 4070', id FROM products WHERE name = 'Komputornix G1';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'RAM', '32 GB', id FROM products WHERE name = 'Komputornix G1';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Procesor', 'Intel Core i5', id FROM products WHERE name = 'HP Pavilion 15';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'RAM', '16 GB', id FROM products WHERE name = 'HP Pavilion 15';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Dysk', '512 GB SSD', id FROM products WHERE name = 'HP Pavilion 15';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Rdzenie / wątki', '14 / 20', id FROM products WHERE name = 'Intel Core i5-14600K';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Socket', 'LGA1700', id FROM products WHERE name = 'Intel Core i5-14600K';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Pamięć', '16 GB GDDR6X', id FROM products WHERE name = 'NVIDIA GeForce RTX 4080 Super';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Ray Tracing', 'Tak', id FROM products WHERE name = 'NVIDIA GeForce RTX 4080 Super';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Typ', 'DDR5', id FROM products WHERE name = 'Corsair Dominator 32GB DDR5 6200';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Taktowanie', '6200 MHz', id FROM products WHERE name = 'Corsair Dominator 32GB DDR5 6200';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Chipset', 'B760', id FROM products WHERE name = 'MSI PRO B760-P WiFi';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Format', 'ATX', id FROM products WHERE name = 'MSI PRO B760-P WiFi';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Moc', '1000W', id FROM products WHERE name = 'Corsair RM1000x';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'Certyfikat', '80 Plus Gold', id FROM products WHERE name = 'Corsair RM1000x';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Typ', 'AIO 360 mm', id FROM products WHERE name = 'Corsair iCUE H150i Elite';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'RGB', 'Tak', id FROM products WHERE name = 'Corsair iCUE H150i Elite';


INSERT INTO product_attributes (name, value, product_id)
SELECT 'Przeznaczenie', 'Gaming', id FROM products WHERE name = 'Komputornix Starter G2';

INSERT INTO product_attributes (name, value, product_id)
SELECT 'System', 'Windows 11', id FROM products WHERE name = 'Komputornix Starter G2';