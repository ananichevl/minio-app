# Getting Started 

There are two modules:
### min-io-client
To run client go to ./min-io-client folder and run sequentially:\
`npm i`\
`npm run start`
### min-io-server
Go to ./min-io-server\
To run minio server run docker command:\
`docker run -p 9000:9000 -p 9001:9001 --name minio1 -v .\minio:/data -e "MINIO_ROOT_USER=ROOTUSER" -e "MINIO_ROOT_PASSWORD=CHANGEME123" quay.io/minio/minio server /data --console-address ":9001"`\
To run be application run file\
min-io-server/src/main/java/org/ananichev/minioserver/MinIoServerApplication.java


