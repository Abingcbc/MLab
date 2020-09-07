docker run -p 9000:9000 \
  -e "MINIO_ACCESS_KEY=mlab" \
  -e "MINIO_SECRET_KEY=87270099" \
  minio/minio server /minio_data