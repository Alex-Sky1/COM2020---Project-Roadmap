use anyhow::Error;
use futures_util::TryStreamExt;
use sqlx::{Row, SqliteConnection};




const GET_BUNDLES_FROM_SELLER: &str = "SELECT * FROM BUNDLE_POSTINGS WHERE seller_id = ? ORDER BY id";
const GET_RESERVATIONS_FROM_CUSTOMER: &str = "SELECT * FROM RESERVATIONS WHERE customer_id = ?";
const GET_RESERVED_BUNDLES_FROM_CUSTOMER: &str = "SELECT * FROM BUNDLES WHERE id = (SELECT bundle_id FROM RESERVATIONS WHERE customer_id = ?)";



pub async fn get_seller_bundle_ids(con: &mut SqliteConnection, id: i64) -> Result<Vec<i64>, Error>{
    let mut rows = sqlx::query(GET_BUNDLES_FROM_SELLER)
        .bind(id)
        .fetch(con);

    let mut bundle_ids = Vec::new();
    while let Some(row) = rows.try_next().await? {
        let bundle_id: i64 = row.get("id");
        bundle_ids.push(bundle_id);
    }

    Ok(bundle_ids)
}

pub async fn get_customer_reserved_bundle_ids(con: &mut SqliteConnection, id: i64) -> Result<Vec<i64>, Error> {
    let mut rows = sqlx::query(GET_RESERVATIONS_FROM_CUSTOMER)
        .bind(id)
        .fetch(con);

    let mut bundle_ids = Vec::new();
    while let Some(row) = rows.try_next().await? {
        let bundle_id: i64 = row.get("bundle_id");
        bundle_ids.push(bundle_id);
    }

    Ok(bundle_ids)
}

