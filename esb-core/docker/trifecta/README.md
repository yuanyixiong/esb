# docker-trifecta

Docker container for [trifecta](https://github.com/ldaniels528/trifecta) v0.21.3. Supports Kafka 0.10
Start the container with the following command:

```sh
docker run -d --name trifecta -p 9000:9000 -e ZK_HOST=localhost:2181 janschultecom/docker-trifecta
```
where ZK_HOST is the url to your Zookeeper instance (e.g. localhost:2181).
