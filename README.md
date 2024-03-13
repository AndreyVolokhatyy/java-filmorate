# java-filmorate
## Template repository for Filmorate project.<br/>
### Schema Data Base for project.<br/>
![alt text](Schema.png)
### Description tables:
#### user:
Таблица пользователей.</br>
id - Уникальный ключ для идентификации записи.</br>
login - Логин пользователя.</br>
name - Имя пользователя.</br>
email - Имейл пользователя.</br>
birthday - Дата рождения пользователя.</br>
created_at - Время создания записи.</br>
is_active - Признак активности записи (Если запись не активна, то значит удалена).</br>
#### friend:
Таблица заявок на дружбу.</br>
id - Уникальный ключ для идентификации записи.</br>
following_user_id - Пользователь отправивший запрос дружбы.</br>
followed_user_id - Пользователь которому отправимли запрос дружбы.</br>
is_accept - Признак подтвержденного запроса дружбы.</br>
created_at - Время создания записи.</br>
is_active - Признак активности записи (Если запись не активна, то значит удалена).</br>
#### film:
Таблица фильмов.</br>
id - Уникальный ключ для идентификации записи.</br>
name - Название фильма.</br>
description Описание фильма.</br>
release_date - Дата релиза фильма.</br>
duration - Продолжительность фильма.</br>
rate_mpa_id - ИД рейтинга Motion Picture Association.</br>
created_at - Время создания записи.</br>
is_active - Признак активности записи (Если запись не активна, то значит удалена).</br>
#### like:
Таблица лайков фильму.</br>
id - Уникальный ключ для идентификации записи.</br>
film_id - ИД фильма.</br>
user_id - ИД пользователя.</br>
created_at - Время создания записи.</br>
is_active - Признак активности записи (Если запись не активна, то значит удалена).</br>
#### genre:
Таблица видов жанров фильмов.</br>
id - Уникальный ключ для идентификации записи.</br>
name - Название жанра.</br>
created_at - Время создания записи.</br>
is_active - Признак активности записи (Если запись не активна, то значит удалена).</br>
#### film_genre:
Таблица жанров фильмов.</br>
id - Уникальный ключ для идентификации записи.</br>
film_id - ИД фильма.</br>
genre_id - ИД жанра.</br>
created_at - Время создания записи.</br>
is_active - Признак активности записи (Если запись не активна, то значит удалена).</br>
#### rate_mpa:
Таблица рейтинга Motion Picture Association.</br>
id - Уникальный ключ для идентификации записи.</br>
name - Название рейтинга Motion Picture Association.</br>
created_at - Время создания записи.</br>
is_active - Признак активности записи (Если запись не активна, то значит удалена).</br>
#### Examples:<br/><br/>
##### get user:<br/>
```sql
    select *
    from user
    where id = ${id} and is_active
```
##### get all users:<br/>
```sql
    select *
    from user
    where is_active
```
##### get friends user:<br/>
```sql
    select u.*
    from user u
    left join friend f on f.followed_id = u.id and f.is_active
    where u.is_active and f.following_id = ${id}
```
##### get film:<br/>
```sql
    select f.*, r.name, g.name
    from film f
    left join rate_mpa r on r.id = f.rate_mpa_id and r.is_active
    left join (
        select fg.film_id, string_agg(g.name, ' ,') name
        from film_genre fg 
        join genre g on g.id = fg.genre_id and g.is_active
        where fg.is_active and fg.film_id = ${id}
        group by fg.film_id
        ) g on g.film_id = f.id
    where f.id = ${id} and f.is_active
```
##### get all films:<br/>
```sql
    select f.*, r.name, g.name
    from film f
    left join rate_mpa r on r.id = f.rate_mpa_id and r.is_active
    left join (
        select fg.film_id, string_agg(g.name, ' ,') name
        from film_genre fg 
        join genre g on g.id = fg.genre_id and g.is_active
        where fg.is_active
        group by fg.film_id
        ) g on g.film_id = f.id
    where f.is_active
```
##### get film sorted rate:<br/>
```sql
    select f.*, r.name, g.name, l.rate
    from film f
    left join rate_mpa r on r.id = f.rate_mpa_id and r.is_active
    left join (
        select fg.film_id, string_agg(g.name, ' ,') name
        from film_genre fg 
        join genre g on g.id = fg.genre_id and g.is_active
        where fg.is_active
        group by fg.film_id
        ) g on g.film_id = f.id
    left join (
        select count(distinct l.user_id) rate, l.film_id
        from like l        
        where l.is_active
        group by l.film_id
        ) l on l.film_id = f.id
    where f.is_active
    order by l.rate desc
```