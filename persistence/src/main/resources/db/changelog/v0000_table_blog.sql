--liquibase formatted sql
--changeset gaile:v0000_table_blog

create table blog (
    id uuid not null,
    blog_headline character varying(2000),
    blog_article int8,
    blog_image varbit,
    blog_date timestamp

);


