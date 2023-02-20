// @generated automatically by Diesel CLI.

diesel::table! {
    user (id) {
        id -> Integer,
        name -> Varchar,
        password -> Varchar,
        created_at -> Datetime,
        last_login -> Datetime,
        is_admin -> Nullable<Bool>,
    }
}
