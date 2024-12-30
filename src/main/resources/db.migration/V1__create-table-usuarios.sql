create table usuarios (
    id_usuario bigint not null auto_increment,
    nombre_usuario varchar(100) not null,
    correo_electronico varchar(150) not null unique,
    contrasena varchar(255) not null,
    primary key (id_usuario)
);