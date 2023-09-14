# common
MinIO 使用指南
MinIO 是一个高性能的对象存储服务器，适用于大数据和容器等用途。它与 Amazon S3 兼容，这意味着您可以使用 Minio 替换 S3 来存储大量非结构化数据。
本项目是一个MinIO 基本操作Module ,简化单机情况下MinIO的操作。

配置 Minio
   在 application.properties 或 application.yml 文件中，您需要提供 Minio 的相关配置：

minio.endpoint=YOUR_MINIO_ENDPOINT
minio.accessKey=YOUR_ACCESS_KEY
minio.secretKey=YOUR_SECRET_KEY
minio.bucketName=YOUR_BUCKET_NAME


