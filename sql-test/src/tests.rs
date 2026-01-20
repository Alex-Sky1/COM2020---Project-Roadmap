use sqlx::{Executor, SqliteConnection};
use anyhow::Error;

const TEST_DATA_QUERY: &str = "
    INSERT INTO CUSTOMERS (username, streak) VALUES (\"Jimby\", 0);
    INSERT INTO CUSTOMERS (username, streak) VALUES (\"Pimby\", 0);
    INSERT INTO CUSTOMERS (username, streak) VALUES (\"Kimby\", 0);
    INSERT INTO CUSTOMERS (username, streak) VALUES (\"Mimby\", 0);
    INSERT INTO CUSTOMERS (username, streak) VALUES (\"Timby\", 0);

    INSERT INTO SELLERS (name) VALUES (\"The Pan Hindle\");
    INSERT INTO SELLERS (name) VALUES (\"Jonesing for a Drink\");
    INSERT INTO SELLERS (name) VALUES (\"The Frying Dan\");
    INSERT INTO SELLERS (name) VALUES (\"Out of the Frying Dan into the Fire\");
    INSERT INTO SELLERS (name) VALUES (\"Georgina's Meeting House\");

    INSERT INTO BUNDLE_POSTINGS (seller_id) VALUES (1);
    INSERT INTO BUNDLE_POSTINGS (seller_id) VALUES (2);
    INSERT INTO BUNDLE_POSTINGS (seller_id) VALUES (3);
    INSERT INTO BUNDLE_POSTINGS (seller_id) VALUES (4);
    INSERT INTO BUNDLE_POSTINGS (seller_id) VALUES (1);

    INSERT INTO RESERVATIONS (bundle_id, customer_id, timestamp, claim_code) VALUES (1, 1, \"2 AM\", 1234);
    INSERT INTO RESERVATIONS (bundle_id, customer_id, timestamp, claim_code) VALUES (2, 5, \"3 AM\", 7548);
    INSERT INTO RESERVATIONS (bundle_id, customer_id, timestamp, claim_code) VALUES (3, 3, \"4 PM\", 7543);
    INSERT INTO RESERVATIONS (bundle_id, customer_id, timestamp, claim_code) VALUES (4, 2, \"5 PM\", 6765);
";
async fn fill_with_test_data(con: &mut SqliteConnection) -> Result<(), Error> {
    con.execute(TEST_DATA_QUERY).await?;
    Ok(())
}


#[cfg(test)]
mod database_tests {
    use crate::{init, queries::{get_customer_reserved_bundle_ids, get_seller_bundle_ids}, tests::fill_with_test_data};
    use anyhow::Error;

    
    #[tokio::test]
    async fn test_get_seller_bundles() -> Result<(), Error> {
        let mut con = init::connect().await?;
        init::build(&mut con).await?;
        fill_with_test_data(&mut con).await?;
        let ids = get_seller_bundle_ids(&mut con, 1).await?;
        assert_eq!(ids, vec![1, 5]);

        Ok(())
    }

    #[tokio::test]
    async fn test_get_customer_reservations() -> Result<(), Error> {
        let mut con = init::connect().await?;
        init::build(&mut con).await?;
        fill_with_test_data(&mut con).await?;

        let ids = get_customer_reserved_bundle_ids(&mut con, 1).await?;
        assert_eq!(ids, vec![1]);

        let ids = get_customer_reserved_bundle_ids(&mut con, 3).await?;
        assert_eq!(ids, vec![3]);

        Ok(())
    }
}