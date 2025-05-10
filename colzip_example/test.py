from pyspark.sql import SparkSession


if __name__ == "__main__":
    spark = SparkSession.builder \
        .appName("Create Parquet File") \
        .getOrCreate()

    spark.conf.set("spark.hadoop.parquet.colzip.enable", "true")
    spark.conf.set("spark.hadoop.parquet.colzip.column.list", "col0")

    data = [
        ("Alice", "Alice", "Alice"),
        ("Bob", "Bob", "Bob"),
        ("Charlie", "Charlie", "Charlie"),
        ("David", "David", "David"),
        ("Eve", "Eve", "Eve")
    ]
    columns = ["col0", "col1", "col2"]

    df = spark.createDataFrame(data, schema=columns)
    df.coalesce(1).write.mode('overwrite').parquet("/Users/zhuxinyuan/Desktop/spark/colzip_example/people.parquet")

    spark.stop()