create table if not exists player (
  player_id           varchar(100) primary key,
  playfab_player_id   varchar(100),
  power               int not null default 0,
  username            varchar(100) unique,
  city                varchar(100),
  country             varchar(100),
  level               int not null default 1,
  experience          numeric(12,2) not null default 0,
  daily_login_streak  int not null default 0,
  last_login          timestamp not null default now(),
  daily_reward_claimed boolean not null default false,
  pet_slots_unlocked  int not null default 1
);

create table if not exists player_unlocked_pet_skin (
  player_id varchar(100) not null references player(player_id) on delete cascade,
  skin_id   int not null,
  pet_type  varchar(50) not null,
  primary key (player_id, skin_id, pet_type)
);

create table if not exists player_assigned_pet_slot (
  player_id    varchar(100) not null references player(player_id) on delete cascade,
  slot_index   int not null,
  pet_instance_id varchar(100),
  primary key (player_id, slot_index)
);
