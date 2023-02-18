const influx = new Influx.InfluxDB({
  host: 'localhost',
  port: 8086,
  database: 'mydatabase',
  username: 'myuser',
  password: 'mypassword'
});
