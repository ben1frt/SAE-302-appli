influx.query('SELECT * FROM Table')
  .then(rows => {
    console.log(rows);
  })
  .catch(error => {
    console.log(error);
  });
