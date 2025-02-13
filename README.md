# POS SYSTEM BY Springboot & VUE

Springboot X VUE For PostgreSQL
------------

<div>
・Inclubes frontend and backend for POS System<br />
・Management purchase orders record ,transaction history, inventory and product records in POS at bussiness<br />
・Cloud-based web systems offer ease of use with no installation required, significantly reducing costs.<br />
・Use JWT tokens for user authentication and authorization, ensuring security and protection of fixed assets data.<br />
・Provide reporting, exporting, monitoring, and tracking capabilities for the status of purchase orders and inventory.<br />
</div>

Springboot X VUE<br/>
------------
Point of sale with member system<br/>

Language:<br />
------------
・Java8 (Some dependencies only run on Java8)<br />
・Node.js 16.20 (Frontend).<br />
・Vue2<br />
・Tailwind<br />
</div><br />

Core:<br />
------------
・Spring boot 2.4.13<br />
・Mybatis 3.4.1<br />
・Vue 2.6.10<br />
・moment 2.29.1<br />
・PostgreSQL v17<br />
・Spring-Doc Open API  UI 1.6.15<br />
<br />
Component:<br />
------------
・ChartJs 2.9.3<br />
・jspdf 2.5.1<br />
・xlsx ^0.18.2<br />
・xlsx-style ^0.8.13<br />
<br />

Example Image<br />
------------
<img src="/image/pos-1.png"><br />
<img src="/image/pos-2.png"><br />
<img src="/image/pos-3.png"><br />
<img src="/image/pos-4.png"><br />

Online Demo<br />
------------
https://felix-pos.web.app/login<br />
Username: Demo<br />
Password: 888888<br />

Backend & Database deployed on koyeb<br />
Frontend deployed on firebase<br />
Maybe datas will slow to load, please wait it to load thanks<br />

How to run this project<br />
------------
Backend using port 8570<br />
Frontend using port 8080<br />

Backend<br />

~~~
// Go to the backend's file directory
cd java-backend

// Install dependencies using maven
mvn install
//or
mvn clean install -X

// Run the backend
mvn spring-boot:run

// Packing the backend
mvn package

// API Doc URL
http://localhost:8570/swagger-ui.html
~~~
<br />

Frontend<br />

~~~
// Go to the backend's file directory
cd frontend-ts-class

// Install dependencies if using npm
npm run install

// Install dependencies if using yarn
yarn install

// Run the frontend
npm run start
yarn start

// Build the frontend
npm run build
~~~
<br />
