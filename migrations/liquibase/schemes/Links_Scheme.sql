create table if not exists Links
(
    id bigint generated always as identity,
    url text not null,

    updated_at timestamp with time zone not null,
    created_at timestamp with time zone not null,
    last_check timestamp with time zone not null,

    primary key (id),
    unique (url)
)
