
create database TPDISTRIBUIDAS
go

use TPDISTRIBUIDAS
go

--TABLES

create table Partidos(

	id_partido int identity not null,
	pareja_ganadora int,
	fecha_partido datetime,
	tipo_partido tinyint,   --esto es un enum en java
	estado_partido tinyint, --esto es un enum en java
	id_grupo int null, ----el id grupo indica el partido que se jugo en un grupo, es nulo si el partido no corresponde a un grupo

	
	constraint pk_partidos primary key (id_partido)
)






create table Chicos(
	
	id_chico int identity not null,
	nro_chico int,
	id_partido int not null,
	puntaje_maximo int,

	constraint pk_chicos primary key (id_chico)
	)



create table Parejas(

	id_pareja int identity not null,
	id_partido int null,
	id_jugador1 int,
	id_jugador2 int,
	id_grupo int null, --este atributo puede ser nulo, cuando la pareja no fue armada dentro de un grupo

	constraint pk_parejas primary key (id_pareja)
)



create table Manos(

	id_mano int identity not null,
	nro_mano int,
	id_chico int,
	

	constraint pk_Manos primary key (id_mano)
	)


create table PuntajesPareja(

	id_puntaje int identity not null,
	id_pareja int not null,
	puntaje int,

	constraint pk_puntajesPareja primary key (id_puntaje)
)
	


create table Bazas(

	id_baza int identity not null,
	nro_baza int,
	id_mano int,
	id_ganador int,
	

	------orden juego??? no se persiste ya que eso se necesita solo para jugar
	------nos vamos a dar cuenta quien arranco jugando porque va tener el id movimiento 0

	constraint pk_bazas primary key (id_baza)
)



--definimos crear la herencia como una tabla unica por tener pocos registros


create table Movimientos( 

	id_movimiento int identity not null,
	nro_turno int,
	tipo_movimiento tinyint, --el 0 es carta tirada, 1 es envite
	fecha_hora timestamp,
	id_cartaJugador int,
	id_baza int,

	constraint pk_movimientos primary key (id_movimiento)
)



create table CartasJugador(

	id_cartaJugador int identity not null,
	id_jugador int not null,
	id_carta int not null,
	id_mano int not null,
	tirada bit,

	
	constraint pk_cartas_jugador primary key (id_cartaJugador)
)



create table Cartas (

	id_carta int,
	palo tinyint,
	numero int,
	posicion_valor int,  
	
	/* posicion_valor guardara la importancia que tiene la carta de mayor (posicion 1) a menor (ultima), si una carta tiene el mismo valor que otra, tendra misma posicion
	   ejemplo los 3 van a tener mismo posicion_valor
	   el ancho de espada tiene posicion 1 
	   ancho de basto pos 2
	   7 de espada posicion 3
	   7 de oro pos 4
	   los 3 posicion 5
	   los 2 posicion 6
	   1 de oro y copa pos 7 
	   */

	constraint pk_cartas primary key (id_carta)
)


create table Jugadores (

	id_jugador int identity not null,
	apodo varchar(50),
	mail varchar(50),
	clave varchar(50),
	categoria tinyint,
	
	constraint pk_jugadores primary key (id_jugador)

)

create table Rankings (

	id_ranking int identity not null,
	id_jugador int,
	puntos int,
	cant_ganadas int,

	constraint pk_rankings primary key (id_ranking)
	)



create table Ranking_Partido (

	id_partido int,
	id_ranking int,

	constraint pk_ranking_partido primary key (id_partido,id_ranking)

)



create table Grupo_Jugador (

	id_grupo int,
	id_jugador int,

	constraint pk_grupo_jugador primary key (id_grupo, id_jugador)
	)




create table Pareja_Jugador (

	id_jugador int,
	id_pareja int,


	constraint pk_pareja_jugador primary key (id_jugador, id_pareja)
)

create table Grupos (

	id_grupo int identity not null,
	nombre varchar,
	
	constraint pk_grupos primary key (id_grupo)
	)



create table Miembros_Grupo (

	id_miembro int identity not null,
	id_jugador int,
	activo bit,
	id_ranking int,
	tipo_Miembro tinyint,

	constraint pk_miembros_grupos primary key (id_miembro)
)

alter table Miembros_Grupo add constraint fk_miembros_grupo_jugador foreign key(id_jugador) references Jugadores (id_jugador)
alter table Miembros_Grupo add constraint fk_miembros_grupo_ranking foreign key (id_ranking) references Rankings (id_ranking)
alter table Partidos add constraint fk_partidos_grupo foreign key (id_grupo) references Grupos (id_grupo)
alter table Chicos add constraint fk_chico_partido foreign key(id_partido) references Partidos (id_partido)
alter table Parejas add constraint fk_parejas_jugador1 foreign key(id_jugador1) references Jugadores (id_jugador)
alter table Parejas add constraint fk_parejas_jugador2 foreign key(id_jugador2) references Jugadores (id_jugador)
alter table Manos add constraint fk_manos_chico foreign key(id_chico) references Chicos (id_chico)
alter table PuntajesPareja add constraint fk_puntajesParejas_pareja foreign key(id_pareja) references Parejas (id_pareja)
alter table Bazas add constraint fk_bazas_mano foreign key(id_mano) references Manos (id_mano)
alter table Bazas add constraint fk_bazas_ganador foreign key(id_ganador) references Jugadores (id_jugador)
alter table Movimientos add constraint fk_movimientos_cartajugador foreign key(id_cartaJugador) references CartasJugador (id_cartaJugador)
alter table Movimientos add constraint fk_movimientos_baza foreign key (id_baza) references Bazas (id_baza) 
alter table CartasJugador add constraint fk_cartasJugador_jugador foreign key(id_jugador) references Jugadores (id_jugador)
alter table CartasJugador add constraint fk_cartasJugador_mano foreign key(id_mano) references Manos (id_mano)
alter table CartasJugador add constraint fk_cartasJugador_carta foreign key(id_carta) references Cartas (id_carta)
alter table Rankings add constraint fk_rankings_jugador foreign key(id_jugador) references Jugadores (id_jugador)
alter table Ranking_Partido add constraint fk_id_partido_partidos foreign key(id_partido) references Partidos (id_partido)
alter table Ranking_Partido add constraint fk_id_ranking_rankings foreign key(id_ranking) references Rankings (id_ranking)
alter table Grupo_Jugador add constraint fk_id_grupo_grupos foreign key(id_grupo) references Grupos (id_grupo)
alter table Grupo_Jugador add constraint fk_id_jugador_jugadores foreign key(id_jugador) references Jugadores (id_jugador)
alter table Pareja_Jugador add constraint fk_pareja_jugador_jugadores foreign key(id_jugador) references Jugadores (id_jugador)
alter table Pareja_Jugador add constraint fk_pareja_jugador_parejas foreign key(id_pareja) references Parejas (id_pareja)


