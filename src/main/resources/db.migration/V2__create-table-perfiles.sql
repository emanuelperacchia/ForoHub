create table perfiles (
    id_perfil bigint not null auto_increment,
    nombre varchar(50) not null,
    primary key (id_perfil)
);

insert into perfiles (nombre) values ('profesor'), ('alumno'), ('administracion');