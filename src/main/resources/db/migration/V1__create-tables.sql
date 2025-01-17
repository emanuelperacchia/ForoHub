CREATE TABLE usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(150) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    perfil ENUM('PROFESOR', 'ALUMNO', 'ADMINISTRACION') NOT NULL
);
CREATE TABLE cursos (
    id_curso BIGINT AUTO_INCREMENT PRIMARY KEY,
    curso_nombre VARCHAR(100) NOT NULL,
    categoria ENUM('PROGRAMACION', 'MATEMATICAS', 'CIENCIAS', 'HISTORIA') NOT NULL
);
CREATE TABLE topicos (
    id_topico BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo_topico VARCHAR(200) NOT NULL,
    mensaje_topico TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ABIERTO', 'CERRADO', 'RESUELTO', 'PENDIENTE') NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (curso_id) REFERENCES cursos(id_curso)
);
CREATE TABLE respuestas (
    id_respuesta BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensaje_respuesta TEXT NOT NULL,
    topico_id BIGINT NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    autor_id BIGINT NOT NULL,
    solucion BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (topico_id) REFERENCES topicos(id_topico),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id_usuario)
);