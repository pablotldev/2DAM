use rand::random_range;

fn main() {
    let x: i32 = random_range(0..=10);
    println!("Número aleatorio: {}", x)
}