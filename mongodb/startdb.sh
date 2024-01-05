docker-compose up -d

# Chờ cho các container được khởi động
sleep 10

# Lấy địa chỉ IP của các container
IP_ONE=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mongo.one.db)
IP_TWO=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mongo.two.db)
IP_THREE=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mongo.three.db)

# Khởi tạo Replica Set với địa chỉ IP
docker exec -it mongo.one.db mongosh --eval "rs.initiate({_id:'dbrs', members: [{_id:0, host: '172.168.200.202:20000'},{_id:1, host: '172.168.200.202:20001'},{_id:2, host: '172.168.200.202:20002'}]})"

docker exec -it mongo.one.db mongosh --eval "rs.status()"
