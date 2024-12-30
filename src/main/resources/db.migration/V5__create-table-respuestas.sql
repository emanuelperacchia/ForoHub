create table respuestas (
    id_respuesta bigint not null auto_increment,
    mensaje_respuesta text not null,
    topico_id bigint not null,
    fecha_creacion datetime not null,
    autor_id bigint not null,
    solucion boolean not null default false,
    primary key (id_respuesta),
    constraint fk_topico_respuesta foreign key (topico_id) references topicos(id_topico) on delete cascade,
   constraint fk_autor_respuesta foreign key (autor_id)  references usuarios(id_usuario) on delete cascade
);topicos (status) values ('abierto'), ('cerrado'), ('resuelto'), ('pendiente');