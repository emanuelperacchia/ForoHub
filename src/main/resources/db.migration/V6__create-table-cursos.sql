create table cursos (
    id_curso bigint not null auto_increment,
    curso_nombre varchar(100) not null,
    categoria varchar(50) not null,
    primary key (id_curso)
);

insert into cursos (categoria) values ('programacion'), ('matematicas'), ('ciencias'),'abierto'), ('cerrado'), ('resuelto'), ('pendiente');