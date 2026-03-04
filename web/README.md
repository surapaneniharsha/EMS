# EMS Frontend - Employee Management System

Production-ready React frontend with Firebase Authentication, connecting to the Spring Boot backend at `http://localhost:8080/ems`.

## File Structure

```
frontend/
├── index.html
├── package.json
├── vite.config.js
├── .env.example
├── README.md
└── src/
    ├── main.jsx
    ├── components/
    │   ├── Layout.jsx
    │   └── ProtectedRoute.jsx
    ├── context/
    │   └── AuthContext.jsx
    ├── firebase/
    │   └── firebase.js
    ├── pages/
    │   ├── Login.jsx
    │   ├── Register.jsx
    │   ├── Dashboard.jsx
    │   ├── Employees.jsx
    │   └── Departments.jsx
    ├── routes/
    │   └── AppRoutes.jsx
    ├── services/
    │   └── api.js
    └── styles/
        └── index.css
```

## Prerequisites

- Node.js 18+ and npm
- Firebase project with Authentication enabled (Email/Password, Google, GitHub)
- Spring Boot backend running on `http://localhost:8080`

## Step-by-Step Setup

### 1. Install Dependencies

```bash
cd frontend
npm install
```

### 2. Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project (or use existing)
3. Enable **Authentication** → **Sign-in method**:
   - **Email/Password** (enable both)
   - **Google** (enable, add support email)
   - **GitHub** (enable, add OAuth Client ID and Secret from [GitHub Developer Settings](https://github.com/settings/developers))
4. Go to **Project Settings** (gear icon) → **General** → **Your apps**
5. Add a **Web app** (</> icon)
6. Copy the `firebaseConfig` object values

### 3. Environment Configuration

Create a `.env` file in the `frontend` directory:

```bash
cp .env.example .env
```

Edit `.env` and add your Firebase config:

```
VITE_FIREBASE_API_KEY=your_api_key
VITE_FIREBASE_AUTH_DOMAIN=your_project.firebaseapp.com
VITE_FIREBASE_PROJECT_ID=your_project_id
VITE_FIREBASE_STORAGE_BUCKET=your_project.appspot.com
VITE_FIREBASE_MESSAGING_SENDER_ID=your_sender_id
VITE_FIREBASE_APP_ID=your_app_id
```

For production or custom backend URL:

```
VITE_API_URL=http://localhost:8080/ems
```

### 4. Run the Frontend

```bash
npm run dev
```

The app will be available at **http://localhost:3000**

### 5. Build for Production

```bash
npm run build
```

Output is in `dist/`. Serve with any static file server, or:

```bash
npm run preview
```

## Features

- **Firebase Auth**: Email/Password, Google, and GitHub login and registration
- **Token Storage**: Uses `sessionStorage` (cleared when tab closes) instead of `localStorage` for better security
- **Protected Routes**: Dashboard, Employees, Departments require authentication
- **API Integration**: Axios with automatic `Authorization: Bearer <token>` header
- **Token Refresh**: Automatic retry on 401 with refreshed token

## Backend Configuration

1. **CORS**: If the frontend and backend run on different origins (e.g. frontend on port 3000, backend on 8080), add CORS configuration to Spring Boot:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ems/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
```

2. **Firebase JWT**: The backend should validate Firebase JWT tokens in the `Authorization` header. Add Spring Security with Firebase token validation for production.

3. **Empty lists**: The backend DAOs currently throw when `getAll` returns empty. For better UX, consider returning `[]` instead.

## API Endpoints Used

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /ems/getEmployees | List all employees |
| GET | /ems/getEmployee/{id} | Get employee by ID |
| POST | /ems/createEmployee | Create employee |
| PUT | /ems/updateEmployee/{id} | Update employee |
| DELETE | /ems/deleteEmployee/{id} | Delete employee |
| GET | /ems/getDepartments | List all departments |
| GET | /ems/getDepartment/{id} | Get department by ID |
| POST | /ems/createDepartment | Create department |
| PUT | /ems/updateDepartment/{id} | Update department |
| DELETE | /ems/deleteDepartment/{id} | Delete department |
