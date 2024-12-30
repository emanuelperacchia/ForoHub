create table topicos (
    id_topico bigint not null auto_increment,
    titulo_topico varchar(200) not null,
    mensaje_topico text not null,
    fecha_creacion datetime not null,
    status varchar(50) not null,
    autor_id bigint not null,
    curso_id bigint not null,
    primary key (id_topico),
    constraint fk_autor_topico foreign key (autor_id) references usuarios(id_usuario) on delete cascade,
    constraint fk_curso foreign key (curso_id) references cursos(id_curso) on delete cascade
);

insert into topicos (status) values ('abierto'), ('cerrado'), ('resuelto'), ('pendiente');