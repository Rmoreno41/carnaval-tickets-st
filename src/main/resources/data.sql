INSERT INTO localidad (id, nombre, capacidad, vendidas,version) VALUES
  ('palco-6ene',    'Palco Desfile Magno 6 de enero',        100, 0, 0),
  ('tribuna-4ene',  'Tribuna Carnavalito 4 de enero',        300, 0, 0),
  ('graderia-5ene', 'Gradería Canto a la Tierra 5 de enero', 500, 0, 0)
ON CONFLICT (id) DO NOTHING;