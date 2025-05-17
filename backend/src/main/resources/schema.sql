INSERT INTO sectors (id, name, level, parent_id)
SELECT 1, 'Manufacturing', 0, NULL
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 1);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 2, 'Service', 0, NULL
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 2);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 3, 'Other', 0, NULL
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 3);

-- Level 1 under Manufacturing (ID: 1)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 19, 'Construction materials', 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 19);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 18, 'Electronics and Optics', 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 18);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 6, 'Food and Beverage', 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 6);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 13, 'Furniture', 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 13);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 12, 'Machinery', 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 12);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 11, 'Metalworking', 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 11);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 9, 'Plastic and Rubber', 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 9);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 5, 'Printing', 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 5);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 7, 'Textile and Clothing', 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 7);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 8, 'Wood', 1, 1
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 8);

-- Level 2 under Food and Beverage (ID: 6)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 342, 'Bakery & confectionery products', 2, 6
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 342);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 43, 'Beverages', 2, 6
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 43);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 42, 'Fish & fish products', 2, 6
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 42);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 40, 'Meat & meat products', 2, 6
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 40);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 39, 'Milk & dairy products', 2, 6
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 39);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 437, 'Other', 2, 6
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 437);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 378, 'Sweets & snack food', 2, 6
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 378);

-- Level 2 under Furniture (ID: 13)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 389, 'Bathroom/sauna', 2, 13
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 389);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 385, 'Bedroom', 2, 13
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 385);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 390, 'Children''s room', 2, 13
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 390);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 98, 'Kitchen', 2, 13
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 98);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 101, 'Living room', 2, 13
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 101);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 392, 'Office', 2, 13
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 392);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 394, 'Other (Furniture)', 2, 13
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 394);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 341, 'Outdoor', 2, 13
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 341);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 99, 'Project furniture', 2, 13
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 99);

-- Level 2 under Machinery (ID: 12)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 94, 'Machinery components', 2, 12
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 94);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 91, 'Machinery equipment/tools', 2, 12
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 91);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 224, 'Manufacture of machinery', 2, 12
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 224);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 97, 'Maritime', 2, 12
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 97);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 93, 'Metal structures', 2, 12
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 93);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 508, 'Other', 2, 12
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 508);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 227, 'Repair and maintenance service', 2, 12
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 227);

-- Level 3 under Maritime (ID: 97)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 271, 'Aluminium and steel workboats', 3, 97
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 271);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 269, 'Boat/Yacht building', 3, 97
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 269);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 230, 'Ship repair and conversion', 3, 97
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 230);

-- Level 2 under Metalworking (ID: 11)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 67, 'Construction of metal structures', 2, 11
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 67);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 263, 'Houses and buildings', 2, 11
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 263);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 267, 'Metal products', 2, 11
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 267);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 542, 'Metal works', 2, 11
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 542);

-- Level 3 under Metal works (ID: 542)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 75, 'CNC-machining', 3, 542
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 75);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 62, 'Forgings, Fasteners', 3, 542
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 62);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 69, 'Gas, Plasma, Laser cutting', 3, 542
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 69);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 66, 'MIG, TIG, Aluminum welding', 3, 542
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 66);

-- Level 2 under Plastic and Rubber (ID: 9)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 54, 'Packaging', 2, 9
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 54);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 556, 'Plastic goods', 2, 9
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 556);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 559, 'Plastic processing technology', 2, 9
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 559);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 560, 'Plastic profiles', 2, 9
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 560);

-- Level 3 under Plastic processing technology (ID: 559)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 55, 'Blowing', 3, 559
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 55);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 57, 'Moulding', 3, 559
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 57);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 53, 'Plastics welding and processing', 3, 559
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 53);

-- Level 2 under Printing (ID: 5)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 148, 'Advertising', 2, 5
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 148);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 150, 'Book/Periodicals printing', 2, 5
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 150);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 145, 'Labelling and packaging printing', 2, 5
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 145);

-- Level 2 under Textile and Clothing (ID: 7)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 44, 'Clothing', 2, 7
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 44);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 45, 'Textile', 2, 7
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 45);

-- Level 2 under Wood (ID: 8)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 337, 'Other (Wood)', 2, 8
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 337);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 51, 'Wooden building materials', 2, 8
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 51);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 47, 'Wooden houses', 2, 8
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 47);

-- Level 1 under Other (ID: 3)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 37, 'Creative industries', 1, 3
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 37);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 29, 'Energy technology', 1, 3
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 29);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 33, 'Environment', 1, 3
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 33);

-- Level 1 under Service (ID: 2)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 25, 'Business services', 1, 2
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 25);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 35, 'Engineering', 1, 2
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 35);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 28, 'Information Technology and Telecommunications', 1, 2
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 28);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 22, 'Tourism', 1, 2
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 22);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 141, 'Translation services', 1, 2
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 141);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 21, 'Transport and Logistics', 1, 2
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 21);

-- Level 2 under Information Technology and Telecommunications (ID: 28)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 581, 'Data processing, Web portals, E-marketing', 2, 28
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 581);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 576, 'Programming, Consultancy', 2, 28
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 576);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 121, 'Software, Hardware', 2, 28
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 121);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 122, 'Telecommunications', 2, 28
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 122);

-- Level 2 under Transport and Logistics (ID: 21)
INSERT INTO sectors (id, name, level, parent_id)
SELECT 111, 'Air', 2, 21
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 111);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 114, 'Rail', 2, 21
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 114);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 112, 'Road', 2, 21
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 112);

INSERT INTO sectors (id, name, level, parent_id)
SELECT 113, 'Water', 2, 21
    WHERE NOT EXISTS (SELECT 1 FROM sectors WHERE id = 113);