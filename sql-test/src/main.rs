use anyhow::{Error, Ok};

mod init;

#[tokio::main]
async fn main() -> Result<(), Error>{
    let mut con = init::connect().await?;
    init::build(&mut con).await?;
    println!("Hello, world!");

    Ok(())
}