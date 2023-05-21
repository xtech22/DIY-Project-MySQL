DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS project;


create table project (
project_id int auto_increment not null,
project_name varchar(128) not null,
estimated_hours decimal(7,2),
actual_hours decimal(7,2),
difficulty int,
notes text,
primary key (project_id)
);

create table material (
material_id int auto_increment not null,
project_id int not null,
material_name varchar(128) not null,
num_required int,
cost decimal(7,2),
primary key (material_id),
foreign key (project_id) references project (project_id) on delete cascade

);

CREATE TABLE step (
 step_id INT AUTO_INCREMENT NOT NULL,
 project_id INT NOT NULL,
 step_text TEXT NOT NULL,
 step_order INT NOT NULL,
 PRIMARY KEY (step_id),
 FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE category (
 category_id INT AUTO_INCREMENT NOT NULL,
 category_name VARCHAR(128) NOT NULL,
 PRIMARY KEY (category_id)
);

create table project_category (
project_id int not null,
category_id int not null,
foreign key (project_id) references project (project_id) on delete cascade,
foreign key (category_id) references category (category_id) on delete cascade,
unique key (project_id, category_id)

);


























