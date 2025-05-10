from pyspark.sql import SparkSession


if __name__ == "__main__":
    spark = SparkSession.builder \
        .appName("Read Parquet File") \
        .getOrCreate()

    # 读取Parquet文件
    df = spark.read.parquet("/Users/zhuxinyuan/Desktop/spark/colzip_example/people.parquet")

    # 打印数据结构
    print("Parquet文件结构:")
    df.printSchema()

    # 展示数据内容
    print("Parquet文件内容:")
    df.show(truncate=False)

    spark.stop()