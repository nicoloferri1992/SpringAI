INSERT INTO dog (name, owner, description)
SELECT 'Buddy', NULL, 'A friendly golden retriever, 3 years old, loves fetch and cuddles. Great with kids and very sociable.'
WHERE NOT EXISTS (SELECT 1 FROM dog WHERE name = 'Buddy');

INSERT INTO dog (name, owner, description)
SELECT 'Luna', NULL, 'A playful border collie mix, 2 years old, very intelligent and active. Needs a yard and regular exercise.'
WHERE NOT EXISTS (SELECT 1 FROM dog WHERE name = 'Luna');

INSERT INTO dog (name, owner, description)
SELECT 'Max', NULL, 'A calm labrador, 5 years old, great with children and other pets. Already trained, loves water.'
WHERE NOT EXISTS (SELECT 1 FROM dog WHERE name = 'Max');

INSERT INTO dog (name, owner, description)
SELECT 'Bella', NULL, 'A sweet beagle, 1 year old, loves to sniff and explore. Full of energy and curiosity.'
WHERE NOT EXISTS (SELECT 1 FROM dog WHERE name = 'Bella');

INSERT INTO dog (name, owner, description)
SELECT 'Charlie', NULL, 'An energetic poodle mix, 4 years old, very sociable and well-trained. Good for apartment living.'
WHERE NOT EXISTS (SELECT 1 FROM dog WHERE name = 'Charlie');
