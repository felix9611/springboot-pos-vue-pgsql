<h1>POS System</h1>
<p><strong>Spring Boot & Vue.js | Cloud-based POS System</strong></p>
<p>NCPL（Non-Commercial Public License）</p>

<h2>🌟 Project Overview</h2>
<p>This is a full-stack <strong>POS (Point of Sale) System</strong> built with <strong>Spring Boot</strong> and <strong>Vue.js</strong>. It is designed for businesses to manage purchase orders, transactions, inventory, and product records.</p>
<ul>
  <li>Manage purchase orders and transaction history</li>
  <li>Track inventory and product records</li>
  <li>Generate real-time reports and export data</li>
</ul>
<p>The system is <strong>cloud-based</strong>, reducing IT infrastructure costs and offering ease of use with no installation required.</p>

<p>🔗 <strong><a href="https://felix-pos.web.app/login" target="_blank">Online Demo</a></strong></p>
<p><strong>Username:</strong> <code>Demo</code><br>
<strong>Password:</strong> <code>888888</code></p>
<p><em>Note: Backend & Database hosted on Koyeb, Frontend on Firebase. Initial data load may take a few seconds. If cannot load the verification code, please click the "Reset" button.</em></p>

<h2>📸 Example Images</h2>
<!-- Add example images here if available -->
<img src="https://github.com/felix9611/springboot-pos-vue/blob/main/image/pos-1.png" alt="Example Image 1">
<img src="https://github.com/felix9611/springboot-pos-vue/blob/main/image/pos-2.png" alt="Example Image 2">
<img src="https://github.com/felix9611/springboot-pos-vue/blob/main/image/pos-3.png" alt="Example Image 3">
<img src="https://github.com/felix9611/springboot-pos-vue/blob/main/image/pos-4.png" alt="Example Image 4">

<h2>🚀 Key Features</h2>
<ul>
  <li><strong>Comprehensive Purchase Order & Transaction Management</strong></li>
  <li><strong>Real-Time Inventory Tracking</strong></li>
  <li><strong>Member System Integration</strong></li>
  <li><strong>Secure Authentication & Authorization:</strong> Utilizes <strong>JWT tokens</strong> for data protection.</li>
  <li><strong>Reporting & Exporting:</strong> Showing chart graphs and generate reports in PDF and Excel formats.</li>
</ul>

<h2>⚙️ Tech Stack</h2>
<h3>Frontend:</h3>
<ul>
  <li>Vue 2.6.10</li>
  <li>Node.js 16.20</li>
  <li>Typescript</li>
  <li>Tailwind CSS</li>
  <li>Chart.js 2.9.3</li>
  <li>xlsx, jspdf for reporting and data export</li>
</ul>
<h3>Backend:</h3>
<ul>
  <li>Spring Boot 2.4.13</li>
  <li>MyBatis 3.4.1 for database interaction</li>
  <li>PostgreSQL v17</li>
  <li>Spring-Doc OpenAPI UI 1.6.15 for API documentation</li>
</ul>

<h2>📋 How to Run the Project</h2>

<h3>Backend</h3>
<pre><code>// Go to the backend's file directory
cd backend-java

// Install dependencies using maven
mvn install
// or
mvn clean install -X

// Run the backend
mvn spring-boot:run

// Packing the backend
mvn package

// API Documentation URL
http://localhost:8570/swagger-ui.html
</code></pre>

<h3>Frontend</h3>
<pre><code>// Go to the frontend's file directory
cd frontend-ts-class

// Install dependencies (npm)
npm run install

// Or install dependencies (yarn)
yarn install

// Run the frontend
npm run start
// or
yarn start

// Build the frontend
npm run build
</code></pre>

<h2>🌐 Deployment</h2>
<ul>
  <li><strong>Backend:</strong> Koyeb (Spring Boot)</li>
  <li><strong>Frontend:</strong> Firebase Hosting</li>
</ul>

<h2>📈 Business Impact</h2>
<ul>
  <li><strong>Improved Business Operations</strong> with real-time inventory tracking and reporting.</li>
  <li><strong>Enhanced Data Security</strong> through JWT-based user authentication.</li>
  <li><strong>Reduced Costs</strong> by eliminating the need for on-premise installations.</li>
</ul>
