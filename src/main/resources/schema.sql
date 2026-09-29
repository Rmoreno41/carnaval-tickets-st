DROP TABLE IF EXISTS pedido;
DROP TABLE IF EXISTS localidad;

CREATE TABLE localidad (
  id        VARCHAR(40) PRIMARY KEY,
  nombre    VARCHAR(120) NOT NULL,
  capacidad INT NOT NULL,
  vendidas  INT NOT NULL DEFAULT 0,
  precio    BIGINT NOT NULL
);

CREATE TABLE pedido (
  id           VARCHAR(40) PRIMARY KEY,
  documento    VARCHAR(20) NOT NULL,
  localidad_id VARCHAR(40) NOT NULL REFERENCES localidad(id),
  cantidad     INT NOT NULL,
  total        BIGINT NOT NULL,
  estado       VARCHAR(20) NOT NULL,
  creado_en    TIMESTAMP NOT NULL
);