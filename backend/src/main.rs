pub mod models;
pub mod schema;

use std::{env, process::exit};

use diesel::prelude::*;
use diesel::MysqlConnection;
use dotenvy::dotenv;
use log::error;
use log::info;

use crate::models::NewUser;

use self::schema::user;

fn main() {
    env_logger::init();

    let mut conn = establish_connection();

    let new_user = NewUser { name: "Mondei1", password: "123", is_admin: &true };

    diesel::insert_into(user::table)
        .values(&new_user)
        .get_result(conn);
    info!("Database connection has been established.");
}

pub fn establish_connection() -> MysqlConnection {
    dotenv().ok();

    let database_url = match env::var("DATABASE_URL") {
        Ok(url) => url,
        Err(_) => {
            error!("Environment variable DATABASE_URL needs to be defined. For example: `DATABASE_URL=mysql://[USER]:[PASSWORRD]@[HOST]/[DB]`");
            exit(1);
        }
    };

    match MysqlConnection::establish(&database_url) {
        Ok(conn) => conn,
        Err(err) => {
            error!("Database connection failed: {}", err);
            exit(1);
        }
    }
}