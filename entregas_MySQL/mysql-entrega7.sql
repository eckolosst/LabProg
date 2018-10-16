-- a - Cree una función para aplicar formato de salida a las solicitudes de préstamo. Donde se muestre “La solicitud de préstamo
-- ’id_solicitud‘ pedida el día ’fecha_de_peticion’ tuvo un resultado ‘resultado‘”. Para una solicitud que entra por parametro

-- MySQL
DELIMITER $$
CREATE FUNCTION solicitudes2018(id int) RETURNS varchar(150)
BEGIN
RETURN (SELECT concat('La solicitud de préstamo con id ',id_solicitud,' pedida el día ',fecha,' tuvo resultado ', resultado) FROM Solicitud_prestamo WHERE id_solicitud=id);
END;
$$
DELIMITER;

SELECT solicitudes2018();

-- b - Realice una función que calcule la diferencia en días entre dos fechas.
-- MySQL
DELIMITER $$
CREATE FUNCTION difereciaFechas(f1 date, f2 date) RETURNS int
BEGIN
RETURN (DATEDIFF(f1,f2));
END;
$$
DELIMITER;

SELECT difereciaFechas("2018-01-06",  "2018-01-01");

-- c - Utilice la función anterior para calcular la cantidad de días que pasaron entre la fecha de petición y la fecha de decisión de
-- cada solicitud de préstamo. De esta manera se quiere conocer cuánto, en promedio, se tarda en tomar una decisión.
-- MySQL
DELIMITER $$
CREATE FUNCTION promedioDemoraDecision() RETURNS float
BEGIN
RETURN (SELECT AVG(difereciaFechas(p.fecha,s.fecha)) FROM Solicitud_prestamo s JOIN Prestamo p ON s.id_solicitud=p.id_solicitud);
END;
$$
DELIMITER;

SELECT promedioDemoraDecision();

-- d - En Posgresql, cree una función Validez2, igual al inciso c, con la opción RETURN NULL ON NULL INPUT.  Utilice la función con
--algun parametro en Nulo. ¿Qué puede concluir?


-- e - Realice una función que, ingresando el tipodoc, nrodoc y la fecha de nacimiento de una persona, modifique la tupla de persona que
--concuerda con el tipodoc y nrodoc, y coloque la fecha de nacimiento ingresada, y a la vez y devuelva la edad de la persona en
--años, meses y días.
-- MySQL
DELIMITER $$
CREATE FUNCTION setFechaNacimiento(tipodoc VARCHAR(3), nrodoc VARCHAR(10), fecha date) RETURNS FLOAT
BEGIN
UPDATE Persona SET fecha_nacimiento=fecha WHERE tipo_doc=tipodoc AND nro_doc=nrodoc;
RETURN (SELECT difereciaFechas(NOW(),fecha) DIV 365);
END;
$$
DELIMITER;

SELECT setFechaNacimiento();

-- f - Realice dos funciones que liste en un registro (en una) y en una tabla (en otra), las fechas de fin de los préstamos vigentes (en
--un formato personalizado, ‘El día ‘ dd ‘ del mes ’ mm ‘ del año’) junto con el socio y el garante.
-- MySQL
DELIMITER $$
CREATE FUNCTION nombre() RETURN tipodedato
BEGIN

END;
$$

SELECT nombre();

-- MySQL
DELIMITER $$
CREATE FUNCTION nombre() RETURN tipodedato
BEGIN

END;
$$

SELECT nombre();
