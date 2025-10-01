INSERT INTO patio (nome) VALUES
  ('Pátio Centro'),
  ('Pátio Norte'),
  ('Pátio Sul'),
  ('Pátio Leste'),
  ('Pátio Oeste'),
  ('Pátio Industrial'),
  ('Pátio Logística 1'),
  ('Pátio Logística 2'),
  ('Pátio Aeroporto'),
  ('Pátio Porto');

INSERT INTO iot (ativo, bateria, coordenada_x, coordenada_y) VALUES
  (TRUE,  95, 'X-23.5505', 'Y-46.6333'),
  (TRUE,  72, 'X-23.5610', 'Y-46.6561'),
  (FALSE, 40, 'X-23.5702', 'Y-46.6500'),
  (TRUE,  88, 'X-23.5599', 'Y-46.6401'),
  (FALSE, 15, 'X-23.5750', 'Y-46.6200'),
  (TRUE,  63, 'X-23.5601', 'Y-46.6302'),
  (TRUE, 100, 'X-23.5900', 'Y-46.6100'),
  (FALSE,  5, 'X-23.6000', 'Y-46.7000'),
  (TRUE,  54, 'X-23.5800', 'Y-46.6900'),
  (TRUE,  NULL, 'X-23.5650', 'Y-46.6800');


INSERT INTO moto (modelo, placa, data_entrada, data_saida) VALUES
  ('Honda CG 160',       'ABC1D23', '2025-08-01 09:00:00', '2025-08-06 17:30:00'),
  ('Yamaha Fazer 250',   'BCD2E34', '2025-08-03 10:15:00', '2025-08-13 16:45:00'),
  ('Honda Biz 125',      'CDE3F45', '2025-08-05 08:30:00', '2025-08-09 14:20:00'),
  ('Yamaha NMax 160',    'DEF4G56', '2025-08-07 11:00:00', NULL),
  ('Honda PCX 160',      'EFG5H67', '2025-08-09 13:10:00', '2025-08-20 18:00:00'),
  ('Yamaha MT-03',       'FGH6I78', '2025-08-12 09:40:00', NULL),
  ('Honda CB 500F',      'GHI7J89', '2025-08-15 07:55:00', '2025-08-24 19:30:00'),
  ('Yamaha Lander 250',  'HIJ8K90', '2025-08-18 12:25:00', NULL),
  ('Honda XRE 300',      'IJK9L01', '2025-08-22 10:05:00', NULL),
  ('Yamaha Crosser 150', 'JKL0M12', '2025-08-26 15:45:00', NULL);

