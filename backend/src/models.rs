use chrono::{NaiveDateTime};
use diesel::prelude::*;
use crate::schema::user;

#[derive(Queryable)]
pub struct User {
    pub id: i32,
    pub name: String,
    pub created_at: NaiveDateTime,
    pub last_login: NaiveDateTime,
    pub is_admin: bool
}

/// `password` should be the plain password. It will be hashed before it's written to db.
#[derive(Insertable)]
#[diesel(table_name = user)]
pub struct NewUser<'a> {
    pub name: &'a str,
    pub password: &'a str,
    pub is_admin: &'a bool
}