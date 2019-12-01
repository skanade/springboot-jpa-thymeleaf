# springboot-jpa-thymeleaf
SpringBoot + JPA + Thymeleaf sample

1) Create the following table in your MySQL database

```sql
create table people (
  id INT NOT NULL AUTO_INCREMENT,
  first VARCHAR(30) NOT NULL,
  last VARCHAR(30) NOT NULL,
  PRIMARY KEY (id)
);
```

2) Set the following System properties for your MySQL database 

```
-DMYSQL_DB=
-DMYSQL_USER=
-DMYSQL_PASSWORD=
```

And if you want to use a different port than 3306

```
-DMYSQL_PORT=  
```

3) Run the class: com.example.shun.app.MainApplication from your IDE

OR

2) Export the following environment variables for your MySQL DB

```
export MYSQL_DB=
export MYSQL_USER=
export MYSQL_PASSWORD=
```

And if you want to use a different port than 3306

```
export MYSQL_PORT=
```

3) Run from Maven

```
mvn test
```

( currently pom.xml is setup to only run test classes with names including *FastTests* )
