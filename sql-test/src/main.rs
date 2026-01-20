use anyhow::{Error, Ok};

mod init;
mod queries;
mod tests;

#[tokio::main]
async fn main() -> Result<(), Error>{
    let mut con = init::connect().await?;
    init::build(&mut con).await?;
    println!("Hello, world!");

    Ok(())
}