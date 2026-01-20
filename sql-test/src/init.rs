use anyhow::Error;
use sqlx::{Connection, Executor, SqliteConnection};

pub async fn connect() -> Result<SqliteConnection, Error>{
    Ok(SqliteConnection::connect("sqlite::memory:").await?)
}



const INIT_QUERY: &str = "
CREATE TABLE IF NOT EXISTS CUSTOMERS (
    id INTEGER PRIMARY KEY,
    username TEXT NOT NULL,
    streak INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS SELLERS (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS BUNDLE_POSTINGS (
    id INTEGER PRIMARY KEY,
    seller_id INTEGER,
    FOREIGN KEY (seller_id) REFERENCES SELLERS(id)
);
";


pub async fn build(con: &mut SqliteConnection) -> Result<(), Error>{
    con.execute(INIT_QUERY).await?;
    Ok(())
}
