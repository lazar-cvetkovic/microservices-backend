create table if not exists node (
  node_id          varchar(100) primary key,
  player_id        varchar(100),
  score            numeric(12,2) not null default 0,
  country          varchar(100),
  city             varchar(100),
  time_when_updated timestamp not null default now()
);

-- optional index
create index if not exists idx_node_player on node(player_id);
